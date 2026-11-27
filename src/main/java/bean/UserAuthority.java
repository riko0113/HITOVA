package bean;

public class UserAuthority {
	private Long userId;
	private String userName;
	private String userType;
	private int accountStatusId;
	private int authorityId;
	private String authorityName;
	
	//ユーザーIDを取得する
	public Long getUserId() {
		return userId;
	}
	
	//ユーザーID設定する
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	//氏名を取得する
	public String getUserName() {
		return userName;
	}
	
	//氏名を設定する
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//ユーザー種別を取得する
	public String getUserType() {
		return userType;
	}
	
	//ユーザー種別設定する
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	//アカウント状態IDを取得する
	public int getAccountStatusId() {
		return accountStatusId;
	}
	
	//アカウント状態IDを設定する
	public void setAccountStatusId(int accountStatusId) {
		this.accountStatusId = accountStatusId;
	}
	
	//現在の権限IDを取得する
	public int getAuthorityId() {
		return authorityId;
	}
	
	//現在の権限IDを設定する
	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}
	
	//現在の権限名を取得する
	public String getAuthorityName() {
		return authorityName;
	}
	
	//現在の権限名を設定する
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
}
