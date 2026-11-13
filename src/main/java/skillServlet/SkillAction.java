package skillServlet;

import java.util.List;

import DAO.SkillDAO;
import bean.JobSeekerAccount;
import bean.Skill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SkillAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            JobSeekerAccount candidate = (JobSeekerAccount) session.getAttribute("candidate");

            Long candidateId = null;
            if (candidate != null) {
                candidateId = candidate.getCandidate_id();
            }

            // ログイン機能完成までのダミーID
            if (candidateId == null) {
                candidateId = 1L; 
            }

            SkillDAO dao = new SkillDAO();
            List<Skill> skillList = dao.findByCandidateId(candidateId);

            request.setAttribute("skillList", skillList);

            return "/skill/skill.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "スキル情報の取得中にエラーが発生しました。");
            return "/error/error.jsp";
        }
    }
}