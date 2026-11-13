package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.AchievementManagement;
import bean.ApplicationHistory;
import bean.ApplicationHistoryDetail;
import bean.Job;
import bean.JobSeekerAccount;
import bean.Portfolio;
import bean.QualificationDetail;
import bean.Skill;

public class ApplicationHistoryDAO extends DAO {
    
    // 企業IDを指定して応募履歴一覧を取得するメソッド
    public List<ApplicationHistory> searchAll(Long jobId) throws Exception {
        List<ApplicationHistory> list = new ArrayList<>();
        
        // 取得するカラムをすべて明記したSQL
        String sql = "SELECT " +
                "  ah.application_history_id, " +
                "  ah.candidate_account_id, " +
                "  ah.job_id, " +
                "  ah.profile AS ah_profile, " +
                "  ah.application_at, " +
                "  js.candidate_id AS js_candidate_id, " +
                "  js.nick_name, " +
                "  js.email AS js_email, " +
                "  js.authority_id, " +
                "  au.authority_name, " +
                "  js.created_at AS js_created_at, " +
                "  js.identity_verification, " +
                "  js.firebase_id, " +
                "  js.scout, " +
                "  js.account_status_id, " +
                "  ast.status_name, " + 
                "  js.desired_industry_id, " +
                "  ind.industry_name, " + 
                "  js.desired_occupation_id, " +
                "  op.occupation_name, " +
                "  js.union_id, " +
                "  un.union_name, " +
                "  j.company_account_id, " +
                "  j.title AS job_title, " +
                "  j.location " +
                "FROM applications_history ah " +
                "LEFT JOIN job_seeker_accounts js " +
                "  ON ah.candidate_account_id = js.candidate_id " +
                "LEFT JOIN jobs j " +
                "  ON ah.job_id = j.job_id " +
                "LEFT JOIN authorities au " +
                "  ON js.authority_id = au.authority_id " +
                "LEFT JOIN accounts_status ast " +
                "  ON js.account_status_id = ast.account_status_id " +
                "LEFT JOIN industries ind " +
                "  ON js.desired_industry_id = ind.industry_id " +
                "LEFT JOIN occupations op " +
                "  ON js.desired_occupation_id = op.occupation_id " +
                "LEFT JOIN unions un " +
                "  ON js.union_id = un.union_id " +
                "WHERE ah.job_id = ?";

        // try-with-resources構文でコネクションを自動クローズ
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // 企業IDをセット
            st.setLong(1, jobId);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ApplicationHistory applicant = new ApplicationHistory();
                    
                    // ApplicationHistory Beanにセット
                    applicant.setApplication_history_id(rs.getLong("application_history_id"));
                    applicant.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    applicant.setJob_id(rs.getLong("job_id"));
                    applicant.setProfile(rs.getString("ah_profile"));
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
                    account.setAccount_status_name(rs.getString("status_name"));
                    account.setDesired_industry_id(rs.getInt("desired_industry_id"));
                    account.setDesired_industry_name(rs.getString("industry_name"));
                    account.setDesired_occupation_id(rs.getInt("desired_occupation_id"));
                    account.setDesired_occupation_name(rs.getString("occupation_name"));
                    account.setUnion_id(rs.getInt("union_id"));
                    account.setUnion_name(rs.getString("union_name"));
                    applicant.setJobSeekerAccount(account);

                    list.add(applicant);
                }
            }
        }
        return list;
    }
    
    // 応募履歴IDを指定して詳細を取得するメソッド
    public ApplicationHistoryDetail searchDetail(Long applicationHistoryId)
            throws Exception {

        // 1件分のオブジェクトを生成
        ApplicationHistoryDetail detail = null;

        String sql =
            "SELECT " +
            "  ah.application_history_id, " +
            "  ah.candidate_account_id, " +
            "  ah.job_id, " +
            "  ah.profile AS ah_profile, " +
            "  ah.application_at, " +

            "  js.candidate_id AS js_candidate_id, " +
            "  js.nick_name, " +
            "  js.email AS js_email, " +
            "  js.authority_id, " +
            "  au.authority_name, " +
            "  js.created_at AS js_created_at, " +
            "  js.identity_verification, " +
            "  js.firebase_id, " +
            "  js.scout, " +
            "  js.account_status_id, " +
            "  ast.status_name, " +
            "  js.desired_industry_id, " +
            "  ind.industry_name, " +
            "  js.desired_occupation_id, " +
            "  op.occupation_name, " +
            "  js.union_id, " +
            "  un.union_name, " +
            "  j.company_account_id, " +
            "  j.title AS job_title, " +
            "  j.location " +

            "FROM applications_history ah " +
            "LEFT JOIN job_seeker_accounts js " +
            "  ON ah.candidate_account_id = js.candidate_id " +
            "LEFT JOIN jobs j " +
            "  ON ah.job_id = j.job_id " +
            "LEFT JOIN authorities au ON js.authority_id = au.authority_id " +
            "LEFT JOIN accounts_status ast ON js.account_status_id = ast.account_status_id " + 
            "LEFT JOIN industries as ind ON js.desired_industry_id = ind.industry_id " + 
            "LEFT JOIN occupations as op ON js.desired_occupation_id = op.occupation_id " + 
            "LEFT JOIN unions as un ON js.union_id = un.union_id " + 

            "WHERE ah.application_history_id = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, applicationHistoryId);

            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {

                    detail = new ApplicationHistoryDetail();

                    // --- ApplicationHistory Beanにセット ---
                    ApplicationHistory applicant = new ApplicationHistory();
                    applicant.setApplication_history_id(rs.getLong("application_history_id"));
                    applicant.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    applicant.setJob_id(rs.getLong("job_id"));
                    applicant.setProfile(rs.getString("ah_profile"));

                    Timestamp appAt = rs.getTimestamp("application_at");
                    if (appAt != null) {
                        applicant.setApplication_at(appAt.toLocalDateTime());
                    }

                    // --- Job Beanにセット ---
                    Job job = new Job();
                    job.setJob_id(rs.getLong("job_id"));
                    job.setCompany_account_id(rs.getLong("company_account_id"));
                    job.setTitle(rs.getString("job_title"));
                    job.setLocation(rs.getString("location"));
                    applicant.setJob(job);

                    // --- JobSeekerAccount Beanにセット ---
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
                    account.setAccount_status_name(rs.getString("status_name"));
                    account.setDesired_industry_id(rs.getInt("desired_industry_id"));
                    account.setDesired_industry_name(rs.getString("industry_name"));
                    account.setDesired_occupation_id(rs.getInt("desired_occupation_id"));
                    account.setDesired_occupation_name(rs.getString("occupation_name"));
                    account.setUnion_id(rs.getInt("union_id"));
                    account.setUnion_name(rs.getString("union_name"));

                    applicant.setJobSeekerAccount(account);
                    detail.setApplicationHistory(applicant);

                    // 求職者IDを取得
                    Long candidateAccountId = rs.getLong("candidate_account_id");

                    // スキル・資格・実績・ポートフォリオ一覧をセット
                    detail.setSkills(findSkillsByCandidateId(con, candidateAccountId));
                    detail.setQualifications(findQualificationDetailsByCandidateId(con, candidateAccountId));
                    detail.setAchievements(findAchievementManagementsByCandidateId(con, candidateAccountId));
                    detail.setPortfolios(findPortfoliosByCandidateId(con, candidateAccountId));
                }
            }
        }

        return detail; // 1件分のオブジェクト（見つからなければnull）を返す
    }
    
    // --- 各種 1対多データ取得メソッド ---
    //スキル
    private List<Skill> findSkillsByCandidateId(Connection con, Long candidateId) throws Exception {
        List<Skill> list = new ArrayList<>();
        String sql = "SELECT skill_id, candidate_id, skill_name, skill_content FROM skills WHERE candidate_id = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
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
    
    //資格詳細
    private List<QualificationDetail> findQualificationDetailsByCandidateId(Connection con, Long candidateId) throws Exception {
        List<QualificationDetail> list = new ArrayList<>();
        String sql = "SELECT qd.qualification_detail_id, qd.qualification_name, qd.field_id, fl.field_name " +
                     "FROM qualifications q " +
                     "JOIN qualifications_detail qd ON q.qualification_detail_id = qd.qualification_detail_id " +
                     "JOIN fields as fl ON qd.field_id = fl.field_id " + 
                     "WHERE q.candidate_account_id = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, candidateId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    QualificationDetail qd = new QualificationDetail();
                    qd.setQualification_detail_id(rs.getLong("qualification_detail_id"));
                    qd.setQualification_name(rs.getString("qualification_name"));
                    qd.setField_id(rs.getLong("field_id"));
                    qd.setField_name(rs.getString("field_name"));
                    list.add(qd);
                }
            }
        }
        return list;
    }
    
    //実績管理
    private List<AchievementManagement> findAchievementManagementsByCandidateId(Connection con, Long candidateId) throws Exception {
        List<AchievementManagement> list = new ArrayList<>();
        String sql = "SELECT am.task_id, am.achievement, am.requirement, am.howtosubmit_id, su.howtosubmit_name, " +
                     "       am.registration_date, am.field_id, fi.field_name, am.category_id, ca.category_name, " +
                     "       am.union_id, un.union_name " +
                     "FROM achievements a " +
                     "JOIN achievement_managements am ON a.task_id = am.task_id " +
                     "JOIN submissions su ON am.howtosubmit_id = su.howtosubmit_id " +
                     "JOIN fields fi ON am.field_id = fi.field_id " +
                     "JOIN categories ca ON am.category_id = ca.category_id " +
                     "JOIN unions un ON am.union_id = un.union_id " +
                     "WHERE a.candidate_account_id = ?";
        
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, candidateId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    AchievementManagement am = new AchievementManagement();
                    am.setTask_id(rs.getLong("task_id"));
                    am.setAchievement(rs.getString("achievement"));
                    am.setRequirement(rs.getString("requirement"));
                    am.setHowtosubmit_id(rs.getLong("howtosubmit_id"));
                    am.setHowtosubmit_name(rs.getString("howtosubmit_name"));
                    java.sql.Date regDate = rs.getDate("registration_date");
                    if (regDate != null) {
                        am.setRegistration_dat(regDate.toLocalDate());
                    }
                    am.setField_id(rs.getLong("field_id"));
                    am.setField_name(rs.getString("field_name"));
                    am.setCategory_id(rs.getLong("category_id"));
                    am.setCategory_name(rs.getString("category_name"));
                    am.setUnion_id(rs.getLong("union_id"));
                    am.setUnion_name(rs.getString("union_name"));
                    list.add(am);
                }
            }
        }
        return list;
    }

    //ポートフォリオ
    private List<Portfolio> findPortfoliosByCandidateId(Connection con, Long candidateId) throws Exception {
        List<Portfolio> list = new ArrayList<>();
        String sql = "SELECT portfolio_id, work_name, description, file_url, candidate_account_id FROM portfolios WHERE candidate_account_id = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, candidateId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Portfolio p = new Portfolio();
                    p.setPortfolio_id(rs.getLong("portfolio_id"));
                    p.setWork_name(rs.getString("work_name"));
                    p.setDescription(rs.getString("description"));
                    p.setFile_url(rs.getString("file_url"));
                    p.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    list.add(p);
                }
            }
        }
        return list;
    }
}