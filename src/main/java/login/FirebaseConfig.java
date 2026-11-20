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

// Firebaseの接続設定を読み込む。
public final class FirebaseConfig {
    private final String api_key;
    private final String project_id;

    public FirebaseConfig(String api_key, String project_id) throws FirebaseException {
        this.api_key = api_key == null ? "" : api_key.trim();
        this.project_id = project_id == null ? "" : project_id.trim();
        if (this.api_key.isEmpty() || this.api_key.length() > 1024
                || containsWhitespace(this.api_key)) {
            throw configurationError();
        }
        if (this.project_id.length() > 128 || containsWhitespace(this.project_id)) {
            throw configurationError();
        }
    }

    public static FirebaseConfig load(InputStream fallback_properties) throws FirebaseException {
        Properties values = new Properties();
        try (InputStream fallback = fallback_properties) {
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
            String environment_key = System.getenv("FIREBASE_API_KEY");
            String api_key = environment_key != null
                    ? environment_key : values.getProperty("firebase.apiKey", "");
            return new FirebaseConfig(api_key, values.getProperty("firebase.projectId", ""));
        } catch (IOException | RuntimeException exception) {
            throw configurationError();
        }
    }

    public String getApiKey() { return api_key; }

    public String getProjectId() { return project_id; }

    private static void loadProperties(Properties values, Reader source) throws IOException {
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
        return new FirebaseException("CONFIG_ERROR");
    }
}
