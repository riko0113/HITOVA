package login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ErrorAction extends Action {
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
            AuthSupport.Flash flash = AuthSupport.takeFlash(session);
            String message = flash.error.isEmpty()
                    ? "処理を完了できませんでした。前の画面に戻ってやり直してください。" : flash.error;
            // JSPに表示する値を渡す
            request.setAttribute("authError", message);
            request.setAttribute("authReturnPath", auth.pending(session) == null ? "/login/Log.action" : "/login/Verify.action");
            if (auth.dao == null) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
            return "/login-logaut/error.jsp";
        }
    }
}
