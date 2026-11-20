package achievementServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ChallengeListAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/achievement/challengeList.jsp";
    }
}