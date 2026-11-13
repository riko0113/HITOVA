package portfolio;

import DAO.PortfolioDAO;
import bean.Portfolio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class PortfolioDeleteAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            HttpSession session = request.getSession();
            
            // ログインチェック
            Long userId = (Long) session.getAttribute("candidate_account_id");
            if (userId == null) {
                // 未ログインの場合の処理
                response.sendRedirect(request.getContextPath() + "");
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
            
            // データベースから該当の作品を削除する
            dao.delete(portfolioId, userId); 
            
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