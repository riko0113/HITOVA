package companyServlet;

import DAO.CompanyDAO;
import bean.CompanyAccount;
import bean.CorporationInformation;
import bean.SoleProprietorInformation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AccountInfoAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        // セッションから企業アカウントIDを取得
        Long companyAccountId = null;
        Object sessionId = session.getAttribute("company_account_id");
        if (sessionId instanceof Long) {
            companyAccountId = (Long) sessionId;
        } else if (session.getAttribute("companyAccount") instanceof CompanyAccount) {
            companyAccountId = ((CompanyAccount) session.getAttribute("companyAccount")).getCompany_account_id();
        }

        // テスト
        if (companyAccountId == null) {
            companyAccountId = 2001L; 
        }

        CompanyDAO dao = new CompanyDAO();
        CompanyAccount ca = dao.getCompanyAccountById(companyAccountId);
        CorporationInformation ci = dao.getCorporationInfoById(companyAccountId);
        SoleProprietorInformation spi = dao.getSoleProprietorInfoById(companyAccountId);

        request.setAttribute("ca", ca);
        request.setAttribute("ci", ci);
        request.setAttribute("spi", spi);

        return "/company/accountInfo.jsp";
    }
}