package applicant;

import java.util.List;

import DAO.ApplicationHistoryDAO;
import bean.ApplicationHistoryDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ApplicantDetailAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
        	
        	//応募履歴IDを取り出す
        	String applicationHistoryIdStr = request.getParameter("application_history_id");
        	Long applicationHistoryId = null;

        	if (applicationHistoryIdStr != null && !applicationHistoryIdStr.isEmpty()) {
        	    try {
        	        applicationHistoryId = Long.valueOf(applicationHistoryIdStr);
        	    } catch (NumberFormatException e) {
        	        // パラメータが数値に変換できない場合のエラー処理
        	        e.printStackTrace();
        	    }
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
        	    }
        	}
        	
            // 応募履歴DAOのインスタンス生成
            ApplicationHistoryDAO dao = new ApplicationHistoryDAO();
            
            // Listに応募者詳細の結果を返す
            List<ApplicationHistoryDetail> detailList = dao.searchDetail(applicationHistoryId);
            
            request.setAttribute("detailList", detailList);
            request.setAttribute("jobId", jobId);
            
            return "/applicant/applicant_detail.jsp"; 
            
        } catch (Exception e) {
            //サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();
            
            //ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "データの取得中にエラーが発生しました。しばらく経ってから再度お試しください。");
            
            //エラー用の専用画面、または元の安全な画面へフォワードする
            return ""; 
        }
    }
}
