package account;

import DAO.JobSeekerAccountDAO;
import bean.JobSeekerAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AccountInformationAction extends Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//Sessionを取得する
		HttpSession session = request.getSession(false);
		
		//Login状態を確認する
		
        // 【要確認・変更】
        // ログイン機能のAction.javaを確認する。
        // ログイン機能で設定しているログイン画面の遷移先に合わせて変更する。
		if (session == null || session.getAttribute("candidate_account_id") == null) {
			return "/login.jsp";
		}
		//求職者ID取得する
		
        // 【要確認・変更】
        // ログイン機能のAction.javaを確認する。
        // session.setAttribute() で設定している属性名を確認し、
        // "candidate_account_id" と異なる場合は変更する。
		Long candidate = long.valueOf(session.getAttribute("candidate_account_id").toString());
		
		//DAOを生成する
		JobSeekerAccountDAO dao = new JobSeekerAccountDAO();
		
		//アカウント情報を取得する
		JobSeekerAccount account = dao.search(candidateId);
		
		//アカウント情報が存在しない場合
		if(account == null) {
			request.setAttribute("errorMessage","アカウント情報を取得できませんでした");
			
			return "/account/accountInforamtion.jsp";
		}
		//JSPにアカウント情報を渡す
		request.setAttribute("account", account);
		
		return "/account/accontInfomation.jsp";
	}

}
