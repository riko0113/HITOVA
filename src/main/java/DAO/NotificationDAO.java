package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

        String sql = "SELECT ca.company_account_id, "
                + "COALESCE(ci.official_company_name, spi.trade_name, ca.person_name) AS display_name "
                + "FROM company_accounts ca "
                + "LEFT JOIN corporation_information ci ON ca.company_account_id = ci.company_account_id "
                + "LEFT JOIN sole_proprietor_information spi ON ca.company_account_id = spi.company_account_id "
                + "ORDER BY ca.company_account_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NotificationReceiver receiver = new NotificationReceiver();
                receiver.setAccountId(rs.getLong("company_account_id"));
                receiver.setName(rs.getString("display_name"));
                list.add(receiver);
            }
        }

        return list;
    }

    // 組合一覧を取得する
    public List<NotificationReceiver> findAllUnions()
            throws Exception {

        List<NotificationReceiver> list = new ArrayList<>();

        String sql = "SELECT union_id, union_name FROM unions ORDER BY union_id";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NotificationReceiver receiver = new NotificationReceiver();
                receiver.setAccountId(rs.getLong("union_id"));
                receiver.setName(rs.getString("union_name"));
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
                    NotificationReceiver receiver = new NotificationReceiver();
                    receiver.setAccountId(rs.getLong("company_account_id"));
                    receiver.setName(rs.getString("person_name"));
                    list.add(receiver);
                }
            }
        }

        return list;
    }

    // 宛先種別IDを取得する（DBに未登録の種別名なら自動登録してIDを返す）
    public Integer findOrCreateReceiverTypeId(String receiverName)
            throws Exception {

        Integer existingId = findReceiverTypeId(receiverName);
        if (existingId != null) {
            return existingId;
        }

        String insertSql = "INSERT INTO receiver_type (receiver_name) VALUES (?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, receiverName);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return findReceiverTypeId(receiverName);
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
                NotificationReceiver receiver = new NotificationReceiver();
                receiver.setAccountId(rs.getLong("candidate_id"));
                receiver.setName(rs.getString("nick_name"));
                list.add(receiver);
            }
        }

        return list;
    }

    // アカウント種別に応じた通知一覧を取得する（既存メソッド）
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
                    notification.setNotification_id(rs.getLong("notification_id"));
                    notification.setMessage(rs.getString("message"));
                    notification.setSend_at(rs.getTimestamp("send_at").toLocalDateTime());
                    notification.setReceiver_type_id(rs.getInt("receiver_type_id"));
                    notification.setReceiver_type_name(rs.getString("receiver_name"));
                    list.add(notification);
                }
            }
        }
        return list;
    }

    // 求職者向けの通知一覧を取得する
    public List<Notification> findNotificationsForJobSeeker(long candidateId)
            throws Exception {

        List<Notification> list = new ArrayList<>();

        String sql = "SELECT n.notification_id, n.message, "
                + "n.send_at, n.receiver_type_id, rt.receiver_name "
                + "FROM notifications n "
                + "JOIN receiver_type rt "
                + "ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE (rt.receiver_name IN ('求職者', '全体') AND n.receiver_account_id IS NULL) "
                + "   OR (rt.receiver_name = '求職者' AND n.receiver_account_id = ?) "
                + "ORDER BY n.send_at DESC, n.notification_id DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, candidateId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification notification = new Notification();
                    notification.setNotification_id(rs.getLong("notification_id"));
                    notification.setMessage(rs.getString("message"));
                    notification.setSend_at(rs.getTimestamp("send_at").toLocalDateTime());
                    notification.setReceiver_type_id(rs.getInt("receiver_type_id"));
                    notification.setReceiver_type_name(rs.getString("receiver_name"));
                    list.add(notification);
                }
            }
        }
        return list;
    }

    // 求職者向け：既読済み最大IDより大きい未読通知件数を取得する
    public int countUnreadAfterId(long candidateId, long lastReadId)
            throws Exception {

        String sql = "SELECT COUNT(*) "
                + "FROM notifications n "
                + "JOIN receiver_type rt ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE ((rt.receiver_name IN ('求職者', '全体') AND n.receiver_account_id IS NULL) "
                + "    OR (rt.receiver_name = '求職者' AND n.receiver_account_id = ?)) "
                + "  AND n.notification_id > ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, candidateId);
            ps.setLong(2, lastReadId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // 企業向けの通知一覧を取得する（全体・企業全体 + 自社宛て個別通知）
    public List<Notification> findNotificationsForCompany(long companyAccountId)
            throws Exception {

        List<Notification> list = new ArrayList<>();

        String sql = "SELECT n.notification_id, n.message, "
                + "n.send_at, n.receiver_type_id, rt.receiver_name "
                + "FROM notifications n "
                + "JOIN receiver_type rt "
                + "ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE (rt.receiver_name IN ('企業', '全体') AND n.receiver_account_id IS NULL) "
                + "   OR (rt.receiver_name = '企業' AND n.receiver_account_id = ?) "
                + "ORDER BY n.send_at DESC, n.notification_id DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, companyAccountId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification notification = new Notification();
                    notification.setNotification_id(rs.getLong("notification_id"));
                    notification.setMessage(rs.getString("message"));
                    notification.setSend_at(rs.getTimestamp("send_at").toLocalDateTime());
                    notification.setReceiver_type_id(rs.getInt("receiver_type_id"));
                    notification.setReceiver_type_name(rs.getString("receiver_name"));
                    list.add(notification);
                }
            }
        }
        return list;
    }

    // 企業向け：既読済み最大IDより大きい未読通知件数を取得する
    public int countUnreadCompanyAfterId(long companyAccountId, long lastReadId)
            throws Exception {

        String sql = "SELECT COUNT(*) "
                + "FROM notifications n "
                + "JOIN receiver_type rt ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE ((rt.receiver_name IN ('企業', '全体') AND n.receiver_account_id IS NULL) "
                + "    OR (rt.receiver_name = '企業' AND n.receiver_account_id = ?)) "
                + "  AND n.notification_id > ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, companyAccountId);
            ps.setLong(2, lastReadId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
 // 組合向けの通知一覧を取得する（全体・組合全体 + 自組合宛て個別通知）
    public List<Notification> findNotificationsForUnion(Integer unionId)
            throws Exception {

        List<Notification> list = new ArrayList<>();

        String sql;
        if (unionId != null) {
            sql = "SELECT n.notification_id, n.message, "
                + "n.send_at, n.receiver_type_id, rt.receiver_name "
                + "FROM notifications n "
                + "JOIN receiver_type rt ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE (rt.receiver_name IN ('組合', '全体') AND n.receiver_account_id IS NULL) "
                + "   OR (rt.receiver_name = '組合' AND n.receiver_account_id = ?) "
                + "ORDER BY n.send_at DESC, n.notification_id DESC";
        } else {
            sql = "SELECT n.notification_id, n.message, "
                + "n.send_at, n.receiver_type_id, rt.receiver_name "
                + "FROM notifications n "
                + "JOIN receiver_type rt ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE rt.receiver_name IN ('組合', '全体') AND n.receiver_account_id IS NULL "
                + "ORDER BY n.send_at DESC, n.notification_id DESC";
        }

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (unionId != null) {
                ps.setLong(1, unionId.longValue());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification notification = new Notification();
                    notification.setNotification_id(rs.getLong("notification_id"));
                    notification.setMessage(rs.getString("message"));
                    notification.setSend_at(rs.getTimestamp("send_at").toLocalDateTime());
                    notification.setReceiver_type_id(rs.getInt("receiver_type_id"));
                    notification.setReceiver_type_name(rs.getString("receiver_name"));
                    list.add(notification);
                }
            }
        }
        return list;
    }

    // 組合向け：既読済み最大IDより大きい未読通知件数を取得する
    public int countUnreadUnionAfterId(Integer unionId, long lastReadId)
            throws Exception {

        String sql;
        if (unionId != null) {
            sql = "SELECT COUNT(*) "
                + "FROM notifications n "
                + "JOIN receiver_type rt ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE ((rt.receiver_name IN ('組合', '全体') AND n.receiver_account_id IS NULL) "
                + "    OR (rt.receiver_name = '組合' AND n.receiver_account_id = ?)) "
                + "  AND n.notification_id > ?";
        } else {
            sql = "SELECT COUNT(*) "
                + "FROM notifications n "
                + "JOIN receiver_type rt ON n.receiver_type_id = rt.receiver_type_id "
                + "WHERE (rt.receiver_name IN ('組合', '全体') AND n.receiver_account_id IS NULL) "
                + "  AND n.notification_id > ?";
        }

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (unionId != null) {
                ps.setLong(1, unionId.longValue());
                ps.setLong(2, lastReadId);
            } else {
                ps.setLong(1, lastReadId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}