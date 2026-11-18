
package account;

import DAO.JobSeekerAccountDAO;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AccountEditErrorAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // セッションを取得する
        HttpSession session = request.getSession(false);

        // ログイン状態を確認する
        if (session == null ||
                session.getAttribute("candidate_account_id") == null) {

            // 【要確認・変更】
            // ログイン機能のAction.javaを確認する。
            // ログインしていない場合の遷移先に変更する。
            return "/login.jsp";
        }

        // 求職者アカウントIDを取得する
        // 【要確認・変更】
        // ログイン機能のAction.javaを確認する。
        // session.setAttribute()の属性名と一致させる。
        Long candidateId = Long.valueOf(
                session.getAttribute("candidate_account_id").toString()
        );

        // エラーメッセージを取得する
        String errorMessage =
                (String) session.getAttribute("accountEditError");

        // エラーメッセージを画面に渡す
        request.setAttribute("errorMessage", errorMessage);

        // DAOを生成する
        JobSeekerAccountDAO dao = new JobSeekerAccountDAO();

        // アカウント情報を取得する
        JobSeekerAccount account = dao.search(candidateId);

        // 編集画面にアカウント情報を渡す
        request.setAttribute("account", account);

        // エラー画面に遷移する
        return "/account/accountEditError.jsp";
    }
}
