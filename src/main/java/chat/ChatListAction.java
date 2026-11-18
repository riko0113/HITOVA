package chat;

import java.util.List;

import DAO.ApplicationHistoryDAO;
import bean.ChatMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ChatListAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {

        	// セッションからユーザーIDを取得して未ログインチェック
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("candidate_id");
            
            if (userId == null) {
            	// ログイン画面へリダイレクトする処理
            	response.sendRedirect(request.getContextPath() + "");
                return "null"; 
            }
            
            // チャットDAOのインスタンス生成
            ApplicationHistoryDAO dao = new ApplicationHistoryDAO();
            
            // Listにチャット一覧の結果を返す
            List<ChatMessage> chatList = dao.searchAll();
            
            request.setAttribute("chatList", chatList);
            
            return "/applicant/applicant_list.jsp"; 
            
        } catch (Exception e) {
            //サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();
            
            //ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "データの取得中にエラーが発生しました。しばらく経ってから再度お試しください。");
            
            //エラー用の専用画面、または元の安全な画面へフォワードする
            return "/error.jsp"; 
        }
    }

}
