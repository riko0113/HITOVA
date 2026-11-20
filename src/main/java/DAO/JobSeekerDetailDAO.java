
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JobSeekerDetailDAO {

    private DataSource getDataSource() throws NamingException {
        return (DataSource) new InitialContext()
                .lookup("java:comp/env/jdbc/hitova");
    }

    public Map<String, Object> findByCandidateId(long candidateId)
            throws SQLException, NamingException {

        Map<String, Object> result = new LinkedHashMap<>();

        String basicSql =
            "SELECT j.candidate_id, j.nick_name, j.email, " +
            "j.identity_verification, j.scout, " +
            "i.industry_name, o.occupation_name AS account_occupation_name " +
            "FROM job_seeker_accounts j " +
            "LEFT JOIN industries i ON j.desired_industry_id = i.industry_id " +
            "LEFT JOIN occupations o ON j.desired_occupation_id = o.occupation_id " +
            "WHERE j.candidate_id = ? AND j.scout = TRUE";

        String profileSql =
            "SELECT p.profile_id, p.last_name, p.first_name, " +
            "p.last_name_kana, p.first_name_kana, p.birth_day, p.gender, " +
            "p.current_postal_code, p.current_address, p.highest_education, " +
            "p.work_history, p.desired_work_location, " +
            "et.employment_type_name, o.occupation_name AS profile_occupation_name " +
            "FROM profiles p " +
            "LEFT JOIN employments_type et " +
            "ON p.desired_employment_type_id = et.employment_type_id " +
            "LEFT JOIN occupations o " +
            "ON p.desired_job_type_id = o.occupation_id " +
            "WHERE p.candidate_account_id = ?";

        String skillSql =
            "SELECT skill_id, skill_name, skill_content " +
            "FROM skills WHERE candidate_id = ? ORDER BY skill_id";

        String qualificationSql =
            "SELECT q.qualification_id, q.qualification_date, " +
            "qd.qualification_name, f.field_name " +
            "FROM qualifications q " +
            "JOIN qualifications_detail qd " +
            "ON q.qualification_detail_id = qd.qualification_detail_id " +
            "JOIN fields f ON qd.field_id = f.field_id " +
            "WHERE q.candidate_account_id = ? " +
            "ORDER BY q.qualification_date DESC";

        String achievementSql =
            "SELECT a.achievement_id, am.achievement, " +
            "am.achievement_content, f.field_name, c.category_name, " +
            "pf.passorfail_name, a.evaluation, a.achievement_date " +
            "FROM achievements a " +
            "JOIN achievement_managements am ON a.task_id = am.task_id " +
            "JOIN fields f ON am.field_id = f.field_id " +
            "JOIN categories c ON am.category_id = c.category_id " +
            "JOIN passorfails pf ON a.passorfail_id = pf.passorfail_id " +
            "WHERE a.candidate_account_id = ? " +
            "ORDER BY a.achievement_date DESC";

        String portfolioSql =
            "SELECT portfolio_id, work_name, description, file_url " +
            "FROM portfolios WHERE candidate_account_id = ? " +
            "ORDER BY portfolio_id DESC";

        try (Connection con = getDataSource().getConnection()) {

            Map<String, Object> basicInfo =
                    findOne(con, basicSql, candidateId);

            if (basicInfo == null) {
                return null;
            }

            Map<String, Object> profileInfo =
                    findOne(con, profileSql, candidateId);

            if (profileInfo == null) {
                return null;
            }

            Map<String, Object> searchInfo = new LinkedHashMap<>();
            searchInfo.put("industry_name", basicInfo.get("industry_name"));
            searchInfo.put("occupation_name",
                    profileInfo.get("profile_occupation_name"));
            searchInfo.put("account_occupation_name",
                    basicInfo.get("account_occupation_name"));
            searchInfo.put("employment_type_name",
                    profileInfo.get("employment_type_name"));
            searchInfo.put("desired_work_location",
                    profileInfo.get("desired_work_location"));

            result.put("basicInfo", basicInfo);
            result.put("profileInfo", profileInfo);
            result.put("searchInfo", searchInfo);

            result.put("skillList",
                    findList(con, skillSql, candidateId));

            result.put("qualificationList",
                    findList(con, qualificationSql, candidateId));

            result.put("achievementList",
                    findList(con, achievementSql, candidateId));

            result.put("portfolioList",
                    findList(con, portfolioSql, candidateId));
        }

        return result;
    }

    private Map<String, Object> findOne(
            Connection con, String sql, long candidateId)
            throws SQLException {

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, candidateId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return toMap(rs);
                }
            }
        }

        return null;
    }

    private List<Map<String, Object>> findList(
            Connection con, String sql, long candidateId)
            throws SQLException {

        List<Map<String, Object>> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, candidateId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(toMap(rs));
                }
            }
        }

        return list;
    }

    private Map<String, Object> toMap(ResultSet rs)
            throws SQLException {

        Map<String, Object> map = new LinkedHashMap<>();
        int count = rs.getMetaData().getColumnCount();

        for (int i = 1; i <= count; i++) {
            map.put(
                rs.getMetaData().getColumnLabel(i),
                rs.getObject(i)
            );
        }

        return map;
    }
}
