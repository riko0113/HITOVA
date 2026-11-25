package login;

import java.sql.SQLException;

import DAO.LoginAccountDAO;
import DAO.FLoginDAO;
import bean.FirebaseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// Firebaseに登録し、DBに仮登録を保存して確認メールを送る。
public class RegisterAction extends Action {
    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        AuthSupport auth = AuthSupport.get(request);
        AuthWeb.noStore(response);
        if (!auth.post(request, response)) {
            return "/login/error.jsp";
        }
        HttpSession session = auth.session(request, response);
        synchronized (session) {
            String email = AuthWeb.display(AuthWeb.field(request, "email", 254));
            String nickname = AuthWeb.display(AuthWeb.field(request, "nickname", 128));
            String password = AuthWeb.field(request, "password", 128);
            String confirmation = AuthWeb.field(request, "passwordConfirm", 128);
            if (!AuthWeb.validEmail(email) || password == null || password.length() < 8 || password.length() > 20
                    || !password.equals(confirmation)
                    || !nickname.matches("(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{4,20}")
                    || !AuthWeb.checked(request, "agreeTerms") || !AuthWeb.checked(request, "agreePrivacy")) {
                return AuthWeb.redirectAfterPost(request, response, "/login/error.jsp");
            }
            try {
                LoginAccountDAO accounts = auth.accounts;
                if (accounts.nicknameExists(nickname)) {
                    return AuthWeb.redirectAfterPost(request, response, "/login/error.jsp");
                }
                FLoginDAO dao = auth.dao;
                FirebaseUser user = dao.register(email, password);
                accounts.registerPending(user.getUid(), user.getEmail(), nickname);
                AuthSupport.Pending pending = auth.beginPending(request, session, user);
                if (user.isEmailVerified()) {
                    AuthSupport.Result result = auth.finish(request, session, pending);
                    return AuthWeb.redirectForResult(request, response, result, "/login/login.jsp");
                }
                AuthSupport.Result result = auth.sendMail(session, pending);
                return AuthWeb.redirectForResult(request, response, result, "/login/Verify.action");
            } catch (FirebaseException | SQLException exception) {
                return AuthWeb.redirectAfterPost(request, response, "/login/error.jsp");
            }
        }
    }
}
