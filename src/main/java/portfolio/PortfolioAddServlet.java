
package portfolio;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/portfolio/portfolioAdd")
@jakarta.servlet.annotation.MultipartConfig

public class PortfolioAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // JSPから入力内容を受け取る
        String workName = request.getParameter("workName");
        String description = request.getParameter("description");
     // JSPからファイルを受け取る
        Part workFile = request.getPart("workFile");
     // 作品名のチェック
        if (workName == null || workName.trim().isEmpty()) {
            request.setAttribute("workNameError", "作品名を入力してください。");
        }
        // 説明文のチェック
        if (description == null || description.trim().isEmpty()) {
            request.setAttribute("descriptionError", "説明文を入力してください。");
        }
     // ファイル容量のチェック
        if (workFile != null && workFile.getSize() > 100 * 1024 * 1024) {
            request.setAttribute("fileError", "ファイルは100MB以内で選択してください。");
        }
        // エラーがある場合
        if (request.getAttribute("workNameError") != null
                || request.getAttribute("descriptionError") != null) {

            request.getRequestDispatcher(
                "/portfolio/jsp/portfolioAdd.jsp"
            ).forward(request, response);

            return;
        }

        // ここにデータベースへの登録処理を書く

        // 登録完了画面へ移動
        response.sendRedirect(
            request.getContextPath() + "/portfolio/jsp/portfoliodone.jsp"
        );
    }
}
