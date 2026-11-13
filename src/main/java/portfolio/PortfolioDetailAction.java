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
            String portfolioIdStr = request.getParameter("portfolio_id");

            // 1件分のデータを格納する変数を宣言
            Portfolio portfolio = null;

            if (portfolioIdStr != null && !portfolioIdStr.isEmpty()) {
                Long portfolioId = Long.parseLong(portfolioIdStr);
                
                // ポートフォリオDAOのインスタンス生成
                PortfolioDAO dao = new PortfolioDAO();
                
                // 1件のポートフォリオ情報を取得
                portfolio = dao.searchDetail(portfolioId);
            }
            
            // リクエストスコープにセット
            request.setAttribute("portfolio", portfolio);
        
            // 作品詳細画面のJSPパスを返す
            return "/portfolio/jsp/portfoliodetails.jsp";
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "画面の表示中にエラーが発生しました。しばらく経ってから再度お試しください。");
            return "/error.jsp"; 
        }
    }
}