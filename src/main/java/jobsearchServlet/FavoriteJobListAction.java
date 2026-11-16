package jobsearchServlet;

import java.util.List;

import DAO.JobSearchDAO;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class FavoriteJobListAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JobSearchDAO dao = new JobSearchDAO();

        HttpSession session = request.getSession();
        Long candidateId = (Long) session.getAttribute("candidate_id");
        if (candidateId == null) {
            candidateId = dao.getDefaultCandidateId();
        }

        List<Job> favoriteList = dao.getFavoriteJobs(candidateId);
        request.setAttribute("favoriteList", favoriteList);

        return "/jobsearch/favoriteJobs.jsp";
    }
}