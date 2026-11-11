package login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LogoutAction extends Action {
    // FrontControllerから呼ばれる処理
    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        AuthSupport auth = AuthSupport.get(request);
        HttpSession session = auth.session(request, response);
        synchronized (session) {
            if (!auth.post(request, response, session, false)) {
                return null;
            }
            // セッションからログイン情報を消す
            AuthWeb.clear(session);
            session.setAttribute(AuthWeb.fingerprint_key, auth.config_fingerprint);
            request.changeSessionId();
            AuthWeb.redirect(request, response, "/login/Log.action");
            return null;
        }
    }
}
