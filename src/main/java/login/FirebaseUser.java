package login;

import java.io.Serializable;

/**
 * Firebase が確認した認証結果。HttpSession 内だけで使用します。
 * ID トークン・更新トークンを JSP、URL、ログへ出力しないでください。
 */
public final class FirebaseUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String uid;
    private final String email;
    private final boolean emailVerified;
    private final String idToken;
    private final String refreshToken;

    FirebaseUser(String uid, String email, boolean emailVerified,
            String idToken, String refreshToken) {
        this.uid = uid;
        this.email = email;
        this.emailVerified = emailVerified;
        this.idToken = idToken;
        this.refreshToken = refreshToken;
    }

    public String getUid() { return uid; }
    public String getEmail() { return email; }
    public boolean isEmailVerified() { return emailVerified; }
    public String getIdToken() { return idToken; }
    public String getRefreshToken() { return refreshToken; }
}
