
package Notification;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 通知送信画面を表示するAction
public class NotificationDisplayAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        // セッションを取得
        HttpSession session = request.getSession(false);

        // ログインしていない場合
        if (session == null) {
            request.setAttribute("errorMessage", "ログインしてください。");
            return "/Notification/jsp/tuuti.jsp";
        }

        // アカウントIDを取得
        Long accountId = (Long) session.getAttribute("accountId");

        if (accountId == null) {
            request.setAttribute("errorMessage", "ログインしてください。");
            return "/Notification/jsp/tuuti.jsp";
        }

        // アカウントの種類を取得
        String accountType = (String) session.getAttribute("accountType");

        // 管理者・組合管理者以外を拒否
        if (!"ADMIN".equals(accountType)
                && !"UNION_ADMIN".equals(accountType)) {

            request.setAttribute(
                "errorMessage",
                "通知を送信する権限がありません。"
            );

            return "/Notification/jsp/tuuti.jsp";
        }
        // 通知送信画面を表示
        return "/Notification/jsp/tuutiSend.jsp";
    }
    
}
