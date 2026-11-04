package skillServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SkillAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO: 後でDAOを使ってデータベースからスキル一覧を取得します
        return "/skill/skill.jsp";
    }
}