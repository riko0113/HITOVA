package jobsearchServlet;

import java.util.List;

import DAO.JobSearchDAO;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class JobSearchExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 画面から検索キーワードを受け取る（nullの場合は空文字）
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        // データベース検索
        JobSearchDAO dao = new JobSearchDAO();
        List<Job> jobList = dao.searchJobs(keyword);

        // 検索結果をJSPに渡すためにリクエストスコープにセット
        request.setAttribute("jobList", jobList);

        // 検索結果を表示する画面へ遷移
        return "/jobsearch/jobSearchResult.jsp"; 
    }
}