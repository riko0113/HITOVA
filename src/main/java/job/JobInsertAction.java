package job;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import DAO.JobDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import login.AuthWeb;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import tool.Action;

public class JobInsertAction extends Action {

    // S3アップロード設定
    private static final String BUCKET_NAME = "hitova-187872251103-us-east-1-an";
    private static final Region REGION = Region.US_EAST_1;
    private static final long MAX_FILE_SIZE = 10L * 1024 * 1024; // 求人画像上限: 10MB

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/png",
            "image/jpeg",
            "image/webp"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            ".png",
            ".jpg",
            ".jpeg",
            ".webp"
    );

    /**
     * 文字列をintへ安全に変換する。未入力や数値以外の場合はデフォルト値を返す。
     */
    private int parseIntOrDefault(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            request.setCharacterEncoding("UTF-8");

            // ログイン状態チェック
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute(AuthWeb.company_key);

            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                return "null";
            }

            // フォーム入力値の取得（数値項目は未選択・不正値でも例外にならないよう安全に変換）
            int jobTypeId = parseIntOrDefault(request.getParameter("job_type_id"), -1);
            String title = request.getParameter("title");
            int employmentTypeId = parseIntOrDefault(request.getParameter("employment_type_id"), -1);
            String description = request.getParameter("description");
            String requirement = request.getParameter("requirement");
            String headcount = request.getParameter("headcount");
            String location = request.getParameter("location");
            String access = request.getParameter("access");
            int salaryTypeId = parseIntOrDefault(request.getParameter("salary_type_id"), -1);
            String salary = request.getParameter("salary");
            String allowance = request.getParameter("allowance");
            String transportationFeeStr = request.getParameter("transportation_fee");
            String workingHours = request.getParameter("working_hours");
            String holiday = request.getParameter("holiday");
            String smokingPrevention = request.getParameter("smoking_prevention");
            String[] insurance = request.getParameterValues("insurance");
            String benefits = request.getParameter("benefits");
            Part imageFile = request.getPart("image_url");
            String workplacePr = request.getParameter("workplace_pr");
            String[] selectionProcessArray = request.getParameterValues("selection_process");
            String interviewLocation = request.getParameter("interview_location");
            String publicationStatusStr = request.getParameter("publication_status");

            boolean hasError = false;

            // 1. 職種ID（必須 / 1以上）
            if (jobTypeId <= 0) {
                request.setAttribute("jobTypeError", "職種を選択してください。");
                hasError = true;
            }

            // 2. 求人タイトル（必須 / 20文字以内）
            if (title == null || title.trim().isEmpty()) {
                request.setAttribute("titleError", "求人タイトルを入力してください。");
                hasError = true;
            } else if (title.length() > 20) {
                request.setAttribute("titleError", "求人タイトルは20文字以内で入力してください。");
                hasError = true;
            }

            // 3. 雇用形態ID（必須 / 1以上）
            if (employmentTypeId <= 0) {
                request.setAttribute("employmentTypeError", "雇用形態を選択してください。");
                hasError = true;
            }

            // 4. 仕事内容（必須）
            if (description == null || description.trim().isEmpty()) {
                request.setAttribute("descriptionError", "仕事内容を入力してください。");
                hasError = true;
            }

            // 5. 応募資格・条件（必須 / 500文字以内）
            if (requirement == null || requirement.trim().isEmpty()) {
                request.setAttribute("requirementError", "応募資格を入力してください。");
                hasError = true;
            } else if (requirement.length() > 500) {
                request.setAttribute("requirementError", "応募資格は500文字以内で入力してください。");
                hasError = true;
            }

            // 6. 募集人数（任意 / 10文字以内）
            if (headcount != null && headcount.length() > 10) {
                request.setAttribute("headcountError", "採用人数は10文字以内で入力してください。");
                hasError = true;
            }

            // 7. 勤務地（必須）
            if (location == null || location.trim().isEmpty()) {
                request.setAttribute("locationError", "勤務地を入力してください。");
                hasError = true;
            }

            // 8. アクセス（任意 / 50文字以内）
            if (access != null && access.length() > 50) {
                request.setAttribute("accessError", "アクセス情報は50文字以内で入力してください。");
                hasError = true;
            }

            // 9. 給与区分ID（必須 / 1以上）
            if (salaryTypeId <= 0) {
                request.setAttribute("salaryTypeError", "給与形態を選択してください。");
                hasError = true;
            }

            // 10. 給与（必須 / 30文字以内）
            if (salary == null || salary.trim().isEmpty()) {
                request.setAttribute("salaryError", "給与を入力してください。");
                hasError = true;
            } else if (salary.length() > 30) {
                request.setAttribute("salaryError", "給与は30文字以内で入力してください。");
                hasError = true;
            }

            // 11. 諸手当（必須 / 200文字以内）
            if (allowance == null || allowance.trim().isEmpty()) {
                request.setAttribute("allowanceError", "諸手当を入力してください（ない場合は「なし」等）。");
                hasError = true;
            } else if (allowance.length() > 200) {
                request.setAttribute("allowanceError", "諸手当は200文字以内で入力してください。");
                hasError = true;
            }

            // 12. 交通費支給フラグ（必須）
            if (transportationFeeStr == null || transportationFeeStr.trim().isEmpty()) {
                request.setAttribute("transportationFeeError", "交通費支給の有無を選択してください。");
                hasError = true;
            }
            boolean transportationFee = Boolean.parseBoolean(transportationFeeStr);

            // 13. 勤務時間（必須 / 50文字以内）
            if (workingHours == null || workingHours.trim().isEmpty()) {
                request.setAttribute("workingHoursError", "勤務時間を入力してください。");
                hasError = true;
            } else if (workingHours.length() > 50) {
                request.setAttribute("workingHoursError", "勤務時間は50文字以内で入力してください。");
                hasError = true;
            }

            // 14. 休日・休暇（必須 / 30文字以内）
            if (holiday == null || holiday.trim().isEmpty()) {
                request.setAttribute("holidayError", "休日・休暇を入力してください。");
                hasError = true;
            } else if (holiday.length() > 30) {
                request.setAttribute("holidayError", "休日・休暇は30文字以内で入力してください。");
                hasError = true;
            }

            // 15. 受動喫煙対策（必須 / 50文字以内）
            if (smokingPrevention == null || smokingPrevention.trim().isEmpty()) {
                request.setAttribute("smokingPreventionError", "受動喫煙防止措置を入力してください。");
                hasError = true;
            } else if (smokingPrevention.length() > 50) {
                request.setAttribute("smokingPreventionError", "受動喫煙防止措置は50文字以内で入力してください。");
                hasError = true;
            }

            // 16. 加入保険（必須 / 1件以上）
            if (insurance == null || insurance.length == 0) {
                request.setAttribute("insuranceError", "加入保険を少なくとも1つ選択してください。");
                hasError = true;
            }

            // 17. 福利厚生（任意 / 200文字以内）
            if (benefits != null && benefits.length() > 200) {
                request.setAttribute("benefitsError", "福利厚生は200文字以内で入力してください。");
                hasError = true;
            }

            // 18. 求人画像（必須 / 10MB以内 / PNG・JPEG・WebP）
            String extension = "";
            if (imageFile == null || imageFile.getSize() <= 0) {
                request.setAttribute("imageUrlError", "求人画像を選択してください。");
                hasError = true;
            } else {
                // ファイル名から拡張子を抽出し小文字化
                String submittedFileName = imageFile.getSubmittedFileName();
                if (submittedFileName != null) {
                    int dotIndex = submittedFileName.lastIndexOf(".");
                    if (dotIndex >= 0) {
                        extension = submittedFileName.substring(dotIndex).toLowerCase(Locale.ROOT);
                    }
                }

                // MIMEタイプを小文字化して比較用に正規化
                String contentType = imageFile.getContentType();
                String normalizedContentType = (contentType != null)
                        ? contentType.toLowerCase(Locale.ROOT)
                        : "";

                if (imageFile.getSize() > MAX_FILE_SIZE) {
                    request.setAttribute("imageUrlError", "画像は10MB以内で選択してください。");
                    hasError = true;
                } else if (!ALLOWED_CONTENT_TYPES.contains(normalizedContentType)
                        || !ALLOWED_EXTENSIONS.contains(extension)) {
                    request.setAttribute("imageUrlError", "画像（PNG / JPEG / WebP）を選択してください。");
                    hasError = true;
                }
            }

            // 19. 職場PR（任意 / 200文字以内）
            if (workplacePr != null && workplacePr.length() > 200) {
                request.setAttribute("workplacePrError", "職場PRは200文字以内で入力してください。");
                hasError = true;
            }

            // 20. 選考プロセス（配列の空要素を除外して1文字列へ結合 / 必須 / 150文字以内）
            String selectionProcess = "";
            if (selectionProcessArray != null) {
                List<String> validSteps = new ArrayList<>();
                for (String step : selectionProcessArray) {
                    if (step != null && !step.trim().isEmpty()) {
                        validSteps.add(step.trim());
                    }
                }
                selectionProcess = String.join(" → ", validSteps);
            }

            if (selectionProcess.isEmpty()) {
                request.setAttribute("selectionProcessError", "選考プロセスを入力してください。");
                hasError = true;
            } else if (selectionProcess.length() > 150) {
                request.setAttribute("selectionProcessError", "選考プロセスは150文字以内で入力してください。");
                hasError = true;
            }

            // 21. 面接場所（必須）
            if (interviewLocation == null || interviewLocation.trim().isEmpty()) {
                request.setAttribute("interviewLocationError", "面接場所を入力してください。");
                hasError = true;
            }

            // 22. 公開ステータス（必須）
            if (publicationStatusStr == null || publicationStatusStr.trim().isEmpty()) {
                request.setAttribute("publicationStatusError", "公開ステータスを選択してください。");
                hasError = true;
            }
            boolean publicationStatus = Boolean.parseBoolean(publicationStatusStr);

            // バリデーションエラー時は入力画面へ戻す
            if (hasError) {
                return "/jobregister/jobregister.jsp";
            }

            // S3へ画像をアップロードし、DB保存用のオブジェクトキーを生成
            String s3FileName = "jobs/" + UUID.randomUUID().toString() + extension;

            try (S3Client s3Client = S3Client.builder()
                    .region(REGION)
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();
                 InputStream fileContent = imageFile.getInputStream()) {

                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(s3FileName)
                        .contentType(imageFile.getContentType())
                        .build();

                s3Client.putObject(
                        putObjectRequest,
                        RequestBody.fromInputStream(fileContent, imageFile.getSize())
                );
            }

            String imageUrl = s3FileName;

            // 求人データをDBに登録して一覧画面へリダイレクト
            JobDAO dao = new JobDAO();
            dao.insert(
                    userId,
                    jobTypeId,
                    title,
                    employmentTypeId,
                    description,
                    requirement,
                    headcount,
                    location,
                    access,
                    salaryTypeId,
                    salary,
                    allowance,
                    transportationFee,
                    workingHours,
                    holiday,
                    smokingPrevention,
                    insurance,
                    benefits,
                    imageUrl,
                    workplacePr,
                    selectionProcess,
                    interviewLocation,
                    publicationStatus
            );
            
            //登録が成功したら、セッションを削除
            session.removeAttribute("infoList");
            session.removeAttribute("companyInfo");
            
            response.sendRedirect(request.getContextPath() + "/job/JobList.action");
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "データの登録中にエラーが発生しました。しばらく経ってから再度お試しください。");
            return "/error.jsp";
        }
    }
}