package DAO;

import bean.FirebaseUser;
import login.FirebaseConfig;
import login.FirebaseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/** ログイン・新規登録に必要な情報をFirebaseから取得します。 */
public final class LoginDAO {
    private static final String ACCOUNTS = "https://identitytoolkit.googleapis.com/v1/accounts:";
    private static final String TOKEN = "https://securetoken.googleapis.com/v1/token";
    private static final int MAX_RESPONSE_BYTES = 65536;
    private static final String SESSION_MESSAGE = "認証の有効期限が切れました。もう一度ログインしてください。";
    private final String encodedApiKey;
    private final Transport transport;

    public LoginDAO(FirebaseConfig config) {
        this(config, new HttpsTransport());
    }

    // 接続先は固定したまま、テストでは通信部分だけを置き換えられます。
    LoginDAO(FirebaseConfig config, Transport transport) {
        if (config == null || transport == null) {
            throw new IllegalArgumentException("Firebase の設定と通信処理が必要です。");
        }
        this.encodedApiKey = encode(config.getApiKey());
        this.transport = transport;
    }

    // 新規登録の結果を取得する
    public FirebaseUser register(String email, String password) throws FirebaseException {
        return authenticate("signUp", email, password);
    }

    // メールアドレスとパスワードでログインする
    public FirebaseUser login(String email, String password) throws FirebaseException {
        return authenticate("signInWithPassword", email, password);
    }

    private FirebaseUser authenticate(String operation, String email, String password)
            throws FirebaseException {
        String address = email == null ? "" : email.trim();
        if (address.isEmpty() || address.length() > 254 || hasControl(address)
                || password == null || password.isEmpty() || password.length() > 4096) {
            throw new FirebaseException("INVALID_INPUT", "メールアドレスとパスワードを確認してください。");
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("email", address);
        payload.addProperty("password", password);
        payload.addProperty("returnSecureToken", true);
        JsonObject result = account(operation, payload);
        // Firebaseから返ったユーザーIDと認証情報を取り出す
        String uid = requiredString(result, "localId", 128);
        String idToken = requiredString(result, "idToken", 16384);
        String refreshToken = requiredString(result, "refreshToken", 16384);
        return lookup(idToken, refreshToken, uid);
    }

    public FirebaseUser refresh(FirebaseUser user) throws FirebaseException {
        requireUser(user);
        String body = "grant_type=refresh_token&refresh_token=" + encode(user.getRefreshToken());
        JsonObject result = post(TOKEN, "application/x-www-form-urlencoded", body);
        String uid = requiredString(result, "user_id", 128);
        if (!user.getUid().equals(uid)) {
            throw sessionError();
        }
        return lookup(requiredString(result, "id_token", 16384),
                requiredString(result, "refresh_token", 16384), uid);
    }

    /** 期限切れの場合は refresh() の戻り値でセッションを更新してから再実行します。 */
    public boolean sendVerification(FirebaseUser user) throws FirebaseException {
        requireUser(user);
        FirebaseUser checked = lookup(user.getIdToken(), user.getRefreshToken(), user.getUid());
        if (checked.isEmailVerified()) {
            return false;
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("requestType", "VERIFY_EMAIL");
        payload.addProperty("idToken", checked.getIdToken());
        account("sendOobCode", payload);
        return true;
    }

    /** UID とメール確認状態は必ず Firebase の応答から決めます。 */
    private FirebaseUser lookup(String idToken, String refreshToken, String expectedUid)
            throws FirebaseException {
        JsonObject payload = new JsonObject();
        payload.addProperty("idToken", idToken);
        JsonObject result = account("lookup", payload);
        JsonElement usersValue = result.get("users");
        if (usersValue == null || !usersValue.isJsonArray()) {
            throw responseError();
        }
        JsonArray users = usersValue.getAsJsonArray();
        if (users.size() != 1 || !users.get(0).isJsonObject()) {
            throw responseError();
        }
        JsonObject user = users.get(0).getAsJsonObject();
        String uid = requiredString(user, "localId", 128);
        if (!uid.equals(expectedUid)) {
            throw sessionError();
        }
        if (optionalBoolean(user, "disabled")) {
            throw new FirebaseException("USER_DISABLED", "このアカウントでは認証できません。管理者に確認してください。");
        }
        String email = requiredString(user, "email", 254);
        boolean emailVerified = optionalBoolean(user, "emailVerified");
        // 取得した値をBeanにまとめて返す
        return new FirebaseUser(uid, email, emailVerified, idToken, refreshToken);
    }

    private JsonObject account(String operation, JsonObject payload) throws FirebaseException {
        return post(ACCOUNTS + operation, "application/json", payload.toString());
    }

    private JsonObject post(String endpoint, String contentType, String body) throws FirebaseException {
        Response response;
        try {
            response = transport.post(URI.create(endpoint + "?key=" + encodedApiKey), contentType, body);
        } catch (IOException | RuntimeException exception) {
            // URL には API キーが含まれるため、元の例外・応答本文は表示も記録もしません。
            throw new FirebaseException("CONNECTION_ERROR",
                    "認証サービスに接続できません。しばらくしてからお試しください。");
        }
        if (response == null || response.body == null || response.body.length() > MAX_RESPONSE_BYTES) {
            throw responseError();
        }
        JsonObject json = parseObject(response.body);
        if (response.status != 200) {
            throw firebaseError(json);
        }
        // HTTP 200 に error が混ざった場合も、成功として扱いません。
        if (json.has("error")) {
            throw firebaseError(json);
        }
        return json;
    }

    private static void requireUser(FirebaseUser user) throws FirebaseException {
        if (user == null || user.getUid() == null || user.getIdToken() == null
                || user.getRefreshToken() == null || user.getUid().isEmpty()
                || user.getIdToken().isEmpty() || user.getRefreshToken().isEmpty()) {
            throw sessionError();
        }
    }

    private static String requiredString(JsonObject object, String name, int maxLength)
            throws FirebaseException {
        JsonElement value = object.get(name);
        if (value == null || !value.isJsonPrimitive() || !value.getAsJsonPrimitive().isString()) {
            throw responseError();
        }
        String text = value.getAsString();
        if (text.trim().isEmpty() || text.length() > maxLength || hasControl(text)) {
            throw responseError();
        }
        return text;
    }

    private static boolean optionalBoolean(JsonObject object, String name) throws FirebaseException {
        JsonElement value = object.get(name);
        if (value == null) {
            return false;
        }
        if (!value.isJsonPrimitive() || !value.getAsJsonPrimitive().isBoolean()) {
            throw responseError();
        }
        return value.getAsBoolean();
    }

    private static boolean hasControl(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isISOControl(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static JsonObject parseObject(String body) throws FirebaseException {
        try (JsonReader reader = new JsonReader(new StringReader(body))) {
            reader.setLenient(false);
            JsonElement value = readJson(reader, 0);
            if (!value.isJsonObject() || reader.peek() != JsonToken.END_DOCUMENT) {
                throw responseError();
            }
            return value.getAsJsonObject();
        } catch (IOException | RuntimeException exception) {
            throw responseError();
        }
    }

    // 応答のサイズ・深さを制限し、不正な型、重複キー、緩い JSON 表記を受け付けません。
    private static JsonElement readJson(JsonReader reader, int depth) throws IOException {
        if (depth > 32) {
            throw new IOException("JSON nesting limit");
        }
        switch (reader.peek()) {
        case BEGIN_OBJECT:
            JsonObject object = new JsonObject();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (object.has(name)) {
                    throw new IOException("Duplicate JSON key");
                }
                object.add(name, readJson(reader, depth + 1));
            }
            reader.endObject();
            return object;
        case BEGIN_ARRAY:
            JsonArray array = new JsonArray();
            reader.beginArray();
            while (reader.hasNext()) {
                array.add(readJson(reader, depth + 1));
            }
            reader.endArray();
            return array;
        case STRING:
            return new JsonPrimitive(reader.nextString());
        case NUMBER:
            return new JsonPrimitive(new BigDecimal(reader.nextString()));
        case BOOLEAN:
            return new JsonPrimitive(reader.nextBoolean());
        case NULL:
            reader.nextNull();
            return JsonNull.INSTANCE;
        default:
            throw new IOException("Invalid JSON value");
        }
    }

    private static FirebaseException firebaseError(JsonObject response) {
        String code = "";
        JsonElement error = response.get("error");
        if (error != null && error.isJsonObject()) {
            JsonElement message = error.getAsJsonObject().get("message");
            if (message != null && message.isJsonPrimitive() && message.getAsJsonPrimitive().isString()) {
                code = message.getAsString().split("[ :]+", 2)[0];
            }
        }
        switch (code) {
        case "EMAIL_EXISTS":
            return new FirebaseException(code, "このメールアドレスは登録済みです。ログインしてください。");
        case "INVALID_EMAIL":
            return new FirebaseException(code, "メールアドレスを正しく入力してください。");
        case "WEAK_PASSWORD":
        case "PASSWORD_DOES_NOT_MEET_REQUIREMENTS":
            return new FirebaseException(code, "パスワードが Firebase の設定条件を満たしていません。");
        case "EMAIL_NOT_FOUND":
        case "INVALID_PASSWORD":
        case "INVALID_LOGIN_CREDENTIALS":
        case "USER_DISABLED":
            return new FirebaseException(code, "メールアドレスまたはパスワードを確認してください。");
        case "INVALID_ID_TOKEN":
        case "TOKEN_EXPIRED":
        case "INVALID_REFRESH_TOKEN":
        case "USER_NOT_FOUND":
        case "CREDENTIAL_TOO_OLD_LOGIN_AGAIN":
        case "INVALID_GRANT":
            return sessionError();
        case "TOO_MANY_ATTEMPTS_TRY_LATER":
        case "QUOTA_EXCEEDED":
        case "RESET_PASSWORD_EXCEED_LIMIT":
            return new FirebaseException("RATE_LIMITED", "操作が多すぎます。時間をおいて再度お試しください。");
        case "OPERATION_NOT_ALLOWED":
        case "API_KEY_INVALID":
        case "INVALID_API_KEY":
        case "PROJECT_NOT_FOUND":
        case "CONFIGURATION_NOT_FOUND":
            return new FirebaseException("CONFIG_ERROR", "Firebase の設定とメール／パスワード認証の有効化を確認してください。");
        default:
            return new FirebaseException("AUTH_ERROR", "認証処理に失敗しました。時間をおいて再度お試しください。");
        }
    }

    private static FirebaseException responseError() {
        return new FirebaseException("INVALID_RESPONSE", "認証サービスの応答を確認できません。もう一度お試しください。");
    }

    private static FirebaseException sessionError() {
        return new FirebaseException("SESSION_EXPIRED", SESSION_MESSAGE);
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (java.io.UnsupportedEncodingException impossible) {
            throw new IllegalStateException("UTF-8 is required");
        }
    }

    interface Transport {
        Response post(URI uri, String contentType, String body) throws IOException;
    }

    static final class Response {
        final int status;
        final String body;

        Response(int status, String body) {
            this.status = status;
            this.body = body;
        }
    }

    private static final class HttpsTransport implements Transport {
        @Override
        public Response post(URI uri, String contentType, String body) throws IOException {
            // URI はこのクラス内の公式 HTTPS エンドポイントからだけ組み立てます。
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            try {
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(20000);
                connection.setInstanceFollowRedirects(false);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", contentType + "; charset=UTF-8");
                connection.setRequestProperty("X-Firebase-Locale", "ja");
                connection.setDoOutput(true);
                byte[] request = body.getBytes(StandardCharsets.UTF_8);
                connection.setFixedLengthStreamingMode(request.length);
                try (OutputStream output = connection.getOutputStream()) {
                    output.write(request);
                }
                int status = connection.getResponseCode();
                InputStream stream = status >= 400 ? connection.getErrorStream() : connection.getInputStream();
                return new Response(status, readResponse(stream));
            } finally {
                connection.disconnect();
            }
        }

        private static String readResponse(InputStream source) throws IOException {
            if (source == null) {
                return "";
            }
            try (InputStream input = source; ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = input.read(buffer)) != -1) {
                    if (output.size() + length > MAX_RESPONSE_BYTES) {
                        throw new IOException("Response size limit");
                    }
                    output.write(buffer, 0, length);
                }
                return new String(output.toByteArray(), StandardCharsets.UTF_8);
            }
        }
    }
}
