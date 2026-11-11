package login;

import DAO.LoginDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ResendAction extends Action {
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
            AuthSupport.Pending pending = auth.pending(session);
            if (pending == null) {
                auth.expired(request, response, session);
                return null;
            }
            long elapsed = auth.now() - pending.last_mail_attempt;
            if (pending.last_mail_attempt >= 0 && (elapsed < 0 || elapsed < AuthSupport.resend_millis)) {
                AuthSupport.flash(session, "確認メールの再送は、前回の操作から60秒以上あけてください。",
                        "");
                AuthWeb.redirect(request, response, "/login/Error.action");
                return null;
            }
            try {
                // DAOで現在のメール確認状態を取得する
                LoginDAO dao = auth.dao;
                pending.user = dao.refresh(pending.user);
                if (pending.user.isEmailVerified()) {
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
                if (AuthSupport.invalidSession(exception)) {
                    auth.expired(request, response, session);
                } else {
                    AuthSupport.flash(session, AuthWeb.errorMessage(exception), "");
                    AuthWeb.redirect(request, response, "/login/Error.action");
                }
            }
            return null;
        }
    }
}
