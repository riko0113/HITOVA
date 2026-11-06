package skillServlet;

import java.util.List;

import DAO.SkillDAO;
import bean.Skill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SkillAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // ダミーの求職者ID(1)で一覧を取得する
        Long candidateId = 1L;

        SkillDAO dao = new SkillDAO();
        List<Skill> skillList = dao.findByCandidateId(candidateId);

        request.setAttribute("skillList", skillList);

        return "/skill/skill.jsp";
    }
}