package review;

import java.time.LocalDate;

import DAO.ReviewDAO;
import bean.Review;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import tool.Action;

public class ReviewPostAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Object candidateObj = (session != null) ? session.getAttribute(AuthWeb.candidate_key) : null;
        if (!(candidateObj instanceof Long)) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return null;
        }

        long candidateId = (Long) candidateObj;
        long jobId = Long.parseLong(request.getParameter("job_id"));
        String evalutation = request.getParameter("evalutation");
        String content = request.getParameter("content");

        ReviewDAO dao = new ReviewDAO();
        Review review = new Review();
        review.setJob_id(jobId);
        review.setCandidate_account_id(candidateId);
        review.setEvalutation(evalutation != null ? evalutation.trim() : "");
        review.setContent(content != null ? content.trim() : "");
        review.setPosted_at(LocalDate.now());

        // プロフィール未登録の場合は投稿不可にして一覧へ戻す
        if (!dao.fillDemographicsFromProfile(review, candidateId)) {
            response.sendRedirect(request.getContextPath() + "/review/ReviewList.action?job_id=" + jobId);
            return null;
        }

        if (review.getEvalutation().isEmpty() || review.getContent().isEmpty()) {
            request.setAttribute("error", "おすすめ対象と入力内容を入力してください。");
            request.setAttribute("job_id", jobId);
            return "/review/reviewPost.jsp";
        }

        dao.insert(review);

        response.sendRedirect(request.getContextPath() + "/review/reviewComplete.jsp?job_id=" + jobId);
        return null;
    }
}