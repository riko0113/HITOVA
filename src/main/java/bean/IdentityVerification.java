package bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IdentityVerification implements java.io.Serializable{

	private Long verification_id;
	private String name;
	private LocalDate birth_date;
	private String address;
	private String document_file_url;
	private Boolean review_flag;
	private LocalDateTime application_at;
	private Long candidate_account_id;
	
	
	//本人確認ID
	public Long getVerification_id() {
        return verification_id;
    }
	
	public void setVerification_id(Long verification_id) {
        this.verification_id = verification_id;
    }
	
	//氏名
	public String getName() {
        return name;
    }
	
	public void setName(String name) {
        this.name = name;
    }
	
	//生年月日
	public LocalDate getBirth_date() {
        return birth_date;
    }
	
	public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }
	
	//住所
	public String getAddress() {
        return address;
    }
	
	public void setAddress(String address) {
        this.address = address;
    }
	
	//本人確認書類保存先URL
	public String getDocument_file_url() {
        return document_file_url;
    }
	
	public void setDocument_file_url(String document_file_url) {
        this.document_file_url = document_file_url;
    }
	
	//審査可否
	public Boolean getReview_flag() {
        return review_flag;
    }
	
	public void setReview_flag(Boolean review_flag) {
        this.review_flag = review_flag;
    }
	
	//申請日時
	public LocalDateTime getApplication_at() {
        return application_at;
    }
	
	public void setApplication_at(LocalDateTime application_at) {
        this.application_at = application_at;
    }
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
        return candidate_account_id;
    }
	
	public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
	
}
