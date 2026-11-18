package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.UUID;

public class UnionAdminDAO extends DAO {

    // 未ログイン時のフォールバック用：最初の組合IDを取得
    public int getDefaultUnionId() throws Exception {
        int unionId = 1;
        Connection con = getConnection();
        String sql = "SELECT union_id FROM unions ORDER BY union_id ASC LIMIT 1";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            unionId = rs.getInt("union_id");
        }
        rs.close();
        st.close();
        con.close();
        return unionId;
    }

    // 氏名（nick_name）の重複チェック
    public boolean isNickNameExists(String nickName) throws Exception {
        boolean exists = false;
        Connection con = getConnection();
        String sql = "SELECT 1 FROM job_seeker_accounts WHERE nick_name = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, nickName);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            exists = true;
        }
        rs.close();
        st.close();
        con.close();
        return exists;
    }

    // メールアドレスの重複チェック
    public boolean isEmailExists(String email) throws Exception {
        boolean exists = false;
        Connection con = getConnection();
        String sql = "SELECT 1 FROM job_seeker_accounts WHERE email = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            exists = true;
        }
        rs.close();
        st.close();
        con.close();
        return exists;
    }

    // 組合管理者アカウントの新規登録（authority_id = 3, account_status_id = 2）
    public void insertUnionAdmin(String name, String email, int unionId) throws Exception {
        Connection con = getConnection();
        String sql = "INSERT INTO job_seeker_accounts "
                   + "(nick_name, email, account_status_id, authority_id, identity_verification, firebase_id, scout, desired_industry_id, desired_occupation_id, union_id) "
                   + "VALUES (?, ?, 2, 3, false, ?, false, ?, ?, ?)";

        String tempFirebaseId = "union_admin_" + UUID.randomUUID().toString();

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, name);
        st.setString(2, email);
        st.setString(3, tempFirebaseId);
        st.setNull(4, Types.INTEGER);
        st.setNull(5, Types.INTEGER);
        st.setInt(6, unionId);

        st.executeUpdate();
        st.close();
        con.close();
    }
}