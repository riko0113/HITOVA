package skillServlet;

import DAO.SkillDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SkillRegisterExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. JSPのフォーム（name属性）から入力された文字列を直接受け取る
        String skillName = request.getParameter("skillTitle");
        String skillContent = request.getParameter("skillDetail");

        // 2. 誰のスキルかを指定するID（※後述の重要注意事項を参照）
        long candidateId = 1L; 

        // 3. DAOを呼び出してデータベースに保存
        SkillDAO dao = new SkillDAO();
        dao.insert(candidateId, skillName, skillContent);

        // 4. 保存が終わったら、登録完了画面へ進む（変更箇所）
        return "/skill/skillRegisterComplete.jsp";
    }
}