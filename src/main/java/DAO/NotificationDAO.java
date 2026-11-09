
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.Notification;
import bean.NotificationReceiver;

public class NotificationDAO {

    // DBに接続する
    private Connection getConnection()
            throws NamingException, SQLException {

        InitialContext context = new InitialContext();

        DataSource ds = (DataSource) context.lookup(
                "java:comp/env/jdbc/hitova");

        return ds.getConnection();
    }

    // 通知を登録する
    public int insert(Notification notification)
            throws NamingException, SQLException {

        String sql = "INSERT INTO notifications "
                + "(receiver_type_id, message, receiver_account_id) "
                + "VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notification.getReceiver_type_id());
            ps.setString(2, notification.getMessage());

            if (notification.getReceiver_account_id() == null) {
                ps.setNull(3, java.sql.Types.BIGINT);
            } else {
                ps.setLong(3, notification.getReceiver_account_id());
            }

            return ps.executeUpdate();
        }
    }

    // 企業一覧を取得する
    public List<NotificationReceiver> findAllCompanies()
            throws NamingException, SQLException {

        List<NotificationReceiver> list = new ArrayList<>();

        String sql = "SELECT company_account_id, person_name "
                + "FROM company_accounts "
                + "ORDER BY company_account_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NotificationReceiver receiver =
                        new NotificationReceiver();

                receiver.setAccountId(
                        rs.getLong("company_account_id"));

                receiver.setName(
                        rs.getString("person_name"));

                list.add(receiver);
            }
        }

        return list;
    }

    // 組合IDから企業一覧を取得する
    public List<NotificationReceiver> findCompaniesByUnionId(
            Integer unionId)
            throws NamingException, SQLException {

        List<NotificationReceiver> list = new ArrayList<>();

        String sql = "SELECT company_account_id, person_name "
                + "FROM company_accounts "
                + "WHERE union_id = ? "
                + "ORDER BY company_account_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, unionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NotificationReceiver receiver =
                            new NotificationReceiver();

                    receiver.setAccountId(
                            rs.getLong("company_account_id"));

                    receiver.setName(
                            rs.getString("person_name"));

                    list.add(receiver);
                }
            }
        }

        return list;
    }

    // 宛先種別IDを取得する
    public Integer findReceiverTypeId(String receiverName)
            throws NamingException, SQLException {

        String sql = "SELECT receiver_type_id "
                + "FROM receiver_type "
                + "WHERE receiver_name = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, receiverName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("receiver_type_id");
                }
            }
        }

        return null;
    }

    // 求職者一覧を取得する
    public List<NotificationReceiver> findAllJobSeekers()
            throws NamingException, SQLException {

        List<NotificationReceiver> list = new ArrayList<>();

        String sql = "SELECT candidate_id, nick_name "
                + "FROM job_seeker_accounts "
                + "ORDER BY candidate_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NotificationReceiver receiver =
                        new NotificationReceiver();

                receiver.setAccountId(
                        rs.getLong("candidate_id"));

                receiver.setName(
                        rs.getString("nick_name"));

                list.add(receiver);
            }
        }

        return list;
    }
}
