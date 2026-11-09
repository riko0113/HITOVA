package qualificationServlet;

import DAO.QualificationDAO;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class QualificationUpdateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            JobSeekerAccount candidate = (JobSeekerAccount) session.getAttribute("candidate"); 

            Long candidateId = null;
            if (candidate != null) {
                candidateId = candidate.getCandidate_id();
            }

            // TODO: ログイン機能完成まではダミーIDを使用
            if (candidateId == null) {
                candidateId = 1L;
            }

            // 削除対象のID配列を取得
            String[] deleteIds = request.getParameterValues("delete_ids");

            if (deleteIds != null && deleteIds.length > 0) {
                QualificationDAO dao = new QualificationDAO();
                for (String idStr : deleteIds) {
                    if (idStr != null && !idStr.trim().isEmpty()) {
                        Long qualificationId = Long.parseLong(idStr.trim());
                        dao.delete(qualificationId, candidateId);
                    }
                }
            }

            // 削除完了後、一覧表示アクションへ遷移してデータを再取得
            return "/qualificationServlet/Qualification.action";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "資格情報の更新中にエラーが発生しました。");
            return "/qualification/qualificationRegisterError.jsp";
        }
    }
}