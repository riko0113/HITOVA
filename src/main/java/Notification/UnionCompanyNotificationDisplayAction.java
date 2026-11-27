package Notification;

import java.util.List;

import DAO.NotificationDAO;
import bean.NotificationReceiver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class UnionCompanyNotificationDisplayAction extends Action {

    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Integer unionId = null;
        Object unionIdObj = session.getAttribute("union_id");
        if (unionIdObj instanceof Integer) {
            unionId = (Integer) unionIdObj;
        } else if (unionIdObj instanceof Long) {
            unionId = ((Long) unionIdObj).intValue();
        }

        NotificationDAO dao = new NotificationDAO();
        List<NotificationReceiver> companyList;

        if (unionId != null) {
            companyList = dao.findCompaniesByUnionId(unionId);
        } else {
            companyList = dao.findAllCompanies();
        }

        request.setAttribute("companyList", companyList);
        request.setAttribute("unionSendLoaded", true);

        return "/unionAdmin/companyNotificationSend.jsp";
    }
}