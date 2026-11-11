package login;

import DAO.LoginDAO;
import bean.FirebaseUser;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.function.LongSupplier;

/** 認証の接続設定と、複数のActionで使うセッション処理です。 */
final class AuthSupport {
    static final String support_key = "login.authSupport";
    static final int max_request_bytes = 8192;
    static final long resend_millis = 60L * 1000L;
    static final String config_message =
            "現在、認証サービスを利用できません。しばらくしてからもう一度お試しください。";
    static final String expired_message =
            "認証の有効期限が切れました。もう一度ログインしてください。";
    final LoginDAO dao;
    final String config_fingerprint;
    private final LongSupplier clock;

    private AuthSupport(ServletContext context) {
        LoginDAO client = null;
        String fingerprint = "not-configured";
        try {
            FirebaseConfig config = FirebaseConfig.load(
                    context.getResourceAsStream("/WEB-INF/firebase.properties"));
            client = new LoginDAO(config);
            fingerprint = AuthWeb.fingerprint(config);
        } catch (FirebaseException exception) {
            // 接続できない場合の表示は各Actionで行います。
        }
        dao = client;
        config_fingerprint = fingerprint;
        clock = System::currentTimeMillis;
    }

    AuthSupport(LoginDAO dao, FirebaseConfig config, LongSupplier clock) {
        if (dao == null || config == null || clock == null) {
            throw new IllegalArgumentException("認証の設定が必要です。");
        }
        this.dao = dao;
        this.config_fingerprint = AuthWeb.fingerprint(config);
        this.clock = clock;
    }

    static AuthSupport get(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        synchronized (context) {
            AuthSupport auth = (AuthSupport) context.getAttribute(support_key);
            if (auth == null) {
                auth = new AuthSupport(context);
                context.setAttribute(support_key, auth);
            }
            return auth;
        }
    }

    HttpSession session(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthWeb.noStore(response);
        request.setCharacterEncoding("UTF-8");
        return AuthWeb.session(request, config_fingerprint, true);
    }

    boolean getPage(HttpServletRequest request, HttpServletResponse response) {
        if (!"GET".equals(request.getMethod())) {
            response.setHeader("Allow", "GET");
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return false;
        }
        return true;
    }

    boolean post(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, boolean needs_firebase) throws IOException {
        if (!"POST".equals(request.getMethod())) {
            response.setHeader("Allow", "POST");
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return false;
        }
        if (request.getContentLengthLong() > max_request_bytes) {
            flash(session, "入力内容が大きすぎます。", "");
            AuthWeb.redirect(request, response, "/login/Error.action");
            return false;
        }
        String content_type = request.getContentType();
        if (content_type == null || !"application/x-www-form-urlencoded".equals(
                content_type.split(";", 2)[0].trim().toLowerCase(java.util.Locale.ROOT))) {
            flash(session, "正しい形式で送信できませんでした。入力画面からやり直してください。", "");
            AuthWeb.redirect(request, response, "/login/Error.action");
            return false;
        }
        return !needs_firebase || available(request, response, session);
    }

    boolean available(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws IOException {
        if (dao == null) {
            flash(session, config_message, "");
            AuthWeb.redirect(request, response, "/login/Error.action");
            return false;
        }
        return true;
    }

    Pending beginPending(HttpServletRequest request, HttpSession session,
            FirebaseUser user) {
        Object old_value = session.getAttribute(AuthWeb.pending_key);
        long prior_attempt = -1L;
        if (old_value instanceof Pending && ((Pending) old_value).user.getUid().equals(user.getUid())) {
            prior_attempt = ((Pending) old_value).last_mail_attempt;
        }
        session.removeAttribute(AuthWeb.proof_key);
        session.removeAttribute(AuthWeb.flash_key);
        Pending pending = new Pending(user, now(), prior_attempt);
        session.setAttribute(AuthWeb.pending_key, pending);
        request.changeSessionId();
        return pending;
    }

    boolean sendMail(HttpSession session, Pending pending) {
        pending.last_mail_attempt = now();
        try {
            boolean sent = dao.sendVerification(pending.user);
            String message = sent
                    ? "確認メールを送信しました。"
                    : "メールアドレスはすでに確認済みです。「サイトの確認をする」を押してください。";
            flash(session, "", message);
            return true;
        } catch (FirebaseException exception) {
            // Firebase 登録が成功した状態を失わず、送信できたとも表示しません。
            flash(session, "確認メールを送信できませんでした。" + AuthWeb.errorMessage(exception)
                    + " 60秒後に再送できます。", "");
            return false;
        }
    }

    void finish(HttpServletRequest request, HttpSession session, Pending pending) {
        if (!pending.user.isEmailVerified()) {
            throw new IllegalStateException("Verified Firebase account is required");
        }
        session.removeAttribute(AuthWeb.pending_key);
        session.removeAttribute(AuthWeb.flash_key);
        session.setAttribute(AuthWeb.proof_key, new Proof(pending.user, pending.started_at));
        request.changeSessionId();
    }

    Pending pending(HttpSession session) {
        Object value = session.getAttribute(AuthWeb.pending_key);
        if (!(value instanceof Pending) || AuthWeb.expired(((Pending) value).started_at, now())) {
            session.removeAttribute(AuthWeb.pending_key);
            return null;
        }
        return (Pending) value;
    }

    void expired(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        session.removeAttribute(AuthWeb.pending_key);
        session.removeAttribute(AuthWeb.proof_key);
        flash(session, expired_message, "");
        AuthWeb.redirect(request, response, "/login/Error.action");
    }

    static boolean invalidSession(FirebaseException exception) {
        return "SESSION_EXPIRED".equals(exception.getCode()) || "USER_DISABLED".equals(exception.getCode());
    }

    long now() { return clock.getAsLong(); }

    static void flash(HttpSession session, String error, String message) {
        session.setAttribute(AuthWeb.flash_key, new Flash(error, message));
    }

    static Flash takeFlash(HttpSession session) {
        Object value = session.getAttribute(AuthWeb.flash_key);
        session.removeAttribute(AuthWeb.flash_key);
        return value instanceof Flash ? (Flash) value : new Flash("", "");
    }

    static final class Pending implements Serializable {
        static final long serialVersionUID = 1L;
        FirebaseUser user;
        final long started_at;
        long last_mail_attempt;

        Pending(FirebaseUser user, long started_at, long last_mail_attempt) {
            this.user = user;
            this.started_at = started_at;
            this.last_mail_attempt = last_mail_attempt;
        }
    }

    static final class Proof implements Serializable {
        static final long serialVersionUID = 1L;
        FirebaseUser user;
        final long started_at;

        Proof(FirebaseUser user, long started_at) {
            this.user = user;
            this.started_at = started_at;
        }
    }

    static final class Flash implements Serializable {
        static final long serialVersionUID = 1L;
        final String error;
        final String message;

        Flash(String error, String message) {
            this.error = error;
            this.message = message;
        }
    }
}
