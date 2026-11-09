package skillServlet;

import DAO.SkillDAO;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SkillUpdateAction extends Action {
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

            String[] deleteIds = request.getParameterValues("delete_ids");

            if (deleteIds != null && deleteIds.length > 0) {
                SkillDAO dao = new SkillDAO();
                for (String idStr : deleteIds) {
                    if (idStr != null && !idStr.trim().isEmpty()) {
                        Long skillId = Long.parseLong(idStr.trim());
                        dao.delete(skillId, candidateId);
                    }
                }
            }

            return "/skillServlet/Skill.action";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "スキル情報の更新中にエラーが発生しました。");
            return "/error/error.jsp";
        }
    }
}