package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Achievement;

public class AchievementDAO extends DAO {

    public int insert(Achievement achievement) throws Exception {
        String sql = "INSERT INTO achievements ("
                   + "candidate_account_id, task_id, passorfail_id, evaluation, achievement_date"
                   + ") VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, achievement.getCandidate_account_id());
            st.setLong(2, achievement.getTask_id());
            // 初期合否ステータス（指定がない場合は 3: 審査中）
            st.setLong(3, achievement.getPassorfail_id() != null ? achievement.getPassorfail_id() : 3L);
            st.setString(4, achievement.getEvaluation() != null ? achievement.getEvaluation() : "一次受付完了（自動審査対象）");
            
            LocalDate date = achievement.getAchievement_date() != null ? achievement.getAchievement_date() : LocalDate.now();
            st.setDate(5, Date.valueOf(date));

            return st.executeUpdate();
        }
    }

    /**
     * 指定した求職者アカウントの実績提出一覧を取得する
     */
    public List<Achievement> findByCandidateId(Long candidateAccountId) throws Exception {
        List<Achievement> list = new ArrayList<>();
        String sql = "SELECT a.achievement_id, a.candidate_account_id, a.task_id, "
                   + "a.passorfail_id, p.passorfail_name, a.evaluation, a.achievement_date "
                   + "FROM achievements a "
                   + "LEFT JOIN passorfails p ON a.passorfail_id = p.passorfail_id "
                   + "WHERE a.candidate_account_id = ? "
                   + "ORDER BY a.achievement_date DESC, a.achievement_id DESC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, candidateAccountId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Achievement item = new Achievement();
                    item.setAchievement_id(rs.getLong("achievement_id"));
                    item.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    item.setTask_id(rs.getLong("task_id"));
                    item.setPassorfail_id(rs.getLong("passorfail_id"));
                    item.setPassorfail_name(rs.getString("passorfail_name"));
                    item.setEvaluation(rs.getString("evaluation"));
                    
                    Date d = rs.getDate("achievement_date");
                    if (d != null) {
                        item.setAchievement_date(d.toLocalDate());
                    }
                    list.add(item);
                }
            }
        }
        return list;
    }
}