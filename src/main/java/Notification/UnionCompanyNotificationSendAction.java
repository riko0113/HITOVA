package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.Notification;
import bean.NotificationReceiver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class UnionCompanyNotificationSendAction extends Action {

    private NotificationDAO notificationDAO = new NotificationDAO();

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String targetMode = request.getParameter("targetMode"); // "all" or "single"
            String companyAccountIdStr = request.getParameter("companyAccountId");
            String message = request.getParameter("message");

            if (message == null || message.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"メッセージを入力してください。\"}"
                );
                return null;
            }

            Integer receiverTypeId = notificationDAO.findOrCreateReceiverTypeId("企業");
            if (receiverTypeId == null) {
                throw new Exception("宛先種別「企業」の取得に失敗しました。");
            }

            if ("single".equals(targetMode)) {
                if (companyAccountIdStr == null || companyAccountIdStr.trim().isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(
                        "{\"success\":false,\"message\":\"送信先の企業を選択してください。\"}"
                    );
                    return null;
                }

                long companyId = Long.parseLong(companyAccountIdStr.trim());
                Notification notification = new Notification();
                notification.setReceiver_type_id(receiverTypeId);
                notification.setMessage(message.trim());
                notification.setReceiver_account_id(companyId);
                notificationDAO.insert(notification);

            } else {
                // 組合IDがセッションにある場合は所属企業ごとに送信、なければ企業全体へ一括送信
                HttpSession session = request.getSession();
                Integer unionId = null;
                Object unionIdObj = session.getAttribute("union_id");
                if (unionIdObj instanceof Integer) {
                    unionId = (Integer) unionIdObj;
                } else if (unionIdObj instanceof Long) {
                    unionId = ((Long) unionIdObj).intValue();
                }

                if (unionId != null) {
                    List<NotificationReceiver> targets = notificationDAO.findCompaniesByUnionId(unionId);
                    if (targets.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write(
                            "{\"success\":false,\"message\":\"送信対象の所属企業が登録されていません。\"}"
                        );
                        return null;
                    }
                    for (NotificationReceiver target : targets) {
                        Notification notification = new Notification();
                        notification.setReceiver_type_id(receiverTypeId);
                        notification.setMessage(message.trim());
                        notification.setReceiver_account_id(target.getAccountId());
                        notificationDAO.insert(notification);
                    }
                } else {
                    Notification notification = new Notification();
                    notification.setReceiver_type_id(receiverTypeId);
                    notification.setMessage(message.trim());
                    notification.setReceiver_account_id(null);
                    notificationDAO.insert(notification);
                }
            }

            response.getWriter().write(
                "{\"success\":true,\"message\":\"企業へ通知を送信しました。\"}"
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