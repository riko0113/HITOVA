package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class UnionAdminNotificationAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // DAOで全体向け通知を取得
        NotificationDAO dao = new NotificationDAO();

        List<Notification> notificationList =
                dao.findNotifications("全体");

        // JSPに通知一覧を渡す
        request.setAttribute("notificationList", notificationList);

        return "/Notification/jsp/unionAdmintuuti.jsp";
    }
}