package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bean.Job;

public class JobApplyDAO extends DAO {

    // 確認画面用のプロフィール表示・JSON保持クラス
    public static class CandidateProfileData {
        private String fullName;
        private String profileSummary;
        private String profileJson;

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getProfileSummary() { return profileSummary; }
        public void setProfileSummary(String profileSummary) { this.profileSummary = profileSummary; }
        public String getProfileJson() { return profileJson; }
        public void setProfileJson(String profileJson) { this.profileJson = profileJson; }
    }

    // 応募履歴一覧の1行データ
    public static class AppliedJobRow {
        private Long job_id;
        private String company_name;
        private String status_label;
        private String updated_date;
        private String deadline_date;
        private int entry_count;

        public Long getJob_id() { return job_id; }
        public void setJob_id(Long job_id) { this.job_id = job_id; }
        public String getCompany_name() { return company_name; }
        public void setCompany_name(String company_name) { this.company_name = company_name; }
        public String getStatus_label() { return status_label; }
        public void setStatus_label(String status_label) { this.status_label = status_label; }
        public String getUpdated_date() { return updated_date; }
        public void setUpdated_date(String updated_date) { this.updated_date = updated_date; }
        public String getDeadline_date() { return deadline_date; }
        public void setDeadline_date(String deadline_date) { this.deadline_date = deadline_date; }
        public int getEntry_count() { return entry_count; }
        public void setEntry_count(int entry_count) { this.entry_count = entry_count; }
    }

    // 未ログイン時のフォールバック用：先頭の求職者IDを取得
    public Long getDefaultCandidateId() throws Exception {
        Long id = 1001L;
        Connection con = getConnection();
        String sql = "SELECT candidate_id FROM job_seeker_accounts WHERE authority_id = 1 ORDER BY candidate_id ASC LIMIT 1";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            id = rs.getLong("candidate_id");
        }
        rs.close();
        st.close();
        con.close();
        return id;
    }

    // 応募対象の求人＋企業名を取得
    public Job getJobForApply(Long jobId) throws Exception {
        Job job = null;
        Connection con = getConnection();
        String sql = "SELECT j.job_id, j.title, j.company_account_id, "
                   + "COALESCE(ci.official_company_name, spi.trade_name) AS company_name "
                   + "FROM jobs j "
                   + "LEFT JOIN corporation_information ci ON j.company_account_id = ci.company_account_id "
                   + "LEFT JOIN sole_proprietor_information spi ON j.company_account_id = spi.company_account_id "
                   + "WHERE j.job_id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, jobId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            job = new Job();
            job.setJob_id(rs.getLong("job_id"));
            job.setTitle(rs.getString("title"));
            job.setCompany_account_id(rs.getLong("company_account_id"));
            job.setCompany_name(rs.getString("company_name"));
        }
        rs.close();
        st.close();
        con.close();
        return job;
    }

    // 求職者のプロフィール情報と送信用JSONを取得
    public CandidateProfileData getCandidateProfile(Long candidateId) throws Exception {
        CandidateProfileData data = new CandidateProfileData();
        Connection con = getConnection();

        String sql = "SELECT jsa.nick_name, p.profile_id, p.last_name, p.first_name, p.birth_day, "
                   + "p.current_address, p.desired_work_location, p.desired_job_type_id, "
                   + "p.highest_education, p.work_history, "
                   + "o.occupation_name, et.employment_type_name "
                   + "FROM job_seeker_accounts jsa "
                   + "LEFT JOIN profiles p ON jsa.candidate_id = p.candidate_account_id "
                   + "LEFT JOIN occupations o ON p.desired_job_type_id = o.occupation_id "
                   + "LEFT JOIN employments_type et ON p.desired_employment_type_id = et.employment_type_id "
                   + "WHERE jsa.candidate_id = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, candidateId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            String lastName = rs.getString("last_name");
            String firstName = rs.getString("first_name");
            String nickName = rs.getString("nick_name");

            if (lastName != null && firstName != null) {
                data.setFullName(lastName + " " + firstName);
                String occ = rs.getString("occupation_name") != null ? rs.getString("occupation_name") : "未設定";
                String emp = rs.getString("employment_type_name") != null ? rs.getString("employment_type_name") : "未設定";
                String loc = rs.getString("desired_work_location") != null ? rs.getString("desired_work_location") : "未設定";
                String edu = rs.getString("highest_education") != null ? rs.getString("highest_education") : "未設定";
                String work = rs.getString("work_history") != null ? rs.getString("work_history") : "なし";
                String birth = rs.getDate("birth_day") != null ? rs.getDate("birth_day").toString() : "";

                StringBuilder summary = new StringBuilder();
                summary.append("希望職種：").append(occ).append(" ／ 希望雇用形態：").append(emp).append("\n");
                summary.append("希望勤務地：").append(loc).append(" ／ 最終学歴：").append(edu).append("\n");
                summary.append("経歴・職歴：").append(work);
                data.setProfileSummary(summary.toString());

                long profileId = rs.getLong("profile_id");
                int desiredJobId = rs.getInt("desired_job_type_id");
                String fullNamePlain = lastName + " " + firstName;

                String json = String.format(
                    "{\"profile_id\": %d, \"name\": \"%s\", \"birth_day\": \"%s\", \"desired_job_type_id\": %d, \"highest_education\": \"%s\", \"work_history\": \"%s\", \"desired_work_location\": \"%s\"}",
                    profileId,
                    escapeJson(fullNamePlain),
                    escapeJson(birth),
                    desiredJobId,
                    escapeJson(edu),
                    escapeJson(work),
                    escapeJson(loc)
                );
                data.setProfileJson(json);
            } else {
                data.setFullName(nickName != null ? nickName : "求職者");
                data.setProfileSummary("プロフィール情報が未登録です。プロフィール画面から登録内容を確認できます。");
                data.setProfileJson("{\"name\": \"" + escapeJson(data.getFullName()) + "\"}");
            }
        }

        rs.close();
        st.close();
        con.close();
        return data;
    }

    // すでにこの求人へ応募済みかチェック
    public boolean hasAlreadyApplied(Long candidateId, Long jobId) throws Exception {
        boolean exists = false;
        Connection con = getConnection();
        String sql = "SELECT 1 FROM applications_history WHERE candidate_account_id = ? AND job_id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, candidateId);
        st.setLong(2, jobId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            exists = true;
        }
        rs.close();
        st.close();
        con.close();
        return exists;
    }

    // 応募登録（applications_history への INSERT と通知作成）
    public void registerApplication(Long candidateId, Long jobId, Long companyAccountId, String profileJson) throws Exception {
        if (hasAlreadyApplied(candidateId, jobId)) {
            return;
        }

        Connection con = getConnection();

        String sql = "INSERT INTO applications_history (candidate_account_id, job_id, profile) VALUES (?, ?, ?::jsonb)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, candidateId);
        st.setLong(2, jobId);
        st.setString(3, profileJson);
        st.executeUpdate();
        st.close();

        // 通知登録（求職者宛・企業宛）
        String notifSql = "INSERT INTO notifications (receiver_type_id, message, receiver_account_id) VALUES (?, ?, ?)";
        PreparedStatement stNotif1 = con.prepareStatement(notifSql);
        stNotif1.setInt(1, 2);
        stNotif1.setString(2, "応募を受け付けました。");
        stNotif1.setLong(3, candidateId);
        stNotif1.executeUpdate();
        stNotif1.close();

        if (companyAccountId != null) {
            PreparedStatement stNotif2 = con.prepareStatement(notifSql);
            stNotif2.setInt(1, 1);
            stNotif2.setString(2, "新しい応募が届いています。");
            stNotif2.setLong(3, companyAccountId);
            stNotif2.executeUpdate();
            stNotif2.close();
        }

        con.close();
    }

    // 求職者自身の応募履歴一覧を取得
    public List<AppliedJobRow> getCandidateApplications(Long candidateId) throws Exception {
        List<AppliedJobRow> list = new ArrayList<>();
        Connection con = getConnection();

        String sql = "SELECT ah.job_id, ah.application_at, j.updated_at, "
                   + "COALESCE(ci.official_company_name, spi.trade_name) AS company_name, "
                   + "(SELECT COUNT(*) FROM applications_history ah2 WHERE ah2.job_id = j.job_id) AS entry_count "
                   + "FROM applications_history ah "
                   + "INNER JOIN jobs j ON ah.job_id = j.job_id "
                   + "LEFT JOIN corporation_information ci ON j.company_account_id = ci.company_account_id "
                   + "LEFT JOIN sole_proprietor_information spi ON j.company_account_id = spi.company_account_id "
                   + "WHERE ah.candidate_account_id = ? "
                   + "ORDER BY ah.application_at DESC";

        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, candidateId);
        ResultSet rs = st.executeQuery();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        while (rs.next()) {
            AppliedJobRow row = new AppliedJobRow();
            row.setJob_id(rs.getLong("job_id"));
            row.setCompany_name(rs.getString("company_name"));
            row.setStatus_label("選考中");

            if (rs.getTimestamp("application_at") != null) {
                row.setUpdated_date(sdf.format(rs.getTimestamp("application_at")));
            } else if (rs.getTimestamp("updated_at") != null) {
                row.setUpdated_date(sdf.format(rs.getTimestamp("updated_at")));
            } else {
                row.setUpdated_date("-");
            }

            // jobsテーブルに締切日カラムがないため掲載更新表示または随時募集を表示
            row.setDeadline_date("随時募集");
            row.setEntry_count(rs.getInt("entry_count"));
            list.add(row);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    private String escapeJson(String val) {
        if (val == null) return "";
        return val.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}