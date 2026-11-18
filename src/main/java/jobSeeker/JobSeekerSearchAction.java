
package jobSeeker;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import DAO.JobSeekerSearchDAO;
import bean.JobSeekerSearch;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class JobSeekerSearchAction extends Action {

    private static final int PAGE_SIZE = 10;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("===== JobSeekerSearchAction START =====");
        request.setCharacterEncoding("UTF-8");

        JobSeekerSearchDAO dao = new JobSeekerSearchDAO();

        try {

        	// プルダウン一覧を取得
        	List<Map<String, Object>> employmentTypeList = dao.getEmploymentTypeList();
        	List<Map<String, Object>> jobTypeList = dao.getJobTypeList();
        	List<Map<String, Object>> industryList = dao.getIndustryList();
        	List<Map<String, Object>> qualificationTypeList = dao.getQualificationTypeList();
        	List<Map<String, Object>> qualificationList = dao.getQualificationList();
        	List<Map<String, Object>> achievementTypeList = dao.getAchievementTypeList();
        	List<Map<String, Object>> achievementList = dao.getAchievementList();

        	// JSPへ渡す
        	request.setAttribute("employmentTypeList", employmentTypeList);
        	request.setAttribute("jobTypeList", jobTypeList);
        	request.setAttribute("industryList", industryList);
        	request.setAttribute("qualificationTypeList", qualificationTypeList);
        	request.setAttribute("qualificationList", qualificationList);
        	request.setAttribute("achievementTypeList", achievementTypeList);
        	request.setAttribute("achievementList", achievementList);

        	// 取得結果を確認
        	System.out.println("雇用形態: " + employmentTypeList);
        	System.out.println("職種: " + jobTypeList);
        	System.out.println("業界: " + industryList);
        	System.out.println("資格種類: " + qualificationTypeList);
        	System.out.println("資格: " + qualificationList);
        	System.out.println("実績種類: " + achievementTypeList);
        	System.out.println("実績: " + achievementList);

           
            // 検索条件Beanを作成
            JobSeekerSearch condition = new JobSeekerSearch();

            // 初回表示（GET）
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                request.setAttribute("search", condition);
                request.setAttribute("candidateList", Collections.emptyList());
                request.setAttribute("resultCount", 0);
                return "/jobSeeker/jobSeekerSearch.jsp";
            }

            // POSTから検索条件を取得
            condition.setEmploymentType(toInteger(request.getParameter("employment_type")));
            condition.setJobType(toInteger(request.getParameter("job_type")));
            condition.setIndustry(toInteger(request.getParameter("industry")));
            condition.setQualificationType(toInteger(request.getParameter("qualification_type")));
            condition.setQualification(toInteger(request.getParameter("qualification")));
            condition.setCustomQualification(request.getParameter("custom_qualification"));
            condition.setAchievementType(toInteger(request.getParameter("achievement_type")));
            condition.setAchievement(toInteger(request.getParameter("achievement")));

            // 選択された検索条件数を確認
            int selectedCount = 0;
            if (condition.getEmploymentType() != null) selectedCount++;
            if (condition.getJobType() != null) selectedCount++;
            if (condition.getIndustry() != null) selectedCount++;
            if (condition.getQualificationType() != null) selectedCount++;
            if (condition.getQualification() != null) selectedCount++;
            if (condition.getCustomQualification() != null
                    && !condition.getCustomQualification().trim().isEmpty()) selectedCount++;
            if (condition.getAchievementType() != null) selectedCount++;
            if (condition.getAchievement() != null) selectedCount++;

            request.setAttribute("search", condition);
            System.out.println("選択された検索条件数：" + selectedCount);

            // 検索条件が3つ未満の場合
            if (selectedCount < 3) {
                request.setAttribute("errorMessage", "検索条件を3つ以上選択してください。");
                request.setAttribute("candidateList", Collections.emptyList());
                request.setAttribute("resultCount", 0);
                return "/jobSeeker/jobSeekerSearch.jsp";
            }

            // 検索結果件数を取得
            int resultCount = dao.count(condition);
            int totalPages = (int) Math.ceil((double) resultCount / PAGE_SIZE);

            // ページ番号を取得
            int currentPage = 1;
            String pageParameter = request.getParameter("page");

            if (pageParameter != null && !pageParameter.trim().isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageParameter);
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }

            if (currentPage < 1) currentPage = 1;
            if (totalPages > 0 && currentPage > totalPages) currentPage = totalPages;

            // 求職者を検索
            List<JobSeekerSearch> candidateList = dao.search(condition, currentPage, PAGE_SIZE);

            // 検索結果をJSPへ渡す
            request.setAttribute("candidateList", candidateList);
            request.setAttribute("resultCount", resultCount);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            System.out.println("検索結果件数：" + resultCount);
            System.out.println("===== JobSeekerSearchAction END =====");

            return "/jobSeeker/jobSeekerSearch.jsp";

        } catch (Exception e) {
            System.err.println("===== 求職者検索エラー =====");
            e.printStackTrace();
            request.setAttribute("errorMessage", "検索処理中にエラーが発生しました。");
            return "/jobSeeker/jobSeekerSearch.jsp";
        }
    }

    // 空文字をnullに変換
    private Integer toInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
