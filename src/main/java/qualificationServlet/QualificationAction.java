package qualificationServlet;

import java.util.List;

import DAO.QualificationDAO;
import bean.JobSeekerAccount;
import bean.Qualification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class QualificationAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            JobSeekerAccount candidate = (JobSeekerAccount) session.getAttribute("candidate"); // ←ここを修正

            Long candidateId = null;
            if (candidate != null) {
                candidateId = candidate.getCandidate_id(); // ←ここを修正
            }

            // TODO: ログイン機能完成まではダミーID(1)を使用
            if (candidateId == null) {
                candidateId = 1L; 
            }

            QualificationDAO dao = new QualificationDAO();
            List<Qualification> qualificationList = dao.findByCandidateId(candidateId);

            request.setAttribute("qualificationList", qualificationList);

            return "/qualification/qualification.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "資格情報の取得中にエラーが発生しました。");
            return "/error/error.jsp";
        }
    }
}