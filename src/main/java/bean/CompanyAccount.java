package bean;

public class CompanyAccount implements java.io.Serializable{

	private Long company_account_id;
	private String person_name;
	private String person_name_kana;
	private String department;
	private String position;
	private String email;
	private String firebase_id;
	private String business_type;
	private String business_description;
	private Long industry_id;
	private String industry_name;
	private int number_of_employee;
	private int established_year;
	private Boolean review_status;
	private Long account_status_id;
	private String account_status_name;
	private Long union_id;
	private String union_name;
	
	
	//企業アカウントID
	public Long getCompany_account_id() {
        return company_account_id;
    }
	
	public void setCompany_account_id(Long company_account_id) {
        this.company_account_id = company_account_id;
    }
	
	//担当者名
	public String getPerson_name() {
        return person_name;
    }
	
	public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }
	
	//フリガナ
	public String getPerson_name_kana() {
        return person_name_kana;
    }
	
	public void setPerson_name_kana(String person_name_kana) {
        this.person_name_kana = person_name_kana;
    }
	
	//部署
	public String getDepartment() {
        return department;
    }
	
	public void setDepartment(String department) {
        this.department = department;
    }
	
	//役職
	public String getPosition() {
        return position;
    }
	
	public void setPosition(String position) {
        this.position = position;
    }
	
	//メールアドレス
	public String getEmail() {
	    return email;
	}
		
	public void setEmail(String email) {
	    this.email = email;
	}
	
	//Firebase_id
	public String getFirebase_id() {
        return firebase_id;
    }
	
	public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }
	
	//事業形態
	public String getBusiness_type() {
        return business_type;
    }
	
	public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }
	
	
	//事業内容
	public String getBusiness_description() {
        return business_description;
    }
	
	public void setBusiness_description(String business_description) {
        this.business_description = business_description;
    }
	
	//業種ID
	public Long getIndustry_id() {
        return industry_id;
    }
	
	public void setIndustry_id(Long industry_id) {
        this.industry_id = industry_id;
    }
	
	//業種名
	public String getIndustry_name() {
        return industry_name;
    }
	
	public void setIndustry_name(String industry_name) {
        this.industry_name = industry_name;
    }
	
	//従業員数
	public int getNumber_of_employee() {
        return number_of_employee;
	}
		
	public void setNumber_of_employee(int number_of_employee) {
        this.number_of_employee = number_of_employee;
	}
	
	//設立年
	public int getEstablished_year() {
        return established_year;
	}
			
	public void setEstablished_year(int established_year) {
        this.established_year = established_year;
	}
	
	//審査状況
	public Boolean getReview_status() {
        return review_status;
    }
		
	public void setReview_status(Boolean review_status) {
        this.review_status = review_status;
    }
		
	//アカウント状態ID
	public Long getAccount_status_id() {
        return account_status_id;
    }
		
	public void setAccount_status_id(Long account_status_id) {
        this.account_status_id = account_status_id;
    }
	
	//アカウント状態名
	public String getAccount_status_name() {
        return account_status_name;
    }
		
	public void setAccount_status_name(String account_status_name) {
        this.account_status_name = account_status_name;
    }
	
	//所属ID
	public Long getUnion_id() {
        return union_id;
    }
		
	public void setUnion_id(Long union_id) {
        this.union_id = union_id;
    }
	
	//所属名
	public String getUnion_name() {
        return union_name;
    }
		
	public void setUnion_name(String union_name) {
        this.union_name = union_name;
    }
}
