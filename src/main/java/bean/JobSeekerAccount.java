package bean;

import java.time.LocalDateTime;

public class JobSeekerAccount implements java.io.Serializable{

	private Long candidate_id;
	private String nick_name;
	private String email;
	private Long authority_id;
	private String authority_name;
	private LocalDateTime created_at;
	private Boolean identity_verification;
	private String firebase_id;
	private Boolean scout;
	private int account_status_id;
	private String account_status_name;
	private int desired_industry_id;
	private String desired_industry_name;
	private int desired_occupation_id;
	private String desired_occupation_name;
	private int union_id;
	private String union_name;
	
	
	//求職者アカウントID
	public Long getCandidate_id() {
        return candidate_id;
    }
	
	public void setCandidate_id(Long candidate_id) {
        this.candidate_id = candidate_id;
    }
	
	//ニックネーム
	public String getNick_name() {
        return nick_name;
    }
	
	public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
	
	//メールアドレス
	public String getEmail() {
        return email;
    }
	
	public void setEmail(String email) {
        this.email = email;
    }
	
	//権限ID
	public Long getAuthority_id() {
        return authority_id;
    }
	
	public void setAuthority_id(Long authority_id) {
        this.authority_id = authority_id;
    }
	
	//権限名
	public String getAuthority_name() {
        return authority_name;
    }
	
	public void setAuthority_name(String authority_name) {
        this.authority_name = authority_name;
    }
	
	//作成日時
	public LocalDateTime getCreated_at() {
	    return created_at;
	}
		
	public void setCreated_at(LocalDateTime created_at) {
	    this.created_at = created_at;
	}
	
	//本人確認
	public Boolean getIdentity_verification() {
        return identity_verification;
    }
	
	public void setIdentity_verification(Boolean identity_verification) {
        this.identity_verification = identity_verification;
    }
	
	//FirebaseID
	public String getFirebase_id() {
        return firebase_id;
    }
	
	public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }
	
	
	//スカウト
	public Boolean getScout() {
        return scout;
    }
	
	public void setScout(Boolean scout) {
        this.scout = scout;
    }
	
	//アカウント状態ID
	public int getAccount_status_id() {
        return account_status_id;
    }
	
	public void setAccount_status_id(int account_status_id) {
        this.account_status_id = account_status_id;
    }
	
	//アカウント状態名
	public String getAccount_status_name() {
        return account_status_name;
    }
	
	public void setAccount_status_name(String account_status_name) {
        this.account_status_name = account_status_name;
    }
	
	//希望業種ID
	public int getDesired_industry_id() {
        return desired_industry_id;
	}
		
	public void setDesired_industry_id(int desired_industry_id) {
        this.desired_industry_id = desired_industry_id;
	}
	
	//業種名
	public String getDesired_industry_name() {
        return desired_industry_name;
	}
			
	public void setDesired_industry_name(String desired_industry_name) {
        this.desired_industry_name = desired_industry_name;
	}
	
	//希望職種ID
	public int getDesired_occupation_id() {
        return desired_occupation_id;
    }
		
	public void setDesired_occupation_id(int desired_occupation_id) {
        this.desired_occupation_id = desired_occupation_id;
    }
		
	//職種名
	public String getDesired_occupation_name() {
        return desired_occupation_name;
    }
		
	public void setDesired_occupation_name(String desired_occupation_name) {
        this.desired_occupation_name = desired_occupation_name;
    }
	
	//所属ID 
	public int getUnion_id() {
        return union_id;
    }
		
	public void setUnion_id(int union_id) {
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
