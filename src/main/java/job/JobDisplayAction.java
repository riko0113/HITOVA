package job;

import java.util.List;

import DAO.CompanyDAO;
import DAO.JobDAO;
import bean.CompanyAccount;
import bean.Job;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.AuthWeb;
import tool.S3PresignHelper;
import tool.Action;

public class JobDisplayAction extends Action {

    // FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute(AuthWeb.company_key);

            // 未ログインチェック
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login/login.jsp");
                return null;
            }

            // 企業情報をセッションから取得（未取得ならDBから取得してキャッシュ）
            CompanyAccount companyInfo = (CompanyAccount) session.getAttribute("companyInfo");
            if (companyInfo == null) {
                CompanyDAO companyDao = new CompanyDAO();
                companyInfo = companyDao.getCompanyAccountById(userId);
                session.setAttribute("companyInfo", companyInfo);
            }

            // 選択肢情報をセッションから取得（未取得ならDBから取得してキャッシュ）
            @SuppressWarnings("unchecked")
            List<Job> infoList = (List<Job>) session.getAttribute("infoList");
            JobDAO jobDao = new JobDAO();
            if (infoList == null) {
                infoList = jobDao.searchAll(userId);
                session.setAttribute("infoList", infoList);
            }

            // 編集対象の求人IDを取得
            String jobIdStr = request.getParameter("job_id");
            if (jobIdStr == null || jobIdStr.trim().isEmpty()) {
                request.setAttribute("errorMessage", "求人IDが指定されていません。");
                return "/error.jsp";
            }

            Long jobId = Long.parseLong(jobIdStr.trim());

            // 自社（userId）の求人データのみ1件取得
            Job job = jobDao.searchDetail(jobId, userId);
            if (job == null) {
                request.setAttribute("errorMessage", "指定された求人が見つからないか、表示する権限がありません。");
                return "/error.jsp";
            }

            // 登録済み画像の閲覧用署名付きURLを発行
            String presignedUrl = S3PresignHelper.createPresignedGetUrl(job.getImage_url());

            request.setAttribute("job", job);
            request.setAttribute("presignedUrl", presignedUrl);
            return "/jobregister/jobEdit.jsp";

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "不正な求人IDが指定されました。");
            return "/error.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "画面の表示中にエラーが発生しました。しばらく経ってから再度お試しください。");
            return "/error.jsp";
        }
    }
}