package jobsearchServlet;

import java.util.List;

import DAO.JobSearchDAO;
import bean.Job;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class JobSearchExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            JobSeekerAccount candidate = (JobSeekerAccount) session.getAttribute("candidate");

            Long candidateId = null;
            if (candidate != null) {
                candidateId = candidate.getCandidate_id();
            }

            if (candidateId == null) {
                candidateId = 1L; // ダミーID
            }

            // 画面から検索キーワードを受け取る
            String keyword = request.getParameter("keyword");
            if (keyword == null) {
                keyword = "";
            }

            JobSearchDAO dao = new JobSearchDAO();
            List<Job> jobList = dao.searchJobs(keyword);

            // JSPへデータを渡す
            request.setAttribute("jobList", jobList);
            request.setAttribute("keyword", keyword);

            return "/jobsearch/jobSearchResult.jsp"; 

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "求人の検索中にエラーが発生しました。");
            return "/jobsearch/jobSearchError.jsp"; 
        }
    }
}