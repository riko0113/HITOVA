package bean;

import java.time.LocalDate;

public class Achivement implements java.io.Serializable{

	private Long achivement_id;
	private Long task_id;
	private Long passorfail_id;
	private String passorfail_name;
	private String evaluation;
	private LocalDate achivement_date;
	private Long candidate_account_id;
	
	
	//実績提出ID
	public Long getAchivement_id() {
        return achivement_id;
    }
	
	public void setAchivement_id(Long achivement_id) {
        this.achivement_id = achivement_id;
    }
	
	//実績管理ID
	public Long getTask_id() {
        return task_id;
    }
	
	public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }
	
	//合否ID
	public Long getPassorfail_id() {
        return passorfail_id;
    }
	
	public void setPassorfail_id(Long passorfail_id) {
        this.passorfail_id = passorfail_id;
    }
	
	//合否名
	public String getPassorfail_name() {
        return passorfail_name;
    }
	
	public void setPassorfail_name(String passorfail_name) {
        this.passorfail_name = passorfail_name;
    }
	
	//評価
	public String getEvaluation() {
        return evaluation;
    }
	
	public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
	
	//取得日
	public LocalDate getAchivement_date() {
        return achivement_date;
    }
	
	public void setAchivement_date(LocalDate achivement_date) {
        this.achivement_date = achivement_date;
    }
	
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
        return candidate_account_id;
    }
	
	public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
	
}
