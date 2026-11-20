package chat;

import java.io.PrintWriter;
import java.util.List;

import DAO.ChatDAO;
import bean.ChatMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;


public class GetMessageAjaxAction extends Action {
    //FrontControllerから呼ばれるメイン処理。
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            // Ajax通信のレスポンスとしてJSON（UTF-8）を返すことを指定
            response.setContentType("application/json; charset=UTF-8");

            // クエリパラメータから対象ルームIDと、画面側が保持している最新メッセージIDを取得
            String roomIdParam = request.getParameter("roomId");
            String lastIdParam = request.getParameter("lastMessageId");

            // roomIdが未指定・空文字の場合は取得対象がないため空配列JSONを返して終了
            if (roomIdParam == null || roomIdParam.trim().isEmpty()) {
                response.getWriter().write("[]");
                return null;
            }

            // 文字列のroomIdをLong型に変換（前後空白を除去）
            Long roomId = Long.valueOf(roomIdParam.trim());

            // lastMessageIdが指定されていればLong型に変換、未指定なら0L（先頭から取得）とする
            Long lastMessageId = (lastIdParam != null && !lastIdParam.trim().isEmpty())
                    ? Long.valueOf(lastIdParam.trim())
                    : 0L;

            // DAO経由で「指定ルームかつ lastMessageId より大きいID」の新着メッセージ一覧を取得
            ChatDAO dao = new ChatDAO();
            List<ChatMessage> newMessages = dao.searchNewMessages(roomId, lastMessageId);

            // JavaScript側（createBubbleElement）の想定プロパティ名に合わせてJSON配列を手動組み立て
            StringBuilder json = new StringBuilder();
            json.append("[");
            for (int i = 0; i < newMessages.size(); i++) {
                ChatMessage msg = newMessages.get(i);

                // 2件目以降の要素の前にはカンマを付与
                if (i > 0) {
                    json.append(",");
                }

                // 1件分のメッセージオブジェクトを生成
                // ※文字列項目（sendContent, sendAt）はJSONが壊れないようエスケープする
                json.append("{")
                    .append("\"messageId\":").append(msg.getMessage_id()).append(",")
                    .append("\"roomId\":").append(msg.getRoom_id()).append(",")
                    .append("\"sendContent\":\"").append(escapeJson(msg.getSend_content())).append("\",")
                    .append("\"sendAt\":\"").append(escapeJson(String.valueOf(msg.getSend_at()))).append("\",")
                    .append("\"senderType\":").append(msg.getSender_type())
                    .append("}");
            }
            json.append("]");

            // 組み立てたJSON文字列を出力ストリームへ書き込み、バッファをフラッシュ
            PrintWriter out = response.getWriter();
            out.write(json.toString());
            out.flush();

            // レスポンス書き込み済みのため、JSPへの画面遷移は行わない
            return null;

        } catch (Exception e) {
            e.printStackTrace();

            // サーバー内部エラー（500）を設定
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            // JS側でres.json()を呼んでもパースエラーになりにくいよう空配列を返却
            try {
                response.getWriter().write("[]");
            } catch (Exception ignored) {
                // レスポンス出力時の二次例外は無視
            }
            return null;
        }
    }

    //JSONのダブルクォーテーション文字列内に安全に埋め込めるよう、制御文字や記号をエスケープするヘルパーメソッド。
    private String escapeJson(String str) {
        // null または "null" 文字列の場合は空文字に正規化
        if (str == null || "null".equals(str)) {
            return "";
        }
        // バックスラッシュ → 二重引用符 → 改行・タブの順にエスケープ
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\r", "\\r")
                  .replace("\n", "\\n")
                  .replace("\t", "\\t");
    }
}