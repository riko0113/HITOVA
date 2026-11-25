package login;

import DAO.FLoginDAO;
import bean.FirebaseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// メールアドレスとパスワードを受け取り、Firebaseでログインする。
public class LogAction extends Action {
    @Override
    public String execute(
        HttpServletRequest request, 
        HttpServletResponse response
    ) throws Exception {
        AuthWeb.noStore(response);

        String method = request.getMethod();
        // セッションに保存したIDでログイン後の画面を選ぶ。
        if ("GET".equals(method)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                if (session.getAttribute(AuthWeb.candidate_key) instanceof Long) {
                    return "/toppage/candidateTop.jsp";
                }
                if (session.getAttribute(AuthWeb.company_key) instanceof Long) {
                    return "/toppage/companyTop.jsp";
                }
            }
            return "/login/error.jsp";
        }

        if (!"POST".equals(method)) {
            response.setHeader("Allow", "GET, POST");
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return "/login/error.jsp";
        }

        request.setCharacterEncoding("UTF-8");
        String email = AuthWeb.display(AuthWeb.field(request, "email", 254));
        String password = AuthWeb.field(request, "password", 4096);

        if (!AuthWeb.validEmail(email) || password == null || password.isEmpty()) {
            return AuthWeb.redirectAfterPost(request, response, "/login/error.jsp");
        }

        AuthSupport auth = AuthSupport.get(request);
        if (auth.dao == null) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return "/login/error.jsp";
        }

        HttpSession session = auth.session(request, response);
        synchronized (session) {
            try {
                FLoginDAO dao = auth.dao;
                FirebaseUser user = dao.login(email, password);
                AuthSupport.Pending pending = auth.beginPending(request, session, user);
                if (user.isEmailVerified()) {
                    AuthSupport.Result result = auth.finish(request, session, pending);
                    return AuthWeb.redirectForResult(request, response, result, "/login/Log.action");
                }

                AuthSupport.Result result = auth.checkWaiting(session, user);
                return AuthWeb.redirectForResult(request, response, result, "/login/Verify.action");
            } catch (FirebaseException exception) {
                return AuthWeb.redirectAfterPost(request, response, "/login/error.jsp");
            }
        }
    }
}
