package portfolio;

import java.util.List;

import DAO.PortfolioDAO;
import bean.Portfolio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class PortfolioAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
        	HttpSession session = request.getSession();
        	
        	Long userId = (Long) session.getAttribute("candidate_account_id");
            
            //未ログインの場合のチェック
            if (userId == null) {
                // ログイン画面へフォワードする処理
                request.setAttribute("errorMessage", "ログインしてください。");
                return "";
            }
            
            // ポートフォリオDAOのインスタンス生成
            PortfolioDAO dao = new PortfolioDAO();
            
            // Listにポートフォリオ一覧の結果を返す
            List<Portfolio> portfolioList = dao.searchList(userId);
            
            request.setAttribute("portfolioList", portfolioList);
        	
            // 作品一覧画面のJSPパスを返す
            return "/portfolio/jsp/portfolio.jsp";
        } catch (Exception e) {
            // サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();
            
            // ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "画面の表示中にエラーが発生しました。しばらく経ってから再度お試しください。");
            
            // エラー時の遷移先を返す（必要に応じてエラー用JSP等を指定）
            return ""; 
        }
    }
}