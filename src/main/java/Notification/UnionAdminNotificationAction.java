package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class UnionAdminNotificationAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        Integer unionId = null;
        Object unionIdObj = session.getAttribute("union_id");
        if (unionIdObj instanceof Integer) {
            unionId = (Integer) unionIdObj;
        } else if (unionIdObj instanceof Long) {
            unionId = ((Long) unionIdObj).intValue();
        }

        NotificationDAO dao = new NotificationDAO();
        List<Notification> notificationList = dao.findNotificationsForUnion(unionId);

        // 閲覧した通知の最大IDをセッションに保存（既読化）
        long maxId = 0L;
        for (Notification n : notificationList) {
            if (n.getNotification_id() > maxId) {
                maxId = n.getNotification_id();
            }
        }
        Long prevReadId = (Long) session.getAttribute("lastReadUnionNotifId");
        if (prevReadId == null || maxId > prevReadId) {
            session.setAttribute("lastReadUnionNotifId", maxId);
        }

        request.setAttribute("notificationList", notificationList);
        request.setAttribute("unionNotifLoaded", true);

        return "/Notification/jsp/unionAdmintuuti.jsp";
    }
}