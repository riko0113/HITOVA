package login;

/** 画面へ渡しても秘密情報を含まない認証エラーです。 */
public final class FirebaseException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String code;

    public FirebaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
