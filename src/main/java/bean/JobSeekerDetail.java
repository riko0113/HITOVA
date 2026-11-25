
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobSeekerDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Object> basicInfo;
    private Map<String, Object> profileInfo;
    private Map<String, Object> searchInfo;

    private List<Map<String, Object>> skillList = new ArrayList<>();
    private List<Map<String, Object>> qualificationList = new ArrayList<>();
    private List<Map<String, Object>> achievementList = new ArrayList<>();
    private List<Map<String, Object>> portfolioList = new ArrayList<>();

    public Map<String, Object> getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(Map<String, Object> basicInfo) {
        this.basicInfo = basicInfo;
    }

    public Map<String, Object> getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(Map<String, Object> profileInfo) {
        this.profileInfo = profileInfo;
    }

    public Map<String, Object> getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(Map<String, Object> searchInfo) {
        this.searchInfo = searchInfo;
    }

    public List<Map<String, Object>> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Map<String, Object>> skillList) {
        this.skillList = skillList;
    }

    public List<Map<String, Object>> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<Map<String, Object>> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public List<Map<String, Object>> getAchievementList() {
        return achievementList;
    }

    public void setAchievementList(List<Map<String, Object>> achievementList) {
        this.achievementList = achievementList;
    }

    public List<Map<String, Object>> getPortfolioList() {
        return portfolioList;
    }

    public void setPortfolioList(List<Map<String, Object>> portfolioList) {
        this.portfolioList = portfolioList;
    }
}
