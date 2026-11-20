package jobsearchServlet;

import DAO.CompanyDAO;
import DAO.JobSearchDAO;
import bean.CompanyAccount;
import bean.CorporationInformation;
import bean.Job;
import bean.SoleProprietorInformation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class JobDetailAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/jobsearchServlet/JobSearch.action");
            return null;
        }

        Long jobId = Long.parseLong(idStr);
        JobSearchDAO jobDao = new JobSearchDAO();

        // 求職者IDの取得
        HttpSession session = request.getSession();
        Long candidateId = (Long) session.getAttribute("candidate_id");
        if (candidateId == null) {
            candidateId = jobDao.getDefaultCandidateId();
        }

        // 1. 求人詳細と保存状態の取得
        Job job = jobDao.getJobById(jobId);
        if (job == null) {
            response.sendRedirect(request.getContextPath() + "/jobsearchServlet/JobSearch.action");
            return null;
        }
        boolean isFavorite = jobDao.isFavoriteJob(candidateId, jobId);

        // 2. 企業の詳細情報の取得
        CompanyDAO companyDao = new CompanyDAO();
        Long companyId = job.getCompany_account_id();
        CompanyAccount ca = companyDao.getCompanyAccountById(companyId);
        CorporationInformation ci = companyDao.getCorporationInfoById(companyId);
        SoleProprietorInformation spi = companyDao.getSoleProprietorInfoById(companyId);

        request.setAttribute("job", job);
        request.setAttribute("isFavorite", isFavorite);
        request.setAttribute("ca", ca);
        request.setAttribute("ci", ci);
        request.setAttribute("spi", spi);

        return "/jobsearch/jobDetail.jsp";
    }
}