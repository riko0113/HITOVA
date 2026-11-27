package admin;

import DAO.AdminReportDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class AdminReportDeleteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("report_id");
        if (idStr != null && !idStr.isEmpty()) {
            long reportId = Long.parseLong(idStr);
            AdminReportDAO dao = new AdminReportDAO();
            dao.deleteById(reportId);
        }

        response.sendRedirect(request.getContextPath() + "/admin/AdminReportList.action?result=deleted");
        return null;
    }
}