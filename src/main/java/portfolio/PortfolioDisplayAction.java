package portfolio;

import DAO.PortfolioDAO;
import bean.Portfolio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PortfolioDisplayAction extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            // リクエストパラメータから編集対象のポートフォリオIDを取得
            String portfolioIdStr = request.getParameter("portfolio_id");

            // 1件分のポートフォリオデータを格納する変数
            Portfolio portfolio = null;
            // 現在登録されているファイルの閲覧用署名付きURL
            String presignedUrl = null;
            // 登録済みファイルが動画かどうかの判定フラグ
            boolean isVideo = false;

            if (portfolioIdStr != null && !portfolioIdStr.isEmpty()) {
                // ポートフォリオIDをLong型へ変換
                Long portfolioId = Long.parseLong(portfolioIdStr);

                // ポートフォリオDAOのインスタンス生成
                PortfolioDAO dao = new PortfolioDAO();

                // 1件のポートフォリオ情報を取得
                portfolio = dao.searchDetail(portfolioId);

                // データが存在する場合、DBに保存されたオブジェクトキーから署名付きURLを発行
                if (portfolio != null) {
                    String objectKey = portfolio.getFile_url();
                    presignedUrl = S3PresignHelper.createPresignedGetUrl(objectKey);
                    isVideo = S3PresignHelper.isVideoKey(objectKey);
                }
            }

            // リクエストスコープにセット（編集画面JSPでの表示・判定に使用）
            request.setAttribute("portfolio", portfolio);
            request.setAttribute("presignedUrl", presignedUrl);
            request.setAttribute("isVideo", isVideo);

            // 編集画面のJSPパスを返す
            return "/portfolio/jsp/portfolioedit.jsp";

        } catch (Exception e) {
            // サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();

            // ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "画面の表示中にエラーが発生しました。しばらく経ってから再度お試しください。");

            // エラー時の遷移先を返す
            return "/error.jsp";
        }
    }
}