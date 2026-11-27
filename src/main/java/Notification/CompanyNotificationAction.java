package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.CompanyAccount;
import bean.Notification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 企業用の通知一覧表示Action
public class CompanyNotificationAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        // AccountInfoActionと同じロジックで企業アカウントIDを取得
        Long companyAccountId = null;
        if (session.getAttribute("company_account_id") instanceof Long) {
            companyAccountId = (Long) session.getAttribute("company_account_id");
        } else if (session.getAttribute("companyAccount") instanceof CompanyAccount) {
            companyAccountId = ((CompanyAccount) session.getAttribute("companyAccount")).getCompany_account_id();
        }

        // テスト時フォールバック
        if (companyAccountId == null) {
            companyAccountId = 2001L;
        }

        // DAOで企業向け通知を取得
        NotificationDAO dao = new NotificationDAO();
        List<Notification> notificationList =
                dao.findNotificationsForCompany(companyAccountId);

        // 取得した通知の最大IDをセッションに保存（既読化）
        long maxId = 0L;
        for (Notification n : notificationList) {
            if (n.getNotification_id() > maxId) {
                maxId = n.getNotification_id();
            }
        }
        Long prevReadId = (Long) session.getAttribute("lastReadCompanyNotifId");
        if (prevReadId == null || maxId > prevReadId) {
            session.setAttribute("lastReadCompanyNotifId", maxId);
        }

        // JSPに通知一覧を渡す
        request.setAttribute("notificationList", notificationList);

        return "/Notification/jsp/companytuuti.jsp";
    }
}