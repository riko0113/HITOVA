
package bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class JobSeekerSearch {

    // ==============================
    // 検索条件
    // ==============================

    private Integer employmentType;
    private Integer jobType;
    private Integer industry;
    private Integer qualificationType;
    private Integer qualification;
    private String customQualification;
    private Integer achievementType;
    private Integer achievement;

    // 求職者情報（検索結果）
    private Long candidateId;
    private String name;
    private LocalDate birthDate;
    private Boolean identityVerification;

    // 検索結果一覧
    private List<JobSeekerSearch> candidateList;

    // プルダウン表示用リスト
    private List<Map<String, Object>> employmentTypeList;
    private List<Map<String, Object>> jobTypeList;
    private List<Map<String, Object>> industryList;
    private List<Map<String, Object>> qualificationTypeList;
    private List<Map<String, Object>> qualificationList;
    private List<Map<String, Object>> achievementTypeList;
    private List<Map<String, Object>> achievementList;

    // ページ情報

    private int resultCount;
    private int currentPage = 1;
    private int totalPages;

    // ==============================
    // 検索条件 Getter / Setter
    // ==============================

    public Integer getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(Integer employmentType) {
        this.employmentType = employmentType;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public Integer getQualificationType() {
        return qualificationType;
    }

    public void setQualificationType(Integer qualificationType) {
        this.qualificationType = qualificationType;
    }

    public Integer getQualification() {
        return qualification;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }

    public String getCustomQualification() {
        return customQualification;
    }

    public void setCustomQualification(String customQualification) {
        this.customQualification = customQualification;
    }

    public Integer getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(Integer achievementType) {
        this.achievementType = achievementType;
    }

    public Integer getAchievement() {
        return achievement;
    }

    public void setAchievement(Integer achievement) {
        this.achievement = achievement;
    }

    // ==============================
    // 求職者情報 Getter / Setter
    // ==============================

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getIdentityVerification() {
        return identityVerification;
    }

    public void setIdentityVerification(Boolean identityVerification) {
        this.identityVerification = identityVerification;
    }

    // ==============================
    // 検索結果一覧 Getter / Setter
    // ==============================

    public List<JobSeekerSearch> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<JobSeekerSearch> candidateList) {
        this.candidateList = candidateList;
    }

    // ==============================
    // プルダウンリスト Getter / Setter
    // ==============================

    public List<Map<String, Object>> getEmploymentTypeList() {
        return employmentTypeList;
    }

    public void setEmploymentTypeList(
            List<Map<String, Object>> employmentTypeList) {
        this.employmentTypeList = employmentTypeList;
    }

    public List<Map<String, Object>> getJobTypeList() {
        return jobTypeList;
    }

    public void setJobTypeList(
            List<Map<String, Object>> jobTypeList) {
        this.jobTypeList = jobTypeList;
    }

    public List<Map<String, Object>> getIndustryList() {
        return industryList;
    }

    public void setIndustryList(
            List<Map<String, Object>> industryList) {
        this.industryList = industryList;
    }

    public List<Map<String, Object>> getQualificationTypeList() {
        return qualificationTypeList;
    }

    public void setQualificationTypeList(
            List<Map<String, Object>> qualificationTypeList) {
        this.qualificationTypeList = qualificationTypeList;
    }

    public List<Map<String, Object>> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(
            List<Map<String, Object>> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public List<Map<String, Object>> getAchievementTypeList() {
        return achievementTypeList;
    }

    public void setAchievementTypeList(
            List<Map<String, Object>> achievementTypeList) {
        this.achievementTypeList = achievementTypeList;
    }

    public List<Map<String, Object>> getAchievementList() {
        return achievementList;
    }

    public void setAchievementList(
            List<Map<String, Object>> achievementList) {
        this.achievementList = achievementList;
    }

    // ==============================
    // ページ情報 Getter / Setter
    // ==============================

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
