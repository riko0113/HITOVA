package tool;

import java.time.Duration;
import java.util.Locale;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

/**
 * Amazon S3 の署名付きURL生成や、オブジェクトキーに関する補助処理を行うユーティリティクラス。
 */
public class S3PresignHelper {

    /** 対象のS3バケット名 */
    private static final String BUCKET_NAME = "hitova-187872251103-us-east-1-an";

    /** S3バケットが存在するリージョン（米国東部・バージニア北部） */
    private static final Region REGION = Region.US_EAST_1;

    /**
     * 指定されたS3オブジェクトキーに対する閲覧用（GET）の署名付きURLを発行します。
     * 署名付きURLの有効期限は15分です。
     *
     * @param objectKey S3バケット内のオブジェクトキー（パス）
     * @return 発行された署名付きURL文字列。objectKeyがnullまたは空文字の場合はnullを返す
     */
    public static String createPresignedGetUrl(String objectKey) {
        // キーが未指定（null または空白のみ）の場合はURLを発行せず null を返却
        if (objectKey == null || objectKey.trim().isEmpty()) {
            return null;
        }

        // try-with-resources で S3Presigner を生成し、処理終了時に確実にクローズする
        try (S3Presigner presigner = S3Presigner.builder()
                .region(REGION)
                // 環境変数、システムプロパティ、EC2/ECSロールなどのデフォルト認証情報プロバイダを使用
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()) {

            // 取得対象となるバケットとオブジェクトキーを指定したリクエストを作成
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(objectKey)
                    .build();

            // 署名付きURLの有効期限（15分）と取得リクエストを設定
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(15))
                    .getObjectRequest(getObjectRequest)
                    .build();

            // 署名付きURLを生成し、文字列表現に変換して返却
            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            return presignedRequest.url().toString();
        }
    }

    /**
     * オブジェクトキーの拡張子から、動画ファイル（.mp4 / .webm / .mov）かどうかを判定します。
     * 大文字・小文字は区別しません。
     *
     * @param objectKey S3バケット内のオブジェクトキー（ファイル名・パス）
     * @return 対象の動画拡張子に一致する場合はtrue、それ以外やnullの場合はfalse
     */
    public static boolean isVideoKey(String objectKey) {
        if (objectKey == null) {
            return false;
        }
        // ロケール依存の大文字小文字変換トラブルを防ぐため Locale.ROOT を指定して小文字化
        String lower = objectKey.toLowerCase(Locale.ROOT);
        return lower.endsWith(".mp4") || lower.endsWith(".webm") || lower.endsWith(".mov");
    }
}