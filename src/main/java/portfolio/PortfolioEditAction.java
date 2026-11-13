package portfolio;

import java.io.InputStream;
import java.util.UUID;

import software.amazon.awssdk.regions.Region;

import DAO.PortfolioDAO;
import bean.Portfolio; 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import tool.Action;

public class PortfolioEditAction extends Action {

    // S3の設定（登録用と同じ）
    private static final String BUCKET_NAME = "あなたのS3バケット名";
    private static final Region REGION = Region.AP_NORTHEAST_1;

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            // 文字エンコーディングの設定
            request.setCharacterEncoding("UTF-8");

            // ログインチェック
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("candidate_account_id");
            
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + ""); // ログイン画面へ
                return null; 
            }
            
            // フォームから hidden で送られてきた ID と入力値を受け取る
            String idStr = request.getParameter("portfolio_id");
            String workName = request.getParameter("work_name");
            String description = request.getParameter("description");
            Part workFile = request.getPart("work_file");
            
            long portfolioId = Long.parseLong(idStr);
            
            // 既存データを取得（所有者確認や、ファイルが変更されなかった場合のURL維持のため）
            PortfolioDAO dao = new PortfolioDAO();
            Portfolio existingPortfolio = dao.searchDetail(portfolioId);
            
            if (existingPortfolio == null || !existingPortfolio.getCandidate_account_id().equals(userId)) {
                request.setAttribute("errorMessage", "編集権限がありません。");
                return "/error.jsp";
            }

            // バリデーションチェック
            if (workName == null || workName.trim().isEmpty()) {
                request.setAttribute("workNameError", "作品名を入力してください。");
            }
            if (description == null || description.trim().isEmpty()) {
                request.setAttribute("descriptionError", "説明文を入力してください。");
            }
            if (workFile != null && workFile.getSize() > 100 * 1024 * 1024) {
                request.setAttribute("fileError", "ファイルは100MB以内で選択してください。");
            }

            // エラーがある場合、入力画面に戻す
            if (request.getAttribute("workNameError") != null
                    || request.getAttribute("descriptionError") != null
                    || request.getAttribute("fileError") != null) {
                return "/portfolio/jsp/portfolioAdd.jsp";
            }

            // ファイルの処理（新しくファイルが選択された場合のみS3にアップロード）
            String fileUrl = existingPortfolio.getFile_url(); // デフォルトは既存のURLを維持
            
            if (workFile != null && workFile.getSize() > 0) {
            	//getSubmittedFileName() → そのPartオブジェクトが持っているアップロードされたファイルのファイル名を取得するメソッド
                String submittedFileName = workFile.getSubmittedFileName();
                
                String extension = "";
                int dotIndex = submittedFileName.lastIndexOf(".");
                if (dotIndex >= 0) {
                    extension = submittedFileName.substring(dotIndex);
                }
                String s3FileName = UUID.randomUUID().toString() + extension;

             // S3へアップロード
             try (S3Client s3Client = S3Client.builder()
                     // アップロード先のAWSリージョン（地域、例: 東京リージョンなど）を指定してビルダーを準備
                     .region(REGION)
                     // サーバーの認証情報（環境変数やIAMロールなど）をデフォルトのプロバイダから自動取得して設定
                     .credentialsProvider(DefaultCredentialsProvider.create())
                     .build(); // 3. S3Clientのインスタンスを生成
                  // アップロード対象のファイルから、データを読み込むための入力ストリーム（InputStream）を取得
                  InputStream fileContent = workFile.getInputStream()) {

                 // S3にファイルを保存（PutObject）するためのリクエスト情報を組み立てるビルダーを生成
                 PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                         // 保存先のS3バケット名を指定
                         .bucket(BUCKET_NAME)
                         // S3内でのファイルの保存名（キー名、ここではUUIDを使った一意なファイル名）を指定
                         .key(s3FileName)
                         // ファイルのコンテンツタイプ（MIMEタイプ、例: image/jpeg や application/pdf など）を設定
                         .contentType(workFile.getContentType())
                         .build(); // 9. リクエストオブジェクトを完成させる

                 // 実際にS3クライアントを使ってファイルをアップロードする
                 // 第1引数: アップロード設定（バケット名やファイル名など）
                 // 第2引数: 送信データ本体（InputStreamの中身と、ファイルのサイズを指定してメモリ効率よく送信）
                 s3Client.putObject(putObjectRequest,
                         RequestBody.fromInputStream(fileContent, workFile.getSize()));
             }

                // 新しいファイルのURLを生成
                fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", 
                        BUCKET_NAME, REGION.id(), s3FileName);
            }

            // データベースのデータをアップデート
            dao.update(portfolioId, userId, workName, description, fileUrl);

            // 完了画面へ（または一覧へリダイレクト）
            return "/portfolio/jsp/portfoliodone2.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "更新処理中にエラーが発生しました。");
            return "/error.jsp";
        }
    }
}