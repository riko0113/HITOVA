package profile;




import java.time.LocalDate;
import java.time.LocalDateTime;

import DAO.ProfileRegisterDAO;
import bean.Profile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ProfileRegisterAction extends Action {

    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // JSPから基本情報を取得する
        String lastName = request.getParameter("last_name");
        String firstName = request.getParameter("first_name");
        String lastNameKana = request.getParameter("last_name_kana");
        String firstNameKana = request.getParameter("first_name_kana");

        // JSPから生年月日と性別を取得する
        String birthDateText = request.getParameter("birth_date");
        String genderText = request.getParameter("gender");

        // JSPから現住所情報を取得する
        String postalCode = request.getParameter("current_postal_code");
        String prefecture = request.getParameter("current_prefecture");
        String municipality = request.getParameter("current_municipality");
        String addressDetail = request.getParameter("current_address_detail");

        // JSPから希望雇用形態IDを取得する
        String employmentTypeText =
                request.getParameter("desired_employment_type_id");

        // JSPから希望勤務地を取得する
        String workplacePrefecture =
                request.getParameter("workplace_prefecture");
        String workplaceMunicipality =
                request.getParameter("workplace_municipality");

        // JSPから希望職種IDを取得する
        String jobTypeText =
                request.getParameter("desired_job_type_id");

        // JSPから学歴情報を取得する
        String schoolName = request.getParameter("school_name");
        String facultyName = request.getParameter("faculty_name");
        String graduationDate = request.getParameter("graduation_date");
        String graduationStatus = request.getParameter("graduation_status");

        // JSPから職務経歴を取得する
        String workHistory = request.getParameter("work_history");

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

        // GenderをString型からBoolean型に変換する
        if (genderText != null && !genderText.isEmpty()) {
            profile.setgender(Boolean.valueOf(genderText));
        }

        // 現住所情報をBeanに設定する
        profile.setCurrent_postal_code(postalCode);

        // 都道府県、市区町村、番地を結合して現住所を作成する
        String currentAddress =
                prefecture + municipality + addressDetail;

        profile.setCurrent_address(currentAddress);

        // 希望雇用形態IDをString型からint型に変換する
        if (employmentTypeText != null && !employmentTypeText.isEmpty()) {
            profile.setDesired_employment_type_id(
                    Integer.parseInt(employmentTypeText));
        }

        // 希望勤務地の都道府県と市区町村を結合する
        String desiredWorkLocation =
                workplacePrefecture + workplaceMunicipality;

        profile.setDesired_work_location(desiredWorkLocation);

        // 希望職種IDをString型からInteger型に変換する
        if (jobTypeText != null && !jobTypeText.isEmpty()) {
            profile.setDesired_job_type_id(
                    Integer.valueOf(jobTypeText));
        }

        // 学歴情報を結合して最終学歴を作成する
        String highestEducation =
                "学校名：" + schoolName
                + "、学部・学科：" + facultyName
                + "、卒業年月：" + graduationDate
                + "、卒業状況：" + graduationStatus;

        profile.setHighest_education(highestEducation);

        // 職務経歴をBeanに設定する
        profile.setWork_history(workHistory);

        // セッションからログイン中の求職者アカウントIDを取得する
        HttpSession session = request.getSession();

        Long candidateAccountId =
                (Long) session.getAttribute("candidate_account_id");

        profile.setCandidate_account_id(candidateAccountId);

        // ProfileDAOのインスタンスを作成する
        ProfileRegisterDAO dao = new ProfileRegisterDAO();

        // Profile情報をデータベースに登録する
        int result = dao.insert(profile);

        // 登録に成功した場合
        if (result > 0) {
            request.setAttribute("profile", profile);
            return "/profile/profileDisplay.jsp";
        }

        // 登録に失敗した場合
        return "/profile/profileInputError.jsp";
    }
}
