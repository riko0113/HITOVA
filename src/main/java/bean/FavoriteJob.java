package bean;

public class FavoriteJob implements java.io.Serializable{

	private Long favorite_job_id;
	private Long candidate_account_id;
	private Long job_id;
	
	
	//お気に入り求人ID
	public Long getFavorite_job_id() {
        return favorite_job_id;
    }
	
	public void setFavorite_job_id(Long favorite_job_id) {
        this.favorite_job_id = favorite_job_id;
    }
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
        return candidate_account_id;
    }
	
	public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
	
	//求人ID
	public Long getJob_id() {
        return job_id;
    }
	
	public void setJob_id(Long job_id) {
        this.job_id = job_id;
    }
	
	
}
