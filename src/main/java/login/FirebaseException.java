package login;

// Firebase認証の失敗理由をエラーコードで伝える。
public final class FirebaseException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String code;

    public FirebaseException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
