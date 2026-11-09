package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.NotificationReceiver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

// 通知送信画面を表示するAction
public class NotificationDisplayAction extends Action {

    // DAOを使う準備
    private NotificationDAO notificationDAO =
            new NotificationDAO();

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // 企業一覧を取得
        List<NotificationReceiver> companyList =
                notificationDAO.findAllCompanies();

        // 求職者一覧を取得
        List<NotificationReceiver> userList =
                notificationDAO.findAllJobSeekers();

        // 取得した一覧をJSPに渡す
        request.setAttribute("companyList", companyList);
        request.setAttribute("userList", userList);

        // 表示するJSPを返す
        return "/Notification/jsp/tuutiSend.jsp";
    }
}
