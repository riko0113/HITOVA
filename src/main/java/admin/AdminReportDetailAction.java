package admin;

import DAO.AdminReportDAO;
import DAO.AdminReportDAO.AdminReportItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class AdminReportDetailAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idStr = request.getParameter("report_id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/AdminReportList.action");
            return null;
        }

        long reportId = Long.parseLong(idStr);
        AdminReportDAO dao = new AdminReportDAO();
        AdminReportItem report = dao.findById(reportId);

        if (report == null) {
            response.sendRedirect(request.getContextPath() + "/admin/AdminReportList.action");
            return null;
        }

        request.setAttribute("report", report);
        return "/admin/reportDetail.jsp";
    }
}