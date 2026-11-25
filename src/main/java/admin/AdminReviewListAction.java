package admin;

import java.util.List;

import DAO.AdminReviewDAO;
import DAO.AdminReviewDAO.AdminReviewItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class AdminReviewListAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminReviewDAO dao = new AdminReviewDAO();
        List<AdminReviewItem> allReviews = dao.findAll();

        int pageSize = 10;
        int totalCount = allReviews.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) totalCount / pageSize));

        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {}
        }
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalCount);
        List<AdminReviewItem> pagedReviews = allReviews.subList(fromIndex, toIndex);

        request.setAttribute("reviews", pagedReviews);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("startNo", fromIndex + 1);

        return "/admin/userReviews.jsp";
    }
}