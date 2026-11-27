package companyApproval;

import DAO.CompanyApprovalDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class CompanyApprovalUpdateAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idParam = request.getParameter("id");
        String actionType = request.getParameter("actionType"); // "APPROVED" or "REJECTED"

        if (idParam != null && !idParam.isEmpty() &&
            ("APPROVED".equals(actionType) || "REJECTED".equals(actionType))) {
            long id = Long.parseLong(idParam);
            CompanyApprovalDAO dao = new CompanyApprovalDAO();
            dao.updateApprovalStatus(id, actionType);
        }

        String msg = "APPROVED".equals(actionType) ? "approved" : "rejected";
        response.sendRedirect(request.getContextPath() + "/companyApproval/CompanyApprovalList.action?result=" + msg);
        return null;
    }
}