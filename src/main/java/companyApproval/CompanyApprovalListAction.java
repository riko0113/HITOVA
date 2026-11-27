package companyApproval;

import java.util.List;

import DAO.CompanyApprovalDAO;
import DAO.CompanyApprovalDAO.ApprovalCompanyItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class CompanyApprovalListAction extends Action {

    private static final int PAGE_SIZE = 10;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String statusFilter = request.getParameter("status");
        if (statusFilter == null) {
            statusFilter = "";
        }

        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Math.max(1, Integer.parseInt(pageParam));
            } catch (NumberFormatException ignored) {
                currentPage = 1;
            }
        }

        CompanyApprovalDAO dao = new CompanyApprovalDAO();
        int totalCount = dao.countApplications(statusFilter);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalCount / PAGE_SIZE));
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        int offset = (currentPage - 1) * PAGE_SIZE;
        List<ApprovalCompanyItem> applicationList = dao.findApplications(statusFilter, PAGE_SIZE, offset);

        request.setAttribute("applicationList", applicationList);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("statusFilter", statusFilter);
        request.setAttribute("listLoaded", true);

        return "/companyApproval/companyApproval.jsp";
    }
}