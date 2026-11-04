package bean;

import java.time.LocalDateTime;

public class ChatRoom implements java.io.Serializable{

	private Long room_id;
	private Long candidate_account_id;
	private Long company_account_id;
	private LocalDateTime created_at;
	
	
	
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
	
}
