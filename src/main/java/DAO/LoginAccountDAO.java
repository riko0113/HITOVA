package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.CompanyAccount;
import bean.JobSeekerAccount;

// 求職者アカウントを読み書きし、企業のログイン時に利用状態を取得する。
public class LoginAccountDAO extends DAO {
    public JobSeekerAccount find(String uid) throws SQLException {
        String sql = "SELECT j.candidate_id, j.nick_name, j.email, j.firebase_id, "
                + "j.account_status_id, s.status_name, j.authority_id, a.authority_name "
                + "FROM job_seeker_accounts j "
                + "JOIN accounts_status s ON s.account_status_id = j.account_status_id "
                + "JOIN authorities a ON a.authority_id = j.authority_id "
                + "WHERE j.firebase_id = ?";

        try (Connection con = connect(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, uid);
            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                JobSeekerAccount account = new JobSeekerAccount();
                account.setCandidate_id(rs.getLong("candidate_id"));
                account.setNick_name(rs.getString("nick_name"));
                account.setEmail(rs.getString("email"));
                account.setFirebase_id(rs.getString("firebase_id"));
                account.setAccount_status_id(rs.getInt("account_status_id"));
                account.setAccount_status_name(rs.getString("status_name"));
                account.setAuthority_id(rs.getLong("authority_id"));
                account.setAuthority_name(rs.getString("authority_name"));
                return account;
            }
        }
    }

    // FirebaseのUIDから企業IDと利用状態を取得する。
    public CompanyAccount findCompany(String uid) throws SQLException {
        String sql = "SELECT c.company_account_id, c.account_status_id, s.status_name "
                + "FROM company_accounts c "
                + "JOIN accounts_status s ON s.account_status_id = c.account_status_id "
                + "WHERE c.firebase_id = ?";

        try (Connection con = connect(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, uid);
            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                CompanyAccount company = new CompanyAccount();
                company.setCompany_account_id(rs.getLong("company_account_id"));
                company.setAccount_status_id(rs.getLong("account_status_id"));
                company.setAccount_status_name(rs.getString("status_name"));
                return company;
            }
        }
    }

    public boolean nicknameExists(String nickname) throws SQLException {
        String sql = "SELECT 1 FROM job_seeker_accounts WHERE nick_name = ?";
        try (Connection con = connect(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, nickname);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        }
    }

    // 求職者を仮登録し、所属ID（union_id）に1を保存する。
    public void registerPending(String uid, String email, String nickname) throws SQLException {
        String sql = "INSERT INTO job_seeker_accounts "
                + "(nick_name, email, account_status_id, authority_id, firebase_id, union_id) VALUES (?, ?, "
                + "(SELECT account_status_id FROM accounts_status WHERE status_name = ?), "
                + "(SELECT authority_id FROM authorities WHERE authority_name = ?), ?, 1) "
                + "ON CONFLICT (firebase_id) DO NOTHING";

        try (Connection con = connect(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, nickname);
            st.setString(2, email);
            st.setString(3, "仮登録中");
            st.setString(4, "求職者");
            st.setString(5, uid);
            st.executeUpdate();
        }
    }

    // 仮登録中の求職者を利用中に切り替える。
    public void activate(String uid) throws SQLException {
        String sql = "UPDATE job_seeker_accounts SET account_status_id = "
                + "(SELECT account_status_id FROM accounts_status WHERE status_name = ?) "
                + "WHERE firebase_id = ? AND account_status_id = "
                + "(SELECT account_status_id FROM accounts_status WHERE status_name = ?) "
                + "AND authority_id = (SELECT authority_id FROM authorities WHERE authority_name = ?)";

        try (Connection con = connect(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, "利用中");
            st.setString(2, uid);
            st.setString(3, "仮登録中");
            st.setString(4, "求職者");
            st.executeUpdate();
        }
    }

    private Connection connect() throws SQLException {
        try {
            return getConnection();
        } catch (SQLException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new SQLException("Could not obtain the login database connection", exception);
        }
    }
}
