package login;

import DAO.LoginDAO;
import bean.FirebaseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class CompleteAction extends Action {
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
            Object value = session.getAttribute(AuthWeb.proof_key);
            if (!(value instanceof AuthSupport.Proof) || AuthWeb.expired(((AuthSupport.Proof) value).started_at, auth.now())) {
                auth.expired(request, response, session);
                return null;
            }
            AuthSupport.Proof proof = (AuthSupport.Proof) value;
            try {
                // 完了画面を再表示するときも、失効・無効化・メール確認を Firebase へ確認します。
                LoginDAO dao = auth.dao;
                FirebaseUser fresh = dao.refresh(proof.user);
                if (!fresh.isEmailVerified()) {
                    auth.expired(request, response, session);
                    return null;
                }
                proof.user = fresh;
                // JSPに表示する値を渡す
                request.setAttribute("authEmail", fresh.getEmail());
                return "/login-logaut/complete.jsp";
            } catch (FirebaseException exception) {
                session.removeAttribute(AuthWeb.proof_key);
                AuthSupport.flash(session, AuthWeb.errorMessage(exception), "");
                AuthWeb.redirect(request, response, "/login/Error.action");
            }
            return null;
        }
    }
}
