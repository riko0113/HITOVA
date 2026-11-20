package job;

import java.util.List;

import DAO.JobDAO;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class JobListAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {

        	HttpSession session = request.getSession();
        	
        	Long userId = (Long) session.getAttribute("company_account_id");
            
            //未ログインの場合のチェック
            if (userId == null) {
                // ログイン画面へリダイレクトする処理
            	response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                return "null";
            }
            
            // 求人DAOのインスタンス生成
            JobDAO dao = new JobDAO();
            
            // Listに求人一覧の結果を返す
            List<Job> jobList = dao.searchAll(userId);
            
            request.setAttribute("jobList", jobList);
            
            return ""; 
            
        } catch (Exception e) {
            //サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();
            
            //ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "データの取得中にエラーが発生しました。しばらく経ってから再度お試しください。");
            
            //エラー用の専用画面、または元の安全な画面へフォワードする
            return "/error.jsp"; 
        }
    }
}
