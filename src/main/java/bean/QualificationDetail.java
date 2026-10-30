package bean;

public class QualificationDetail implements java.io.Serializable{

	private Long qualification_detail_id;
	private String qualification_name;
	private Long field_id;
	private String field_name;
	
	
	//資格詳細ID
	public Long getQualification_detail_id() {
        return qualification_detail_id;
    }
	
	public void setQualification_detail_id(Long qualification_detail_id) {
        this.qualification_detail_id = qualification_detail_id;
    }
	
	//資格名
	public String getQualification_name() {
        return qualification_name;
    }
	
	public void setQualification_name(String qualification_name) {
        this.qualification_name = qualification_name;
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
	
}
