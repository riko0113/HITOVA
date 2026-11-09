package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.JobSeekerAccount;
import bean.Profile;

// プロフィール表示DAO
public class ProfileDisplayDAO extends DAO {

    // プロフィール情報を取得する
    public Profile search(Long candidateAccountId) throws Exception {

        Profile profile = null;

        String sql =
            "SELECT " +
            "p.profile_id, " +
            "p.candidate_account_id, " +
            "p.last_name, " +
            "p.first_name, " +
            "p.last_name_kana, " +
            "p.first_name_kana, " +
            "p.birth_day, " +
            "p.gender, " +
            "p.current_postal_code, " +
            "p.current_address, " +
            "p.desired_employment_type_id, " +
            "et.employment_type_name AS desired_employment_type_name, " +
            "p.desired_work_location, " +
            "p.desired_job_type_id, " +
            "o.occupation_name AS desired_job_type_name, " +
            "p.highest_education, " +
            "p.work_history, " +
            "js.identity_verification " +
            "FROM profiles p " +
            "JOIN job_seeker_accounts js " +
            "ON p.candidate_account_id = js.candidate_id " +
            "JOIN employments_type et " +
            "ON p.desired_employment_type_id = et.employment_type_id " +
            "JOIN occupations o " +
            "ON p.desired_job_type_id = o.occupation_id " +
            "WHERE p.candidate_account_id = ?";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            st.setLong(1, candidateAccountId);

            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {

                    // プロフィール情報
                    profile = new Profile();

                    profile.setProfile_id(rs.getLong("profile_id"));

                    profile.setCandidate_account_id(
                        rs.getLong("candidate_account_id")
                    );

                    profile.setLast_name(rs.getString("last_name"));
                    profile.setFirst_name(rs.getString("first_name"));

                    profile.setLast_name_kana(
                        rs.getString("last_name_kana")
                    );

                    profile.setFirst_name_kana(
                        rs.getString("first_name_kana")
                    );

                    // 生年月日
                    profile.setBirth_date(
                        rs.getDate("birth_day")
                            .toLocalDate()
                            .atStartOfDay()
                    );

                    // 性別
                    profile.setgender(rs.getBoolean("gender"));

                    // 住所
                    profile.setCurrent_postal_code(
                        rs.getString("current_postal_code")
                    );

                    profile.setCurrent_address(
                        rs.getString("current_address")
                    );

                    // 希望雇用形態
                    profile.setDesired_employment_type_id(
                        rs.getInt("desired_employment_type_id")
                    );

                    profile.setDesired_employment_type_name(
                        rs.getString("desired_employment_type_name")
                    );

                    // 希望勤務地
                    profile.setDesired_work_location(
                        rs.getString("desired_work_location")
                    );

                    // 希望職種
                    profile.setDesired_job_type_id(
                        rs.getInt("desired_job_type_id")
                    );

                    profile.setDesired_job_type_name(
                        rs.getString("desired_job_type_name")
                    );

                    // 学歴・職歴
                    profile.setHighest_education(
                        rs.getString("highest_education")
                    );

                    profile.setWork_history(
                        rs.getString("work_history")
                    );

                    // 求職者アカウント情報
                    JobSeekerAccount account = new JobSeekerAccount();

                    account.setIdentity_verification(
                        rs.getBoolean("identity_verification")
                    );

                    // Profileにアカウント情報を設定
                    profile.setJobSeekerAccount(account);
                }
            }
        }

        return profile;
    }
}