package login;

import DAO.LoginDAO;
import bean.FirebaseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class RegisterAction extends Action {
    // FrontControllerから呼ばれる処理
    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        AuthSupport auth = AuthSupport.get(request);
        HttpSession session = auth.session(request, response);
        synchronized (session) {
            if (!auth.post(request, response, session, true)) {
                return null;
            }
            // フォームから入力内容を取り出す
            String email = AuthWeb.display(AuthWeb.field(request, "email", 254));
            String nickname = AuthWeb.display(AuthWeb.field(request, "nickname", 128));
            String password = AuthWeb.field(request, "password", 128);
            String confirmation = AuthWeb.field(request, "passwordConfirm", 128);
            // 入力内容と同意を確認する
            String error = "";
            if (!AuthWeb.validEmail(email)) {
                error = "メールアドレスを正しく入力してください。";
            } else if (password == null || password.length() < 8 || password.length() > 20) {
                error = "パスワードは8〜20文字で入力してください。";
            } else if (!password.equals(confirmation)) {
                error = "確認用パスワードが一致していません。";
            } else if (!nickname.matches("(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{4,20}")) {
                error = "ニックネームは英字と数字をそれぞれ含む半角英数字4〜20文字で入力してください。";
            } else if (!AuthWeb.checked(request, "agreeTerms") || !AuthWeb.checked(request, "agreePrivacy")) {
                error = "利用規約とプライバシーポリシーへの同意が必要です。";
            }
            if (!error.isEmpty()) {
                AuthSupport.flash(session, error, "");
                AuthWeb.redirect(request, response, "/login/Error.action");
                return null;
            }
            try {
                // DAOでFirebaseに問い合わせ、結果を受け取る
                LoginDAO dao = auth.dao;
                FirebaseUser user = dao.register(email, password);
                // メール確認の結果に合わせて次の画面へ進む
                AuthSupport.Pending pending = auth.beginPending(request, session, user);
                if (user.isEmailVerified()) {
                    auth.finish(request, session, pending);
                    AuthWeb.redirect(request, response, "/login/Complete.action");
                    return null;
                }
                if (auth.sendMail(session, pending)) {
                    AuthWeb.redirect(request, response, "/login/Verify.action");
                } else {
                    AuthWeb.redirect(request, response, "/login/Error.action");
                }
            } catch (FirebaseException exception) {
                AuthSupport.flash(session, AuthWeb.errorMessage(exception), "");
                AuthWeb.redirect(request, response, "/login/Error.action");
            }
            return null;
        }
    }
}
