package applicant;

import java.util.List;

import DAO.ApplicationHistoryDAO;
import bean.ApplicationHistory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ApplicantListAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {

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
        	        return "/applicant/error.jsp";
                }
            }
            
            // 応募履歴DAOのインスタンス生成
            ApplicationHistoryDAO dao = new ApplicationHistoryDAO();
            
            // Listに応募履歴一覧の結果を返す
            List<ApplicationHistory> historyList = dao.searchAll(jobId);
            
            request.setAttribute("historyList", historyList);
            
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
