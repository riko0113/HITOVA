package bean;

import java.time.LocalDateTime;

public class Job implements java.io.Serializable{

	private Long job_id;
	private Long job_type_id;
	private String job_type_name;
	private Long company_account_id;
	private int employment_type_id;
	private String employment_type_name;
	private int salary_type_id;
	private String salary_type_name;
	private String title;
	private String description;
	private String requirement;
	private String headcount;
	private String location;
	private String access;
	private String salary;
	private String allowance;
	private Boolean transportation_fee;
	private String working_hours;
	private String holiday;
	private String smoking_prevention;
	private String benefits;
	private String insurance;
	private String image_url;
	private String workplace_pr;
	private String selection_process;
	private String interview_location;
	private Boolean publication_status;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	
	//求人ID
	public Long getJob_id() {
        return job_id;
    }
	
	public void setJob_id(Long job_id) {
        this.job_id = job_id;
    }
	
	//職種ID
	public Long getJob_type_id() {
        return job_type_id;
    }
	
	public void setJob_type_id(Long job_type_id) {
        this.job_type_id = job_type_id;
    }
	
	//職種名
	public String getJob_type_name() {
        return job_type_name;
    }
	
	public void setJob_type_name(String job_type_name) {
        this.job_type_name = job_type_name;
    }
	
	//企業アカウントID
	public Long getCompany_account_id() {
        return company_account_id;
    }
	
	public void setCompany_account_id(Long company_account_id) {
        this.company_account_id = company_account_id;
    }
	
	//雇用形態ID
	public int getEmployment_type_id() {
        return employment_type_id;
    }
	
	public void setEmployment_type_id(int employment_type_id) {
        this.employment_type_id = employment_type_id;
    }
	
	//雇用形態名
	public String getEmployment_type_name() {
	    return employment_type_name;
	}
		
	public void setEmployment_type_name(String employment_type_name) {
	    this.employment_type_name = employment_type_name;
	}
	
	//給与区分ID
	public int getSalary_type_id() {
        return salary_type_id;
    }
	
	public void setSalary_type_id(int salary_type_id) {
        this.salary_type_id = salary_type_id;
    }
	
	//給与区分名
	public String getSalary_type_name() {
        return salary_type_name;
    }
	
	public void setSalary_type_name(String salary_type_name) {
        this.salary_type_name = salary_type_name;
    }
	
	
	//求人タイトル
	public String getTitle() {
        return title;
    }
	
	public void setTitle(String title) {
        this.title = title;
    }
	
	//仕事内容
	public String getDescription() {
        return description;
    }
	
	public void setDescription(String description) {
        this.description = description;
    }
	
	//応募資格・条件
	public String getRequirement() {
        return requirement;
    }
	
	public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
	
	//募集人数
	public String getHeadcount() {
        return headcount;
	}
		
	public void setHeadcount(String headcount) {
        this.headcount = headcount;
	}
	
	//勤務地
	public String getLocation() {
        return location;
	}
			
	public void setLocation(String location) {
        this.location = location;
	}
	
	//最寄り駅・アクセス
	public String getaccess() {
        return access;
    }
		
	public void setaccess(String access) {
        this.access = access;
    }
		
	//給与
	public String getSalary() {
        return salary;
    }
		
	public void setSalary(String salary) {
        this.salary = salary;
    }
	
	//手当
	public String getAllowance() {
        return allowance;
    }
		
	public void setAllowance(String allowance) {
        this.allowance = allowance;
    }
	
	//交通費
	public Boolean getTransportation_fee() {
        return transportation_fee;
    }
		
	public void setTransportation_fee(Boolean transportation_fee) {
        this.transportation_fee = transportation_fee;
    }
	
	//勤務時間・シフト
	public String getWorking_hours() {
        return working_hours;
    }
		
	public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }
	
	//休日・休暇
	public String getHoliday() {
        return holiday;
    }
		
	public void setHoliday(String holiday) {
        this.holiday = holiday;
    }
	
	//受動喫煙対策
	public String getSmoking_prevention() {
        return smoking_prevention;
    }
		
	public void setSmoking_prevention(String smoking_prevention) {
        this.smoking_prevention = smoking_prevention;
    }
	
	//求人画像保存先URL
	public String getImage_url() {
        return image_url;
    }
		
	public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
	
	//加入保険
	public String getInsurance() {
        return insurance;
    }
		
	public void setInsurance(String insurance) {
        this.insurance = insurance;
    }
	
	//待遇福利厚生
	public String getBenefits() {
        return benefits;
    }
		
	public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
	
	//職場の特徴
	public String getWorkplace_pr() {
        return workplace_pr;
    }
		
	public void setWorkplace_pr(String workplace_pr) {
        this.workplace_pr = workplace_pr;
    }
	
	//選考プロセス
	public String getSelection_process() {
        return selection_process;
    }
		
	public void setSelection_process(String selection_process) {
        this.selection_process = selection_process;
    }
	
	//面接場所
	public String getInterview_location() {
        return interview_location;
    }
		
	public void setInterview_location(String interview_location) {
        this.interview_location = interview_location;
    }
	
	//掲載ステータス
	public Boolean getPublication_status() {
        return publication_status;
    }
		
	public void setPublication_status(Boolean publication_status) {
        this.publication_status = publication_status;
    }
	
	//作成日時
	public LocalDateTime getCreated_at() {
        return created_at;
    }
		
	public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
	
	//更新日時
	public LocalDateTime getUpdated_at() {
        return updated_at;
    }
		
	public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
