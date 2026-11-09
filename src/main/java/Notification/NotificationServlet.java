
package Notification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import DAO.NotificationDAO;
import bean.NotificationReceiver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notification")
public class NotificationServlet extends HttpServlet {

    private NotificationDAO notificationDAO = new NotificationDAO();

    // 通知送信画面を表示
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<NotificationReceiver> companyList =
                    notificationDAO.findAllCompanies();

            List<NotificationReceiver> userList =
                    notificationDAO.findAllJobSeekers();

            request.setAttribute("companyList", companyList);
            request.setAttribute("userList", userList);

            request.getRequestDispatcher(
                    "/Notification/jsp/tuutiSend.jsp")
                    .forward(request, response);

        } catch (NamingException | SQLException e) {
            throw new ServletException(e);
        }
    }

    // 通知を送信
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    
        String receiverType = request.getParameter("receiverType");
        String receiverAccountId =
                request.getParameter("receiverAccountId");
        String message = request.getParameter("message");
       
        try {

            // 宛先種別を日本語に変換
            String receiverName = null;

            if ("company".equals(receiverType)) {
                receiverName = "企業";
            } else if ("user".equals(receiverType)) {
                receiverName = "求職者";
            } else if ("all".equals(receiverType)) {
                receiverName = "全体";
            }

            // 宛先種別の確認
            if (receiverName == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"宛先種別を選択してください。\"}");
                return;
            }

            // メッセージの確認
            if (message == null || message.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                    "{\"success\":false,\"message\":\"メッセージを入力してください。\"}");
                return;
            }

            // 宛先種別IDをDBから取得
            Integer receiverTypeId =
                    notificationDAO.findReceiverTypeId(receiverName);

            if (receiverTypeId == null) {
                throw new ServletException(
                        "宛先種別がDBに存在しません。");
            }

            // 通知情報をBeanに格納
            bean.Notification notification =
                    new bean.Notification();

            notification.setReceiver_type_id(receiverTypeId);
            notification.setMessage(message);

            // 全体通知の場合
            if ("all".equals(receiverType)) {

                notification.setReceiver_account_id(null);

            } else {

                // 個別通知の場合
                if (receiverAccountId == null
                        || receiverAccountId.trim().isEmpty()) {

                    response.setStatus(
                            HttpServletResponse.SC_BAD_REQUEST);

                    response.getWriter().write(
                        "{\"success\":false,\"message\":\"送信先を選択してください。\"}");
                    return;
                }

                notification.setReceiver_account_id(
                        Long.parseLong(receiverAccountId));
            }

            // DBに登録
            notificationDAO.insert(notification);

            // 成功結果を返す
            response.getWriter().write(
                "{\"success\":true,\"message\":\"通知を送信しました。\"}");

        } catch (NumberFormatException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"送信先の指定が正しくありません。\"}");

        } catch (NamingException | SQLException e) {

            e.printStackTrace();

            throw new ServletException(e);
        }
    }
}
