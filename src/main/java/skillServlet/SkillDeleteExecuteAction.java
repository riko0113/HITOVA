package skillServlet;

import DAO.SkillDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SkillDeleteExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // JSPから送られてくる、削除対象のスキルIDの配列を取得（name="delete_ids"）
        String[] deleteIds = request.getParameterValues("delete_ids");

        if (deleteIds != null && deleteIds.length > 0) {
            SkillDAO dao = new SkillDAO();
            
            for (String idStr : deleteIds) {
                Long id = Long.parseLong(idStr);
                dao.delete(id);
            }
        }

        // 削除後、スキル一覧画面へリダイレクト
        return "/skillServlet/Skill.action";
    }
}