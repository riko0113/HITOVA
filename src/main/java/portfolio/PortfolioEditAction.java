package portfolio;

import java.io.InputStream;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import DAO.PortfolioDAO;
import bean.Portfolio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import login.AuthWeb;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import tool.Action;

public class PortfolioEditAction extends Action {

    // アップロード先のS3バケット名
    private static final String BUCKET_NAME = "hitova-187872251103-us-east-1-an";
    // S3バケットが存在するAWSリージョン
    private static final Region REGION = Region.US_EAST_1;
    // アップロードを許可する最大ファイルサイズ（100MB）
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
            // リクエストパラメータの文字化けを防ぐためUTF-8に設定
            request.setCharacterEncoding("UTF-8");

            // セッションからログイン中の求職者アカウントIDを取得
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute(AuthWeb.candidate_key);

            // 未ログインの場合はログイン画面へリダイレクト
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                return null;
            }

            // フォームから送信されたID・作品名・説明文を取得
            String idStr = request.getParameter("portfolio_id");
            String workName = request.getParameter("work_name");
            String description = request.getParameter("description");

            // 新しく選択されたファイルを取得（未選択時はnullまたはサイズ0）
            Part workFile = request.getPart("work_file");

            // ポートフォリオIDを数値変換
            long portfolioId = Long.parseLong(idStr);

            // 既存データ取得と所有者チェック
            PortfolioDAO dao = new PortfolioDAO();
            Portfolio existingPortfolio = dao.searchDetail(portfolioId);

            if (existingPortfolio == null
                    || !existingPortfolio.getCandidate_account_id().equals(userId)) {
                request.setAttribute("errorMessage", "編集権限がありません。");
                return "/error.jsp";
            }

            // 作品名の必須チェック
            if (workName == null || workName.trim().isEmpty()) {
                request.setAttribute("workNameError", "作品名を入力してください。");
            }

            // 説明文の必須チェック
            if (description == null || description.trim().isEmpty()) {
                request.setAttribute("descriptionError", "説明文を入力してください。");
            }

            // 新しいファイルが選択されている場合のみサイズ・拡張子・MIMEタイプを検証
            String extension = "";
            if (workFile != null && workFile.getSize() > 0) {
                // 100MB上限チェック
                if (workFile.getSize() > MAX_FILE_SIZE) {
                    request.setAttribute("fileError", "ファイルは100MB以内で選択してください。");
                }

                // ファイル名から拡張子を抽出して小文字化
                String submittedFileName = workFile.getSubmittedFileName();
                if (submittedFileName != null) {
                    int dotIndex = submittedFileName.lastIndexOf(".");
                    if (dotIndex >= 0) {
                        extension = submittedFileName.substring(dotIndex).toLowerCase(Locale.ROOT);
                    }
                }

                // MIMEタイプを小文字化
                String contentType = workFile.getContentType();
                String normalizedContentType = (contentType != null)
                        ? contentType.toLowerCase(Locale.ROOT)
                        : "";

                // 許可された画像・動画形式かチェック
                if (!ALLOWED_CONTENT_TYPES.contains(normalizedContentType)
                        || !ALLOWED_EXTENSIONS.contains(extension)) {
                    if (request.getAttribute("fileError") == null) {
                        request.setAttribute(
                                "fileError",
                                "画像（PNG / JPEG / WebP）または動画（MP4 / WebM / MOV）を選択してください。"
                        );
                    }
                }
            }

            // バリデーションエラーがある場合は画面へ戻す
            if (request.getAttribute("workNameError") != null
                    || request.getAttribute("descriptionError") != null
                    || request.getAttribute("fileError") != null) {
                request.setAttribute("portfolio", existingPortfolio);
                return "/portfolio/jsp/portfolioAdd.jsp";
            }

            // DBに保存されていた古いオブジェクトキーを控えておく
            String oldObjectKey = existingPortfolio.getFile_url();
            // 実際にDBへ保存するオブジェクトキー（デフォルトは古いキーを維持）
            String newObjectKey = oldObjectKey;
            // 新しいファイルへ差し替えたかどうかのフラグ
            boolean isFileReplaced = false;

            // 新しいファイルが選択されている場合のみS3へアップロード
            if (workFile != null && workFile.getSize() > 0) {
                String s3FileName = UUID.randomUUID().toString() + extension;

                try (S3Client s3Client = S3Client.builder()
                        .region(REGION)
                        .credentialsProvider(DefaultCredentialsProvider.create())
                        .build();
                     InputStream fileContent = workFile.getInputStream()) {

                    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(s3FileName)
                            .contentType(workFile.getContentType())
                            .build();

                    s3Client.putObject(
                            putObjectRequest,
                            RequestBody.fromInputStream(fileContent, workFile.getSize())
                    );
                }

                newObjectKey = s3FileName;
                isFileReplaced = true;
            }

            // DBのデータを新しい情報（または新しいオブジェクトキー）で更新
            dao.update(portfolioId, userId, workName, description, newObjectKey);

            // 新しいファイルに差し替え済みで、かつ古いオブジェクトキーが存在する場合はS3から削除
            if (isFileReplaced
                    && oldObjectKey != null
                    && !oldObjectKey.trim().isEmpty()
                    && !oldObjectKey.equals(newObjectKey)) {

                try (S3Client s3Client = S3Client.builder()
                        .region(REGION)
                        .credentialsProvider(DefaultCredentialsProvider.create())
                        .build()) {

                    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(oldObjectKey)
                            .build();

                    s3Client.deleteObject(deleteObjectRequest);
                }
            }

            // 更新完了画面へ遷移
            return "/portfolio/jsp/portfoliodone2.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "更新処理中にエラーが発生しました。");
            return "/error.jsp";
        }
    }
}