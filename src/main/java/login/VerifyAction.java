package login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class VerifyAction extends Action {
    // FrontControllerから呼ばれる処理
    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        AuthSupport auth = AuthSupport.get(request);
        HttpSession session = auth.session(request, response);
        synchronized (session) {
            if (!auth.getPage(request, response)) {
                return null;
            }
            if (!auth.available(request, response, session)) {
                return null;
            }
            AuthSupport.Pending pending = auth.pending(session);
            if (pending == null) {
                auth.expired(request, response, session);
                return null;
            }
            AuthSupport.Flash flash = AuthSupport.takeFlash(session);
            // JSPに表示する値を渡す
            request.setAttribute("authEmail", pending.user.getEmail());
            request.setAttribute("authMessage", flash.message);
            return "/login-logaut/emailSent.jsp";
        }
    }
}
