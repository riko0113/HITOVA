package admin;

import DAO.AdminReviewDAO;
import DAO.AdminReviewDAO.AdminReviewItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class AdminReviewDetailAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/AdminReviewList.action");
            return null;
        }

        long reviewId = Long.parseLong(idStr);
        AdminReviewDAO dao = new AdminReviewDAO();
        AdminReviewItem review = dao.findById(reviewId);

        if (review == null) {
            response.sendRedirect(request.getContextPath() + "/admin/AdminReviewList.action");
            return null;
        }

        request.setAttribute("review", review);
        return "/admin/userReviewJudge.jsp";
    }
}