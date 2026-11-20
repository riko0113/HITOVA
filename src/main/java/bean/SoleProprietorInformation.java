package bean;

public class SoleProprietorInformation implements java.io.Serializable{

	private Long company_account_id;
	private String trade_name;
	private String company_representative_name;
	private String business_office_address;
	private String business_office_phone_number;
	private String store_verification_url;
	private String business_verification_url;
	
	
	//企業アカウントID
	public Long getCompany_account_id() {
        return company_account_id;
    }
	
	public void setCompany_account_id(Long company_account_id) {
        this.company_account_id = company_account_id;
    }
	
	//屋号
	public String getTrade_name() {
        return trade_name;
    }
	
	public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }
	
	//代表者名
	public String getCompany_representative_name() {
        return company_representative_name;
    }
	
	public void setCompany_representative_name(String company_representative_name) {
        this.company_representative_name = company_representative_name;
    }
	
	//事業所所在地
	public String getBusiness_office_address() {
        return business_office_address;
    }
	
	public void setBusiness_office_address(String business_office_address) {
        this.business_office_address = business_office_address;
    }
	
	//事業所電話番号
	public String getBusiness_office_phone_number() {
        return business_office_phone_number;
    }
	
	public void setBusiness_office_phone_number(String business_office_phone_number) {
        this.business_office_phone_number = business_office_phone_number;
    }
	
	//店舗確認URL
	public String getStore_verification_url() {
	    return store_verification_url;
	}
		
	public void setStore_verification_url(String store_verification_url) {
	    this.store_verification_url = store_verification_url;
	}
	
	//事業確認書類保存先URL
	public String getBusiness_verification_url() {
        return business_verification_url;
    }
	
	public void setBusiness_verification_url(String business_verification_url) {
        this.business_verification_url = business_verification_url;
    }
	
}
