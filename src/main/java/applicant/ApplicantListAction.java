package applicant;

import java.util.List;

import DAO.ApplicationHistoryDAO;
import bean.ApplicationHistory;
import bean.CompanyAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ApplicantListAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            HttpSession session = request.getSession();
            
            CompanyAccount company = (CompanyAccount) session.getAttribute("company");

            // 企業IDを取り出す
            Long companyAccountId = null;
            if (company != null) {
                companyAccountId = company.getCompany_account_id(); 
            }
            
            // 企業IDがセッションにない場合
            if (companyAccountId == null) {
                request.setAttribute("errorMessage", "セッションが切れました。再度ログインしてください。");
                return ""; 
            }
            
            // 応募履歴DAOのインスタンス生成
            ApplicationHistoryDAO dao = new ApplicationHistoryDAO();
            
            // Listに応募履歴一覧の結果を返す
            List<ApplicationHistory> historyList = dao.searchAll(companyAccountId);
            
            request.setAttribute("historyList", historyList);
            
            return "/applicant/applicant_list.jsp"; 
            
        } catch (Exception e) {
            //サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();
            
            //ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "データの取得中にエラーが発生しました。しばらく経ってから再度お試しください。");
            
            //エラー用の専用画面、または元の安全な画面へフォワードする
            return ""; 
        }
    }
}
