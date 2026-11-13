package skillServlet;

import DAO.SkillDAO;
import bean.JobSeekerAccount;
import bean.Skill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SkillRegisterExecuteAction extends Action {
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
                candidateId = 1L; // ダミーID
            }

            // 自由記述のテキストを取得
            String skillName = request.getParameter("skill_name");
            String skillContent = request.getParameter("skill_content");

            Skill skill = new Skill();
            skill.setCandidate_account_id(candidateId);
            skill.setSkill_name(skillName);
            skill.setSkill_content(skillContent);

            SkillDAO dao = new SkillDAO();
            int count = dao.insert(skill);

            if (count > 0) {
                return "/skill/skillRegisterComplete.jsp"; 
            } else {
                request.setAttribute("errorMessage", "登録処理に失敗しました。");
                return "/skill/skillRegister.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "スキルの登録中にエラーが発生しました。");
            return "/skill/skillRegisterError.jsp";
        }
    }
}