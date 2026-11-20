package login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// フォームの入力取得やセッションを扱う共通処理。
public final class AuthWeb {
    static final String fingerprint_key = "authCheck.fingerprint";
    // メール確認待ちの情報をセッションに保存するときの名前。
    static final String pending_key = "authCheck.pending";
    // ログイン済みの求職者IDを保存する名前。他の画面でも使う。
    public static final String candidate_key = "candidate_account_id";
    static final String mail_sent_key = "login.mailSent";
    public static final String nickname_key = "nickname"; 

    // メール確認待ちを始めてからの有効時間（30分）。
    static final long session_millis = 30L * 60L * 1000L;

    private AuthWeb() { }

    static void noStore(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
    }

    static HttpSession session(HttpServletRequest request, String fingerprint) {
        HttpSession session = request.getSession();
        synchronized (session) {
            if (!fingerprint.equals(session.getAttribute(fingerprint_key))) {
                clear(session);
                session.setAttribute(fingerprint_key, fingerprint);
            }
        }
        return session;
    }

    static void clear(HttpSession session) {
        session.removeAttribute(pending_key);
        session.removeAttribute(candidate_key);
        session.removeAttribute(mail_sent_key);
        session.removeAttribute(nickname_key);
        session.removeAttribute(fingerprint_key);
    }

    // フォームの値を1件取り出し、文字数と制御文字を確認する。
    static String field(HttpServletRequest request, String name, int maximum) {
        String value = request.getParameter(name);
        if (value == null || value.length() > maximum) {
            return null;
        }
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
}
