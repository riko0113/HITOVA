package jobsearchServlet;

import java.util.List;

import DAO.JobApplyDAO;
import DAO.JobApplyDAO.AppliedJobRow;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ApplicationHistoryListAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JobApplyDAO dao = new JobApplyDAO();

        HttpSession session = request.getSession();
        Long candidateId = (Long) session.getAttribute("candidate_id");
        if (candidateId == null) {
            candidateId = dao.getDefaultCandidateId();
        }

        List<AppliedJobRow> appliedList = dao.getCandidateApplications(candidateId);
        request.setAttribute("appliedList", appliedList);

        return "/jobsearch/applicationHistory.jsp";
    }
}