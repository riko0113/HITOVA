package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Skill;

public class SkillDAO {
    private final String URL = "jdbc:postgresql://localhost:5432/hitova";
    private final String USER = "postgres";
    private final String PASS = "post";

    // 登録処理
    public int insert(Skill skill) throws Exception {
        int count = 0;
        String sql = "INSERT INTO skills (candidate_id, skill_name, skill_content) VALUES (?, ?, ?)";

        Class.forName("org.postgresql.Driver");

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, skill.getCandidate_account_id()); 
            st.setString(2, skill.getSkill_name());
            st.setString(3, skill.getSkill_content());

            count = st.executeUpdate();
        }
        return count;
    }

    // 一覧取得処理
    public List<Skill> findByCandidateId(Long candidateId) throws Exception {
        List<Skill> list = new ArrayList<>();
        
        String sql = "SELECT skill_id, candidate_id, skill_name, skill_content " +
                     "FROM skills " +
                     "WHERE candidate_id = ? " +
                     "ORDER BY skill_id DESC";

        Class.forName("org.postgresql.Driver");

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement st = con.prepareStatement(sql)) {
            
            st.setLong(1, candidateId);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Skill s = new Skill();
                    s.setSkill_id(rs.getLong("skill_id"));
                    s.setCandidate_account_id(rs.getLong("candidate_id"));
                    s.setSkill_name(rs.getString("skill_name"));
                    s.setSkill_content(rs.getString("skill_content"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    // 削除処理
    public int delete(Long skillId) throws Exception {
        int count = 0;
        String sql = "DELETE FROM skills WHERE skill_id = ?";

        Class.forName("org.postgresql.Driver");

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, skillId);
            count = st.executeUpdate();
        }
        return count;
    }
}