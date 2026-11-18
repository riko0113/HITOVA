package bean;

public class CorporationInformation extends CompanyAccount {

    private String corporate_number;
    private String official_company_name;
    private String head_office_address;
    private String main_phone_number;
    private String company_website_url;
    
    // 法人番号
    public String getCorporate_number() {
        return corporate_number;
    }
    
    public void setCorporate_number(String corporate_number) {
        this.corporate_number = corporate_number;
    }
    
    // 正式会社名
    public String getOfficial_company_name() {
        return official_company_name;
    }
    
    public void setOfficial_company_name(String official_company_name) {
        this.official_company_name = official_company_name;
    }
    
    // 本社所在地
    public String getHead_office_address() {
        return head_office_address;
    }
    
    public void setHead_office_address(String head_office_address) {
        this.head_office_address = head_office_address;
    }
    
    // 代表電話番号
    public String getMain_phone_number() {
        return main_phone_number;
    }
    
    public void setMain_phone_number(String main_phone_number) {
        this.main_phone_number = main_phone_number;
    }
    
    // 会社ホームページURL
    public String getCompany_website_url() {
        return company_website_url;
    }
        
    public void setCompany_website_url(String company_website_url) {
        this.company_website_url = company_website_url;
    }
}