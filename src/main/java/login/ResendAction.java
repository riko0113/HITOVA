package login;

import DAO.FLoginDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 再送可能な時間になったら、確認メールを再送する。
public class ResendAction extends Action {
    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        AuthSupport auth = AuthSupport.get(request);
        HttpSession session = auth.session(request, response);
        synchronized (session) {
            if (!auth.post(request, response)) {
                return "/login/error.jsp";
            }
            AuthSupport.Pending pending = auth.pending(session);
            if (pending == null) {
                AuthSupport.clearLogin(session);
                return AuthWeb.redirectAfterPost(request, response, "/login/error.jsp");
            }
            if (auth.resendRemainingMillis(pending) > 0) {
                return AuthWeb.redirectAfterPost(request, response, "/login/error.jsp");
            }
            try {
                FLoginDAO dao = auth.dao;
                pending.user = dao.refresh(pending.user);
                if (pending.user.isEmailVerified()) {
                    AuthSupport.Result result = auth.finish(request, session, pending);
                    return AuthWeb.redirectForResult(request, response, result, "/login/Log.action");
                }
                AuthSupport.Result result = auth.sendMail(session, pending);
                return AuthWeb.redirectForResult(request, response, result, "/login/Verify.action");
            } catch (FirebaseException exception) {
                auth.verificationError(session, exception);
                return AuthWeb.redirectAfterPost(request, response, "/login/error.jsp");
            }
        }
    }
}
