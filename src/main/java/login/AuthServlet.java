package login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.function.LongSupplier;

/** Firebase の登録・メール確認・ログイン・ログアウトを受け付けます。 */
@WebServlet(urlPatterns = "/auth/*", loadOnStartup = 1)
public final class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_REQUEST_BYTES = 8192;
    private static final long RESEND_MILLIS = 60L * 1000L;
    private static final String CONFIG_MESSAGE =
            "現在、認証サービスを利用できません。しばらくしてからもう一度お試しください。";
    private static final String EXPIRED_MESSAGE =
            "認証の有効期限が切れました。もう一度ログインしてください。";
    private transient FirebaseClient firebase;
    private String configFingerprint = "not-configured";
    private transient LongSupplier clock = System::currentTimeMillis;

    public AuthServlet() { }

    // 本番と同じ固定接続先のまま、パッケージ内のテストだけ通信と時刻を置き換えます。
    AuthServlet(FirebaseClient firebase, FirebaseConfig config) {
        this(firebase, config, System::currentTimeMillis);
    }

    AuthServlet(FirebaseClient firebase, FirebaseConfig config, LongSupplier clock) {
        if (firebase == null || config == null || clock == null) {
            throw new IllegalArgumentException("認証の設定が必要です。");
        }
        this.firebase = firebase;
        this.configFingerprint = AuthWeb.fingerprint(config);
        this.clock = clock;
    }

    @Override
    public void init() {
        if (firebase != null) {
            return;
        }
        try {
            FirebaseConfig config = FirebaseConfig.load(
                    getServletContext().getResourceAsStream("/WEB-INF/firebase.properties"));
            configFingerprint = AuthWeb.fingerprint(config);
            firebase = new FirebaseClient(config);
        } catch (FirebaseException exception) {
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthWeb.noStore(response);
        HttpSession session = AuthWeb.session(request, configFingerprint, true);
        String path = path(request);
        if ("/".equals(path)) {
            AuthWeb.redirect(request, response, "login");
        } else if ("/error".equals(path)) {
            showError(request, response, session);
        } else if ("/login".equals(path)) {
            showLogin(request, response, session, firebase == null);
        } else if (firebase == null) {
            showLogin(request, response, session, true);
        } else if ("/verify".equals(path)) {
            showVerify(request, response, session);
        } else if ("/complete".equals(path)) {
            showComplete(request, response, session);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthWeb.noStore(response);
        request.setCharacterEncoding("UTF-8");
        HttpSession session = AuthWeb.session(request, configFingerprint, true);
        if (request.getContentLengthLong() > MAX_REQUEST_BYTES) {
            flash(session, "入力内容が大きすぎます。", "");
            AuthWeb.redirect(request, response, "error");
            return;
        }
        String contentType = request.getContentType();
        if (contentType == null || !"application/x-www-form-urlencoded".equals(
                contentType.split(";", 2)[0].trim().toLowerCase(java.util.Locale.ROOT))) {
            flash(session, "正しい形式で送信できませんでした。入力画面からやり直してください。", "");
            AuthWeb.redirect(request, response, "error");
            return;
        }
        // 同じ一時セッションでの連打によるメール重複送信・状態の取り違えを防ぎます。
        synchronized (session) {
            String path = path(request);
            if ("/logout".equals(path)) {
                AuthWeb.clear(session);
                session.setAttribute(AuthWeb.FINGERPRINT, configFingerprint);
                request.changeSessionId();
                AuthWeb.redirect(request, response, "login");
            } else if (firebase == null) {
                showLogin(request, response, session, true);
            } else if ("/register".equals(path)) {
                register(request, response, session);
            } else if ("/login".equals(path)) {
                login(request, response, session);
            } else if ("/check".equals(path)) {
                check(request, response, session);
            } else if ("/resend".equals(path)) {
                resend(request, response, session);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        String email = AuthWeb.display(AuthWeb.field(request, "email", 254));
        String nickname = AuthWeb.display(AuthWeb.field(request, "nickname", 128));
        String password = AuthWeb.field(request, "password", 128);
        String confirmation = AuthWeb.field(request, "passwordConfirm", 128);
        String error = "";
        if (!AuthWeb.validEmail(email)) {
            error = "メールアドレスを正しく入力してください。";
        } else if (password == null || password.length() < 8 || password.length() > 20) {
            error = "パスワードは8〜20文字で入力してください。";
        } else if (!password.equals(confirmation)) {
            error = "確認用パスワードが一致していません。";
        } else if (!nickname.matches("(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{4,20}")) {
            error = "ニックネームは英字と数字をそれぞれ含む半角英数字4〜20文字で入力してください。";
        } else if (!AuthWeb.checked(request, "agreeTerms") || !AuthWeb.checked(request, "agreePrivacy")) {
            error = "利用規約とプライバシーポリシーへの同意が必要です。";
        }
        if (!error.isEmpty()) {
            flash(session, error, "");
            AuthWeb.redirect(request, response, "error");
            return;
        }
        try {
            FirebaseUser user = firebase.register(email, password);
            Pending pending = beginPending(request, session, user);
            if (user.isEmailVerified()) {
                finish(request, session, pending);
                AuthWeb.redirect(request, response, "complete");
                return;
            }
            if (sendMail(session, pending)) {
                AuthWeb.redirect(request, response, "verify");
            } else {
                AuthWeb.redirect(request, response, "error");
            }
        } catch (FirebaseException exception) {
            flash(session, AuthWeb.errorMessage(exception), "");
            AuthWeb.redirect(request, response, "error");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        String email = AuthWeb.display(AuthWeb.field(request, "email", 254));
        String password = AuthWeb.field(request, "password", 4096);
        if (!AuthWeb.validEmail(email) || password == null || password.isEmpty()) {
            flash(session, "メールアドレスとパスワードを入力してください。", "");
            AuthWeb.redirect(request, response, "error");
            return;
        }
        try {
            FirebaseUser user = firebase.login(email, password);
            Pending pending = beginPending(request, session, user);
            if (user.isEmailVerified()) {
                finish(request, session, pending);
                AuthWeb.redirect(request, response, "complete");
            } else {
                flash(session, "", "メール内のリンクを開いてから確認してください。"
                        + "確認メールが見当たらない場合は、再送できます。");
                AuthWeb.redirect(request, response, "verify");
            }
        } catch (FirebaseException exception) {
            flash(session, AuthWeb.errorMessage(exception), "");
            AuthWeb.redirect(request, response, "error");
        }
    }

    private Pending beginPending(HttpServletRequest request, HttpSession session,
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

    private void check(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        Pending pending = pending(session);
        if (pending == null) {
            expired(request, response, session);
            return;
        }
        try {
            pending.user = firebase.refresh(pending.user);
            if (pending.user.isEmailVerified()) {
                finish(request, session, pending);
                AuthWeb.redirect(request, response, "complete");
            } else {
                flash(session, "メールアドレスはまだ確認されていません。メール内のリンクを開いてから、"
                        + "もう一度確認してください。", "");
                AuthWeb.redirect(request, response, "error");
            }
        } catch (FirebaseException exception) {
            if (invalidSession(exception)) {
                expired(request, response, session);
            } else {
                flash(session, AuthWeb.errorMessage(exception), "");
                AuthWeb.redirect(request, response, "error");
            }
        }
    }

    private void resend(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        Pending pending = pending(session);
        if (pending == null) {
            expired(request, response, session);
            return;
        }
        long elapsed = now() - pending.lastMailAttempt;
        if (pending.lastMailAttempt >= 0 && (elapsed < 0 || elapsed < RESEND_MILLIS)) {
            flash(session, "確認メールの再送は、前回の操作から60秒以上あけてください。",
                    "");
            AuthWeb.redirect(request, response, "error");
            return;
        }
        try {
            pending.user = firebase.refresh(pending.user);
            if (pending.user.isEmailVerified()) {
                finish(request, session, pending);
                AuthWeb.redirect(request, response, "complete");
                return;
            }
            if (sendMail(session, pending)) {
                AuthWeb.redirect(request, response, "verify");
            } else {
                AuthWeb.redirect(request, response, "error");
            }
        } catch (FirebaseException exception) {
            if (invalidSession(exception)) {
                expired(request, response, session);
            } else {
                flash(session, AuthWeb.errorMessage(exception), "");
                AuthWeb.redirect(request, response, "error");
            }
        }
    }

    private boolean sendMail(HttpSession session, Pending pending) {
        pending.lastMailAttempt = now();
        try {
            boolean sent = firebase.sendVerification(pending.user);
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

    private void finish(HttpServletRequest request, HttpSession session, Pending pending) {
        if (!pending.user.isEmailVerified()) {
            throw new IllegalStateException("Verified Firebase account is required");
        }
        session.removeAttribute(AuthWeb.PENDING);
        session.removeAttribute(AuthWeb.FLASH);
        session.setAttribute(AuthWeb.PROOF, new Proof(pending.user, pending.startedAt));
        request.changeSessionId();
    }

    private void showLogin(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, boolean unavailable) throws ServletException, IOException {
        synchronized (session) {
            if (unavailable) {
                flash(session, CONFIG_MESSAGE, "");
                AuthWeb.redirect(request, response, "error");
                return;
            }
            request.getRequestDispatcher("/login-logaut/login.jsp").forward(request, response);
        }
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        synchronized (session) {
            Flash flash = takeFlash(session);
            String message = flash.error.isEmpty()
                    ? "処理を完了できませんでした。前の画面に戻ってやり直してください。" : flash.error;
            request.setAttribute("authError", message);
            request.setAttribute("authReturnPath", pending(session) == null ? "/auth/login" : "/auth/verify");
            if (firebase == null) {
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            }
            request.getRequestDispatcher("/login-logaut/error.jsp").forward(request, response);
        }
    }

    private void showVerify(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        synchronized (session) {
            Pending pending = pending(session);
            if (pending == null) {
                expired(request, response, session);
                return;
            }
            Flash flash = takeFlash(session);
            request.setAttribute("authEmail", pending.user.getEmail());
            request.setAttribute("authMessage", flash.message);
            request.getRequestDispatcher("/login-logaut/emailSent.jsp").forward(request, response);
        }
    }

    private void showComplete(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        synchronized (session) {
            Object value = session.getAttribute(AuthWeb.PROOF);
            if (!(value instanceof Proof) || AuthWeb.expired(((Proof) value).startedAt, now())) {
                expired(request, response, session);
                return;
            }
            Proof proof = (Proof) value;
            try {
                // 完了画面を再表示するときも、失効・無効化・メール確認を Firebase へ確認します。
                FirebaseUser fresh = firebase.refresh(proof.user);
                if (!fresh.isEmailVerified()) {
                    expired(request, response, session);
                    return;
                }
                proof.user = fresh;
                request.setAttribute("authEmail", fresh.getEmail());
                request.getRequestDispatcher("/login-logaut/complete.jsp").forward(request, response);
            } catch (FirebaseException exception) {
                session.removeAttribute(AuthWeb.PROOF);
                flash(session, AuthWeb.errorMessage(exception), "");
                AuthWeb.redirect(request, response, "error");
            }
        }
    }

    private Pending pending(HttpSession session) {
        Object value = session.getAttribute(AuthWeb.PENDING);
        if (!(value instanceof Pending) || AuthWeb.expired(((Pending) value).startedAt, now())) {
            session.removeAttribute(AuthWeb.PENDING);
            return null;
        }
        return (Pending) value;
    }

    private void expired(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        session.removeAttribute(AuthWeb.PENDING);
        session.removeAttribute(AuthWeb.PROOF);
        flash(session, EXPIRED_MESSAGE, "");
        AuthWeb.redirect(request, response, "error");
    }

    private static boolean invalidSession(FirebaseException exception) {
        return "SESSION_EXPIRED".equals(exception.getCode()) || "USER_DISABLED".equals(exception.getCode());
    }

    private static String path(HttpServletRequest request) {
        return request.getPathInfo() == null ? "/" : request.getPathInfo();
    }

    private long now() { return clock.getAsLong(); }

    private static void flash(HttpSession session, String error, String message) {
        session.setAttribute(AuthWeb.FLASH, new Flash(error, message));
    }

    private static Flash takeFlash(HttpSession session) {
        Object value = session.getAttribute(AuthWeb.FLASH);
        session.removeAttribute(AuthWeb.FLASH);
        return value instanceof Flash ? (Flash) value : new Flash("", "");
    }

    private static final class Pending implements Serializable {
        private static final long serialVersionUID = 1L;
        private FirebaseUser user;
        private final long startedAt;
        private long lastMailAttempt;

        private Pending(FirebaseUser user, long startedAt, long lastMailAttempt) {
            this.user = user;
            this.startedAt = startedAt;
            this.lastMailAttempt = lastMailAttempt;
        }
    }

    private static final class Proof implements Serializable {
        private static final long serialVersionUID = 1L;
        private FirebaseUser user;
        private final long startedAt;

        private Proof(FirebaseUser user, long startedAt) {
            this.user = user;
            this.startedAt = startedAt;
        }
    }

    private static final class Flash implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String error;
        private final String message;

        private Flash(String error, String message) {
            this.error = error;
            this.message = message;
        }
    }
}
