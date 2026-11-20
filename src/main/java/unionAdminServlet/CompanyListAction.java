package unionAdminServlet;

import java.util.List;

import DAO.CompanyDAO;
import DAO.CompanyDAO.CompanyRow;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class CompanyListAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompanyDAO dao = new CompanyDAO();
        List<CompanyRow> companyList = dao.searchCompanies(null);

        request.setAttribute("companyList", companyList);
        return "/unionAdmin/companyList.jsp";
    }
}