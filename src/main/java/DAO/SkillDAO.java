package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Skill;

public class SkillDAO extends DAO {

    // スキル登録処理
    public int insert(Skill skill) throws Exception {
        int count = 0;
        // 自由記述のテキストを保存する
        String sql = "INSERT INTO skills (candidate_id, skill_name, skill_content) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, skill.getCandidate_account_id());
            st.setString(2, skill.getSkill_name());
            st.setString(3, skill.getSkill_content());

            count = st.executeUpdate();
        }
        return count;
    }

    // 求職者IDを指定してスキル一覧を取得する処理
    public List<Skill> findByCandidateId(Long candidateId) throws Exception {
        List<Skill> list = new ArrayList<>();

        // 外部テーブルとのJOIN不要。直接skillsテーブルから取得
        String sql = "SELECT skill_id, candidate_id, skill_name, skill_content " +
                     "FROM skills " +
                     "WHERE candidate_id = ? " +
                     "ORDER BY skill_id DESC";

        try (Connection con = getConnection();
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

    // スキル削除処理
    public int delete(Long skillId, Long candidateId) throws Exception {
        int count = 0;
        String sql = "DELETE FROM skills WHERE skill_id = ? AND candidate_id = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, skillId);
            st.setLong(2, candidateId);

            count = st.executeUpdate();
        }
        return count;
    }
}