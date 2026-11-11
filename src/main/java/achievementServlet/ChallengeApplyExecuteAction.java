package achievementServlet;

import java.time.LocalDate;

import DAO.AchievementDAO;
import bean.Achivement;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ChallengeApplyExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            JobSeekerAccount candidate = (JobSeekerAccount) session.getAttribute("candidate");

            Long candidateId = null;
            if (candidate != null) {
                candidateId = candidate.getCandidate_id();
            }

            if (candidateId == null) {
                candidateId = 1001L;
            }

            String taskIdStr = request.getParameter("task_id");
            String challengeName = request.getParameter("challenge_name");

            Long taskId = 10001L;
            if (taskIdStr != null && !taskIdStr.isEmpty()) {
                taskId = Long.parseLong(taskIdStr);
            }

            Achivement achivement = new Achivement();
            achivement.setTask_id(taskId);
            achivement.setCandidate_account_id(candidateId);
            achivement.setAchivement_date(LocalDate.now());
            achivement.setPassorfail_id(3L); // 3: 審査中
            achivement.setEvaluation("一次受付完了（自動審査対象）");

            AchievementDAO dao = new AchievementDAO();
            int count = dao.insert(achivement);

            if (count > 0) {
                request.setAttribute("challengeName", challengeName);
                return "/achievement/challengeComplete.jsp";
            } else {
                request.setAttribute("errorMessage", "課題の登録に失敗しました。");
                return "/achievement/challengeError.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "システムエラーが発生しました: " + e.getMessage());
            return "/achievement/challengeError.jsp";
        }
    }
}