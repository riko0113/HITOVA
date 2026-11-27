package companyServlet;

import java.util.List;

import DAO.CompanyTopDAO;
import DAO.CompanyTopDAO.CompanyNoticeItem;
import bean.CompanyAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class CompanyTopAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        Long companyAccountId = null;
        Object sessionId = session.getAttribute("company_account_id");
        if (sessionId instanceof Long) {
            companyAccountId = (Long) sessionId;
        } else if (session.getAttribute("companyAccount") instanceof CompanyAccount) {
            companyAccountId = ((CompanyAccount) session.getAttribute("companyAccount")).getCompany_account_id();
        }

        // テスト環境用フォールバック（AccountInfoActionと統一）
        if (companyAccountId == null) {
            companyAccountId = 2001L;
        }

        CompanyTopDAO dao = new CompanyTopDAO();
        int applicantCount = dao.countTotalApplicants(companyAccountId);
        int publishedJobCount = dao.countPublishedJobs(companyAccountId);
        int totalJobCount = dao.countAllJobs(companyAccountId);
        List<CompanyNoticeItem> noticeList = dao.findRecentCompanyNotices(companyAccountId);

        request.setAttribute("applicantCount", applicantCount);
        request.setAttribute("publishedJobCount", publishedJobCount);
        request.setAttribute("totalJobCount", totalJobCount);
        request.setAttribute("noticeList", noticeList);
        request.setAttribute("topLoaded", true);

        return "/toppage/companyTop.jsp";
    }
}