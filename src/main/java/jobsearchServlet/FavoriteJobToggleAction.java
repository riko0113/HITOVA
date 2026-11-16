package jobsearchServlet;

import DAO.JobSearchDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class FavoriteJobToggleAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jobIdStr = request.getParameter("job_id");
        if (jobIdStr != null && !jobIdStr.isEmpty()) {
            Long jobId = Long.parseLong(jobIdStr);
            JobSearchDAO dao = new JobSearchDAO();

            HttpSession session = request.getSession();
            Long candidateId = (Long) session.getAttribute("candidate_id");
            if (candidateId == null) {
                candidateId = dao.getDefaultCandidateId();
            }

            dao.toggleFavoriteJob(candidateId, jobId);
            response.sendRedirect(request.getContextPath() + "/jobsearchServlet/JobDetail.action?id=" + jobId);
            return null;
        }

        response.sendRedirect(request.getContextPath() + "/jobsearchServlet/JobSearch.action");
        return null;
    }
}