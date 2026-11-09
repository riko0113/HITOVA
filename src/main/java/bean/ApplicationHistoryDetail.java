package bean;

import java.io.Serializable;
import java.util.List;

public class ApplicationHistoryDetail implements Serializable {
    private JobSeekerAccount jobSeekerAccount;
    private ApplicationHistory applicationHistory;
    private List<Skill> skills;
    private List<QualificationDetail> qualifications; 
    private List<AchivementManagement> achievements; 
    private List<Portfolio> portfolios;

    // 求職者アカウント情報
    public JobSeekerAccount getJobSeekerAccount() { return jobSeekerAccount; }
    public void setJobSeekerAccount(JobSeekerAccount jobSeekerAccount) { this.jobSeekerAccount = jobSeekerAccount; }
    
    //応募履歴情報
    public ApplicationHistory getApplicationHistory() { return applicationHistory; }
    public void setApplicationHistory(ApplicationHistory applicationHistory) { this.applicationHistory = applicationHistory; }
    
    //スキル情報
    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }
    
    //資格詳細情報
    public List<QualificationDetail> getQualifications() { return qualifications; }
    public void setQualifications(List<QualificationDetail> qualifications) { this.qualifications = qualifications; }
    
    //資格一覧情報
    public List<AchivementManagement> getAchievements() { return achievements; }
    public void setAchievements(List<AchivementManagement> achievements) { this.achievements = achievements; }
    
    //ポートフォリオ情報
    public List<Portfolio> getPortfolios() { return portfolios; }
    public void setPortfolios(List<Portfolio> portfolios) { this.portfolios = portfolios; }
}