package companyServlet;

import DAO.CompanyAccountEditDAO;
import DAO.CompanyAccountEditDAO.CompanyEditData;
import bean.CompanyAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AccountEditAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // AccountInfoActionと完全に同じロジックで企業アカウントIDを取得
        Long companyAccountId = null;
        Object sessionId = session.getAttribute("company_account_id");
        if (sessionId instanceof Long) {
            companyAccountId = (Long) sessionId;
        } else if (session.getAttribute("companyAccount") instanceof CompanyAccount) {
            companyAccountId = ((CompanyAccount) session.getAttribute("companyAccount")).getCompany_account_id();
        }

        // テスト用フォールバック（AccountInfoActionと統一）
        if (companyAccountId == null) {
            companyAccountId = 2001L;
        }

        CompanyAccountEditDAO dao = new CompanyAccountEditDAO();

        // POST送信時（変更を保存）
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            CompanyEditData current = dao.findById(companyAccountId);
            if (current == null) {
                response.sendRedirect(request.getContextPath() + "/companyServlet/AccountInfo.action");
                return null;
            }

            String personName = trimParam(request.getParameter("person_name"));
            String personNameKana = trimParam(request.getParameter("person_name_kana"));
            String department = trimParam(request.getParameter("department"));
            String position = trimParam(request.getParameter("position"));
            String businessDesc = trimParam(request.getParameter("business_description"));
            String industryIdStr = trimParam(request.getParameter("industry_id"));
            String empStr = trimParam(request.getParameter("number_of_employee"));
            String yearStr = trimParam(request.getParameter("established_year"));

            current.setPerson_name(personName);
            current.setPerson_name_kana(personNameKana);
            current.setDepartment(department.isEmpty() ? null : department);
            current.setPosition(position.isEmpty() ? null : position);
            current.setBusiness_description(businessDesc);

            if (!industryIdStr.isEmpty()) {
                current.setIndustry_id(Integer.parseInt(industryIdStr));
            }
            current.setNumber_of_employee(empStr.isEmpty() ? null : Integer.parseInt(empStr));
            current.setEstablished_year(yearStr.isEmpty() ? null : Integer.parseInt(yearStr));

            if (current.isIsCorporation()) {
                current.setOfficial_company_name(trimParam(request.getParameter("official_company_name")));
                current.setMain_phone_number(trimParam(request.getParameter("main_phone_number")));
                String web = trimParam(request.getParameter("company_website_url"));
                current.setCompany_website_url(web.isEmpty() ? null : web);
            } else if (current.isIsSoleProprietor()) {
                current.setTrade_name(trimParam(request.getParameter("trade_name")));
                current.setCompany_representative_name(trimParam(request.getParameter("company_representative_name")));
                current.setBusiness_office_address(trimParam(request.getParameter("business_office_address")));
                current.setBusiness_office_phone_number(trimParam(request.getParameter("business_office_phone_number")));
                String storeUrl = trimParam(request.getParameter("store_verification_url"));
                current.setStore_verification_url(storeUrl.isEmpty() ? null : storeUrl);
            }

            // 必須項目の入力チェック
            if (personName.isEmpty() || personNameKana.isEmpty() || businessDesc.isEmpty()) {
                request.setAttribute("error", "必須項目を入力してください。");
                request.setAttribute("company", current);
                request.setAttribute("industryList", dao.findAllIndustries());
                return "/company/accountEdit.jsp";
            }

            dao.update(current);

            response.sendRedirect(request.getContextPath() + "/companyServlet/AccountInfo.action?updated=true");
            return null;
        }

        // GET表示時（編集フォーム初期表示）
        CompanyEditData company = dao.findById(companyAccountId);
        if (company == null) {
            response.sendRedirect(request.getContextPath() + "/companyServlet/AccountInfo.action");
            return null;
        }

        request.setAttribute("company", company);
        request.setAttribute("industryList", dao.findAllIndustries());
        return "/company/accountEdit.jsp";
    }

    private String trimParam(String val) {
        return val == null ? "" : val.trim();
    }
}