package portfolio;

import DAO.PortfolioDAO;
import bean.Portfolio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import tool.Action;

public class PortfolioDeleteAction extends Action {

    // アップロード先のS3バケット名
    private static final String BUCKET_NAME = "hitova-187872251103-us-east-1-an";
    // S3バケットが存在するAWSリージョン
    private static final Region REGION = Region.US_EAST_1;

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            HttpSession session = request.getSession();

            // ログインチェック
            Long userId = (Long) session.getAttribute(AuthWeb.candidate_key);
            if (userId == null) {
                // 未ログインの場合の処理
                response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                return null;
            }

            // JSPから送られてきた portfolio_id を受け取る
            String idStr = request.getParameter("portfolio_id");
            if (idStr == null || idStr.isEmpty()) {
                request.setAttribute("errorMessage", "削除する作品が指定されていません。");
                return "/error.jsp";
            }
            long portfolioId = Long.parseLong(idStr);

            // ポートフォリオDAOのインスタンス生成
            PortfolioDAO dao = new PortfolioDAO();

            // 削除する前に「本当にこのユーザーの作品か？」をデータベースから確認する（所有者チェック）
            Portfolio existingPortfolio = dao.searchDetail(portfolioId);
            if (existingPortfolio == null || !existingPortfolio.getCandidate_account_id().equals(userId)) {
                request.setAttribute("errorMessage", "削除権限がありません。");
                return "/error.jsp";
            }

            // S3から削除するために、DBに保存されているオブジェクトキーを控えておく
            String objectKey = existingPortfolio.getFile_url();

            // データベースから該当の作品を削除する
            dao.delete(portfolioId, userId);

            // オブジェクトキーが存在する場合のみS3から削除する
            if (objectKey != null && !objectKey.trim().isEmpty()) {
                try (S3Client s3Client = S3Client.builder()
                        .region(REGION)
                        .credentialsProvider(DefaultCredentialsProvider.create())
                        .build()) {

                    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(objectKey)
                            .build();

                    s3Client.deleteObject(deleteObjectRequest);
                }
            }

            response.sendRedirect(request.getContextPath() + "/portfolio/Portfolio.action");
            return null;

        } catch (Exception e) {
            // サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();

            // ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "削除処理中にエラーが発生しました。しばらく経ってから再度お試しください。");

            // エラー時の遷移先を返す
            return "/error.jsp";
        }
    }
}