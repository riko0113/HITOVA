package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.NotificationReceiver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

// 通知送信画面を表示するAction
public class NotificationDisplayAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        NotificationDAO dao = new NotificationDAO();

        // 個別指定用の宛先一覧を取得
        List<NotificationReceiver> userList = dao.findAllJobSeekers();
        List<NotificationReceiver> companyList = dao.findAllCompanies();
        List<NotificationReceiver> unionList = dao.findAllUnions();

        request.setAttribute("userList", userList);
        request.setAttribute("companyList", companyList);
        request.setAttribute("unionList", unionList);
        request.setAttribute("sendPageLoaded", true);

        return "/Notification/jsp/tuutiSend.jsp";
    }
}