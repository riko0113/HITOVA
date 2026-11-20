package unionAdminServlet;

import DAO.UnionAdminDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class UnionAdminCreateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password_confirm");

        request.setAttribute("name", name);
        request.setAttribute("email", email);

        // バリデーション
        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()
                || passwordConfirm == null || passwordConfirm.isEmpty()) {
            request.setAttribute("errorMessage", "すべての項目を入力してください。");
            return "/unionAdmin/unionAdminCreate.jsp";
        }

        name = name.trim();
        email = email.trim();

        if (name.length() > 20) {
            request.setAttribute("errorMessage", "氏名は20文字以内で入力してください。");
            return "/unionAdmin/unionAdminCreate.jsp";
        }

        if (!password.equals(passwordConfirm)) {
            request.setAttribute("errorMessage", "パスワードとパスワード確認が一致しません。");
            return "/unionAdmin/unionAdminCreate.jsp";
        }

        UnionAdminDAO dao = new UnionAdminDAO();

        if (dao.isNickNameExists(name)) {
            request.setAttribute("errorMessage", "この氏名はすでに登録されています。");
            return "/unionAdmin/unionAdminCreate.jsp";
        }

        if (dao.isEmailExists(email)) {
            request.setAttribute("errorMessage", "このメールアドレスはすでに登録されています。");
            return "/unionAdmin/unionAdminCreate.jsp";
        }

        // 所属組合IDの取得
        HttpSession session = request.getSession();
        Integer unionId = (Integer) session.getAttribute("union_id");
        if (unionId == null) {
            unionId = dao.getDefaultUnionId();
        }

        dao.insertUnionAdmin(name, email, unionId);

        //完了メッセージを表示
        request.removeAttribute("name");
        request.removeAttribute("email");
        request.setAttribute("successMessage", "組合管理者アカウントを新規作成しました。");

        return "/unionAdmin/unionAdminCreate.jsp";
    }
}