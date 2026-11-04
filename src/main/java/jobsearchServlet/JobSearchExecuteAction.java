package jobsearchServlet;

import java.util.List;
import java.util.Map;

import DAO.JobSearchDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class JobSearchExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 画面から検索キーワードを受け取る（nullの場合は空文字にする）
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        // 2. DAOを使ってデータベースから検索（名前を変更した JobSearchDAO を使用）
        JobSearchDAO dao = new JobSearchDAO();
        List<Map<String, String>> jobList = dao.search(keyword);

        // 3. 検索結果をJSPに渡すためにリクエストスコープにセット
        request.setAttribute("jobList", jobList);

        // 4. 検索結果を表示する画面へ遷移
        return "/jobsearch/jobSearchResult.jsp"; 
    }
}