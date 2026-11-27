package admin;

import java.util.List;

import DAO.AdminReportDAO;
import DAO.AdminReportDAO.AdminReportItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class AdminReportListAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminReportDAO dao = new AdminReportDAO();
        List<AdminReportItem> allList = dao.findAll();

        int pageSize = 10;
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Math.max(1, Integer.parseInt(pageParam));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        int totalItems = allList.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / pageSize));
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        List<AdminReportItem> pageList = allList.subList(fromIndex, toIndex);

        request.setAttribute("reportList", pageList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);

        return "/admin/reportList.jsp";
    }
}