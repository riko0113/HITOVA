package profile;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ProfileInputErrorAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String page = request.getParameter("page");

        if ("edit".equals(page)) {
            return "/profile/profileEdit.jsp";
        }

        return "/profile/profileRegister.jsp";
    }
}
