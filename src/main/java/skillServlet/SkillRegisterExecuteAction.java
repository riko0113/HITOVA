package skillServlet;

import DAO.SkillDAO;
import bean.Skill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SkillRegisterExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 1. 画面（JSP）からの入力値を受け取る
        // ※本来はセッションからログイン中の求職者IDを取得しますが、テスト用に画面から受け取る想定にしています
        String candidateIdStr = request.getParameter("candidate_account_id");
        String skillName = request.getParameter("skill_name");
        String skillContent = request.getParameter("skill_content");

        // 入力チェック（空の場合はエラー画面へ弾くなどの処理）
        if (candidateIdStr == null || skillName == null || skillContent == null) {
            return "/skill/skillRegisterError.jsp"; 
        }

        // 2. 受け取ったデータをSkill Beanにセットする
        Skill skill = new Skill();
        skill.setCandidate_account_id(Long.parseLong(candidateIdStr));
        skill.setSkill_name(skillName);
        skill.setSkill_content(skillContent);

        // 3. DAOを呼び出してデータベースにINSERT（登録）
        SkillDAO dao = new SkillDAO();
        int result = dao.insert(skill);

        // 4. 登録結果に応じて次の画面へ遷移
        if (result > 0) {
            // 登録成功時
        	return "/skill/skillRegisterComplete.jsp";
        } else {
            // 登録失敗時
            return "/skill/skillRegisterError.jsp";
        }
    }
}