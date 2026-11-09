

package profile;

import DAO.ProfileDisplayDAO;
import bean.Profile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ProfileDisplayAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        // ログイン中の求職者IDを取得する
        Long candidateAccountId =
                (Long) session.getAttribute("candidate_account_id");

        if (candidateAccountId == null) {
            return "/login/login.jsp";
        }

        // プロフィール情報を取得する
        ProfileDisplayDAO dao = new ProfileDisplayDAO();

        Profile profile =
                dao.search(candidateAccountId);

        // プロフィールが未登録の場合
        if (profile == null) {
            return "/profile/profileRegister.jsp";
        }

        // JSPにプロフィール情報を渡す
        request.setAttribute("profile", profile);

        return "/profile/profileDisplay.jsp";
    }
}
