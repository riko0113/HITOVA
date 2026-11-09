package qualificationServlet;

import java.time.LocalDate;

import DAO.QualificationDAO;
import bean.JobSeekerAccount;
import bean.Qualification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class QualificationRegisterExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            JobSeekerAccount candidate = (JobSeekerAccount) session.getAttribute("candidate");

            Long candidateId = null;
            if (candidate != null) {
                candidateId = candidate.getCandidate_id();
            }

            // TODO: ログイン機能完成まではダミーIDを使用
            if (candidateId == null) {
                candidateId = 1L;
            }

            // JSPから送られてくるname属性(qualification_small と qualification_date)を取得
            String qualificationDetailIdStr = request.getParameter("qualification_small");
            String dateStr = request.getParameter("qualification_date");

            Long qualificationDetailId = Long.parseLong(qualificationDetailIdStr);
            // カレンダーから送られる "YYYY-MM-DD" をそのまま日付型（LocalDate）に変換
            LocalDate qualificationDate = LocalDate.parse(dateStr);

            // 登録データのセット
            Qualification qualification = new Qualification();
            qualification.setCandidate_account_id(candidateId);
            qualification.setQualification_detail_id(qualificationDetailId);
            qualification.setQualification_date(qualificationDate);

            QualificationDAO dao = new QualificationDAO();
            int count = dao.insert(qualification);

            if (count > 0) {
                // 成功したら完了画面へ
                return "/qualification/qualificationRegisterComplete.jsp"; // ←完了画面の名前に合わせて修正
            } else {
                request.setAttribute("errorMessage", "登録処理に失敗しました。");
                return "/qualification/qualificationRegister.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "資格の登録中にエラーが発生しました。入力内容を確認してください。");
            return "/qualification/qualificationRegisterError.jsp";
        }
    }
}