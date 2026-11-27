package Notification;

import DAO.NotificationDAO;
import bean.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String receiverType = request.getParameter("receiverType");
            String receiverAccountIdStr = request.getParameter("receiverAccountId");
            String message = request.getParameter("message");

            String receiverName = null;
            if ("user".equals(receiverType)) {
                receiverName = "求職者";
            } else if ("company".equals(receiverType)) {
                receiverName = "企業";
            } else if ("union".equals(receiverType)) {
                receiverName = "組合";
            } else if ("all".equals(receiverType)) {
                receiverName = "全体";
            }

            if (receiverName == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"宛先種別を選択してください。\"}"
                );
                return null;
            }

            if (message == null || message.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"メッセージを入力してください。\"}"
                );
                return null;
            }

            // DBから宛先種別IDを取得（組合などが未登録の場合は自動追加）
            Integer receiverTypeId = notificationDAO.findOrCreateReceiverTypeId(receiverName);
            if (receiverTypeId == null) {
                throw new Exception("送信先種別IDの取得に失敗しました。");
            }

            // 個別アカウントID（空の場合は全体送信としてNULL）
            Long receiverAccountId = null;
            if (!"all".equals(receiverType)
                    && receiverAccountIdStr != null
                    && !receiverAccountIdStr.trim().isEmpty()) {
                receiverAccountId = Long.parseLong(receiverAccountIdStr.trim());
            }

            Notification notification = new Notification();
            notification.setReceiver_type_id(receiverTypeId);
            notification.setMessage(message.trim());
            notification.setReceiver_account_id(receiverAccountId);

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