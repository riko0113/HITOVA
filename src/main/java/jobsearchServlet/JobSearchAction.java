package jobsearchServlet;

// Servletに必要なクラスを読み込む
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// このServletに「/jobSearch」というURLを割り当てる
@WebServlet("/JobSearch.action")
public class JobSearchAction extends HttpServlet {

    // GETリクエストを受け取ったときに実行される
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // リクエストの文字コードをUTF-8に設定
        request.setCharacterEncoding("UTF-8");

        // JSPの検索フォームから送られた値を取得
        String keyword = request.getParameter("keyword");
        String jobType = request.getParameter("jobType");
        String salary = request.getParameter("salary");
        String employmentType = request.getParameter("employmentType");
        String location = request.getParameter("location");
        String benefits = request.getParameter("benefits");

        // 取得した値を確認する（開発中の動作確認用）
        System.out.println("検索キーワード：" + keyword);
        System.out.println("職種：" + jobType);
        System.out.println("給与：" + salary);
        System.out.println("雇用形態：" + employmentType);
        System.out.println("勤務地：" + location);
        System.out.println("福利厚生：" + benefits);

        // 検索結果画面に遷移する（現時点では仮）
        request.getRequestDispatcher("/jobsearch/jobsearch.jsp")
               .forward(request, response);
    }
}
