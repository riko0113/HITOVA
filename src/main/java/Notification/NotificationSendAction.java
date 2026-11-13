
package Notification;

import DAO.NotificationDAO;
import bean.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class NotificationSendAction extends Action {

    private NotificationDAO notificationDAO = new NotificationDAO();

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
    	
        try {
        	
            request.setCharacterEncoding("UTF-8");

            // セッションを取得（存在しなければ新しく作らない）
            HttpSession session = request.getSession(false);

            // セッションが存在しない場合
            if (session == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"ログインしてください。\"}"
                );
                return null;
            }

            // アカウントIDを取得
            Long accountId = (Long) session.getAttribute("accountId");

            // アカウントIDが存在しない場合
            if (accountId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"ログインしてください。\"}"
                );
                return null;
            }

            // アカウントの種類を取得
            String accountType = (String) session.getAttribute("accountType");

            // 管理者・組合管理者以外を拒否
            if (!"ADMIN".equals(accountType) && !"UNION_ADMIN".equals(accountType)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"通知を送信する権限がありません。\"}"
                );
                return null;
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // JSPから送信先とメッセージを受け取る
            String receiverType = request.getParameter("receiverType");
            String message = request.getParameter("message");

            // 送信先の種類を確認
            String receiverName = null;

            if ("user".equals(receiverType)) {
                receiverName = "求職者";
            } else if ("company".equals(receiverType)) {
                receiverName = "企業";
            } else if ("all".equals(receiverType)) {
                receiverName = "全体";
            }

            // 送信先チェック
            if (receiverName == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"送信先を選択してください。\"}"
                );
                return null;
            }

            // メッセージチェック
            if (message == null || message.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"メッセージを入力してください。\"}"
                );
                return null;
            }

            // DBから送信先種別IDを取得
            Integer receiverTypeId =
                notificationDAO.findReceiverTypeId(receiverName);

            if (receiverTypeId == null) {
                throw new Exception("送信先種別がDBに存在しません。");
            }

            // 通知データを作成
            Notification notification = new Notification();
            notification.setReceiver_type_id(receiverTypeId);
            notification.setMessage(message);

            // 全体むけ通知なので送信先アカウントIDはNULL
            notification.setReceiver_account_id(null);

            // DBに登録
            notificationDAO.insert(notification);

            response.getWriter().write(
                "{\"success\":true,\"message\":\"通知を送信しました。\"}"
            );

        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(
                "{\"success\":false,\"message\":\"通知の送信に失敗しました。\"}"
            );
        }

        return null;
    }
}
