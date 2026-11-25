package portfolio;

import DAO.PortfolioDAO;
import bean.Portfolio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PortfolioDetailAction extends Action {

    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            // リクエストから表示対象のポートフォリオIDを取得
            String portfolioIdStr = request.getParameter("portfolio_id");

            // 1件分のポートフォリオデータを保持する変数
            Portfolio portfolio = null;
            // 閲覧用の署名付きURL（ファイル未登録時は null のまま）
            String presignedUrl = null;
            // 動画ファイルかどうかの判定フラグ
            boolean isVideo = false;

            if (portfolioIdStr != null && !portfolioIdStr.isEmpty()) {
                // 文字列のIDを数値に変換
                Long portfolioId = Long.parseLong(portfolioIdStr);

                // DAO経由でDBから1件のポートフォリオ情報を取得
                PortfolioDAO dao = new PortfolioDAO();
                portfolio = dao.searchDetail(portfolioId);

                // データが存在する場合、DBのオブジェクトキーから署名付きURLを発行
                if (portfolio != null) {
                    String objectKey = portfolio.getFile_url();
                    presignedUrl = S3PresignHelper.createPresignedGetUrl(objectKey);
                    isVideo = S3PresignHelper.isVideoKey(objectKey);
                }
            }

            // JSPへ渡す属性をリクエストスコープに格納
            request.setAttribute("portfolio", portfolio);
            request.setAttribute("presignedUrl", presignedUrl);
            request.setAttribute("isVideo", isVideo);

            // 作品詳細画面のJSPへフォワード
            return "/portfolio/jsp/portfoliodetails.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "画面の表示中にエラーが発生しました。しばらく経ってから再度お試しください。");
            return "/error.jsp";
        }
    }
}