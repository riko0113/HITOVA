package login;

import DAO.LoginAccountDAO;
import DAO.FLoginDAO;
import bean.FirebaseUser;
import bean.JobSeekerAccount;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.function.LongSupplier;

// Firebaseの認証結果を使って、DBの確認とログイン状態の保存を行う。
final class AuthSupport {
    // 共通の認証処理をアプリ内で保存するときの名前。
    static final String support_key = "login.authSupport";
    // フォーム送信のサイズ上限（バイト）。
    static final int max_request_bytes = 8192;
    // 確認メールを再送できるようになるまでの待ち時間（30秒）。
    static final long resend_millis = 30L * 1000L;
    final FLoginDAO dao;
    final LoginAccountDAO accounts;
    // Firebaseの接続設定が変わったかを見分ける値。
    final String config_fingerprint;
    private final LongSupplier clock;

    private AuthSupport(ServletContext context) {
        FLoginDAO client = null;
        String fingerprint = "not-configured";
        try {
            FirebaseConfig config = FirebaseConfig.load(
                    context.getResourceAsStream("/WEB-INF/firebase.properties"));
            client = new FLoginDAO(config);
            fingerprint = AuthWeb.fingerprint(config);
        } catch (FirebaseException exception) {
        }
        dao = client;
        accounts = new LoginAccountDAO();
        config_fingerprint = fingerprint;
        clock = System::currentTimeMillis;
    }

    AuthSupport(FLoginDAO dao, FirebaseConfig config, LongSupplier clock, LoginAccountDAO accounts) {
        if (dao == null || config == null || clock == null || accounts == null) {
            throw new IllegalArgumentException("認証の設定が必要です。");
        }
        this.dao = dao;
        this.accounts = accounts;
        this.config_fingerprint = AuthWeb.fingerprint(config);
        this.clock = clock;
    }

    // 最初に呼ばれたときに準備し、次からは保存したものを使う。
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
        return AuthWeb.session(request, config_fingerprint);
    }

    boolean post(HttpServletRequest request, HttpServletResponse response) {
        if (!"POST".equals(request.getMethod())) {
            response.setHeader("Allow", "POST");
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return false;
        }
        if (request.getContentLengthLong() > max_request_bytes) {
            response.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
            return false;
        }
        String content_type = request.getContentType();
        if (content_type == null || !"application/x-www-form-urlencoded".equals(
                content_type.split(";", 2)[0].trim().toLowerCase(java.util.Locale.ROOT))) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return false;
        }
        return available(response);
    }

    boolean available(HttpServletResponse response) {
        if (dao == null) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
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
        session.removeAttribute(AuthWeb.candidate_key);
        session.removeAttribute(AuthWeb.mail_sent_key);
        Pending pending = new Pending(user, now(), prior_attempt);
        session.setAttribute(AuthWeb.pending_key, pending);
        request.changeSessionId();
        return pending;
    }

    long resendRemainingMillis(Pending pending) {
        if (pending.last_mail_attempt < 0) {
            return 0;
        }
        long elapsed = Math.max(0L, now() - pending.last_mail_attempt);
        return Math.max(0L, resend_millis - elapsed);
    }

    boolean sendMail(HttpSession session, Pending pending) {
        if (!allowWaiting(session, pending.user)) {
            return false;
        }
        pending.last_mail_attempt = now();
        session.removeAttribute(AuthWeb.mail_sent_key);
        try {
            if (dao.sendVerification(pending.user)) {
                session.setAttribute(AuthWeb.mail_sent_key, true);
            }
            return true;
        } catch (FirebaseException exception) {
            verificationError(session, exception);
            return false;
        }
    }

    // メール確認とDBの利用状態を確かめ、求職者IDをセッションに保存する。
    boolean finish(HttpServletRequest request, HttpSession session, Pending pending) {
        if (!pending.user.isEmailVerified()) {
            clearLogin(session);
            return false;
        }
        try {
            String uid = pending.user.getUid();
            JobSeekerAccount account = accounts.find(uid);
            if (account != null && "求職者".equals(account.getAuthority_name())
                    && "仮登録中".equals(account.getAccount_status_name())) {
                accounts.activate(uid);
                account = accounts.find(uid);
            }
            if (!usableAccount(account, false)) {
                clearLogin(session);
                return false;
            }
            request.changeSessionId();
            session.removeAttribute(AuthWeb.pending_key);
            session.removeAttribute(AuthWeb.mail_sent_key);
            session.setAttribute(AuthWeb.candidate_key, account.getCandidate_id());
            return true;
        } catch (SQLException exception) {
            clearLogin(session);
            return false;
        }
    }

    boolean allowWaiting(HttpSession session, FirebaseUser user) {
        try {
            if (usableAccount(accounts.find(user.getUid()), true)) {
                return true;
            }
        } catch (SQLException exception) {
        }
        clearLogin(session);
        return false;
    }

    private static boolean usableAccount(JobSeekerAccount account, boolean allow_pending) {
        if (account == null || account.getCandidate_id() == null || !"求職者".equals(account.getAuthority_name())) {
            return false;
        }
        String status = account.getAccount_status_name();
        return "利用中".equals(status) || (allow_pending && "仮登録中".equals(status));
    }

    static void clearLogin(HttpSession session) {
        session.removeAttribute(AuthWeb.pending_key);
        session.removeAttribute(AuthWeb.candidate_key);
        session.removeAttribute(AuthWeb.mail_sent_key);
    }

    Pending pending(HttpSession session) {
        Object value = session.getAttribute(AuthWeb.pending_key);
        if (!(value instanceof Pending) || AuthWeb.expired(((Pending) value).started_at, now())) {
            session.removeAttribute(AuthWeb.pending_key);
            return null;
        }
        return (Pending) value;
    }

    void verificationError(HttpSession session, FirebaseException exception) {
        if ("SESSION_EXPIRED".equals(exception.getCode()) || "USER_DISABLED".equals(exception.getCode())) {
            clearLogin(session);
        }
    }

    long now() { return clock.getAsLong(); }

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
}
