<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (request.getAttribute("company") == null) {
        response.sendRedirect(request.getContextPath() + "/companyServlet/AccountEdit.action");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>アカウント情報編集 - HITOVA</title>
    <style>
        body { margin: 0; padding: 0; font-family: sans-serif; background-color: #f8f9fa; color: #333; }
        .layout-wrapper { display: flex; min-height: calc(100vh - 60px); min-width: 1000px; }
        .main-content-area { flex: 1; padding: 40px 60px; background-color: #ffffff; box-sizing: border-box; }
        .detail-container { max-width: 820px; }
        .account-header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px; }
        .section-title-account { font-size: 18px; font-weight: normal; margin: 0; }
        .section-block { margin-bottom: 35px; }
        .section-title-box { font-size: 16px; font-weight: bold; margin-bottom: 15px; }
        .error-box { padding: 10px 14px; margin-bottom: 20px; border: 1px solid #cc0000; background-color: #fff2f2; color: #cc0000; font-size: 13px; }
        .edit-table { width: 100%; border-collapse: collapse; font-size: 14px; }
        .edit-table th { width: 190px; text-align: left; padding: 10px 10px 10px 18px; font-weight: normal; vertical-align: top; }
        .edit-table td { padding: 8px 10px; vertical-align: top; }
        .account-table th { padding-left: 0; width: 190px; }
        .req-mark { color: #cc0000; font-size: 12px; margin-left: 4px; }
        .form-input, .form-select, .form-textarea {
            width: 100%;
            max-width: 460px;
            padding: 7px 10px;
            border: 1px solid #888;
            font-size: 14px;
            font-family: inherit;
            box-sizing: border-box;
        }
        .form-input-short {
            width: 160px;
            padding: 7px 10px;
            border: 1px solid #888;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-textarea {
            min-height: 110px;
            resize: vertical;
            line-height: 1.6;
        }
        .readonly-note { font-size: 12px; color: #666; margin-top: 4px; }
        .btn-group-center { display: flex; justify-content: center; gap: 20px; margin-top: 36px; }
        .btn-outline { padding: 8px 28px; background-color: #ffffff; border: 1px solid #333; color: #333; text-decoration: none; font-size: 14px; cursor: pointer; }
        .btn-outline:hover { background-color: #f0f4f8; }
        .btn-primary { padding: 8px 32px; background-color: #0044cc; border: 1px solid #0044cc; color: #ffffff; font-size: 14px; font-weight: bold; cursor: pointer; }
        .btn-primary:hover { background-color: #003399; }
    </style>
</head>
<body>

    <%@ include file="/companyHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/companySidebar.jsp" %>

        <main class="main-content-area">
            <div class="detail-container">

                <div class="account-header-row">
                    <h2 class="section-title-account">アカウント情報編集</h2>
                </div>

                <c:if test="${not empty error}">
                    <div class="error-box"><c:out value="${error}" /></div>
                </c:if>

                <form action="${pageContext.request.contextPath}/companyServlet/AccountEdit.action" method="post">

                    <div class="section-block">
                        <table class="edit-table account-table">
                            <tr>
                                <th>メールアドレス</th>
                                <td>
                                    <c:out value="${company.email}" />
                                    <div class="readonly-note">※「メールアドレス変更」ボタンから変更してください</div>
                                </td>
                            </tr>
                            <tr>
                                <th>担当者氏名<span class="req-mark">*</span></th>
                                <td>
                                    <input type="text" name="person_name" class="form-input" maxlength="40"
                                           value="<c:out value='${company.person_name}' />" required>
                                </td>
                            </tr>
                            <tr>
                                <th>担当者氏名（カナ）<span class="req-mark">*</span></th>
                                <td>
                                    <input type="text" name="person_name_kana" class="form-input" maxlength="40"
                                           value="<c:out value='${company.person_name_kana}' />" required>
                                </td>
                            </tr>
                            <tr>
                                <th>部署名</th>
                                <td>
                                    <input type="text" name="department" class="form-input" maxlength="20"
                                           value="<c:out value='${company.department}' />">
                                </td>
                            </tr>
                            <tr>
                                <th>役職</th>
                                <td>
                                    <input type="text" name="position" class="form-input" maxlength="20"
                                           value="<c:out value='${company.position}' />">
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="section-block">
                        <div class="section-title-box">■ 企業基本情報</div>
                        <table class="edit-table">
                            <c:choose>
                                <c:when test="${company.isCorporation}">
                                    <tr>
                                        <th>会社名<span class="req-mark">*</span></th>
                                        <td>
                                            <input type="text" name="official_company_name" class="form-input" maxlength="100"
                                                   value="<c:out value='${company.official_company_name}' />" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>法人番号</th>
                                        <td><c:out value="${company.corporate_number}" /></td>
                                    </tr>
                                    <tr>
                                        <th>電話番号<span class="req-mark">*</span></th>
                                        <td>
                                            <input type="text" name="main_phone_number" class="form-input" maxlength="20"
                                                   value="<c:out value='${company.main_phone_number}' />" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>URL</th>
                                        <td>
                                            <input type="url" name="company_website_url" class="form-input"
                                                   value="<c:out value='${company.company_website_url}' />">
                                        </td>
                                    </tr>
                                </c:when>
                                <c:when test="${company.isSoleProprietor}">
                                    <tr>
                                        <th>屋号<span class="req-mark">*</span></th>
                                        <td>
                                            <input type="text" name="trade_name" class="form-input" maxlength="100"
                                                   value="<c:out value='${company.trade_name}' />" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>代表者名<span class="req-mark">*</span></th>
                                        <td>
                                            <input type="text" name="company_representative_name" class="form-input" maxlength="100"
                                                   value="<c:out value='${company.company_representative_name}' />" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>所在地<span class="req-mark">*</span></th>
                                        <td>
                                            <input type="text" name="business_office_address" class="form-input" maxlength="255"
                                                   value="<c:out value='${company.business_office_address}' />" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>電話番号<span class="req-mark">*</span></th>
                                        <td>
                                            <input type="text" name="business_office_phone_number" class="form-input" maxlength="20"
                                                   value="<c:out value='${company.business_office_phone_number}' />" required>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>店舗確認URL</th>
                                        <td>
                                            <input type="url" name="store_verification_url" class="form-input"
                                                   value="<c:out value='${company.store_verification_url}' />">
                                        </td>
                                    </tr>
                                </c:when>
                            </c:choose>
                        </table>
                    </div>

                    <div class="section-block">
                        <div class="section-title-box">■ 事業内容</div>
                        <table class="edit-table">
                            <tr>
                                <th>業種<span class="req-mark">*</span></th>
                                <td>
                                    <select name="industry_id" class="form-select" required>
                                        <c:forEach var="ind" items="${industryList}">
                                            <option value="${ind.industry_id}" ${ind.industry_id == company.industry_id ? 'selected' : ''}>
                                                <c:out value="${ind.industry_name}" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>従業員数</th>
                                <td>
                                    <input type="number" name="number_of_employee" class="form-input-short" min="1"
                                           value="${company.number_of_employee}"> 名
                                </td>
                            </tr>
                            <tr>
                                <th>設立年</th>
                                <td>
                                    <input type="number" name="established_year" class="form-input-short" min="1800" max="2030"
                                           value="${company.established_year}"> 年
                                </td>
                            </tr>
                            <tr>
                                <th>事業内容<span class="req-mark">*</span></th>
                                <td>
                                    <textarea name="business_description" class="form-textarea" required><c:out value="${company.business_description}" /></textarea>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="btn-group-center">
                        <a href="${pageContext.request.contextPath}/companyServlet/AccountInfo.action" class="btn-outline">キャンセル</a>
                        <button type="submit" class="btn-primary">保存する</button>
                    </div>

                </form>

            </div>
        </main>
    </div>

</body>
</html>