<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (request.getAttribute("detailLoaded") == null) {
        response.sendRedirect(request.getContextPath() + "/companyApproval/CompanyApprovalList.action");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>企業登録申請詳細 - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
            background-color: #ffffff;
            color: #222222;
        }
        .main-layout {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 48px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .detail-container {
            max-width: 760px;
            margin: 0 auto;
        }
        .top-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 12px;
            margin-bottom: 24px;
            border-bottom: 1px solid #333333;
        }
        .page-title {
            font-size: 18px;
            font-weight: bold;
            margin: 0;
        }
        .btn-back {
            padding: 6px 14px;
            border: 1px solid #888888;
            background-color: #ffffff;
            color: #222222;
            text-decoration: none;
            font-size: 13px;
        }
        .btn-back:hover {
            background-color: #f5f5f5;
        }
        .info-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
            margin-bottom: 28px;
        }
        .info-table th, .info-table td {
            border: 1px solid #cccccc;
            padding: 13px 16px;
            text-align: left;
            vertical-align: top;
        }
        .info-table th {
            width: 190px;
            background-color: #f5f5f5;
            font-weight: bold;
        }
        .bottom-buttons {
            display: flex;
            justify-content: center;
            gap: 16px;
        }
        .btn-primary {
            padding: 10px 28px;
            background-color: #333333;
            color: #ffffff;
            text-decoration: none;
            font-size: 14px;
            font-weight: bold;
        }
        .btn-primary:hover {
            background-color: #111111;
        }
    </style>
</head>
<body>

    <%@ include file="/unionAdminHeader.jsp" %>

    <div class="main-layout">
        <%@ include file="/unionAdminSidebar.jsp" %>

        <main class="main-content">
            <div class="detail-container">

                <div class="top-actions">
                    <h2 class="page-title">企業登録申請詳細</h2>
                    <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action" class="btn-back">一覧へ戻る</a>
                </div>

                <table class="info-table">
                    <tr>
                        <th>企業アカウントID</th>
                        <td>${company.companyAccountId}</td>
                    </tr>
                    <tr>
                        <th>登録区分</th>
                        <td><c:out value="${company.entityType}" /></td>
                    </tr>
                    <tr>
                        <th>企業名・屋号</th>
                        <td><c:out value="${company.companyName}" /></td>
                    </tr>
                    <tr>
                        <th>法人番号</th>
                        <td><c:out value="${company.corporateNumber}" /></td>
                    </tr>
                    <tr>
                        <th>担当者名</th>
                        <td><c:out value="${company.personName}" /></td>
                    </tr>
                    <tr>
                        <th>部署・役職</th>
                        <td>
                            <c:out value="${not empty company.department ? company.department : '-'}" />
                            <c:if test="${not empty company.position}"> / <c:out value="${company.position}" /></c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>メールアドレス</th>
                        <td><a href="mailto:${company.email}" style="color: #0044cc;"><c:out value="${company.email}" /></a></td>
                    </tr>
                    <tr>
                        <th>電話番号</th>
                        <td><c:out value="${not empty company.phone ? company.phone : '-'}" /></td>
                    </tr>
                    <tr>
                        <th>所在地</th>
                        <td><c:out value="${not empty company.address ? company.address : '-'}" /></td>
                    </tr>
                    <tr>
                        <th>Webサイト・確認URL</th>
                        <td>
                            <c:choose>
                                <c:when test="${not empty company.websiteUrl and company.websiteUrl != '-'}">
                                    <a href="<c:out value='${company.websiteUrl}' />" target="_blank" rel="noopener noreferrer" style="color: #0044cc;">
                                        <c:out value="${company.websiteUrl}" />
                                    </a>
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <th>設立年・従業員数</th>
                        <td>
                            設立：<c:out value="${company.establishedYear != null ? company.establishedYear : '-'}" />年 ／
                            従業員数：<c:out value="${company.numberOfEmployee != null ? company.numberOfEmployee : '-'}" />名
                        </td>
                    </tr>
                    <tr>
                        <th>事業内容</th>
                        <td style="white-space: pre-wrap; line-height: 1.6;"><c:out value="${not empty company.businessDescription ? company.businessDescription : '未入力'}" /></td>
                    </tr>
                    <tr>
                        <th>現在の審査状態</th>
                        <td>
                            <c:choose>
                                <c:when test="${company.status == 'APPROVED'}">承認済み</c:when>
                                <c:when test="${company.status == 'REJECTED'}">拒否</c:when>
                                <c:otherwise>審査中</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>

                <div class="bottom-buttons">
                    <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalDetail.action?id=${company.companyAccountId}&mode=certify" class="btn-primary">
                        企業認証へ
                    </a>
                </div>

            </div>
        </main>
    </div>

</body>
</html>