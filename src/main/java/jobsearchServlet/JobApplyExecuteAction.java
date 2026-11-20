package jobsearchServlet;

import DAO.JobApplyDAO;
import DAO.JobApplyDAO.CandidateProfileData;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class JobApplyExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String jobIdStr = request.getParameter("job_id");
        if (jobIdStr == null || jobIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/jobsearchServlet/JobSearch.action");
            return null;
        }

        Long jobId = Long.parseLong(jobIdStr);
        JobApplyDAO dao = new JobApplyDAO();

        HttpSession session = request.getSession();
        Long candidateId = (Long) session.getAttribute("candidate_id");
        if (candidateId == null) {
            candidateId = dao.getDefaultCandidateId();
        }

        Job job = dao.getJobForApply(jobId);
        if (job == null) {
            response.sendRedirect(request.getContextPath() + "/jobsearchServlet/JobSearch.action");
            return null;
        }

        CandidateProfileData profileData = dao.getCandidateProfile(candidateId);
        dao.registerApplication(candidateId, jobId, job.getCompany_account_id(), profileData.getProfileJson());

        request.setAttribute("job", job);
        return "/jobsearch/jobApplyComplete.jsp";
    }
}