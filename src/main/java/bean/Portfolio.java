package bean;

public class Portfolio implements java.io.Serializable{

	private Long portfolio_id;
	private String work_name;
	private String description;
	private String file_url;
	private Long candidate_account_id;
	
	
	//ポートフォリオID
	public Long getPortfolio_id() {
        return portfolio_id;
    }
	
	public void setPortfolio_id(Long portfolio_id) {
        this.portfolio_id = portfolio_id;
    }
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
        return candidate_account_id;
    }
	
	public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
	
	//作品名
	public String getWork_name() {
        return work_name;
    }
	
	public void setWork_name(String work_name) {
        this.work_name = work_name;
    }
	
	//説明文
	public String getDescription() {
        return description;
    }
	
	public void setDescription(String description) {
        this.description = description;
    }
	
	//ファイル保存先URL
	public String getFile_url() {
        return file_url;
    }
	
	public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

}
