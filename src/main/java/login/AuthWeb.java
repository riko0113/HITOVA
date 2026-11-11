package login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/** この認証確認画面だけで使用する、表示とセッションの補助処理です。 */
final class AuthWeb {
    static final String fingerprint_key = "authCheck.fingerprint";
    static final String pending_key = "authCheck.pending";
    static final String proof_key = "authCheck.proof";
    static final String flash_key = "authCheck.flash";
    static final long session_millis = 30L * 60L * 1000L;

    private AuthWeb() { }

    static void noStore(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0L);
    }

    static HttpSession session(HttpServletRequest request, String fingerprint, boolean create) {
        HttpSession session = request.getSession(create);
        if (session == null) {
            return null;
        }
        synchronized (session) {
            int timeout = session.getMaxInactiveInterval();
            if (timeout <= 0 || timeout > 1800) {
                session.setMaxInactiveInterval(1800);
            }
            if (!fingerprint.equals(session.getAttribute(fingerprint_key))) {
                clear(session);
                session.setAttribute(fingerprint_key, fingerprint);
            }
        }
        return session;
    }

    static void clear(HttpSession session) {
        session.removeAttribute(pending_key);
        session.removeAttribute(proof_key);
        session.removeAttribute(flash_key);
        session.removeAttribute(fingerprint_key);
    }

    /** 複数値・過大入力は受け付けません。パスワードの空白は変更しません。 */
    static String field(HttpServletRequest request, String name, int maximum) {
        String[] values = request.getParameterValues(name);
        if (values == null || values.length != 1 || values[0] == null
                || values[0].length() > maximum) {
            return null;
        }
        String value = values[0];
        for (int i = 0; i < value.length(); i++) {
            if (Character.isISOControl(value.charAt(i))) {
                return null;
            }
        }
        return value;
    }

    static String display(String value) {
        return value == null ? "" : value.trim();
    }

    static boolean checked(HttpServletRequest request, String name) {
        String value = field(request, name, 16);
        return "on".equals(value) || "true".equals(value) || "1".equals(value);
    }

    static boolean validEmail(String email) {
        return email != null && email.length() <= 254
                && email.matches("[^\\s@<>]+@[^\\s@<>]+\\.[^\\s@<>]+");
    }

    static boolean expired(long started_at, long now) {
        return now < started_at || now - started_at >= session_millis;
    }

    static void redirect(HttpServletRequest request, HttpServletResponse response, String path)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        // URL にセッション ID を付けません。認証画面は Cookie を使います。
        response.setHeader("Location", request.getContextPath() + path);
    }

    static String fingerprint(FirebaseConfig config) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(config.getApiKey().getBytes(StandardCharsets.UTF_8));
            digest.update((byte) 0);
            byte[] bytes = digest.digest(config.getProjectId().getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 is required");
        }
    }

    /** 外部応答や例外メッセージを、そのままブラウザーへ渡しません。 */
    static String errorMessage(FirebaseException exception) {
        String code = exception.getCode();
        if ("EMAIL_EXISTS".equals(code)) {
            return "このメールアドレスは登録済みです。ログインしてください。";
        }
        if ("INVALID_EMAIL".equals(code)) {
            return "メールアドレスを正しく入力してください。";
        }
        if ("WEAK_PASSWORD".equals(code) || "PASSWORD_DOES_NOT_MEET_REQUIREMENTS".equals(code)) {
            return "パスワードが Firebase の設定条件を満たしていません。";
        }
        if ("EMAIL_NOT_FOUND".equals(code) || "INVALID_PASSWORD".equals(code)
                || "INVALID_LOGIN_CREDENTIALS".equals(code) || "USER_DISABLED".equals(code)) {
            return "メールアドレスまたはパスワードを確認してください。";
        }
        if ("SESSION_EXPIRED".equals(code)) {
            return "認証の有効期限が切れました。もう一度ログインしてください。";
        }
        if ("RATE_LIMITED".equals(code)) {
            return "操作が多すぎます。時間をおいて再度お試しください。";
        }
        if ("CONFIG_ERROR".equals(code)) {
            return "認証サービスの設定を確認する必要があります。管理者に確認してください。";
        }
        if ("CONNECTION_ERROR".equals(code)) {
            return "認証サービスに接続できません。しばらくしてからお試しください。";
        }
        return "認証処理を確認できません。時間をおいて再度お試しください。";
    }
}
