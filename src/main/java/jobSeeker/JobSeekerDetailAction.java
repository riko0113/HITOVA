
package jobSeeker;

import java.util.List;
import java.util.Map;

import DAO.JobSeekerDetailDAO;
import bean.JobSeekerDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class JobSeekerDetailAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String candidateIdText = request.getParameter("candidate_id");

        if (candidateIdText == null || candidateIdText.trim().isEmpty()) {
            request.setAttribute("errorMessage",
                    "求職者IDを取得できませんでした。");
            return "/jobSeeker/jobSeekerDetail.jsp";
        }

        long candidateId;

        try {
            candidateId = Long.parseLong(candidateIdText);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage",
                    "求職者IDが正しくありません。");
            return "/jobSeeker/jobSeekerDetail.jsp";
        }

        JobSeekerDetailDAO dao = new JobSeekerDetailDAO();
        Map<String, Object> data = dao.findByCandidateId(candidateId);

        if (data == null) {
            request.setAttribute("errorMessage",
                    "求職者情報が存在しないか、公開されていません。");
            return "/jobSeeker/jobSeekerDetail.jsp";
        }

        JobSeekerDetail detail = new JobSeekerDetail();

        detail.setBasicInfo(
                (Map<String, Object>) data.get("basicInfo"));

        detail.setProfileInfo(
                (Map<String, Object>) data.get("profileInfo"));

        detail.setSearchInfo(
                (Map<String, Object>) data.get("searchInfo"));

        detail.setSkillList(
                (List<Map<String, Object>>) data.get("skillList"));

        detail.setQualificationList(
                (List<Map<String, Object>>) data.get("qualificationList"));

        detail.setAchievementList(
                (List<Map<String, Object>>) data.get("achievementList"));

        detail.setPortfolioList(
                (List<Map<String, Object>>) data.get("portfolioList"));

        request.setAttribute("detail", detail);

        return "/jobSeeker/jobSeekerDetail.jsp";
    }
}
