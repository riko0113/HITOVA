package bean;

import java.time.LocalDate;

public class Review implements java.io.Serializable{

	private Long job_review_id;
	private Boolean gender;
	private int age;
	private String content;
	private Long job_id;
	private Long candidate_account_id;
	private String evalutation;
	private LocalDate posted_at;
	
	
	//口コミID
	public Long getJob_review_id() {
        return job_review_id;
    }
	
	public void setJob_review_id(Long job_review_id) {
        this.job_review_id = job_review_id;
    }
	
	//性別
	public Boolean getgender() {
        return gender;
    }
	
	public void setgender(Boolean gender) {
        this.gender = gender;
    }
	
	//年齢
	public int getAge() {
        return age;
    }
	
	public void setAge(int age) {
        this.age = age;
    }
	
	//内容
	public String getContent() {
        return content;
    }
	
	public void setContent(String content) {
        this.content = content;
    }
	
	//求人ID
	public Long getJob_id() {
        return job_id;
    }
	
	public void setJob_id(Long job_id) {
        this.job_id = job_id;
    }
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
	    return candidate_account_id;
	}
		
	public void setCandidate_account_id(Long candidate_account_id) {
	    this.candidate_account_id = candidate_account_id;
	}
	
	//評価
	public String getEvalutation() {
        return evalutation;
    }
	
	public void setEvalutation(String evalutation) {
        this.evalutation = evalutation;
    }
	
	//投稿日
	public LocalDate getPosted_at() {
        return posted_at;
    }
	
	public void setPosted_at(LocalDate posted_at) {
        this.posted_at = posted_at;
    }
	
}
