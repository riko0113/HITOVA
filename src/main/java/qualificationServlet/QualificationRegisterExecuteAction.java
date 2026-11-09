package qualificationServlet;

import java.time.LocalDate;

import DAO.QualificationDAO;
import bean.Qualification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class QualificationRegisterExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 1. JSPからname属性でデータを受け取る
        String candidateIdStr = request.getParameter("candidate_account_id");
        String smallCategoryStr = request.getParameter("qualification_small"); // 資格詳細IDとして使用
        String yearStr = request.getParameter("qualification_year");
        String monthStr = request.getParameter("qualification_month");
        String dayStr = request.getParameter("qualification_day");

        // 入力チェック（どれかが空ならエラー画面へ）
        if (candidateIdStr == null || smallCategoryStr == null || 
            yearStr == null || monthStr == null || dayStr == null || yearStr.isEmpty()) {
            return "/qualification/qualificationRegisterError.jsp"; 
        }

        // 2. 年・月・日を数値に変換し、LocalDateを作成する
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);
        LocalDate qualificationDate = LocalDate.of(year, month, day);

        // 3. Beanにセット
        Qualification q = new Qualification();
        q.setCandidate_account_id(Long.parseLong(candidateIdStr));
        q.setQualification_detail_id(Long.parseLong(smallCategoryStr));
        q.setQualification_date(qualificationDate);

        // 4. DAOでデータベースにINSERT
        QualificationDAO dao = new QualificationDAO();
        int result = dao.insert(q);

        // 5. 結果に応じて画面遷移
        if (result > 0) {
            return "/qualification/qualificationRegisterComplete.jsp";
        } else {
            return "/qualification/qualificationRegisterError.jsp";
        }
    }
}