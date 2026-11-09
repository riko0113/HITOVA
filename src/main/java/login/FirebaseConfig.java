package login;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/** API キーをソースに書かず、実行環境から読み込むための設定です。 */
public final class FirebaseConfig {
    private final String apiKey;
    private final String projectId;

    public FirebaseConfig(String apiKey, String projectId) throws FirebaseException {
        this.apiKey = apiKey == null ? "" : apiKey.trim();
        this.projectId = projectId == null ? "" : projectId.trim();
        if (this.apiKey.isEmpty() || this.apiKey.length() > 1024
                || containsWhitespace(this.apiKey)) {
            throw configurationError();
        }
        if (this.projectId.length() > 128 || containsWhitespace(this.projectId)) {
            throw configurationError();
        }
    }

    /**
     * fallbackProperties には /WEB-INF/firebase.properties のストリームを渡します。
     * 環境変数 HITOVA_FIREBASE_CONFIG、システムプロパティ hitova.firebase.config、
     * fallbackProperties の順で設定ファイルを選びます。指定済み外部ファイルが読めない
     * 場合は停止し、別の設定へ自動で切り替えません。渡されたストリームは閉じます。
     */
    public static FirebaseConfig load(InputStream fallbackProperties) throws FirebaseException {
        Properties values = new Properties();
        try (InputStream fallback = fallbackProperties) {
            String path = nonBlank(System.getenv("HITOVA_FIREBASE_CONFIG"));
            if (path == null) {
                path = nonBlank(System.getProperty("hitova.firebase.config"));
            }
            if (path != null) {
                try (Reader external = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
                    loadProperties(values, external);
                }
            } else if (fallback != null) {
                loadProperties(values, new InputStreamReader(fallback, StandardCharsets.UTF_8));
            }
            String environmentKey = System.getenv("FIREBASE_API_KEY");
            String apiKey = environmentKey != null
                    ? environmentKey : values.getProperty("firebase.apiKey", "");
            return new FirebaseConfig(apiKey, values.getProperty("firebase.projectId", ""));
        } catch (IOException | RuntimeException exception) {
            // ファイル名や例外原因には接続情報が含まれ得るため、画面には出しません。
            throw configurationError();
        }
    }

    public String getApiKey() { return apiKey; }

    /** 設定を区別するための任意の識別子です。認証済み UID の代わりには使いません。 */
    public String getProjectId() { return projectId; }

    private static void loadProperties(Properties values, Reader source) throws IOException {
        // Windows のエディターで付いた UTF-8 BOM も、キー名の一部にしません。
        PushbackReader reader = new PushbackReader(source, 1);
        int first = reader.read();
        if (first != -1 && first != '\uFEFF') {
            reader.unread(first);
        }
        values.load(reader);
    }

    private static String nonBlank(String value) {
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }

    private static boolean containsWhitespace(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i)) || Character.isISOControl(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static FirebaseException configurationError() {
        return new FirebaseException("CONFIG_ERROR",
                "Firebase の設定を確認してください。API キーと設定ファイルの読み込みが必要です。");
    }
}
