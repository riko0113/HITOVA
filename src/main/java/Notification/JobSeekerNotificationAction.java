package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 求職者・企業アカウント共通の通知一覧表示Action
public class JobSeekerNotificationAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // セッションを取得
        HttpSession session = request.getSession(false);

        // ログイン確認
        if (session == null) {
            request.setAttribute("errorMessage", "ログインしてください。");
            return "/Notification/jsp/tuuti.jsp";
        }

        // アカウントIDとアカウント種別を取得
        Long accountId = (Long) session.getAttribute("accountId");
        String accountType = (String) session.getAttribute("accountType");

        if (accountId == null) {
            request.setAttribute("errorMessage", "ログインしてください。");
            return "/Notification/jsp/tuuti.jsp";
        }

        // 求職者・企業アカウント以外は拒否
        if (!"JOB_SEEKER".equals(accountType)
                && !"COMPANY".equals(accountType)) {

            request.setAttribute("errorMessage", "通知一覧を閲覧する権限がありません。");
            return "/Notification/jsp/tuuti.jsp";
        }

        // アカウント種別に応じて宛先名を決める
        String receiverName;

        if ("JOB_SEEKER".equals(accountType)) {
            receiverName = "求職者";
        } else {
            receiverName = "企業";
        }

        // DAOで通知一覧を取得
        NotificationDAO dao = new NotificationDAO();

        List<Notification> notificationList =
                dao.findNotifications(receiverName);

        // JSPに通知一覧を渡す
        request.setAttribute("notificationList", notificationList);

        return "/Notification/jsp/tuuti.jsp";
    }
}