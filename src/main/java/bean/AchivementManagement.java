package bean;

import java.time.LocalDate;

public class AchivementManagement implements java.io.Serializable{

	private Long task_id;
	private String achievement;
	private String requirement;
	private Long howtosubmit_id;
	private String howtosubmit_name;
	private LocalDate registration_date;
	private Long field_id;
	private String field_name;
	private Long category_id;
	private String category_name;
	private Long union_id;
	private String union_name;
	
	
	//実績ID
	public Long getTask_id() {
        return task_id;
    }
	
	public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }
	
	//実績名
	public String getAchievement() {
        return achievement;
    }
	
	public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
	
	//達成条件
	public String getRequirement() {
	    return requirement;
	}
		
	public void setRequirement(String requirement) {
	    this.requirement = requirement;
	}
	
	//提出方法ID
	public Long getHowtosubmit_id() {
        return howtosubmit_id;
    }
	
	public void setHowtosubmit_id(Long howtosubmit_id) {
        this.howtosubmit_id = howtosubmit_id;
    }
	
	//提出方法名
	public String getHowtosubmit_name() {
	    return howtosubmit_name;
	}
		
	public void setHowtosubmit_name(String howtosubmit_name) {
	    this.howtosubmit_name = howtosubmit_name;
	}
	
	//登録日
	public LocalDate getRegistration_date() {
        return registration_date;
    }
	
	public void setRegistration_dat(LocalDate registration_date) {
        this.registration_date = registration_date;
    }
	
	//分野ID
	public Long getField_id() {
        return field_id;
    }
	
	public void setField_id(Long field_id) {
        this.field_id = field_id;
    }
	
	//分野名
	public String getField_name() {
	    return field_name;
	}
		
	public void setField_name(String field_name) {
	    this.field_name = field_name;
	}
	
	//区分ID
	public Long getCategory_id() {
        return category_id;
    }
	
	public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
	
	//区分名
	public String getCategory_name() {
        return category_name;
    }
	
	public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
	
	
	//所属ID
	public Long getUnion_id() {
        return union_id;
    }
	
	public void setUnion_id(Long union_id) {
        this.union_id = union_id;
    }
	
	//所属名
	public String getUnion_name() {
        return union_name;
    }
	
	public void setUnion_name(String union_name) {
        this.union_name = union_name;
    }
	
	
}
