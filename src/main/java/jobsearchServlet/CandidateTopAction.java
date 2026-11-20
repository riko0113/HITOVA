package jobsearchServlet;

import java.util.List;

import DAO.JobSearchDAO;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class CandidateTopAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JobSearchDAO dao = new JobSearchDAO();
        List<Job> allJobs = dao.searchJobs(null, null, null, null, null);

        // 最大4件をおすすめ求人として渡す
        List<Job> recommendedJobs = allJobs.size() > 4 ? allJobs.subList(0, 4) : allJobs;
        request.setAttribute("recommendedJobs", recommendedJobs);

        return "/toppage/candidateTop.jsp";
    }
}