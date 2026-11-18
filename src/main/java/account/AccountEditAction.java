
package account;

import DAO.JobSeekerAccountDAO;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AccountEditAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // セッションを取得する
        HttpSession session = request.getSession(false);

        // ログイン状態を確認する
        if (session == null ||
                session.getAttribute("candidate_account_id") == null) {

            // 【要確認・変更】
            // ログイン機能のAction.javaを確認する。
            // ログインしていない場合の遷移先に変更する。
            return "/login.jsp";
        }

        // 求職者アカウントIDを取得する
        // 【要確認・変更】
        // ログイン機能のAction.javaを確認する。
        // session.setAttribute()で設定している属性名と一致させる。
        Long candidateId = Long.valueOf(
                session.getAttribute("candidate_account_id").toString()
        );

        // DAOを生成する
        JobSeekerAccountDAO dao = new JobSeekerAccountDAO();

        // 更新ボタンが押された場合
        if ("update".equals(request.getParameter("mode"))) {

            // フォームから入力値を取得する
            String nickName = request.getParameter("nick_name");
            String scoutValue = request.getParameter("scout");
            String industryValue = request.getParameter("desired_industry_id");
            String occupationValue = request.getParameter("desired_occupation_id");

            // 入力チェック
            if (nickName == null || nickName.trim().isEmpty()
                    || scoutValue == null
                    || industryValue == null || industryValue.isEmpty()
                    || occupationValue == null || occupationValue.isEmpty()) {

                request.setAttribute("errorMessage",
                        "すべての項目を入力してください。");

                // 入力エラーの場合、編集画面に戻る
                JobSeekerAccount account = dao.search(candidateId);
                request.setAttribute("account", account);

                return "/account/accountEdit.jsp";
            }

            // 入力値を変換する
            boolean scout;
            int desiredIndustryId;
            int desiredOccupationId;

            try {
                scout = Boolean.parseBoolean(scoutValue);
                desiredIndustryId = Integer.parseInt(industryValue);
                desiredOccupationId = Integer.parseInt(occupationValue);

                // true / false 以外の値を拒否する
                if (!"true".equals(scoutValue)
                        && !"false".equals(scoutValue)) {
                    throw new IllegalArgumentException();
                }

            } catch (IllegalArgumentException e) {

                request.setAttribute("errorMessage",
                        "入力内容が正しくありません。");

                JobSeekerAccount account = dao.search(candidateId);
                request.setAttribute("account", account);

                return "/account/accountEdit.jsp";
            }

            // アカウント情報を更新する
            int result = dao.updateAccount(
                    candidateId,
                    nickName.trim(),
                    scout,
                    desiredIndustryId,
                    desiredOccupationId
            );

            // 更新できた場合
            if (result > 0) {

                // 更新後のアカウント情報を取得する
                JobSeekerAccount account = dao.search(candidateId);

                request.setAttribute("account", account);

                // アカウント情報表示画面へ遷移する
                return "/account/accountInformation.jsp";

            } else {

                request.setAttribute("errorMessage",
                        "アカウント情報を更新できませんでした。");

                JobSeekerAccount account = dao.search(candidateId);
                request.setAttribute("account", account);

                return "/account/accountEdit.jsp";
            }
        }

        // 編集画面を開く場合
        // アカウント情報を取得する
        JobSeekerAccount account = dao.search(candidateId);

        // アカウント情報が存在しない場合
        if (account == null) {

            request.setAttribute("errorMessage",
                    "アカウント情報を取得できませんでした。");

            return "/account/accountInformation.jsp";
        }

        // 編集画面にアカウント情報を渡す
        request.setAttribute("account", account);

        return "/account/accountEdit.jsp";
    }
}
