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
        // URLを直接開いた場合は、セッションの求職者IDでログイン状態を確かめる。
        if ("GET".equals(method)) {
            HttpSession session = request.getSession(false);
            if (session == null || !(session.getAttribute(AuthWeb.candidate_key) instanceof Long)) {
                return "/login/error.jsp";
            }
            // TODO ログイン後の遷移先を設定する。接続まではログイン画面を表示する。
            // return "";
            return "/toppage/candidateTop.jsp";
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
            return "/login/error.jsp";
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
                    if (!auth.finish(request, session, pending)) {
                        return "/login/error.jsp";
                    }
                    // TODO ログイン後の遷移先を設定する。接続まではログイン画面を表示する。
                    // return "";
                    return "/toppage/candidateTop.jsp";
                }

                if (!auth.allowWaiting(session, user)) {
                    return "/login/error.jsp";
                }
                return VerifyAction.showPage(request, session, auth, pending);
            } catch (FirebaseException exception) {
                return "/login/error.jsp";
            }
        }
    }
}
