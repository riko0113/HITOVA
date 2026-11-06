package qualificationServlet;

import DAO.QualificationDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class QualificationDeleteExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // JSPから送られてくる、削除対象の資格IDの配列を取得（name="delete_ids"）
        String[] deleteIds = request.getParameterValues("delete_ids");

        // 削除対象が1つ以上ある場合のみ処理を実行
        if (deleteIds != null && deleteIds.length > 0) {
            QualificationDAO dao = new QualificationDAO();
            
            // ループを回して一つずつ削除（DAOのdeleteメソッドを呼び出す）
            for (String idStr : deleteIds) {
                Long id = Long.parseLong(idStr);
                dao.delete(id);
            }
        }

        // 削除処理が終わったら、もう一度一覧画面（QualificationAction）へリダイレクト（またはフォワード）する
        // ※ここでは既存のQualification.actionを呼び出して最新の一覧を再取得させます
        return "/qualificationServlet/Qualification.action";
    }
}