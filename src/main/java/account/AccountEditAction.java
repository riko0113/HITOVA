
package account;

import DAO.JobSeekerAccountDAO;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AccountEditAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);

        // ログイン確認
        if (session == null || session.getAttribute("candidate_account_id") == null) {
            return "/login.jsp";
        }

        Long candidateId;
        try {
            candidateId = Long.valueOf(session.getAttribute("candidate_account_id").toString());
        } catch (NumberFormatException e) {
            return "/login.jsp";
        }

        JobSeekerAccountDAO dao = new JobSeekerAccountDAO();

        // 更新処理
        if ("update".equals(request.getParameter("mode"))) {
            String nickName = request.getParameter("nick_name");
            String scoutValue = request.getParameter("scout");
            String industryValue = request.getParameter("desired_industry_id");
            String occupationValue = request.getParameter("desired_occupation_id");

            if (nickName == null || nickName.trim().isEmpty() || scoutValue == null
                    || industryValue == null || industryValue.isEmpty()
                    || occupationValue == null || occupationValue.isEmpty()) {
                request.setAttribute("errorMessage", "すべての項目を入力してください。");
                request.setAttribute("account", dao.search(candidateId));
                return "/account/accountEdit.jsp";
            }

            boolean scout;
            int desiredIndustryId;
            int desiredOccupationId;

            try {
                if (!"true".equals(scoutValue) && !"false".equals(scoutValue)) {
                    throw new IllegalArgumentException();
                }
                scout = Boolean.parseBoolean(scoutValue);
                desiredIndustryId = Integer.parseInt(industryValue);
                desiredOccupationId = Integer.parseInt(occupationValue);

                if (desiredIndustryId <= 0 || desiredOccupationId <= 0) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                request.setAttribute("errorMessage", "入力内容が正しくありません。");
                request.setAttribute("account", dao.search(candidateId));
                return "/account/accountEdit.jsp";
            }

            int result = dao.updateAccount(candidateId, nickName.trim(), scout,
                    desiredIndustryId, desiredOccupationId);

            if (result > 0) {
                request.setAttribute("account", dao.search(candidateId));
                return "/account/accountInformation.jsp";
            }

            request.setAttribute("errorMessage", "アカウント情報を更新できませんでした。");
            request.setAttribute("account", dao.search(candidateId));
            return "/account/accountEdit.jsp";
        }

        // 編集画面表示
        JobSeekerAccount account = dao.search(candidateId);

        if (account == null) {
            request.setAttribute("errorMessage", "アカウント情報を取得できませんでした。");
            return "/account/accountInformation.jsp";
        }

        request.setAttribute("account", account);
        return "/account/accountEdit.jsp";
    }
}
