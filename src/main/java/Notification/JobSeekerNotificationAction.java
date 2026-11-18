package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 求職者用の通知一覧表示Action
public class JobSeekerNotificationAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // セッションを取得
        HttpSession session = request.getSession(false);

        // ログイン確認
        if (session == null ||
                !(session.getAttribute("candidate_account_id") instanceof Long)) {

            return "/login/login.jsp";
        }

        // 求職者IDを取得
        Long candidateId =
                (Long) session.getAttribute("candidate_account_id");

        // DAOで求職者向け通知を取得
        NotificationDAO dao = new NotificationDAO();

        List<Notification> notificationList =
                dao.findNotifications("求職者");

        // JSPに通知一覧を渡す
        request.setAttribute("notificationList", notificationList);

        return "/Notification/jsp/tuuti.jsp";
    }
}