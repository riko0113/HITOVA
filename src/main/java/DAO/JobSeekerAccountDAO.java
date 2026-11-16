
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.JobSeekerAccount;

public class JobSeekerAccountDAO extends DAO {

    // アカウント情報を取得する（表示・編集画面で使用）
    public JobSeekerAccount search(Long candidateId) throws Exception {

        JobSeekerAccount account = null;

        String sql =
                "SELECT "
                + "j.candidate_id, "
                + "j.email, "
                + "j.nick_name, "
                + "j.identity_verification, "
                + "j.scout, "
                + "j.desired_industry_id, "
                + "i.industry_name, "
                + "j.desired_occupation_id, "
                + "o.occupation_name "
                + "FROM job_seeker_accounts j "
                + "JOIN industries i "
                + "ON j.desired_industry_id = i.industry_id "
                + "JOIN occupations o "
                + "ON j.desired_occupation_id = o.occupation_id "
                + "WHERE j.candidate_id = ?";

        try (
                Connection con = getConnection();
                PreparedStatement st = con.prepareStatement(sql);
        ) {

            st.setLong(1, candidateId);

            try (ResultSet rs = st.executeQuery()) {

                if (rs.next()) {

                    account = new JobSeekerAccount();

                    account.setCandidate_id(
                            rs.getLong("candidate_id"));

                    account.setEmail(
                            rs.getString("email"));

                    account.setNick_name(
                            rs.getString("nick_name"));

                    account.setIdentity_verification(
                            rs.getBoolean("identity_verification"));

                    account.setScout(
                            rs.getBoolean("scout"));

                    account.setDesired_industry_id(
                            rs.getInt("desired_industry_id"));

                    account.setDesired_industry_name(
                            rs.getString("industry_name"));

                    account.setDesired_occupation_id(
                            rs.getInt("desired_occupation_id"));

                    account.setDesired_occupation_name(
                            rs.getString("occupation_name"));
                }
            }
        }

        return account;
    }


    // アカウント情報を更新する（編集画面で使用）
    public int updateAccount(
            Long candidateId,
            String nickName,
            boolean scout,
            int desiredIndustryId,
            int desiredOccupationId) throws Exception {

        String sql =
                "UPDATE job_seeker_accounts SET "
                + "nick_name = ?, "
                + "scout = ?, "
                + "desired_industry_id = ?, "
                + "desired_occupation_id = ? "
                + "WHERE candidate_id = ?";

        try (
                Connection con = getConnection();
                PreparedStatement st = con.prepareStatement(sql);
        ) {

            st.setString(1, nickName);

            st.setBoolean(2, scout);

            st.setInt(3, desiredIndustryId);

            st.setInt(4, desiredOccupationId);

            st.setLong(5, candidateId);

            return st.executeUpdate();
        }
    }
}
