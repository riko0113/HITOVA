package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bean.ChatMessage;
import bean.ChatRoom;


public class ChatDAO extends DAO {
    
	// ユーザーIDを指定してチャット一覧を取得するメソッド
    public List<ChatMessage> searchAll(Long userId) throws Exception {
        List<ChatMessage> list = new ArrayList<>();
        
        // ユーザーID（求職者または企業）が関わるチャットルームのメッセージや、
        // 関連するチャットルーム情報を結合して取得するSQL（最新の会話順）
        String sql = "SELECT " +
                "  m.message_id, m.room_id, m.send_at, m.send_content, m.sender_type, " +
                "  r.candidate_account_id, r.company_account_id, r.created_at " +
                "FROM chat_messages m " +
                "JOIN chat_rooms r ON m.room_id = r.room_id " +
                "WHERE r.candidate_account_id = ? OR r.company_account_id = ? " +
                "ORDER BY m.send_at DESC";

        // try-with-resources構文でコネクションを自動クローズ
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // ユーザーIDを求職者ID・企業IDの両方の条件にセット（どちらかに該当するものを取得）
            st.setLong(1, userId);
            st.setLong(2, userId);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ChatMessage message = new ChatMessage();
                    
                    // ChatMessage Beanにセット
                    message.setMessage_id(rs.getLong("message_id"));
                    message.setRoom_id(rs.getLong("room_id"));
                    message.setSend_at(rs.getObject("send_at", LocalDateTime.class));
                    message.setSend_content(rs.getString("send_content"));
                    message.setSender_type(rs.getBoolean("sender_type"));
                    
                    // 関連するChatRoom Beanも生成してセットする場合
                    ChatRoom room = new ChatRoom();
                    room.setRoom_id(rs.getLong("room_id"));
                    room.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    room.setCompany_account_id(rs.getLong("company_account_id"));
                    room.setCreated_at(rs.getObject("created_at", LocalDateTime.class));
                    
                    message.setChatRoom(room);
                    
                    list.add(message);
                }
            }
        }
        return list;
    }

}
