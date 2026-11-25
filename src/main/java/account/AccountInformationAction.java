
package account;

import DAO.JobSeekerAccountDAO;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AccountInformationAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // セッション取得
        HttpSession session = request.getSession(false);

        // ログイン確認
        if (session == null || session.getAttribute("candidate_account_id") == null) {
            return "/login.jsp";
        }

        // 求職者ID取得
        Long candidateId = Long.valueOf(session.getAttribute("candidate_account_id").toString());

        // DAO生成
        JobSeekerAccountDAO dao = new JobSeekerAccountDAO();

        // アカウント情報取得
        JobSeekerAccount account = dao.search(candidateId);

        // アカウント情報確認
        if (account == null) {
            request.setAttribute("errorMessage", "アカウント情報を取得できませんでした");
            return "/account/accountInformation.jsp";
        }

        // JSPに情報を渡す
        request.setAttribute("account", account);

        return "/account/accountInformation.jsp";
    }
}
