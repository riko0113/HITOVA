package chat.company;

import DAO.ChatDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SendMessageAction extends Action {
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

            // リクエストパラメータ取得
            String roomIdParam = request.getParameter("roomId");
            String sendContent = request.getParameter("sendContent");

            // 入力値チェック（ルームIDまたは本文が空、あるいは500字超の場合は一覧画面へ戻す）
            String trimmedRoomId = (roomIdParam != null) ? roomIdParam.trim() : "";
            String trimmedContent = (sendContent != null) ? sendContent.trim() : "";

            if (trimmedRoomId.isEmpty() || trimmedContent.isEmpty() || trimmedContent.length() > 500) {
                String redirectUrl = request.getContextPath() + "/chat/comany/ChatList.action";
                if (!trimmedRoomId.isEmpty()) {
                    redirectUrl += "?roomId=" + trimmedRoomId;
                }
                response.sendRedirect(redirectUrl);
                return null;
            }

            Long roomId;
            try {
                roomId = Long.valueOf(roomIdParam.trim());
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/chat/company/ChatList.action");
                return null;
            }

            boolean senderType = false;

            // DBへ登録
            ChatDAO dao = new ChatDAO();
            
            dao.insert(roomId, trimmedContent, senderType);

            // 二重送信防止のため、送信後はChatList.actionへリダイレクト（PRGパターン）
            response.sendRedirect(
                request.getContextPath() + "/chat/company/ChatList.action?roomId=" + roomId
            );
            return null;

        } catch (Exception e) {
            // サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();

            // ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "メッセージの送信中にエラーが発生しました。しばらく経ってから再度お試しください。");

            // エラー用の専用画面へフォワードする
            return "/error.jsp";
        }
    }
}
