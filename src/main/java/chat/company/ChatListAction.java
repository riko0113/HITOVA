package chat.company;

import java.util.ArrayList;
import java.util.List;

import DAO.ChatDAO;
import bean.ChatMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ChatListAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {

            // セッションからユーザーIDを取得して未ログインチェック
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("");

            if (userId == null) {
                // ログイン画面へリダイレクトする処理
                response.sendRedirect(request.getContextPath() + "");
                return null;
            }

            // チャットDAOのインスタンス生成
            ChatDAO dao = new ChatDAO();

            // 左ペイン用：ルームごとの最新メッセージ一覧
            List<ChatMessage> roomList = dao.searchLatestByUserId(userId);

            // 初期表示用に全ルームのメッセージもまとめて取得（JS側で保持・切り替えたい場合用）
            List<ChatMessage> allMessages = dao.searchAllByUserId(userId);

            // 現在選択中ルームのメッセージ一覧
            List<ChatMessage> messageList = new ArrayList<>();

            Long targetRoomId = null;
            String roomIdParam = request.getParameter("roomId");

            if (roomIdParam != null && !roomIdParam.isEmpty()) {
                try {
                    targetRoomId = Long.valueOf(roomIdParam);
                } catch (NumberFormatException e) {
                    if (!roomList.isEmpty()) {
                        targetRoomId = roomList.get(0).getRoom_id();
                    }
                }
            } else if (!roomList.isEmpty()) {
                targetRoomId = roomList.get(0).getRoom_id();
            }

            if (targetRoomId != null) {
                // 全件取得済みリストから対象ルーム分を抽出（DB再アクセスを減らす場合）
                for (ChatMessage msg : allMessages) {
                    if (targetRoomId.equals(msg.getRoom_id())) {
                        messageList.add(msg);
                    }
                }
            }

            request.setAttribute("roomList", roomList);
            request.setAttribute("allMessages", allMessages);
            request.setAttribute("messageList", messageList);
            request.setAttribute("selectedRoomId", targetRoomId);

            return "/chat/chat_company.jsp";

        } catch (Exception e) {
            // サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();

            // ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "データの取得中にエラーが発生しました。しばらく経ってから再度お試しください。");

            // エラー用の専用画面、または元の安全な画面へフォワードする
            return "/error.jsp";
        }
    }
}