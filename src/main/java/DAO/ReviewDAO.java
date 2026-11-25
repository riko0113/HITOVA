package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import bean.Review;

public class ReviewDAO extends DAO {

    public static class ReviewItem extends Review {
        private String nick_name;
        private String company_name;

        public String getNick_name() { return nick_name; }
        public void setNick_name(String nick_name) { this.nick_name = nick_name; }
        public String getCompany_name() { return company_name; }
        public void setCompany_name(String company_name) { this.company_name = company_name; }
    }

    // 求人IDから企業名を取得する
    public String findCompanyNameByJobId(long jobId) throws Exception {
        String sql = "SELECT COALESCE(ci.official_company_name, spi.trade_name, c.person_name) AS company_name "
                   + "FROM jobs j "
                   + "JOIN company_accounts c ON j.company_account_id = c.company_account_id "
                   + "LEFT JOIN corporation_information ci ON c.company_account_id = ci.company_account_id "
                   + "LEFT JOIN sole_proprietor_information spi ON c.company_account_id = spi.company_account_id "
                   + "WHERE j.job_id = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, jobId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("company_name");
                }
            }
        }
        return "";
    }

    // 審査済み(true) かつ 承認(true) の口コミのみ取得する
    public List<ReviewItem> findByJobId(long jobId) throws Exception {
        List<ReviewItem> list = new ArrayList<>();
        String sql = "SELECT r.job_review_id, r.gender, r.age, r.content, r.job_id, "
                   + "r.candidate_account_id, r.evaluation, r.posted_at, j_acc.nick_name "
                   + "FROM reviews r "
                   + "LEFT JOIN job_seeker_accounts j_acc ON r.candidate_account_id = j_acc.candidate_id "
                   + "WHERE r.job_id = ? AND r.review_status = TRUE AND r.review_result = TRUE "
                   + "ORDER BY r.posted_at DESC, r.job_review_id DESC";

        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, jobId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ReviewItem item = new ReviewItem();
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
                    item.setNick_name(rs.getString("nick_name"));
                    list.add(item);
                }
            }
        }
        return list;
    }

    // プロフィールが登録済みか確認する
    public boolean hasProfile(long candidateId) throws Exception {
        String sql = "SELECT 1 FROM profiles WHERE candidate_account_id = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, candidateId);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        }
    }

    // profilesテーブルの性別と生年月日(birth_day)から年齢を自動計算してセットする（未登録時はfalse）
    public boolean fillDemographicsFromProfile(Review review, long candidateId) throws Exception {
        String sql = "SELECT gender, birth_day FROM profiles WHERE candidate_account_id = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, candidateId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    review.setgender(rs.getBoolean("gender"));
                    Date bd = rs.getDate("birth_day");
                    if (bd != null) {
                        int age = Period.between(bd.toLocalDate(), LocalDate.now()).getYears();
                        review.setAge(age);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // 口コミを新規登録する（初期状態：review_status = FALSE, review_result = FALSE）
    public void insert(Review review) throws Exception {
        String sql = "INSERT INTO reviews "
                   + "(gender, age, content, job_id, candidate_account_id, evaluation, posted_at, review_status, review_result) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, FALSE, FALSE)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setBoolean(1, review.getgender());
            st.setInt(2, review.getAge());
            st.setString(3, review.getContent());
            st.setLong(4, review.getJob_id());
            st.setLong(5, review.getCandidate_account_id());
            st.setString(6, review.getEvalutation());
            st.setDate(7, Date.valueOf(review.getPosted_at() != null ? review.getPosted_at() : LocalDate.now()));
            st.executeUpdate();
        }
    }

    // 自分の投稿を削除する
    public void delete(long reviewId, long candidateId) throws Exception {
        String sql = "DELETE FROM reviews WHERE job_review_id = ? AND candidate_account_id = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, reviewId);
            st.setLong(2, candidateId);
            st.executeUpdate();
        }
    }
}