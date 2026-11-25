package review;

import java.util.List;

import DAO.ReviewDAO;
import DAO.ReviewDAO.ReviewItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import tool.Action;

public class ReviewListAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jobIdStr = request.getParameter("job_id");
        if (jobIdStr == null || jobIdStr.isEmpty()) {
            return "/jobsearchServlet/JobSearch.action";
        }

        long jobId = Long.parseLong(jobIdStr);
        ReviewDAO dao = new ReviewDAO();
        String companyName = dao.findCompanyNameByJobId(jobId);
        List<ReviewItem> reviews = dao.findByJobId(jobId);

        boolean hasProfile = false;
        HttpSession session = request.getSession(false);
        Object candidateObj = (session != null) ? session.getAttribute(AuthWeb.candidate_key) : null;
        if (candidateObj instanceof Long) {
            hasProfile = dao.hasProfile((Long) candidateObj);
        }

        request.setAttribute("job_id", jobId);
        request.setAttribute("company_name", companyName);
        request.setAttribute("reviews", reviews);
        request.setAttribute("hasProfile", hasProfile);

        return "/review/reviewList.jsp";
    }
}