package portfolio;

import java.io.InputStream;
import java.util.UUID;

import software.amazon.awssdk.regions.Region;

import DAO.PortfolioDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import tool.Action;

public class PortfolioAddAction extends Action {

    // S3の設定
    private static final String BUCKET_NAME = "あなたのS3バケット名";
    private static final Region REGION = Region.AP_NORTHEAST_1; // 例：東京リージョン

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            // 文字エンコーディングの設定
            request.setCharacterEncoding("UTF-8");

            // セッションからユーザーIDを取得して未ログインチェック
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("candidate_account_id");
            
            if (userId == null) {
                request.setAttribute("errorMessage", "ログインしてください。");
                return ""; 
            }

            // JSPから入力内容を受け取る
            String workName = request.getParameter("work_name");
            String description = request.getParameter("description");
            
            // JSPからファイルを受け取る
            Part workFile = request.getPart("work_file");

            // 作品名のチェック
            if (workName == null || workName.trim().isEmpty()) {
                request.setAttribute("workNameError", "作品名を入力してください。");
            }
            // 説明文のチェック
            if (description == null || description.trim().isEmpty()) {
                request.setAttribute("descriptionError", "説明文を入力してください。");
            }
            // ファイル容量のチェック
            if (workFile != null && workFile.getSize() > 100 * 1024 * 1024) {
                request.setAttribute("fileError", "ファイルは100MB以内で選択してください。");
            }

            // エラーがある場合、入力画面に戻す
            if (request.getAttribute("workNameError") != null
                    || request.getAttribute("descriptionError") != null
                    || request.getAttribute("fileError") != null) {
                return "/portfolio/jsp/portfolioAdd.jsp";
            }
            
            // --- S3へのファイルアップロード処理とURL生成 ---
            String fileUrl = null; // データベースに保存したり画面に渡すためのURL
            
            if (workFile != null && workFile.getSize() > 0) {
                String submittedFileName = workFile.getSubmittedFileName();
                
                // ファイル名が重複しないようUUIDを付与
                String extension = "";
                int dotIndex = submittedFileName.lastIndexOf(".");
                if (dotIndex >= 0) {
                    extension = submittedFileName.substring(dotIndex);
                }
                String s3FileName = UUID.randomUUID().toString() + extension;

                // AWS S3クライアントを生成してアップロード
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

                    // ストリームからS3へ直接アップロード
                    s3Client.putObject(putObjectRequest,
                            RequestBody.fromInputStream(fileContent, workFile.getSize()));
                }

                // アップロードしたファイルのURLを構築
                fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", 
                        BUCKET_NAME, REGION.id(), s3FileName);
            }
            
            // --- DAOを使ったデータベースへの登録処理 ---
            PortfolioDAO dao = new PortfolioDAO();
            dao.insert(userId, workName, description, fileUrl);
            
			return "/portfolio/jsp/portfoliodone.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "登録処理中にエラーが発生しました。しばらく経ってから再度お試しください。");
            return "/portfolio/jsp/portfolioAdd.jsp";
        }
    }
}