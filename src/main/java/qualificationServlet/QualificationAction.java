package qualificationServlet;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import DAO.QualificationDAO;
import bean.Qualification;
import tool.Action;

public class QualificationAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 現状はダミーの求職者ID(1)で一覧を取得する
        Long candidateId = 1L;

        // DAOを使ってデータベースから資格リストを取得
        QualificationDAO dao = new QualificationDAO();
        List<Qualification> qualificationList = dao.findByCandidateId(candidateId);

        // JSPで使えるようにリクエストにセット
        request.setAttribute("qualificationList", qualificationList);

        return "/qualification/qualification.jsp";
    }
}