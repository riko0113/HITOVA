package bean;

import java.time.LocalDateTime;

public class ApplicationHistory implements java.io.Serializable{

	private Long application_history_id;
	private Long candidate_account_id;
	private Long job_id;
	private String profile;
	private LocalDateTime application_at;
	
	
	//応募履歴ID
	public Long getApplication_history_id() {
        return application_history_id;
    }
	
	public void setApplication_history_id(Long application_history_id) {
        this.application_history_id = application_history_id;
    }
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
        return candidate_account_id;
    }
	
	public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
	
	//応募日時
	public LocalDateTime getApplication_at() {
        return application_at;
    }
	
	public void setApplication_at(LocalDateTime application_at) {
        this.application_at = application_at;
    }
	
	//プロフィール
	public String getProfile() {
        return profile;
    }
	
	public void setProfile(String profile) {
        this.profile = profile;
    }
	
	//求人ID
	public Long getJob_id() {
        return job_id;
    }
	
	public void setJob_id(Long job_id) {
        this.job_id = job_id;
    }
	
}
