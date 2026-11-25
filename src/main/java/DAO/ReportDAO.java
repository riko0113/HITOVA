package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import bean.Report;

public class ReportDAO extends DAO {

    public static class ReportTarget {
        private long job_id;
        private long company_account_id;
        private String company_name;
        private String job_title;

        public long getJob_id() { return job_id; }
        public void setJob_id(long job_id) { this.job_id = job_id; }
        public long getCompany_account_id() { return company_account_id; }
        public void setCompany_account_id(long company_account_id) { this.company_account_id = company_account_id; }
        public String getCompany_name() { return company_name; }
        public void setCompany_name(String company_name) { this.company_name = company_name; }
        public String getJob_title() { return job_title; }
        public void setJob_title(String job_title) { this.job_title = job_title; }
    }

    // 求人IDから通報対象の企業情報と求人タイトルを取得
    public ReportTarget findTargetByJobId(long jobId) throws Exception {
        String sql = "SELECT j.job_id, j.title, j.company_account_id, "
                   + "COALESCE(ci.official_company_name, spi.trade_name, c.person_name) AS company_name "
                   + "FROM jobs j "
                   + "JOIN company_accounts c ON j.company_account_id = c.company_account_id "
                   + "LEFT JOIN corporation_information ci ON c.company_account_id = ci.company_account_id "
                   + "LEFT JOIN sole_proprietor_information spi ON c.company_account_id = spi.company_account_id "
                   + "WHERE j.job_id = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, jobId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    ReportTarget target = new ReportTarget();
                    target.setJob_id(rs.getLong("job_id"));
                    target.setCompany_account_id(rs.getLong("company_account_id"));
                    target.setCompany_name(rs.getString("company_name"));
                    target.setJob_title(rs.getString("title"));
                    return target;
                }
            }
        }
        return null;
    }

    // Report Bean を受け取って reports テーブルに登録
    public void insert(Report report) throws Exception {
        String sql = "INSERT INTO reports "
                   + "(reporter_id, reported_user_id, content, reported_at, reporter_type, reported_person_type, for_storing_evidence) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, report.getReporter_id());
            st.setLong(2, report.getReported_user_id());
            st.setString(3, report.getContent());
            LocalDateTime dt = (report.getReported_at() != null) ? report.getReported_at() : LocalDateTime.now();
            st.setTimestamp(4, Timestamp.valueOf(dt));
            st.setBoolean(5, Boolean.parseBoolean(report.getReporter_type()));
            st.setBoolean(6, Boolean.parseBoolean(report.getReported_person_type()));
            st.setString(7, report.getFor_storing_evidence() != null ? report.getFor_storing_evidence() : "");
            st.executeUpdate();
        }
    }
}