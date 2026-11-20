package bean;

import java.time.LocalDateTime;

public class ChatRoom implements java.io.Serializable{

	private Long room_id;
	private Long candidate_account_id;
	private Long company_account_id;
	private LocalDateTime created_at;
	private String candidate_name;
	private String company_name;
	
	
	//チャットルームID
	public Long getRoom_id() {
        return room_id;
    }
	
	public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
        return candidate_account_id;
    }
	
	public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
	
	//企業アカウントID
	public Long getCompany_account_id() {
        return company_account_id;
    }
	
	public void setCompany_account_id(Long company_account_id) {
        this.company_account_id = company_account_id;
    }
	
	//作成日時
	public LocalDateTime getCreated_at() {
        return created_at;
    }
	
	public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
	
	//求職者名
	public String getCandidate_name() {
		return candidate_name;
	}
	
	public void setCandidate_name(String candidate_name) {
		this.candidate_name = candidate_name;
	}
	
	//企業名
	public String getCompany_name() {
		return company_name;
	}
	
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
}
