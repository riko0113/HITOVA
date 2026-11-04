package bean;

public class Skill implements java.io.Serializable{

	private Long skill_id;
	private Long candidate_account_id;
	private String skill_name;
	private String skill_content;
	
	
	
	//スキルID
	public Long getSkill_id() {
        return skill_id;
    }
	
	public void setSkill_id(Long skill_id) {
        this.skill_id = skill_id;
    }
	
	//求職者アカウントID
	public Long getCandidate_account_id() {
        return candidate_account_id;
    }
	
	public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
	
	//スキル名
	public String getSkill_name() {
        return skill_name;
    }
	
	public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }
	
	//スキル内容
	public String getSkill_content() {
        return skill_content;
    }
	
	public void setSkill_content(String skill_content) {
        this.skill_content = skill_content;
    }
	
}
