package login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// セッションを終了して、ログイン画面を表示する。
public class LogoutAction extends Action {
    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        if ("POST".equals(request.getMethod())) {
            return AuthWeb.redirectAfterPost(request, response, "/login/login.jsp");
        }
        return "/login/login.jsp";
    }
}
