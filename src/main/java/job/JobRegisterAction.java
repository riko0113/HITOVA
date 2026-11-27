package job;

import java.util.List;

import DAO.CompanyDAO;
import DAO.JobDAO;
import bean.CompanyAccount;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import tool.Action;

public class JobRegisterAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {

            HttpSession session = request.getSession();
            
            Long userId = (Long) session.getAttribute(AuthWeb.company_key);
            
            //未ログインの場合のチェック
            if (userId == null) {
                // ログイン画面へリダイレクトする処理
                response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                return "";
            }
            
            // セッションに企業情報がなければDBから取得して保存
            CompanyAccount companyInfo = (CompanyAccount) session.getAttribute("companyInfo");
            if (companyInfo == null) {
                CompanyDAO c_dao = new CompanyDAO();
                companyInfo = c_dao.getCompanyAccountById(userId);
                session.setAttribute("companyInfo", companyInfo);
            }
            
            // セッションに選択肢データがなければDBから取得して保存
            @SuppressWarnings("unchecked")
            List<Job> infoList = (List<Job>) session.getAttribute("infoList");
            if (infoList == null) {
                JobDAO dao = new JobDAO();
                infoList = dao.searchAll(userId);
                session.setAttribute("infoList", infoList);
            }
            
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