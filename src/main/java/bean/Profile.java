package bean;

import java.time.LocalDateTime;

public class Profile implements java.io.Serializable{

	private Long profile_id;
	private Long desired_employment_type_id;
	private Long desired_job_type_id;
	private String desired_job_type_name;
	private Long candidate_account_id;
	private String last_name;
	private String first_name;
	private String last_name_kana;
	private String first_name_kana;
	private LocalDateTime birth_date;
	private Boolean gender;
	private String current_postal_code;
	private String current_address;
	private String desired_work_location;
	private String highest_education;
	private String work_history;
	
	
	//プロフィールID
	public Long getProfile_id() {
        return profile_id;
    }
	
	public void setProfile_id(Long profile_id) {
        this.profile_id = profile_id;
    }
	
	//希望雇用形態ID
	public Long getDesired_employment_type_id() {
        return desired_employment_type_id;
    }
	
	public void setDesired_employment_type_id(Long desired_employment_type_id) {
        this.desired_employment_type_id = desired_employment_type_id;
    }
	
	//希望職種ID
	public Long getDesired_job_type_id() {
        return desired_job_type_id;
    }
	
	public void setDesired_job_type_id(Long desired_job_type_id) {
        this.desired_job_type_id = desired_job_type_id;
    }
	
	//希望職種名
	public String getDesired_job_type_name() {
	    return desired_job_type_name;
	}
		
	public void setDesired_job_type_name(String desired_job_type_name) {
	    this.desired_job_type_name = desired_job_type_name;
	}
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
        return candidate_account_id;
    }
	
	public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
	
	//氏名姓
	public String getLast_name() {
        return last_name;
    }
	
	public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
	
	//氏名名
	public String getFirst_name() {
	    return first_name;
	}
		
	public void setFirst_name(String first_name) {
	    this.first_name = first_name;
	}
	
	//氏名姓カナ
	public String getLast_name_kana() {
        return last_name_kana;
    }
	
	public void setLast_name_kana(String last_name_kana) {
        this.last_name_kana = last_name_kana;
    }
	
	//氏名名カナ
	public String getFirst_name_kana() {
        return first_name_kana;
    }
	
	public void setFirst_name_kana(String first_name_kana) {
        this.first_name_kana = first_name_kana;
    }
	
	
	//生年月日
	public LocalDateTime getBirth_date() {
        return birth_date;
    }
	
	public void setBirth_date(LocalDateTime birth_date) {
        this.birth_date = birth_date;
    }
	
	//性別
	public Boolean getgender() {
        return gender;
    }
	
	public void setgender(Boolean gender) {
        this.gender = gender;
    }
	
	//現住所郵便番号
	public String getCurrent_postal_code() {
        return current_postal_code;
    }
	
	public void setCurrent_postal_code(String current_postal_code) {
        this.current_postal_code = current_postal_code;
    }
	
	//現住所
	public String getCurrent_address() {
        return current_address;
	}
		
	public void setCurrent_address(String current_address) {
        this.current_address = current_address;
	}
	
	//希望勤務地名
	public String getDesired_work_location() {
        return desired_work_location;
	}
		
	public void setDesired_work_location(String desired_work_location) {
        this.desired_work_location = desired_work_location;
	}
		
	//最終学歴
	public String getHighest_education() {
        return highest_education;
	}
		
	public void setHighest_education(String highest_education) {
        this.highest_education = highest_education;
	}
	
	//職務履歴
	public String getWork_history() {
        return work_history;
	}
		
	public void setWork_history(String work_history) {
        this.work_history = work_history;
	}
	
}
