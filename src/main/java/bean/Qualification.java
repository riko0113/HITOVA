package bean;

import java.time.LocalDate;

public class Qualification implements java.io.Serializable{

    private Long qualification_id;
    private Long candidate_account_id;
    private LocalDate qualification_date;
    private Long qualification_detail_id;
    
    // 追加：画面表示用の資格名
    private String qualification_name;

    public String getQualification_name() {
        return qualification_name;
    }
    public void setQualification_name(String qualification_name) {
        this.qualification_name = qualification_name;
    }

    //資格詳細ID
    public Long getQualification_detail_id() {
        return qualification_detail_id;
    }
    public void setQualification_detail_id(Long qualification_detail_id) {
        this.qualification_detail_id = qualification_detail_id;
    }
    
    //求職者アカウントID
    public Long getCandidate_account_id() {
        return candidate_account_id;
    }
    public void setCandidate_account_id(Long candidate_account_id) {
        this.candidate_account_id = candidate_account_id;
    }
    
    //資格ID
    public Long getQualification_id() {
        return qualification_id;
    }
    public void setQualification_id(Long qualification_id) {
        this.qualification_id = qualification_id;
    }
    
    //取得日
    public LocalDate getQualification_date() {
        return qualification_date;
    }
    public void setQualification_date(LocalDate qualification_date) {
        this.qualification_date = qualification_date;
    }
}