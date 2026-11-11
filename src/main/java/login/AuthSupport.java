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
    static final String ATTRIBUTE = "login.authSupport";
    static final int MAX_REQUEST_BYTES = 8192;
    static final long RESEND_MILLIS = 60L * 1000L;
    static final String CONFIG_MESSAGE =
            "現在、認証サービスを利用できません。しばらくしてからもう一度お試しください。";
    static final String EXPIRED_MESSAGE =
            "認証の有効期限が切れました。もう一度ログインしてください。";
    final LoginDAO dao;
    final String configFingerprint;
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
        configFingerprint = fingerprint;
        clock = System::currentTimeMillis;
    }

    AuthSupport(LoginDAO dao, FirebaseConfig config, LongSupplier clock) {
        if (dao == null || config == null || clock == null) {
            throw new IllegalArgumentException("認証の設定が必要です。");
        }
        this.dao = dao;
        this.configFingerprint = AuthWeb.fingerprint(config);
        this.clock = clock;
    }

    static AuthSupport get(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        synchronized (context) {
            AuthSupport auth = (AuthSupport) context.getAttribute(ATTRIBUTE);
            if (auth == null) {
                auth = new AuthSupport(context);
                context.setAttribute(ATTRIBUTE, auth);
            }
            return auth;
        }
    }

    HttpSession session(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthWeb.noStore(response);
        request.setCharacterEncoding("UTF-8");
        return AuthWeb.session(request, configFingerprint, true);
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
            HttpSession session, boolean needsFirebase) throws IOException {
        if (!"POST".equals(request.getMethod())) {
            response.setHeader("Allow", "POST");
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return false;
        }
        if (request.getContentLengthLong() > MAX_REQUEST_BYTES) {
            flash(session, "入力内容が大きすぎます。", "");
            AuthWeb.redirect(request, response, "/login/Error.action");
            return false;
        }
        String contentType = request.getContentType();
        if (contentType == null || !"application/x-www-form-urlencoded".equals(
                contentType.split(";", 2)[0].trim().toLowerCase(java.util.Locale.ROOT))) {
            flash(session, "正しい形式で送信できませんでした。入力画面からやり直してください。", "");
            AuthWeb.redirect(request, response, "/login/Error.action");
            return false;
        }
        return !needsFirebase || available(request, response, session);
    }

    boolean available(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws IOException {
        if (dao == null) {
            flash(session, CONFIG_MESSAGE, "");
            AuthWeb.redirect(request, response, "/login/Error.action");
            return false;
        }
        return true;
    }

    Pending beginPending(HttpServletRequest request, HttpSession session,
            FirebaseUser user) {
        Object oldValue = session.getAttribute(AuthWeb.PENDING);
        long priorAttempt = -1L;
        if (oldValue instanceof Pending && ((Pending) oldValue).user.getUid().equals(user.getUid())) {
            priorAttempt = ((Pending) oldValue).lastMailAttempt;
        }
        session.removeAttribute(AuthWeb.PROOF);
        session.removeAttribute(AuthWeb.FLASH);
        Pending pending = new Pending(user, now(), priorAttempt);
        session.setAttribute(AuthWeb.PENDING, pending);
        request.changeSessionId();
        return pending;
    }

    boolean sendMail(HttpSession session, Pending pending) {
        pending.lastMailAttempt = now();
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
        session.removeAttribute(AuthWeb.PENDING);
        session.removeAttribute(AuthWeb.FLASH);
        session.setAttribute(AuthWeb.PROOF, new Proof(pending.user, pending.startedAt));
        request.changeSessionId();
    }

    Pending pending(HttpSession session) {
        Object value = session.getAttribute(AuthWeb.PENDING);
        if (!(value instanceof Pending) || AuthWeb.expired(((Pending) value).startedAt, now())) {
            session.removeAttribute(AuthWeb.PENDING);
            return null;
        }
        return (Pending) value;
    }

    void expired(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        session.removeAttribute(AuthWeb.PENDING);
        session.removeAttribute(AuthWeb.PROOF);
        flash(session, EXPIRED_MESSAGE, "");
        AuthWeb.redirect(request, response, "/login/Error.action");
    }

    static boolean invalidSession(FirebaseException exception) {
        return "SESSION_EXPIRED".equals(exception.getCode()) || "USER_DISABLED".equals(exception.getCode());
    }

    long now() { return clock.getAsLong(); }

    static void flash(HttpSession session, String error, String message) {
        session.setAttribute(AuthWeb.FLASH, new Flash(error, message));
    }

    static Flash takeFlash(HttpSession session) {
        Object value = session.getAttribute(AuthWeb.FLASH);
        session.removeAttribute(AuthWeb.FLASH);
        return value instanceof Flash ? (Flash) value : new Flash("", "");
    }

    static final class Pending implements Serializable {
        static final long serialVersionUID = 1L;
        FirebaseUser user;
        final long startedAt;
        long lastMailAttempt;

        Pending(FirebaseUser user, long startedAt, long lastMailAttempt) {
            this.user = user;
            this.startedAt = startedAt;
            this.lastMailAttempt = lastMailAttempt;
        }
    }

    static final class Proof implements Serializable {
        static final long serialVersionUID = 1L;
        FirebaseUser user;
        final long startedAt;

        Proof(FirebaseUser user, long startedAt) {
            this.user = user;
            this.startedAt = startedAt;
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
