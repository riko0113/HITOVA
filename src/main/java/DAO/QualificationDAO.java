package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Qualification;

public class QualificationDAO extends DAO {

    // 資格登録
    public int insert(Qualification qualification) throws Exception {
        int count = 0;
        String sql = "INSERT INTO qualifications (candidate_account_id, qualification_date, qualification_detail_id) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, qualification.getCandidate_account_id());
            st.setDate(2, Date.valueOf(qualification.getQualification_date()));
            st.setLong(3, qualification.getQualification_detail_id());

            count = st.executeUpdate();
        }
        return count;
    }

    // 求職者IDを指定して資格一覧を取得する処理
    public List<Qualification> findByCandidateId(Long candidateId) throws Exception {
        List<Qualification> list = new ArrayList<>();

        String sql = "SELECT q.qualification_id, q.candidate_account_id, q.qualification_date, q.qualification_detail_id, d.qualification_name " +
                     "FROM qualifications q " +
                     "JOIN qualifications_detail d ON q.qualification_detail_id = d.qualification_detail_id " +
                     "WHERE q.candidate_account_id = ? " +
                     "ORDER BY q.qualification_date DESC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, candidateId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Qualification q = new Qualification();
                    q.setQualification_id(rs.getLong("qualification_id"));
                    q.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    q.setQualification_date(rs.getDate("qualification_date").toLocalDate());
                    q.setQualification_detail_id(rs.getLong("qualification_detail_id"));
                    q.setQualification_name(rs.getString("qualification_name"));
                    list.add(q);
                }
            }
        }
        return list;
    }

    // 資格削除
    public int delete(Long qualificationId, Long candidateId) throws Exception {
        int count = 0;
        String sql = "DELETE FROM qualifications WHERE qualification_id = ? AND candidate_account_id = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, qualificationId);
            st.setLong(2, candidateId);

            count = st.executeUpdate();
        }
        return count;
    }
}