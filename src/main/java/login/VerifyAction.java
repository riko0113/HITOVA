package login;

import DAO.FLoginDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// GETで確認待ちの画面を表示し、POSTでメールの確認結果を調べる。
public class VerifyAction extends Action {
    @Override
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        String method = request.getMethod();
        boolean show_page = "GET".equals(method);
        if (!show_page && !"POST".equals(method)) {
            AuthWeb.noStore(response);
            response.setHeader("Allow", "GET, POST");
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return "/login/error.jsp";
        }

        if (request.getSession(false) == null) {
            AuthWeb.noStore(response);
            return "/login/login.jsp";
        }

        AuthSupport auth = AuthSupport.get(request);
        HttpSession session = auth.session(request, response);
        synchronized (session) {
            AuthSupport.Pending pending = auth.pending(session);
            if (pending == null) {
                session.removeAttribute(AuthWeb.mail_sent_key);
                return "/login/login.jsp";
            }
            if (show_page) {
                if (!auth.available(response)) {
                    return "/login/error.jsp";
                }
                return showPage(request, session, auth, pending);
            }

            if (!auth.post(request, response)) {
                return "/login/error.jsp";
            }
            return checkEmail(request, session, auth, pending);
        }
    }

    // メールアドレス、送信済み表示、再送までの残り時間をJSPに渡す。
    static String showPage(HttpServletRequest request, HttpSession session,
            AuthSupport auth, AuthSupport.Pending pending) {
        request.setAttribute("authEmail", pending.user.getEmail());
        request.setAttribute("mailSent", session.getAttribute(AuthWeb.mail_sent_key));
        session.removeAttribute(AuthWeb.mail_sent_key);
        request.setAttribute("resendMillis", auth.resendRemainingMillis(pending));
        return "/login/emailSent.jsp";
    }

    private String checkEmail(HttpServletRequest request,
            HttpSession session, AuthSupport auth, AuthSupport.Pending pending) throws Exception {
        try {
            FLoginDAO dao = auth.dao;
            pending.user = dao.refresh(pending.user);
            if (!pending.user.isEmailVerified() || !auth.finish(request, session, pending)) {
                return "/login/error.jsp";
            }
            // TODO ログイン後の遷移先を設定する。接続まではログイン画面を表示する。
            // return "";
            return "/toppage/candidateTop.jsp";
        } catch (FirebaseException exception) {
            auth.verificationError(session, exception);
            return "/login/error.jsp";
        }
    }
}
