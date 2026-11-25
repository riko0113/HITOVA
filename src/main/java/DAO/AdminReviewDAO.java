package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Review;

public class AdminReviewDAO extends DAO {

    public static class AdminReviewItem extends Review {
        private String poster_name;
        private String target_name;
        private String job_title;

        public String getPoster_name() { return poster_name; }
        public void setPoster_name(String poster_name) { this.poster_name = poster_name; }
        public String getTarget_name() { return target_name; }
        public void setTarget_name(String target_name) { this.target_name = target_name; }
        public String getJob_title() { return job_title; }
        public void setJob_title(String job_title) { this.job_title = job_title; }
    }

    // 未審査(review_status = FALSE) かつ 却下状態(review_result = FALSE) の口コミ一覧を取得する
    public List<AdminReviewItem> findAll() throws Exception {
        List<AdminReviewItem> list = new ArrayList<>();
        String sql = "SELECT r.job_review_id, r.gender, r.age, r.content, r.job_id, "
                   + "r.candidate_account_id, r.evaluation, r.posted_at, r.review_status, r.review_result, "
                   + "COALESCE(j_acc.nick_name, '匿名ユーザー') AS poster_name, "
                   + "COALESCE(ci.official_company_name, spi.trade_name, c.person_name) AS target_name, "
                   + "j.title AS job_title "
                   + "FROM reviews r "
                   + "LEFT JOIN job_seeker_accounts j_acc ON r.candidate_account_id = j_acc.candidate_id "
                   + "LEFT JOIN jobs j ON r.job_id = j.job_id "
                   + "LEFT JOIN company_accounts c ON j.company_account_id = c.company_account_id "
                   + "LEFT JOIN corporation_information ci ON c.company_account_id = ci.company_account_id "
                   + "LEFT JOIN sole_proprietor_information spi ON c.company_account_id = spi.company_account_id "
                   + "WHERE r.review_status = FALSE AND r.review_result = FALSE "
                   + "ORDER BY r.posted_at ASC, r.job_review_id ASC";

        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // 口コミIDで1件取得する
    public AdminReviewItem findById(long reviewId) throws Exception {
        String sql = "SELECT r.job_review_id, r.gender, r.age, r.content, r.job_id, "
                   + "r.candidate_account_id, r.evaluation, r.posted_at, r.review_status, r.review_result, "
                   + "COALESCE(j_acc.nick_name, '匿名ユーザー') AS poster_name, "
                   + "COALESCE(ci.official_company_name, spi.trade_name, c.person_name) AS target_name, "
                   + "j.title AS job_title "
                   + "FROM reviews r "
                   + "LEFT JOIN job_seeker_accounts j_acc ON r.candidate_account_id = j_acc.candidate_id "
                   + "LEFT JOIN jobs j ON r.job_id = j.job_id "
                   + "LEFT JOIN company_accounts c ON j.company_account_id = c.company_account_id "
                   + "LEFT JOIN corporation_information ci ON c.company_account_id = ci.company_account_id "
                   + "LEFT JOIN sole_proprietor_information spi ON c.company_account_id = spi.company_account_id "
                   + "WHERE r.job_review_id = ?";

        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, reviewId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // 承認：review_status = TRUE（審査済み）, review_result = TRUE（承認）
    public void approveById(long reviewId) throws Exception {
        String sql = "UPDATE reviews SET review_status = TRUE, review_result = TRUE WHERE job_review_id = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, reviewId);
            st.executeUpdate();
        }
    }

    // 拒否：review_status = TRUE（審査済み）, review_result = FALSE（却下）
    public void rejectById(long reviewId) throws Exception {
        String sql = "UPDATE reviews SET review_status = TRUE, review_result = FALSE WHERE job_review_id = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, reviewId);
            st.executeUpdate();
        }
    }

    private AdminReviewItem mapRow(ResultSet rs) throws Exception {
        AdminReviewItem item = new AdminReviewItem();
        item.setJob_review_id(rs.getLong("job_review_id"));
        item.setgender(rs.getBoolean("gender"));
        item.setAge(rs.getInt("age"));
        item.setContent(rs.getString("content"));
        item.setJob_id(rs.getLong("job_id"));
        item.setCandidate_account_id(rs.getLong("candidate_account_id"));
        item.setEvalutation(rs.getString("evaluation"));
        Date d = rs.getDate("posted_at");
        if (d != null) {
            item.setPosted_at(d.toLocalDate());
        }
        item.setReview_status(rs.getBoolean("review_status"));
        item.setReview_result(rs.getBoolean("review_result"));
        item.setPoster_name(rs.getString("poster_name"));
        item.setTarget_name(rs.getString("target_name"));
        item.setJob_title(rs.getString("job_title"));
        return item;
    }
}