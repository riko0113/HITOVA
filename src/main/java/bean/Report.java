package bean;

import java.time.LocalDateTime;

public class Report implements java.io.Serializable{

	private Long report_id;
	private Long reporter_id;
	private Long reported_user_id;
	private String content;
	private LocalDateTime reported_at;
	private String reporter_type;
	private String reported_person_type;
	private String for_storing_evidence;
	
	
	//通報ID
	public Long getReport_id() {
        return report_id;
    }
	
	public void setReport_id(Long report_id) {
        this.report_id = report_id;
    }
	
	//通報者ID
	public Long getReporter_id() {
        return reporter_id;
    }
	
	public void setReporter_id(Long reporter_id) {
        this.reporter_id = reporter_id;
    }
	
	//通報対象者ID
	public Long getReported_user_id() {
        return reported_user_id;
    }
	
	public void setReported_user_id(Long reported_user_id) {
        this.reported_user_id = reported_user_id;
    }
	
	//内容
	public String getContent() {
        return content;
    }
	
	public void setContent(String content) {
        this.content = content;
    }
	
	//通報日時
	public LocalDateTime getReported_at() {
        return reported_at;
    }
	
	public void setReported_at(LocalDateTime reported_at) {
        this.reported_at = reported_at;
    }
	
	//通報者種別
	public String getReporter_type() {
        return reporter_type;
    }
	
	public void setReporter_type(String reporter_type) {
        this.reporter_type = reporter_type;
    }
	
	//通報対象者種別
	public String getReported_person_type() {
        return reported_person_type;
    }
	
	public void setReported_person_type(String reported_person_type) {
        this.reported_person_type = reported_person_type;
    }
	
	//証拠保存先URL
	public String getFor_storing_evidence() {
        return for_storing_evidence;
    }
	
	public void setFor_storing_evidence(String for_storing_evidence) {
        this.for_storing_evidence = for_storing_evidence;
    }
	
}
