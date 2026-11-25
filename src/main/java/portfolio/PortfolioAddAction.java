package portfolio;

import java.io.InputStream;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import DAO.PortfolioDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import login.AuthWeb;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import tool.Action;

public class PortfolioAddAction extends Action {

    // アップロード先のS3バケット名
    private static final String BUCKET_NAME = "hitova-187872251103-us-east-1-an";
    // S3バケットが存在するAWSリージョン（米国東部・バージニア北部）
    private static final Region REGION = Region.US_EAST_1;
    // アップロードを許可する最大ファイルサイズ（100MB = 100 * 1024 * 1024 バイト）
    private static final long MAX_FILE_SIZE = 100L * 1024 * 1024;

    // 許可するMIMEタイプの一覧（画像3種＋動画3種）
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/png",
            "image/jpeg",
            "image/webp",
            "video/mp4",
            "video/webm",
            "video/quicktime"
    );

    // 許可するファイル拡張子の一覧（すべて小文字で定義）
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            ".png",
            ".jpg",
            ".jpeg",
            ".webp",
            ".mp4",
            ".webm",
            ".mov"
    );

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            // リクエストパラメータの日本語文字化けを防ぐため文字コードをUTF-8に設定
            request.setCharacterEncoding("UTF-8");

            // 現在のセッションを取得し、ログイン中の求職者（候補者）IDを取り出す
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute(AuthWeb.candidate_key);

            // 未ログイン状態の場合はログイン画面へリダイレクトする
            if (userId == null) {
                // コンテキストパスを付与してログイン画面へ遷移
                response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                // FrontController側で二重フォワードしないよう "null" を返す
                return "null";
            }

            // フォームから送信されたテキスト項目を取得
            String workName = request.getParameter("work_name");
            String description = request.getParameter("description");

            // multipart/form-data で送信されたアップロードファイル（Part）を取得
            Part workFile = request.getPart("work_file");

            // 作品名の必須入力チェック（nullまたは空白のみの場合はエラー）
            if (workName == null || workName.trim().isEmpty()) {
                request.setAttribute("workNameError", "作品名を入力してください。");
            }

            // 説明文の必須入力チェック（nullまたは空白のみの場合はエラー）
            if (description == null || description.trim().isEmpty()) {
                request.setAttribute("descriptionError", "説明文を入力してください。");
            }

            // S3のキー生成時にも再利用する拡張子（例: ".png", ".mp4"）を保持する変数
            String extension = "";

            // ファイルが選択されている場合のみサイズ・拡張子・MIMEタイプを検証
            if (workFile != null && workFile.getSize() > 0) {
                // ファイルサイズが上限（100MB）を超えているかチェック
                if (workFile.getSize() > MAX_FILE_SIZE) {
                    request.setAttribute("fileError", "ファイルは100MB以内で選択してください。");
                }

                // ブラウザから送信された元のファイル名を取得
                String submittedFileName = workFile.getSubmittedFileName();
                if (submittedFileName != null) {
                    // 最後のドット位置を探し、拡張子部分を切り出して小文字化（.JPG -> .jpg 対応）
                    int dotIndex = submittedFileName.lastIndexOf(".");
                    if (dotIndex >= 0) {
                        extension = submittedFileName.substring(dotIndex).toLowerCase(Locale.ROOT);
                    }
                }

                // ブラウザ・サーバーが判定したContent-Type（MIMEタイプ）を取得して小文字化
                String contentType = workFile.getContentType();
                String normalizedContentType = (contentType != null)
                        ? contentType.toLowerCase(Locale.ROOT)
                        : "";

                // MIMEタイプと拡張子の両方が許可リストに含まれているかチェック
                if (!ALLOWED_CONTENT_TYPES.contains(normalizedContentType)
                        || !ALLOWED_EXTENSIONS.contains(extension)) {
                    // すでにサイズ超過エラーが入っている場合は上書きしないようガード
                    if (request.getAttribute("fileError") == null) {
                        request.setAttribute(
                                "fileError",
                                "画像（PNG / JPEG / WebP）または動画（MP4 / WebM / MOV）を選択してください。"
                        );
                    }
                }
            }

            // いずれかの入力項目でバリデーションエラーが発生した場合は入力画面へ戻す
            if (request.getAttribute("workNameError") != null
                    || request.getAttribute("descriptionError") != null
                    || request.getAttribute("fileError") != null) {
                return "/portfolio/jsp/portfolioAdd.jsp";
            }

            // S3へのファイルアップロード処理とオブジェクトキー生成
            // DBに保存するS3オブジェクトキー（ファイル未選択時は null のまま登録）
            String objectKey = null;

            if (workFile != null && workFile.getSize() > 0) {
                // 他ユーザーのファイルや同名の再アップロードと衝突しないようUUIDで一意なキー名を作成
                String s3FileName = UUID.randomUUID().toString() + extension;

                // S3Client とアップロード元の InputStream を try-with-resources で確実にクローズ
                try (S3Client s3Client = S3Client.builder()
                        // 操作対象のAWSリージョンを指定
                        .region(REGION)
                        // 環境変数・~/.aws/credentials・EC2/ECSのIAMロール等から認証情報を自動取得
                        .credentialsProvider(DefaultCredentialsProvider.create())
                        .build();
                     InputStream fileContent = workFile.getInputStream()) {

                    // S3へオブジェクトを保存（PutObject）するためのリクエストを構築
                    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                            // 保存先バケット名
                            .bucket(BUCKET_NAME)
                            // S3内のオブジェクトキー（パス・ファイル名）
                            .key(s3FileName)
                            // 閲覧・配信時に正しい種別として扱われるようContent-Typeを設定
                            .contentType(workFile.getContentType())
                            .build();

                    // ストリームとファイルサイズを指定してS3へアップロード実行
                    s3Client.putObject(
                            putObjectRequest,
                            RequestBody.fromInputStream(fileContent, workFile.getSize())
                    );
                }

                // 後から署名付きURLを発行できるよう、完全なURLではなくオブジェクトキーを保存対象にする
                objectKey = s3FileName;
            }

            // --- DAOを使ったデータベースへの登録処理 ---
            PortfolioDAO dao = new PortfolioDAO();
            // ユーザーID・作品名・説明文・S3オブジェクトキーをDBへ登録
            dao.insert(userId, workName, description, objectKey);

            // 登録完了画面へ遷移
            return "/portfolio/jsp/portfoliodone.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "登録処理中にエラーが発生しました。しばらく経ってから再度お試しください。");
            return "/error.jsp";
        }
    }
}