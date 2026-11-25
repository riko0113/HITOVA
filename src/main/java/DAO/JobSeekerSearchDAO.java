
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.JobSeekerSearch;

public class JobSeekerSearchDAO {

    private Connection getConnection() throws Exception {
        InitialContext context = new InitialContext();
        DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/hitova");
        return ds.getConnection();
    }

    // プルダウン共通処理
    private List<Map<String, Object>> getOptionList(String sql) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> option = new HashMap<>();
                option.put("id", rs.getInt("id"));
                option.put("name", rs.getString("name"));
                list.add(option);
            }
        }
        return list;
    }

    // プルダウン一覧
    public List<Map<String, Object>> getEmploymentTypeList() throws Exception {
        return getOptionList("SELECT employment_type_id AS id, employment_type_name AS name FROM employments_type ORDER BY employment_type_id");
    }

    public List<Map<String, Object>> getJobTypeList() throws Exception {
        return getOptionList("SELECT occupation_id AS id, occupation_name AS name FROM occupations ORDER BY occupation_id");
    }

    public List<Map<String, Object>> getIndustryList() throws Exception {
        return getOptionList("SELECT industry_id AS id, industry_name AS name FROM industries ORDER BY industry_id");
    }

    public List<Map<String, Object>> getQualificationTypeList() throws Exception {
        return getOptionList("SELECT field_id AS id, field_name AS name FROM fields ORDER BY field_id");
    }

    public List<Map<String, Object>> getQualificationList() throws Exception {
        return getOptionList("SELECT qualification_detail_id AS id, qualification_name AS name FROM qualifications_detail ORDER BY qualification_detail_id");
    }

    public List<Map<String, Object>> getAchievementTypeList() throws Exception {
        return getOptionList("SELECT category_id AS id, category_name AS name FROM categories ORDER BY category_id");
    }

    public List<Map<String, Object>> getAchievementList() throws Exception {
        return getOptionList("SELECT task_id AS id, achievement AS name FROM achievement_managements ORDER BY task_id");
    }

    // 検索条件作成
    private StringBuilder buildWhere(JobSeekerSearch condition, List<Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append(" WHERE j.authority_id = 1 AND j.scout = TRUE ");

        if (condition.getEmploymentType() != null) {
            sql.append(" AND p.desired_employment_type_id = ? ");
            params.add(condition.getEmploymentType());
        }

        if (condition.getJobType() != null) {
            sql.append(" AND p.desired_job_type_id = ? ");
            params.add(condition.getJobType());
        }

        if (condition.getIndustry() != null) {
            sql.append(" AND j.desired_industry_id = ? ");
            params.add(condition.getIndustry());
        }

        if (condition.getQualificationType() != null) {
            sql.append(" AND EXISTS (SELECT 1 FROM qualifications q INNER JOIN qualifications_detail qd ON q.qualification_detail_id = qd.qualification_detail_id WHERE q.candidate_account_id = j.candidate_id AND qd.field_id = ?)");
            params.add(condition.getQualificationType());
        }

        if (condition.getQualification() != null) {
            sql.append(" AND EXISTS (SELECT 1 FROM qualifications q WHERE q.candidate_account_id = j.candidate_id AND q.qualification_detail_id = ?)");
            params.add(condition.getQualification());
        }

        if (condition.getCustomQualification() != null && !condition.getCustomQualification().trim().isEmpty()) {
            sql.append(" AND EXISTS (SELECT 1 FROM qualifications q INNER JOIN qualifications_detail qd ON q.qualification_detail_id = qd.qualification_detail_id WHERE q.candidate_account_id = j.candidate_id AND qd.qualification_name LIKE ?)");
            params.add("%" + condition.getCustomQualification().trim() + "%");
        }

        if (condition.getAchievementType() != null) {
            sql.append(" AND EXISTS (SELECT 1 FROM achievements a INNER JOIN achievement_managements am ON a.task_id = am.task_id WHERE a.candidate_account_id = j.candidate_id AND am.category_id = ? AND a.passorfail_id = 1)");
            params.add(condition.getAchievementType());
        }

        if (condition.getAchievement() != null) {
            sql.append(" AND EXISTS (SELECT 1 FROM achievements a WHERE a.candidate_account_id = j.candidate_id AND a.task_id = ? AND a.passorfail_id = 1)");
            params.add(condition.getAchievement());
        }

        return sql;
    }

    // SQLパラメータ設定
    private void setParameters(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
    }

    // 検索結果件数取得
    public int count(JobSeekerSearch condition) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) FROM job_seeker_accounts j INNER JOIN profiles p ON j.candidate_id = p.candidate_account_id ");

        List<Object> params = new ArrayList<>();
        sql.append(buildWhere(condition, params));

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            setParameters(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    // 求職者検索
    public List<JobSeekerSearch> search(JobSeekerSearch condition, int currentPage, int pageSize) throws Exception {
        List<JobSeekerSearch> candidateList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT j.candidate_id, p.last_name, p.first_name, p.birth_day, j.identity_verification FROM job_seeker_accounts j INNER JOIN profiles p ON j.candidate_id = p.candidate_account_id ");

        List<Object> params = new ArrayList<>();
        sql.append(buildWhere(condition, params));
        sql.append(" ORDER BY j.candidate_id LIMIT ? OFFSET ? ");

        params.add(pageSize);
        params.add((currentPage - 1) * pageSize);

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            setParameters(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    JobSeekerSearch candidate = new JobSeekerSearch();
                    candidate.setCandidateId(rs.getLong("candidate_id"));
                    candidate.setName(rs.getString("last_name") + " " + rs.getString("first_name"));

                    java.sql.Date birthDay = rs.getDate("birth_day");
                    if (birthDay != null) candidate.setBirthDate(birthDay.toLocalDate());

                    candidate.setIdentityVerification(rs.getBoolean("identity_verification"));
                    candidateList.add(candidate);
                }
            }
        }
        return candidateList;
    }
}
