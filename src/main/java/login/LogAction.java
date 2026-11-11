package login;

import DAO.LoginDAO;
import bean.FirebaseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LogAction extends Action {
    @Override
    public String execute(
        HttpServletRequest request, 
        HttpServletResponse response
    ) throws Exception {
        AuthSupport auth = AuthSupport.get(request);
        HttpSession session = auth.session(request, response);
        synchronized (session) {
            if ("GET".equals(request.getMethod())) {
                if (!auth.available(request, response, session)) {
                    return null;
                }
                return "/login-logaut/login.jsp";
            }
            if (!auth.post(request, response, session, true)) {
                return null;
            }
            // フォームから入力内容を取り出す
            String email = AuthWeb.display(AuthWeb.field(request, "email", 254));
            String password = AuthWeb.field(request, "password", 4096);
            // メールアドレスとパスワードを確認する
            if (!AuthWeb.validEmail(email) || password == null || password.isEmpty()) {
                AuthSupport.flash(session, "メールアドレスとパスワードを入力してください。", "");
                AuthWeb.redirect(request, response, "/login/Error.action");
                return null;
            }
            try {
                // DAOでFirebaseに問い合わせ、結果を受け取る
                LoginDAO dao = auth.dao;
                FirebaseUser user = dao.login(email, password);
                // メール確認の結果に合わせて次の画面へ進む
                AuthSupport.Pending pending = auth.beginPending(request, session, user);
                if (user.isEmailVerified()) {
                    auth.finish(request, session, pending);
                    AuthWeb.redirect(request, response, "/login/Complete.action");
                    return null;
                }

                AuthSupport.flash(session, "", "メール内のリンクを開いてから確認してください。"
                        + "確認メールが見当たらない場合は、再送できます。");
                AuthWeb.redirect(request, response, "/login/Verify.action");
            } catch (FirebaseException exception) {
                AuthSupport.flash(session, AuthWeb.errorMessage(exception), "");
                AuthWeb.redirect(request, response, "/login/Error.action");
            }
            return null;
        }
    }
}
