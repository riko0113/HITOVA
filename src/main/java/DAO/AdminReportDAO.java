package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AdminReportDAO extends DAO {

    public static class AdminReportItem {
        private long report_id;
        private long reporter_id;
        private long reported_user_id;
        private String content;
        private Timestamp reported_at;
        private boolean reporter_type;
        private boolean reported_person_type;
        private String for_storing_evidence;
        private String reporter_name;
        private String reported_target_name;

        public long getReport_id() { return report_id; }
        public void setReport_id(long report_id) { this.report_id = report_id; }

        public long getReporter_id() { return reporter_id; }
        public void setReporter_id(long reporter_id) { this.reporter_id = reporter_id; }

        public long getReported_user_id() { return reported_user_id; }
        public void setReported_user_id(long reported_user_id) { this.reported_user_id = reported_user_id; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public Timestamp getReported_at() { return reported_at; }
        public void setReported_at(Timestamp reported_at) { this.reported_at = reported_at; }

        public boolean isReporter_type() { return reporter_type; }
        public void setReporter_type(boolean reporter_type) { this.reporter_type = reporter_type; }

        public boolean isReported_person_type() { return reported_person_type; }
        public void setReported_person_type(boolean reported_person_type) { this.reported_person_type = reported_person_type; }

        public String getFor_storing_evidence() { return for_storing_evidence; }
        public void setFor_storing_evidence(String for_storing_evidence) { this.for_storing_evidence = for_storing_evidence; }

        public String getReporter_name() { return reporter_name; }
        public void setReporter_name(String reporter_name) { this.reporter_name = reporter_name; }

        public String getReported_target_name() { return reported_target_name; }
        public void setReported_target_name(String reported_target_name) { this.reported_target_name = reported_target_name; }
    }

    private static final String BASE_SELECT =
            "SELECT rep.report_id, rep.reporter_id, rep.reported_user_id, rep.content, "
          + "rep.reported_at, rep.reporter_type, rep.reported_person_type, rep.for_storing_evidence, "
          + "COALESCE(js_rep.nick_name, ci_rep.official_company_name, spi_rep.trade_name, ca_rep.person_name, '不明') AS reporter_name, "
          + "COALESCE(ci_tgt.official_company_name, spi_tgt.trade_name, ca_tgt.person_name, js_tgt.nick_name, '不明') AS reported_target_name "
          + "FROM reports rep "
          + "LEFT JOIN job_seeker_accounts js_rep ON rep.reporter_id = js_rep.candidate_id "
          + "LEFT JOIN company_accounts ca_rep ON rep.reporter_id = ca_rep.company_account_id "
          + "LEFT JOIN corporation_information ci_rep ON ca_rep.company_account_id = ci_rep.company_account_id "
          + "LEFT JOIN sole_proprietor_information spi_rep ON ca_rep.company_account_id = spi_rep.company_account_id "
          + "LEFT JOIN company_accounts ca_tgt ON rep.reported_user_id = ca_tgt.company_account_id "
          + "LEFT JOIN corporation_information ci_tgt ON ca_tgt.company_account_id = ci_tgt.company_account_id "
          + "LEFT JOIN sole_proprietor_information spi_tgt ON ca_tgt.company_account_id = spi_tgt.company_account_id "
          + "LEFT JOIN job_seeker_accounts js_tgt ON rep.reported_user_id = js_tgt.candidate_id ";

    // 通報一覧を新しい順に全件取得する
    public List<AdminReportItem> findAll() throws Exception {
        List<AdminReportItem> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY rep.reported_at DESC, rep.report_id DESC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // 通報IDで1件詳細を取得する
    public AdminReportItem findById(long reportId) throws Exception {
        String sql = BASE_SELECT + "WHERE rep.report_id = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, reportId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // 通報を削除する（対応完了）
    public void deleteById(long reportId) throws Exception {
        String sql = "DELETE FROM reports WHERE report_id = ?";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, reportId);
            st.executeUpdate();
        }
    }

    private AdminReportItem mapRow(ResultSet rs) throws Exception {
        AdminReportItem item = new AdminReportItem();
        item.setReport_id(rs.getLong("report_id"));
        item.setReporter_id(rs.getLong("reporter_id"));
        item.setReported_user_id(rs.getLong("reported_user_id"));
        item.setContent(rs.getString("content"));
        item.setReported_at(rs.getTimestamp("reported_at"));
        item.setReporter_type(rs.getBoolean("reporter_type"));
        item.setReported_person_type(rs.getBoolean("reported_person_type"));
        item.setFor_storing_evidence(rs.getString("for_storing_evidence"));
        item.setReporter_name(rs.getString("reporter_name"));
        item.setReported_target_name(rs.getString("reported_target_name"));
        return item;
    }
}