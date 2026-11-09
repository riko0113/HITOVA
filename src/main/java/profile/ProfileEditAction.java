package profile;

import java.time.LocalDate;

import DAO.ProfileDisplayDAO;
import DAO.ProfileEditDAO;
import bean.Profile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// プロフィール編集アクション
public class ProfileEditAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // ログイン中の求職者IDを取得
        Long candidateAccountId =
                (Long) session.getAttribute("candidate_account_id");

        // ログインしていない場合
        if (candidateAccountId == null) {
            return "/login/login.jsp";
        }

        // GET：プロフィール編集画面を表示
        if ("GET".equalsIgnoreCase(request.getMethod())) {

            ProfileDisplayDAO displayDAO = new ProfileDisplayDAO();

            Profile profile = displayDAO.search(candidateAccountId);

            // プロフィール未登録の場合
            if (profile == null) {
                return "/profile/profileRegister.jsp";
            }

            request.setAttribute("profile", profile);

            return "/profile/profileEdit.jsp";
        }

        // POST：プロフィールを更新
        if ("POST".equalsIgnoreCase(request.getMethod())) {

            Profile profile = new Profile();

            // 求職者ID
            profile.setCandidate_account_id(candidateAccountId);

            // 基本情報
            profile.setLast_name(request.getParameter("last_name"));
            profile.setFirst_name(request.getParameter("first_name"));
            profile.setLast_name_kana(request.getParameter("last_name_kana"));
            profile.setFirst_name_kana(request.getParameter("first_name_kana"));

            // 生年月日
            String birthDate = request.getParameter("birth_date");

            if (birthDate != null && !birthDate.isEmpty()) {
                profile.setBirth_date(
                        LocalDate.parse(birthDate).atStartOfDay());
            }

            // 性別
            String gender = request.getParameter("gender");

            if (gender != null && !gender.isEmpty()) {
                profile.setgender(Boolean.valueOf(gender));
            }

            // 現住所
            profile.setCurrent_postal_code(
                    request.getParameter("current_postal_code"));

            profile.setCurrent_address(
                    request.getParameter("current_address"));

            // 希望条件
            String employmentType =
                    request.getParameter("desired_employment_type_id");

            if (employmentType != null && !employmentType.isEmpty()) {
                profile.setDesired_employment_type_id(
                        Integer.parseInt(employmentType));
            }

            profile.setDesired_work_location(
                    request.getParameter("desired_work_location"));

            String jobType =
                    request.getParameter("desired_job_type_id");

            if (jobType != null && !jobType.isEmpty()) {
                profile.setDesired_job_type_id(
                        Integer.valueOf(jobType));
            }

            // 学歴
            profile.setHighest_education(
                    request.getParameter("highest_education"));

            // 職歴
            profile.setWork_history(
                    request.getParameter("work_history"));

            // プロフィール更新
            ProfileEditDAO editDAO = new ProfileEditDAO();

            // DAOの戻り値はint型
            int result = editDAO.update(profile);

            // 更新対象が0件の場合
            if (result == 0) {
                request.setAttribute("profile", profile);
                request.setAttribute("errorMessage", "プロフィールを更新できませんでした。");

                return "/profile/profileEdit.jsp";
            }

            // 更新後のプロフィールを再取得
            ProfileDisplayDAO displayDAO = new ProfileDisplayDAO();

            Profile updatedProfile =
                    displayDAO.search(candidateAccountId);

            request.setAttribute("profile", updatedProfile);

            return "/profile/profileDisplay.jsp";
        }

        return "/profile/profileDisplay.jsp";
    }
}