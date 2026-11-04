package bean;

import java.time.LocalDateTime;

public class ChatMessage implements java.io.Serializable{

	private Long message_id;
	private Long room_id;
	private LocalDateTime send_at;
	private String send_content;
	private Boolean sender_type;
	
	
	//チャットメッセージID
	public Long getMessage_id() {
        return message_id;
    }
	
	public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }
	
	//チャットルームID
	public Long getRoom_id() {
        return room_id;
    }
	
	public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }
	
	//送信日時
	public LocalDateTime getSend_at() {
        return send_at;
    }
	
	public void setSend_at(LocalDateTime send_at) {
        this.send_at = send_at;
    }
	
	//送信内容
	public String getSend_content() {
        return send_content;
    }
	
	public void setSend_content(String send_content) {
        this.send_content = send_content;
    }
	
	//送信者種別名
	public Boolean getSender_type() {
        return sender_type;
    }
	
	public void setSender_type(Boolean sender_type) {
        this.sender_type = sender_type;
    }
	
}
