package jobsearchServlet;

import java.util.List;

import DAO.JobSearchDAO;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class JobSearchAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        // フォームから検索条件を受け取る
        String keyword = request.getParameter("keyword");
        String occupationStr = request.getParameter("occupationId");
        String salaryStr = request.getParameter("salaryTypeId");
        String employmentStr = request.getParameter("employmentTypeId");
        String location = request.getParameter("location");

        // 型変換（未入力・空文字の場合はnullにする）
        Long occupationId = (occupationStr != null && !occupationStr.isEmpty()) ? Long.parseLong(occupationStr) : null;
        Integer salaryTypeId = (salaryStr != null && !salaryStr.isEmpty()) ? Integer.parseInt(salaryStr) : null;
        Integer employmentTypeId = (employmentStr != null && !employmentStr.isEmpty()) ? Integer.parseInt(employmentStr) : null;

        // DAOを呼び出して複数条件検索
        JobSearchDAO dao = new JobSearchDAO();
        List<Job> jobList = dao.searchJobs(keyword, occupationId, salaryTypeId, employmentTypeId, location);

        // JSPにデータを渡す
        request.setAttribute("jobList", jobList);
        
        // 検索後にフォームの入力状態を保持するためのセット
        request.setAttribute("keyword", keyword);
        request.setAttribute("occupationId", occupationId);
        request.setAttribute("salaryTypeId", salaryTypeId);
        request.setAttribute("employmentTypeId", employmentTypeId);
        request.setAttribute("location", location);

        return "/jobsearch/jobSearch.jsp";
    }
}