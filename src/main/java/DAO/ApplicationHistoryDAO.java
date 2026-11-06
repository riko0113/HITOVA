package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.ApplicationHistory;
import bean.Job;
import bean.JobSeekerAccount;

public class ApplicationHistoryDAO extends DAO {
    
    // 企業IDを指定して応募履歴一覧を取得するメソッド
    public List<ApplicationHistory> searchAll(Long companyAccountId) throws Exception {
        List<ApplicationHistory> list = new ArrayList<>();
        
        // 取得するカラムをすべて明記したSQL
        String sql = "SELECT " +
                "  ah.application_history_id, ah.candidate_account_id, ah.job_id, ah.profile, ah.application_at, " +
                "  js.candidate_id AS js_candidate_id, js.nick_name, js.email AS js_email, " +
                "  js.authority_id, js.authority_name, js.created_at AS js_created_at, " +
                "  js.identity_verification, js.firebase_id, js.scout, " +
                "  js.account_status_id, js.account_status_name, " +
                "  js.desired_industry_id, js.desired_industry_name, " +
                "  js.desired_occupation_id, js.desired_occupation_name, " +
                "  js.union_id, js.union_name, " +
                "  j.company_account_id, j.title AS job_title, j.location " +
                "FROM application_histories ah " +
                "JOIN job_seeker_accounts js ON ah.candidate_account_id = js.candidate_id " +
                "JOIN jobs j ON ah.job_id = j.job_id " +
                "WHERE j.company_account_id = ?";

        // try-with-resources構文でコネクションを自動クローズ
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // 企業IDをセット
            st.setLong(1, companyAccountId);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ApplicationHistory applicant = new ApplicationHistory();
                    
                    // ApplicationHistory Beanにセット
                    applicant.setApplication_history_id(rs.getLong("application_history_id"));
                    applicant.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    applicant.setJob_id(rs.getLong("job_id"));
                    applicant.setProfile(rs.getString("profile"));
                    Timestamp appAt = rs.getTimestamp("application_at");
                    if (appAt != null) {
                        applicant.setApplication_at(appAt.toLocalDateTime());
                    }
                    
                    // Job Beanにセット
                    Job job = new Job();
                    job.setJob_id(rs.getLong("job_id"));
                    job.setCompany_account_id(rs.getLong("company_account_id"));
                    job.setTitle(rs.getString("job_title"));
                    job.setLocation(rs.getString("location")); // 必要に応じて
                    applicant.setJob(job);
                    
                    // JobSeekerAccount Beanにセット
                    JobSeekerAccount account = new JobSeekerAccount();
                    account.setCandidate_id(rs.getLong("js_candidate_id"));
                    account.setNick_name(rs.getString("nick_name"));
                    account.setEmail(rs.getString("js_email"));
                    account.setAuthority_id(rs.getLong("authority_id"));
                    account.setAuthority_name(rs.getString("authority_name"));
                    Timestamp jsCreated = rs.getTimestamp("js_created_at");
                    if (jsCreated != null) {
                        account.setCreated_at(jsCreated.toLocalDateTime());
                    }
                    account.setIdentity_verification(rs.getBoolean("identity_verification"));
                    account.setFirebase_id(rs.getString("firebase_id"));
                    account.setScout(rs.getBoolean("scout"));
                    account.setAccount_status_id(rs.getInt("account_status_id"));
                    account.setAccount_status_name(rs.getString("account_status_name"));
                    account.setDesired_industry_id(rs.getInt("desired_industry_id"));
                    account.setDesired_industry_name(rs.getString("desired_industry_name"));
                    account.setDesired_occupation_id(rs.getInt("desired_occupation_id"));
                    account.setDesired_occupation_name(rs.getString("desired_occupation_name"));
                    account.setUnion_id(rs.getInt("union_id"));
                    account.setUnion_name(rs.getString("union_name"));
                    applicant.setJobSeekerAccount(account);

                    list.add(applicant);
                }
            }
        }
        return list;
    }
}