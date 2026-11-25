package review;

import DAO.ReviewDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import tool.Action;

public class ReviewDeleteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Object candidateObj = (session != null) ? session.getAttribute(AuthWeb.candidate_key) : null;
        if (!(candidateObj instanceof Long)) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return null;
        }

        long candidateId = (Long) candidateObj;
        long reviewId = Long.parseLong(request.getParameter("job_review_id"));
        long jobId = Long.parseLong(request.getParameter("job_id"));

        ReviewDAO dao = new ReviewDAO();
        dao.delete(reviewId, candidateId);

        response.sendRedirect(request.getContextPath() + "/review/ReviewList.action?job_id=" + jobId);
        return null;
    }
}