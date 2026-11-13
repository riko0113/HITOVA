
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;
import bean.NotificationReceiver;

// 共通DAOを継承
public class NotificationDAO extends DAO {

    // 通知を登録する
    public int insert(Notification notification)
            throws Exception {

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
            throws Exception {

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
            Integer unionId) throws Exception {

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
            throws Exception {

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
            throws Exception {

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
 // アカウント種別に応じた通知一覧を取得する
    public List<Notification> findNotifications(String receiverName)
            throws Exception {

        List<Notification> list = new ArrayList<>();

        String sql = "SELECT n.notification_id, n.message, "
                + "n.send_at, n.receiver_type_id, rt.receiver_name "
                + "FROM notifications n "
                + "JOIN receiver_type rt "
                + "ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE rt.receiver_name IN (?, '全体') "
                + "AND n.receiver_account_id IS NULL "
                + "ORDER BY n.send_at DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, receiverName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Notification notification = new Notification();

                    notification.setNotification_id(
                            rs.getLong("notification_id"));

                    notification.setMessage(
                            rs.getString("message"));

                    notification.setSend_at(
                            rs.getTimestamp("send_at").toLocalDateTime());

                    notification.setReceiver_type_id(
                            rs.getInt("receiver_type_id"));

                    notification.setReceiver_type_name(
                            rs.getString("receiver_name"));

                    list.add(notification);
                }
            }
        }
        return list;
    }
    
}
