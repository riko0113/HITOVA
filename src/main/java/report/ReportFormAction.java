package report;

import DAO.ReportDAO;
import DAO.ReportDAO.ReportTarget;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import tool.Action;

public class ReportFormAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        Object candidateObj = (session != null) ? session.getAttribute(AuthWeb.candidate_key) : null;
        if (!(candidateObj instanceof Long)) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return null;
        }

        String jobIdStr = request.getParameter("job_id");
        if (jobIdStr == null || jobIdStr.isEmpty()) {
            return "/jobsearchServlet/JobSearch.action";
        }

        long jobId = Long.parseLong(jobIdStr);
        ReportDAO dao = new ReportDAO();
        ReportTarget target = dao.findTargetByJobId(jobId);

        request.setAttribute("target", target);
        return "/report/reportForm.jsp";
    }
}