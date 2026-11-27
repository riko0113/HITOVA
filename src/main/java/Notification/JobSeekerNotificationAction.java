package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
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
                !(session.getAttribute(AuthWeb.candidate_key) instanceof Long)) {
            return "/login/login.jsp";
        }

        // 求職者IDを取得
        Long candidateId =
                (Long) session.getAttribute(AuthWeb.candidate_key);

        // DAOで求職者向け通知を取得
        NotificationDAO dao = new NotificationDAO();
        List<Notification> notificationList =
                dao.findNotificationsForJobSeeker(candidateId);

        // 取得した通知の中で最大のnotification_idをセッションに保存（＝ここまでは既読扱い）
        long maxId = 0L;
        for (Notification n : notificationList) {
            if (n.getNotification_id() > maxId) {
                maxId = n.getNotification_id();
            }
        }
        Long prevReadId = (Long) session.getAttribute("lastReadNotifId");
        if (prevReadId == null || maxId > prevReadId) {
            session.setAttribute("lastReadNotifId", maxId);
        }

        // JSPに通知一覧を渡す
        request.setAttribute("notificationList", notificationList);

        return "/Notification/jsp/tuuti.jsp";
    }
}