package bean;

import java.time.LocalDateTime;

public class Notification implements java.io.Serializable{

	private Long notification_id;
	private String message;
	private Long receiver_type_id;
	private String receiver_type_name;
	private LocalDateTime send_at;
	private Long receiver_account_id;
	
	
	//通知ID
	public Long getNotification_id() {
        return notification_id;
    }
	
	public void setNotification_id(Long notification_id) {
        this.notification_id = notification_id;
    }
	
	//メッセージ
	public String getMessage() {
        return message;
    }
	
	public void setMessage(String message) {
        this.message = message;
    }
	
	//宛先種別ID
	public Long getReceiver_type_id() {
        return receiver_type_id;
    }
	
	public void setReceiver_type_id(Long receiver_type_id) {
        this.receiver_type_id = receiver_type_id;
    }
	
	//宛先種別名
	public String getReceiver_type_name() {
        return receiver_type_name;
    }
	
	public void setReceiver_type_name(String receiver_type_name) {
        this.receiver_type_name = receiver_type_name;
    }
	
	//送信日時
	public LocalDateTime getSend_at() {
        return send_at;
    }
	
	public void setSend_at(LocalDateTime send_at) {
        this.send_at = send_at;
    }
	
	//宛先アカウントID
	public Long getReceiver_account_id() {
        return receiver_account_id;
    }
	
	public void setReceiver_account_id(Long receiver_account_id) {
        this.receiver_account_id = receiver_account_id;
    }
	
}
