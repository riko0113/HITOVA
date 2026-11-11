package login;

import DAO.LoginDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class CheckAction extends Action {
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
            try {
                // DAOで現在のメール確認状態を取得する
                LoginDAO dao = auth.dao;
                pending.user = dao.refresh(pending.user);
                if (pending.user.isEmailVerified()) {
                    auth.finish(request, session, pending);
                    AuthWeb.redirect(request, response, "/login/Complete.action");
                } else {
                    AuthSupport.flash(session, "メールアドレスはまだ確認されていません。メール内のリンクを開いてから、"
                            + "もう一度確認してください。", "");
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
