package admin;

import DAO.AdminReviewDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class AdminReviewJudgeAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("job_review_id");
        String decision = request.getParameter("decision");

        if (idStr != null && !idStr.isEmpty()) {
            long reviewId = Long.parseLong(idStr);
            AdminReviewDAO dao = new AdminReviewDAO();
            if ("reject".equals(decision)) {
                dao.rejectById(reviewId);
            } else if ("approve".equals(decision)) {
                dao.approveById(reviewId);
            }
        }

        String resultParam = "reject".equals(decision) ? "rejected" : "approved";
        response.sendRedirect(request.getContextPath() + "/admin/AdminReviewList.action?result=" + resultParam);
        return null;
    }
}