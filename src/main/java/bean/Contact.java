package bean;

import java.util.Date;

public class Contact implements java.io.Serializable{

	private Long contact_id;
	private Long contact_type_id;
	private String contact_type_name;
	private Date date_received;
	private String contact_us;
	private Long sender_type_id;
	private String sender_type_name;
	private String name;
	private String email;
	private String corporate_number;
	private String company_representative_name;
	
	
	//お問い合わせID
	public Long getContact_id() {
        return contact_id;
    }
	
	public void setContact_id(Long contact_id) {
        this.contact_id = contact_id;
    }
	
	//お問い合わせ種別ID
	public Long getContact_type_id() {
        return contact_type_id;
    }
	
	public void setContact_type_id(Long contact_type_id) {
        this.contact_type_id = contact_type_id;
    }
	
	//お問い合わせ種別名
	public String getContact_type_name() {
        return contact_type_name;
    }
	
	public void setContact_type_name(String contact_type_name) {
        this.contact_type_name = contact_type_name;
    }
	
	//受付日
	public Date getDate_received() {
        return date_received;
    }
	
	public void setDate_received(Date date_received) {
        this.date_received = date_received;
    }
	
	//お問い合わせ内容
	public String getContact_us() {
        return contact_us;
    }
	
	public void setContact_us(String contact_us) {
        this.contact_us = contact_us;
    }
	
	//送信者種別ID
	public Long getSender_type_id() {
        return sender_type_id;
    }
	
	public void setSender_type_id(Long sender_type_id) {
        this.sender_type_id = sender_type_id;
    }
	
	//送信者種別名
	public String getSender_type_name() {
        return sender_type_name;
    }
	
	public void setSender_type_name(String sender_type_name) {
        this.sender_type_name = sender_type_name;
    }
	
	//メールアドレス
	public String getEmail() {
        return email;
    }
	
	public void setEmail(String email) {
        this.email = email;
    }
	
	//ニックネーム/企業名/屋号
	public String getName() {
        return name;
    }
	
	public void setName(String name) {
        this.name = name;
    }
	
	//法人番号
	public String getCorporate_number() {
        return corporate_number;
    }
	
	public void setCorporate_number(String corporate_number) {
        this.corporate_number = corporate_number;
    }
	
	//代表者名
	public String getCompany_representative_name() {
        return company_representative_name;
    }
	
	public void setCompany_representative_name(String company_representative_name) {
        this.company_representative_name = company_representative_name;
    }
}
