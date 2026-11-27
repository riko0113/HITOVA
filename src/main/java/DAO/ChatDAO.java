package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bean.ChatMessage;
import bean.ChatRoom;

public class ChatDAO extends DAO {

    // チャットルームと求職者・企業名を結合するための共通SELECT句・FROM句
    private static final String BASE_CHAT_SELECT_FROM =
            "SELECT " +
            "  m.message_id, m.room_id, m.send_at, m.send_content, m.sender_type, " +
            "  r.candidate_account_id, r.company_account_id, r.created_at, " +
            "  p.last_name, p.first_name, " +
            "  COALESCE(corp.offical_company_name, sole.trade_name) AS company_name " +
            "FROM chat_messages m " +
            "JOIN chat_rooms r ON m.room_id = r.room_id " +
            "LEFT JOIN profiles p ON r.candidate_account_id = p.candidate_account_id " +
            "LEFT JOIN company_accounts ca ON r.company_account_id = ca.company_account_id " +
            "LEFT JOIN corporate_informations corp ON r.company_account_id = corp.company_account_id " +
            "  AND ca.business_type = '法人' " +
            "LEFT JOIN sole_proprietor_informations sole ON r.company_account_id = sole.company_account_id " +
            "  AND ca.business_type = '個人事業主' ";

    // 初期一括取得用：ユーザーに関わる全チャットルームの全メッセージを古い順に取得
    public List<ChatMessage> searchAllByUserId(Long userId) throws Exception {
        List<ChatMessage> list = new ArrayList<>();

        String sql = BASE_CHAT_SELECT_FROM +
                "WHERE r.candidate_account_id = ? OR r.company_account_id = ? " +
                "ORDER BY m.room_id ASC, m.send_at ASC, m.message_id ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, userId);
            st.setLong(2, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapChatMessage(rs));
                }
            }
        }
        return list;
    }

    // Ajax新着取得用：指定ルームで lastMessageId より新しいメッセージだけを取得
    public List<ChatMessage> searchNewMessages(Long roomId, Long lastMessageId) throws Exception {
        List<ChatMessage> list = new ArrayList<>();

        String sql = BASE_CHAT_SELECT_FROM +
                "WHERE m.room_id = ? AND m.message_id > ? " +
                "ORDER BY m.send_at ASC, m.message_id ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, roomId);
            st.setLong(2, lastMessageId != null ? lastMessageId : 0L);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapChatMessage(rs));
                }
            }
        }
        return list;
    }

    // 左ペイン用：ユーザーに関わるチャットルームごとの「最新メッセージ1件」を取得（最新ルーム順）
    public List<ChatMessage> searchLatestByUserId(Long userId) throws Exception {
        List<ChatMessage> list = new ArrayList<>();
        
        //指定した求職者または企業アカウントが参加しているチャットルームの一覧を、
        //各ルームの最新メッセージ（未送信ならルーム作成日時）が新しい順に取得する
        String sql = "SELECT " +
                "  sub.message_id, sub.room_id, sub.send_at, sub.send_content, sub.sender_type, " +
                "  sub.candidate_account_id, sub.company_account_id, sub.created_at, " +
                "  sub.last_name, sub.first_name, sub.company_name " +
                "FROM ( " +
                "  SELECT " +
                "    m.message_id, r.room_id, m.send_at, m.send_content, m.sender_type, " +
                "    r.candidate_account_id, r.company_account_id, r.created_at, " +
                "    p.last_name, p.first_name, " +
                "    COALESCE(corp.offical_company_name, sole.trade_name) AS company_name, " +
                "    ROW_NUMBER() OVER ( " +
                "      PARTITION BY r.room_id " +
                "      ORDER BY m.send_at DESC NULLS LAST, m.message_id DESC NULLS LAST " +
                "    ) AS rn " +
                "  FROM chat_rooms r " +
                "  LEFT JOIN chat_messages m ON r.room_id = m.room_id " +
                "  LEFT JOIN profiles p ON r.candidate_account_id = p.candidate_account_id " +
                "  LEFT JOIN company_accounts ca ON r.company_account_id = ca.company_account_id " +
                "  LEFT JOIN corporate_informations corp ON r.company_account_id = corp.company_account_id " +
                "    AND ca.business_type = '法人' " +
                "  LEFT JOIN sole_proprietor_informations sole ON r.company_account_id = sole.company_account_id " +
                "    AND ca.business_type = '個人事業主' " +
                "  WHERE r.candidate_account_id = ? OR r.company_account_id = ? " +
                ") sub " +
                // 各ルームの先頭1件のみ抽出
                "WHERE sub.rn = 1 " +
                // 最新メッセージ日時（未送信時はルーム作成日時）が新しいルーム順で並べる
                "ORDER BY COALESCE(sub.send_at, sub.created_at) DESC, sub.message_id DESC NULLS LAST";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, userId);
            st.setLong(2, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapChatMessage(rs));
                }
            }
        }
        return list;
    }

    // 指定したルームIDのメッセージ一覧を取得（会話の流れに合わせて昇順）
    public List<ChatMessage> searchMessagesByRoomId(Long roomId) throws Exception {
        List<ChatMessage> list = new ArrayList<>();

        String sql = BASE_CHAT_SELECT_FROM +
                "WHERE m.room_id = ? " +
                "ORDER BY m.send_at ASC, m.message_id ASC";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, roomId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(mapChatMessage(rs));
                }
            }
        }
        return list;
    }

    private ChatMessage mapChatMessage(ResultSet rs) throws Exception {
        ChatMessage message = new ChatMessage();

        message.setMessage_id(rs.getObject("message_id", Long.class));
        message.setRoom_id(rs.getObject("room_id", Long.class));
        message.setSend_at(rs.getObject("send_at", LocalDateTime.class));
        message.setSend_content(rs.getString("send_content"));

        Boolean senderType = rs.getObject("sender_type", Boolean.class);
        if (senderType != null) {
            message.setSender_type(senderType);
        }

        ChatRoom room = new ChatRoom();
        room.setRoom_id(rs.getObject("room_id", Long.class));
        room.setCandidate_account_id(rs.getObject("candidate_account_id", Long.class));
        room.setCompany_account_id(rs.getObject("company_account_id", Long.class));
        room.setCreated_at(rs.getObject("created_at", LocalDateTime.class));
        room.setCompany_name(rs.getObject("company_name", String.class));

        // 姓と名を結合（両方nullの場合はnull、片方だけある場合はある方だけ結合）
        String lastName = rs.getString("last_name");
        String firstName = rs.getString("first_name");
        if (lastName != null || firstName != null) {
            room.setCandidate_name((lastName != null ? lastName : "") + (firstName != null ? firstName : ""));
        }

        message.setChatRoom(room);
        return message;
    }

    // チャットメッセージを登録する処理
    public int insert(Long roomId, String sendContent, boolean senderType) throws Exception {
        String sql = "INSERT INTO chat_messages (room_id, send_content, sender_type) VALUES (?, ?, ?)";

        int rowCount = 0;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, roomId);
            st.setString(2, sendContent);
            st.setBoolean(3, senderType);

            rowCount = st.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }

        return rowCount;
    }
    
    // 該当のチャットルームが存在するか確認
    public boolean existsRoom(Long candidateAccountId, Long companyAccountId) throws Exception {
        String sql = "SELECT 1 FROM chat_rooms " +
                     "WHERE candidate_account_id = ? AND company_account_id = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, candidateAccountId);
            st.setLong(2, companyAccountId);

            try (ResultSet rs = st.executeQuery()) {
                return rs.next(); // 1件でもあれば true、なければ false
            }
        }
    }
    
    //チャットルーム新規作成
    public int insertRoom(Long candidateId, Long companyId) throws Exception {
        String sql = "INSERT INTO chat_rooms (candidate_account_id, company_account_id) VALUES (?, ?)";

        int rowCount = 0;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, candidateId);
            st.setLong(2, companyId);

            rowCount = st.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }

        return rowCount;
    }
}