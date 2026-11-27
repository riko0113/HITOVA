package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CompanyTopDAO extends DAO {

    public static class CompanyNoticeItem {
        private long notification_id;
        private String message;
        private Timestamp send_at;

        public long getNotification_id() { return notification_id; }
        public void setNotification_id(long notification_id) { this.notification_id = notification_id; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Timestamp getSend_at() { return send_at; }
        public void setSend_at(Timestamp send_at) { this.send_at = send_at; }
    }

    // 自社求人への総応募者数を取得する
    public int countTotalApplicants(long companyAccountId) throws Exception {
        String sql = "SELECT COUNT(*) "
                   + "FROM applications_history ah "
                   + "JOIN jobs j ON ah.job_id = j.job_id "
                   + "WHERE j.company_account_id = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, companyAccountId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // 公開中（掲載中）の求人数を取得する
    public int countPublishedJobs(long companyAccountId) throws Exception {
        String sql = "SELECT COUNT(*) FROM jobs "
                   + "WHERE company_account_id = ? AND publication_status = TRUE";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, companyAccountId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // 登録済みの全求人数を取得する
    public int countAllJobs(long companyAccountId) throws Exception {
        String sql = "SELECT COUNT(*) FROM jobs WHERE company_account_id = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, companyAccountId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // 企業向けの最新通知（最大5件）をDBから取得する
    public List<CompanyNoticeItem> findRecentCompanyNotices(long companyAccountId) throws Exception {
        List<CompanyNoticeItem> list = new ArrayList<>();
        String sql = "SELECT n.notification_id, n.message, n.send_at "
                   + "FROM notifications n "
                   + "JOIN receiver_type rt ON n.receiver_type_id = rt.receiver_type_id "
                   + "WHERE (rt.receiver_name IN ('企業', '全体') AND n.receiver_account_id IS NULL) "
                   + "   OR (rt.receiver_name = '企業' AND n.receiver_account_id = ?) "
                   + "ORDER BY n.send_at DESC, n.notification_id DESC "
                   + "LIMIT 5";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, companyAccountId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    CompanyNoticeItem item = new CompanyNoticeItem();
                    item.setNotification_id(rs.getLong("notification_id"));
                    item.setMessage(rs.getString("message"));
                    item.setSend_at(rs.getTimestamp("send_at"));
                    list.add(item);
                }
            }
        }
        return list;
    }
}