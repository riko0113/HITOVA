package companyApproval;

import DAO.CompanyApprovalDAO;
import DAO.CompanyApprovalDAO.ApprovalCompanyItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class CompanyApprovalDetailAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/companyApproval/CompanyApprovalList.action");
            return null;
        }

        long id = Long.parseLong(idParam);
        CompanyApprovalDAO dao = new CompanyApprovalDAO();
        ApprovalCompanyItem company = dao.findById(id);

        if (company == null) {
            response.sendRedirect(request.getContextPath() + "/companyApproval/CompanyApprovalList.action");
            return null;
        }

        request.setAttribute("company", company);
        request.setAttribute("detailLoaded", true);

        String mode = request.getParameter("mode");
        if ("certify".equals(mode)) {
            return "/companyApproval/companydetails.jsp";
        }
        return "/companyApproval/companyapply.jsp";
    }
}