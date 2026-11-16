
package profile;

import java.time.LocalDate;
import java.time.LocalDateTime;

import DAO.ProfileRegisterDAO;
import bean.Profile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ProfileRegisterAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        System.out.println("ProfileRegisterAction execute");

        request.setCharacterEncoding("UTF-8");

        // DAOのインスタンスを作成する
        ProfileRegisterDAO dao = new ProfileRegisterDAO();

        // 希望職種一覧を取得する
        request.setAttribute(
                "occupationList",
                dao.getOccupationList()
        );

        System.out.println("Request method: " + request.getMethod());

        System.out.println(
            "Occupation list in Action: "
            + request.getAttribute("occupationList")
        );

        System.out.println(
            "Occupation list size: "
            + ((java.util.List<?>) request.getAttribute("occupationList")).size()
        );

        // 初回表示の場合はプロフィール登録画面を表示する
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return "/profile/profileRegister.jsp";
        }

        // JSPから基本情報を取得する
        String lastName = request.getParameter("last_name");
        String firstName = request.getParameter("first_name");
        String lastNameKana = request.getParameter("last_name_kana");
        String firstNameKana = request.getParameter("first_name_kana");

        // 生年月日と性別を取得する
        String birthDateText = request.getParameter("birth_date");
        String genderText = request.getParameter("gender");

        // 現住所情報を取得する
        String postalCode = request.getParameter("current_postal_code");
        String prefecture = request.getParameter("current_prefecture");
        String municipality = request.getParameter("current_municipality");
        String addressDetail = request.getParameter("current_address_detail");

        // 希望雇用形態IDを取得する
        String employmentTypeText =
                request.getParameter("desired_employment_type_id");

        // 希望勤務地を取得する
        String workplacePrefecture =
                request.getParameter("workplace_prefecture");

        String workplaceMunicipality =
                request.getParameter("workplace_municipality");

        // 希望職種IDを取得する
        String jobTypeText =
                request.getParameter("desired_job_type_id");

        // 学歴情報を取得する
        String highestEducation = request.getParameter("highest_education");
        String schoolName = request.getParameter("school_name");
        String facultyName = request.getParameter("faculty_name");
        String graduationDate = request.getParameter("graduation_date");
        String graduationStatus = request.getParameter("graduation_status");

        // 職務経歴を取得する
        String workHistory = request.getParameter("work_history");

        // 希望職種が選択されていない場合
        if (jobTypeText == null || jobTypeText.isEmpty()) {
            request.setAttribute(
                    "errorMessage",
                    "希望職種を選択してください。"
            );
            return "/profile/profileInputError.jsp";
        }

        // Profile情報を格納するBeanを作成する
        Profile profile = new Profile();

        // 基本情報をBeanに設定する
        profile.setLast_name(lastName);
        profile.setFirst_name(firstName);
        profile.setLast_name_kana(lastNameKana);
        profile.setFirst_name_kana(firstNameKana);

        // 生年月日をString型からLocalDateTime型に変換する
        if (birthDateText != null && !birthDateText.isEmpty()) {

            LocalDateTime birthDate =
                    LocalDate.parse(birthDateText).atStartOfDay();

            profile.setBirth_date(birthDate);
        }

        // 性別をString型からBoolean型に変換する
        if (genderText != null && !genderText.isEmpty()) {
            profile.setgender(Boolean.valueOf(genderText));
        }

        // 現住所情報をBeanに設定する
        profile.setCurrent_postal_code(postalCode);

        // 都道府県、市区町村、番地を結合する
        String currentAddress =
                (prefecture == null ? "" : prefecture)
                + (municipality == null ? "" : municipality)
                + (addressDetail == null ? "" : addressDetail);

        profile.setCurrent_address(currentAddress);

        // 希望雇用形態IDをString型からint型に変換する
        if (employmentTypeText != null && !employmentTypeText.isEmpty()) {
            profile.setDesired_employment_type_id(
                    Integer.parseInt(employmentTypeText)
            );
        }

        // 希望勤務地を結合する
        String desiredWorkLocation =
                (workplacePrefecture == null ? "" : workplacePrefecture)
                + (workplaceMunicipality == null ? "" : workplaceMunicipality);

        profile.setDesired_work_location(desiredWorkLocation);

        // 希望職種IDをString型からInteger型に変換する
        profile.setDesired_job_type_id(
                Integer.valueOf(jobTypeText)
        );

        // 学歴情報を結合する
        String education =
                (highestEducation == null ? "" : highestEducation)
                + " / 学校名：" + (schoolName == null ? "" : schoolName)
                + " / 学部・学科：" + (facultyName == null ? "" : facultyName)
                + " / 卒業年月：" + (graduationDate == null ? "" : graduationDate)
                + " / 卒業状況：" + (graduationStatus == null ? "" : graduationStatus);

        profile.setHighest_education(education);

        // 職務経歴をBeanに設定する
        profile.setWork_history(workHistory);

        // セッションからログイン中の求職者アカウントIDを取得する

        // HttpSession session = request.getSession();

        // Long candidateAccountId =
        //         (Long) session.getAttribute("candidate_account_id");

        // if (candidateAccountId == null) {
        //     throw new Exception("ログイン情報を取得できません。");
        // }

        // profile.setCandidate_account_id(candidateAccountId);

        // テスト用：求職者アカウントIDを仮設定
        profile.setCandidate_account_id(1004L);

        // Profile情報をデータベースに登録する
        int result = dao.insert(profile);

        // 登録に成功した場合
        if (result > 0) {
            request.setAttribute("profile", profile);
            return "/profile/profileDisplay.jsp";
        }

        // 登録に失敗した場合
        request.setAttribute(
                "errorMessage",
                "プロフィールの登録に失敗しました。"
        );

        return "/profile/profileInputError.jsp";
    }
}
