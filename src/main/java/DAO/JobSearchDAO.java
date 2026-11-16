package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Job;

public class JobSearchDAO extends DAO {

    // 1. 求人検索（複数条件）
    public List<Job> searchJobs(String keyword, Long occupationId, Integer salaryTypeId, Integer employmentTypeId, String location) throws Exception {
        List<Job> list = new ArrayList<>();
        Connection con = getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT j.*, o.occupation_name, et.employment_type_name, st.salary_type_name, ");
        // 法人と個人事業主のテーブルから名前を引っ張る
        sql.append("COALESCE(ci.official_company_name, spi.trade_name) AS company_name ");
        sql.append("FROM jobs j ");
        sql.append("LEFT JOIN occupations o ON j.job_type_id = o.occupation_id ");
        sql.append("LEFT JOIN employments_type et ON j.employment_type_id = et.employment_type_id ");
        sql.append("LEFT JOIN salaries_type st ON j.salary_type_id = st.salary_type_id ");
        sql.append("LEFT JOIN corporation_information ci ON j.company_account_id = ci.company_account_id ");
        sql.append("LEFT JOIN sole_proprietor_information spi ON j.company_account_id = spi.company_account_id ");
        sql.append("WHERE j.publication_status = true "); // 公開中の求人のみ

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (j.title LIKE ? OR j.description LIKE ? OR COALESCE(ci.official_company_name, spi.trade_name) LIKE ?) ");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
            params.add(likeKeyword);
        }
        if (occupationId != null && occupationId > 0) {
            sql.append("AND j.job_type_id = ? ");
            params.add(occupationId);
        }
        if (salaryTypeId != null && salaryTypeId > 0) {
            sql.append("AND j.salary_type_id = ? ");
            params.add(salaryTypeId);
        }
        if (employmentTypeId != null && employmentTypeId > 0) {
            sql.append("AND j.employment_type_id = ? ");
            params.add(employmentTypeId);
        }
        if (location != null && !location.trim().isEmpty()) {
            sql.append("AND j.location LIKE ? ");
            params.add("%" + location.trim() + "%");
        }

        sql.append("ORDER BY j.updated_at DESC");

        PreparedStatement st = con.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) {
            st.setObject(i + 1, params.get(i));
        }

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Job job = mapResultSetToJob(rs);
            // 企業名を一時的に title の後ろなどに付与してJSPへ渡すか、
            // 既存の Job.java には company_name が無いので、Job.java に追加することをおすすめします。
            // (今回は JSP 側で使いやすいよう、ここでは mapResultSetToJob に処理を逃がしています)
            list.add(job);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    // 2. 求人詳細取得（ID指定）
    public Job getJobById(Long jobId) throws Exception {
        Job job = null;
        Connection con = getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT j.*, o.occupation_name, et.employment_type_name, st.salary_type_name, ");
        sql.append("COALESCE(ci.official_company_name, spi.trade_name) AS company_name ");
        sql.append("FROM jobs j ");
        sql.append("LEFT JOIN occupations o ON j.job_type_id = o.occupation_id ");
        sql.append("LEFT JOIN employments_type et ON j.employment_type_id = et.employment_type_id ");
        sql.append("LEFT JOIN salaries_type st ON j.salary_type_id = st.salary_type_id ");
        sql.append("LEFT JOIN corporation_information ci ON j.company_account_id = ci.company_account_id ");
        sql.append("LEFT JOIN sole_proprietor_information spi ON j.company_account_id = spi.company_account_id ");
        sql.append("WHERE j.job_id = ? AND j.publication_status = true");

        PreparedStatement st = con.prepareStatement(sql.toString());
        st.setLong(1, jobId);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            job = mapResultSetToJob(rs);
        }

        rs.close();
        st.close();
        con.close();
        return job;
    }
 // 3. テスト用：ログイン実装前にDB内の最初のcandidate_idを取得する補助メソッド
    public Long getDefaultCandidateId() throws Exception {
        Long id = 1L;
        Connection con = getConnection();
        String sql = "SELECT candidate_id FROM job_seeker_accounts ORDER BY candidate_id ASC LIMIT 1";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            id = rs.getLong("candidate_id");
        }
        rs.close();
        st.close();
        con.close();
        return id;
    }

    // 4. 求人が保存済みかチェック
    public boolean isFavoriteJob(Long candidateId, Long jobId) throws Exception {
        boolean exists = false;
        Connection con = getConnection();
        String sql = "SELECT 1 FROM favorite_jobs WHERE candidate_account_id = ? AND job_id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, candidateId);
        st.setLong(2, jobId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            exists = true;
        }
        rs.close();
        st.close();
        con.close();
        return exists;
    }

    // 5. 求人保存の切り替え（未保存ならINSERT、保存済みならDELETE）
    public void toggleFavoriteJob(Long candidateId, Long jobId) throws Exception {
        if (isFavoriteJob(candidateId, jobId)) {
            Connection con = getConnection();
            String sql = "DELETE FROM favorite_jobs WHERE candidate_account_id = ? AND job_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setLong(1, candidateId);
            st.setLong(2, jobId);
            st.executeUpdate();
            st.close();
            con.close();
        } else {
            Connection con = getConnection();
            String sql = "INSERT INTO favorite_jobs (candidate_account_id, job_id) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setLong(1, candidateId);
            st.setLong(2, jobId);
            st.executeUpdate();
            st.close();
            con.close();
        }
    }

    // 6. 保存求人一覧の取得
    public List<Job> getFavoriteJobs(Long candidateId) throws Exception {
        List<Job> list = new ArrayList<>();
        Connection con = getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT j.*, o.occupation_name, et.employment_type_name, st.salary_type_name, ");
        sql.append("COALESCE(ci.official_company_name, spi.trade_name) AS company_name ");
        sql.append("FROM favorite_jobs fj ");
        sql.append("INNER JOIN jobs j ON fj.job_id = j.job_id ");
        sql.append("LEFT JOIN occupations o ON j.job_type_id = o.occupation_id ");
        sql.append("LEFT JOIN employments_type et ON j.employment_type_id = et.employment_type_id ");
        sql.append("LEFT JOIN salaries_type st ON j.salary_type_id = st.salary_type_id ");
        sql.append("LEFT JOIN corporation_information ci ON j.company_account_id = ci.company_account_id ");
        sql.append("LEFT JOIN sole_proprietor_information spi ON j.company_account_id = spi.company_account_id ");
        sql.append("WHERE fj.candidate_account_id = ? AND j.publication_status = true ");
        sql.append("ORDER BY fj.favorite_job_id DESC");

        PreparedStatement st = con.prepareStatement(sql.toString());
        st.setLong(1, candidateId);

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Job job = mapResultSetToJob(rs);
            job.setCompany_name(rs.getString("company_name"));
            list.add(job);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    // ResultSetからJobオブジェクトへのマッピング処理
    private Job mapResultSetToJob(ResultSet rs) throws Exception {
        Job job = new Job();
        job.setJob_id(rs.getLong("job_id"));
        job.setJob_type_id(rs.getLong("job_type_id"));
        job.setJob_type_name(rs.getString("occupation_name"));
        job.setCompany_account_id(rs.getLong("company_account_id"));
        // 既存の Job.java に company_name フィールドが無い場合、追加してください。
        // もし追加しない場合は、この値は取得できても Job にセットできません。
        
        job.setEmployment_type_id(rs.getInt("employment_type_id"));
        job.setEmployment_type_name(rs.getString("employment_type_name"));
        job.setSalary_type_id(rs.getInt("salary_type_id"));
        job.setSalary_type_name(rs.getString("salary_type_name"));
        job.setTitle(rs.getString("title"));
        job.setDescription(rs.getString("description"));
        job.setRequirement(rs.getString("requirement"));
        job.setHeadcount(rs.getString("headcount"));
        job.setLocation(rs.getString("location"));
        job.setaccess(rs.getString("access")); // 既存の命名に合わせる
        job.setSalary(rs.getString("salary"));
        job.setAllowance(rs.getString("allowance"));
        job.setTransportation_fee(rs.getBoolean("transportation_fee"));
        job.setWorking_hours(rs.getString("working_hours"));
        job.setHoliday(rs.getString("holiday"));
        job.setSmoking_prevention(rs.getString("smoking_prevention"));
        job.setBenefits(rs.getString("benefits"));
        job.setInsurance(rs.getString("insurance"));
        job.setImage_url(rs.getString("image_url"));
        job.setWorkplace_pr(rs.getString("workplace_pr"));
        job.setSelection_process(rs.getString("selection_process"));
        job.setInterview_location(rs.getString("interview_location"));
        job.setPublication_status(rs.getBoolean("publication_status"));
        
        // LocalDateTime のマッピング
        java.sql.Timestamp createdTs = rs.getTimestamp("created_at");
        if (createdTs != null) {
            job.setCreated_at(createdTs.toLocalDateTime());
        }
        java.sql.Timestamp updatedTs = rs.getTimestamp("updated_at");
        if (updatedTs != null) {
            job.setUpdated_at(updatedTs.toLocalDateTime());
        }

        return job;
    }
}