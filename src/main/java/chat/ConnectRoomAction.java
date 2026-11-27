package chat;

import java.util.ArrayList;
import java.util.List;

import DAO.ChatDAO;
import bean.ChatMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import tool.Action;

public class ConnectRoomAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {

            // セッションからユーザーIDを取得して未ログインチェック
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute(AuthWeb.company_key);

            if (userId == null) {
                // ログイン画面へリダイレクトする処理
                response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                return null;
            }
            
            String candidateIdStr = request.getParameter("candidate_account_id");
        	Long candidateId = null;

        	if (candidateIdStr != null && !candidateIdStr.isEmpty()) {
        	    try {
        	    	candidateId = Long.valueOf(candidateId);
        	    } catch (NumberFormatException e) {
        	        // パラメータが数値に変換できない場合のエラー処理
        	        e.printStackTrace();
        	        request.setAttribute("errorMessage", "無効なIDが指定されました。");
        	        return "//error.jsp"; 
        	    }
        	}
            
            // チャットDAOのインスタンス生成
            ChatDAO dao = new ChatDAO();
            
            Boolean flg = dao.existsRoom(candidateId, userId);
            
            if (flg == true) {
            	response.sendRedirect(request.getContextPath() + "/chat/company/ChatList.action");
            }else {
            	dao.insertRoom(candidateId, userId);
            	response.sendRedirect(request.getContextPath() + "/chat/company/ChatList.action");
            }
            

            return null;

        } catch (Exception e) {
            // サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();

            // ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "データの取得中にエラーが発生しました。しばらく経ってから再度お試しください。");

            // エラー用の専用画面、または元の安全な画面へフォワードする
            return "/error.jsp";
        }
    }
}
