package unionAdminServlet;

import DAO.CompanyDAO;
import bean.CompanyAccount;
import bean.CorporationInformation;
import bean.SoleProprietorInformation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class CompanyDetailAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idParam = request.getParameter("id");
        Long id = Long.parseLong(idParam);

        CompanyDAO dao = new CompanyDAO();
        CompanyAccount ca = dao.getCompanyAccountById(id);
        CorporationInformation ci = dao.getCorporationInfoById(id);
        SoleProprietorInformation spi = dao.getSoleProprietorInfoById(id);

        request.setAttribute("ca", ca);
        request.setAttribute("ci", ci);
        request.setAttribute("spi", spi);
        return "/unionAdmin/companyDetail.jsp";
    }
}