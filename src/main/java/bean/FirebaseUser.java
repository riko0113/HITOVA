package bean;

import java.io.Serializable;

// FirebaseのUID、メール確認状態、認証トークンなどを持つ。
public final class FirebaseUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String uid;
    private final String email;
    private final boolean email_verified;
    private final String id_token;
    private final String refresh_token;

    public FirebaseUser(String uid, String email, boolean email_verified,
            String id_token, String refresh_token) {
        this.uid = uid;
        this.email = email;
        this.email_verified = email_verified;
        this.id_token = id_token;
        this.refresh_token = refresh_token;
    }

    public String getUid() {
        return uid;
    }
    public String getEmail() {
        return email;
    }
    public boolean isEmailVerified() {
        return email_verified;
    }
    public String getIdToken() {
        return id_token;
    }
    public String getRefreshToken() {
        return refresh_token;
    }
}
