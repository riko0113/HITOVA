package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;

import bean.Job;

public class JobDAO extends DAO {
	
	//登録求人一覧を取得するメソッド
    public List<Job> searchAll(Long companyAccountId) throws Exception {
        List<Job> list = new ArrayList<>();
        
        // 求人情報と各マスタ情報（職種、雇用形態、給与区分など）を結合して取得するSQL
        String sql = "SELECT j.job_id, " +
                     "j.job_type_id, " +
                     "jt.job_type_name, " +
                     "j.company_account_id, " +
                     "j.employment_type_id, " +
                     "et.employment_type_name, " +
                     "j.salary_type_id, " +
                     "st.salary_type_name, " +
                     "j.title, " +
                     "j.description, " +
                     "j.requirement, " +
                     "j.headcount, " +
                     "j.location, " +
                     "j.access, " +
                     "j.salary, " +
                     "j.allowance, " +
                     "j.transportation_fee, " +
                     "j.working_hours, " +
                     "j.holiday, " +
                     "j.smoking_prevention, " +
                     "j.image_url, " +
                     "j.insurance, " +
                     "j.benefits, " +
                     "j.workplace_pr, " +
                     "j.selection_process, " +
                     "j.interview_location, " +
                     "j.publication_status, " +
                     "j.created_at, " +
                     "j.updated_at " +
                     "FROM jobs j " +
                     "LEFT JOIN occupations jt ON j.job_type_id = jt.occupations_id " +
                     "LEFT JOIN employments_type et ON j.employment_type_id = et.employment_type_id " +
                     "LEFT JOIN salaries_type st ON j.salary_type_id = st.salary_type_id " +
                     "WHERE j.company_account_id = ?";

        // try-with-resources構文でコネクションとステートメントを自動クローズ
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            // 企業アカウントIDをセット
            pstmt.setLong(1, companyAccountId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Job job = new Job();
                    
                    //Job Bean にセット
                    job.setJob_id(rs.getLong("job_id"));
                    job.setJob_type_id(rs.getLong("job_type_id"));
                    job.setJob_type_name(rs.getString("job_type_name"));
                    job.setCompany_account_id(rs.getLong("company_account_id"));
                    job.setEmployment_type_id(rs.getInt("employment_type_id"));
                    job.setEmployment_type_name(rs.getString("employment_type_name"));
                    job.setSalary_type_id(rs.getInt("salary_type_id"));
                    job.setSalary_type_name(rs.getString("salary_type_name"));
                    job.setTitle(rs.getString("title"));
                    job.setDescription(rs.getString("description"));
                    job.setRequirement(rs.getString("requirement"));
                    job.setHeadcount(rs.getString("headcount"));
                    job.setLocation(rs.getString("location"));
                    job.setaccess(rs.getString("access")); 
                    job.setSalary(rs.getString("salary"));
                    job.setAllowance(rs.getString("allowance"));
                    
                    // Boolean型 (NULL許容の場合はオブジェクトとして取得するか判定)
                    job.setTransportation_fee(rs.getObject("transportation_fee") != null ? rs.getBoolean("transportation_fee") : null);
                    
                    job.setWorking_hours(rs.getString("working_hours"));
                    job.setHoliday(rs.getString("holiday"));
                    job.setSmoking_prevention(rs.getString("smoking_prevention"));
                    job.setImage_url(rs.getString("image_url"));
                    job.setInsurance(rs.getString("insurance"));
                    job.setBenefits(rs.getString("benefits"));
                    job.setWorkplace_pr(rs.getString("workplace_pr"));
                    job.setSelection_process(rs.getString("selection_process"));
                    job.setInterview_location(rs.getString("interview_location"));
                    
                    job.setPublication_status(rs.getObject("publication_status") != null ? rs.getBoolean("publication_status") : null);
                    
                    // 日時データの変換 (Timestamp -> LocalDateTime)
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    if (createdAt != null) {
                        job.setCreated_at(createdAt.toLocalDateTime());
                    }
                    
                    Timestamp updatedAt = rs.getTimestamp("updated_at");
                    if (updatedAt != null) {
                        job.setUpdated_at(updatedAt.toLocalDateTime());
                    }

                    list.add(job);
                }
            }
        }
        return list;
    }
    
    //求人公開ステータスを変更するメソッド
    public int updateStatus(Long companyAccountId, Long jobId, Boolean status) throws Exception {
    	
    	// SQL文の定義（WHERE句でjob_idとcompany_account_idを指定し、他人のデータを書き換えられないようにする）
        String sql = "UPDATE jobs SET status = ? WHERE job_id = ? AND company_account_id = ?";
        
        int rowCount = 0;

        // try-with-resources構文を用いて、ConnectionやPreparedStatementを確実にクローズする
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // プレースホルダーに値をセット
            st.setBoolean(1, status);
            st.setLong(2, jobId);
            st.setLong(3, companyAccountId);
            
            // UPDATE文を実行し、影響を受けた行数を取得
            rowCount = st.executeUpdate();
            
        } catch (SQLException e) {
            throw e; // 呼び出し元（Actionクラス）のtry-catchで捕捉できるように再スロー
        }
        
        return rowCount;
    }
    
    //求人を削除するメソッド
    public int delete(Long jobId, Long companyAccountId) throws Exception {
    	
    	// SQL文の定義（WHERE句でjob_idとcompany_account_idを指定）
        String sql = "DELETE FROM jobs WHERE job_id = ? AND company_account_id = ?";
        
        int rowCount = 0;

        // try-with-resources構文を用いて、ConnectionやPreparedStatementを確実にクローズする
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // プレースホルダーに値をセット
            st.setLong(1, jobId);
            st.setLong(2, companyAccountId);
            
            // DELETE文を実行し、影響を受けた行数を取得
            rowCount = st.executeUpdate();
            
        } catch (SQLException e) {
            throw e; // 呼び出し元（Actionクラス）のtry-catchで捕捉できるように再スロー
        }
        
        return rowCount;
    }
    
    // 求人を登録するメソッド
    public int insert(
            Long userId, 
            int jobTypeId, 
            String title, 
            int employmentTypeId, 
            String description, 
            String requirement, 
            String headcount, 
            String location, 
            String access, 
            int salaryTypeId, 
            String salary, 
            String allowance, 
            boolean transportationFee, 
            String workingHours, 
            String holiday, 
            String smokingPrevention, 
            String[] insurance, 
            String benefits, 
            String imageUrl, 
            String workplacePr, 
            String selectionProcess, 
            String interviewLocation, 
            boolean publicationStatus
    ) throws Exception {
        
        // SQL文の定義（プレースホルダーを使用し、SQLインジェクションを防止）
        String sql = "INSERT INTO jobs ("
                   + "job_type_id, title, company_account_id, employment_type_id, description, "
                   + "requirement, headcount, location, access, salary_type_id, "
                   + "salary, allowance, transportation_fee, working_hours, holiday, "
                   + "smoking_prevention, insurance, benefits, image_url, workplace_pr, "
                   + "selection_process, interview_location, publication_status"
                   + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        int rowCount = 0;

        // try-with-resources構文を用いて、ConnectionやPreparedStatementを確実にクローズする
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
        	// プレースホルダーに値をセット
            st.setInt(1, jobTypeId);
            st.setString(2, title);
            st.setLong(3, userId);
            st.setInt(4, employmentTypeId);
            st.setString(5, description);
            st.setString(6, requirement);
            st.setString(7, headcount);
            st.setString(8, location);
            st.setString(9, access);
            st.setInt(10, salaryTypeId);
            st.setString(11, salary);
            st.setString(12, allowance);
            st.setBoolean(13, transportationFee);
            st.setString(14, workingHours);
            st.setString(15, holiday);
            st.setString(16, smokingPrevention);
            
            // insuranceのString[] をJSON配列の文字列に変換して設定
            PGobject jsonObject = new PGobject();
            jsonObject.setType("jsonb");
            jsonObject.setValue(convertStringArrayToJson(insurance));
            st.setObject(16 + 1, jsonObject);
            
            st.setString(18, benefits);
            st.setString(19, imageUrl);
            st.setString(20, workplacePr);
            st.setString(21, selectionProcess);
            st.setString(22, interviewLocation);
            st.setBoolean(23, publicationStatus);
            
            // INSERT文を実行し、影響を受けた行数を取得
            rowCount = st.executeUpdate();
            
        } catch (SQLException e) {
            throw e; // 呼び出し元（Actionクラスなど）のtry-catchで捕捉できるように再スロー
        }
        
        return rowCount;
    }
    
    // String[] をJSON形式の文字列に変換するメソッド
    private String convertStringArrayToJson(String[] array) {
    	// 配列がnull、または要素が空の場合は、空のJSON配列を返す
        if (array == null || array.length == 0) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            // 要素内のダブルクォーテーション（"）をエスケープしつつ、前後をダブルクォーテーションで囲む
            sb.append("\"").append(array[i].replace("\"", "\\\"")).append("\"");
            
            // 最後の要素以外の場合は、要素を区切るためのカンマを追加する
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        
        return sb.toString();
    }
    
    // 特定のマスタ情報（職種・雇用形態・給与区分）のリストを取得するメソッド
    public List<Job> getInfo(Long companyAccountId) throws Exception {
        List<Job> list = new ArrayList<>();
        
        
        // 必要なカラムだけを選択して重複を除外（必要に応じて DISTINCT を付与）
        String sql = "SELECT DISTINCT " +
                     "j.job_type_id, " +
                     "jt.job_type_name, " +
                     "j.employment_type_id, " +
                     "et.employment_type_name, " +
                     "j.salary_type_id, " +
                     "st.salary_type_name " +
                     "FROM jobs j " +
                     "LEFT JOIN occupations jt ON j.job_type_id = jt.occupations_id " +
                     "LEFT JOIN employments_type et ON j.employment_type_id = et.employment_type_id " +
                     "LEFT JOIN salaries_type st ON j.salary_type_id = st.salary_type_id";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Job job = new Job();
                
                // 取得したい項目のみ Bean にセット
                job.setJob_type_id(rs.getLong("job_type_id"));
                job.setJob_type_name(rs.getString("job_type_name"));
                job.setEmployment_type_id(rs.getInt("employment_type_id"));
                job.setEmployment_type_name(rs.getString("employment_type_name"));
                job.setSalary_type_id(rs.getInt("salary_type_id"));
                job.setSalary_type_name(rs.getString("salary_type_name"));

                list.add(job);
            }
        }
        return list;
    }
    
}