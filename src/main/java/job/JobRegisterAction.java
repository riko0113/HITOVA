package job;

import java.util.List;

import DAO.CompanyDAO;
import DAO.JobDAO;
import bean.CompanyAccount;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class JobRegisterAction extends Action {
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
            	response.sendRedirect(request.getContextPath() + "");
                return "";
            }
            
            // 企業アカウントDAOのインスタンス生成
            CompanyDAO c_dao = new CompanyDAO();
            
            //企業情報を返す
            CompanyAccount companyInfo = c_dao.getCompanyDetails(userId);
            
            request.setAttribute("companyInfo", companyInfo);
            
            // 求人DAOのインスタンス生成
            JobDAO dao = new JobDAO();
            
            // Listにマスタ情報一覧の結果を返す
            List<Job> infoList = dao.getInfo(userId);
            
            request.setAttribute("infoList", infoList);
            
            return "/jobregister/jobregister.jsp"; 
            
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
