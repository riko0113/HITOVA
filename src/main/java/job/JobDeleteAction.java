package job;


import DAO.JobDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import tool.Action;

public class JobDeleteAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
        	//企業アカウントIDを取り出す
        	HttpSession session = request.getSession();
        	
        	Long userId = (Long) session.getAttribute(AuthWeb.company_key);
            
            //未ログインの場合のチェック
            if (userId == null) {
                // ログイン画面へリダイレクトする処理
            	response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                return null;
            }
            
            //求人IDを取り出す
        	String jobIdStr = request.getParameter("job_id");
        	Long jobId = null;

        	if (jobIdStr != null && !jobIdStr.isEmpty()) {
        	    try {
        	        jobId = Long.valueOf(jobIdStr);
        	    } catch (NumberFormatException e) {
        	        // パラメータが数値に変換できない場合のエラー処理
        	        e.printStackTrace();
        	        request.setAttribute("errorMessage", "無効なIDが指定されました。");
        	        return "//error.jsp"; 
        	    }
        	}
            
            // 求人DAOのインスタンス生成
            JobDAO dao = new JobDAO();
            
            // 対象のデータを削除
            dao.delete(jobId, userId);
            
            return ""; 
            
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
