<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (request.getAttribute("listLoaded") == null) {
        response.sendRedirect(request.getContextPath() + "/companyApproval/CompanyApprovalList.action");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>企業登録申請一覧 - HITOVA</title>
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
        .page-container {
            max-width: 980px;
            margin: 0 auto;
        }
        .page-header {
            padding-bottom: 12px;
            margin-bottom: 20px;
            border-bottom: 1px solid #333333;
        }
        .page-title {
            font-size: 18px;
            font-weight: bold;
            margin: 0;
        }
        .toolbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 16px;
        }
        .filter-tabs {
            display: flex;
            gap: 6px;
        }
        .filter-tab {
            padding: 5px 14px;
            border: 1px solid #cccccc;
            background-color: #ffffff;
            color: #333333;
            text-decoration: none;
            font-size: 13px;
        }
        .filter-tab:hover {
            background-color: #f5f5f5;
        }
        .filter-tab.active {
            background-color: #f0f0f0;
            color: #111111;
            border-color: #666666;
            font-weight: bold;
        }
        .count-text {
            font-size: 13px;
            color: #555555;
            margin: 0;
        }
        .data-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 13px;
            margin-bottom: 24px;
        }
        .data-table th, .data-table td {
            border: 1px solid #cccccc;
            padding: 11px 14px;
            text-align: left;
            white-space: nowrap;
        }
        .data-table th {
            background-color: #f5f5f5;
            font-weight: bold;
            color: #222222;
        }
        .data-table td.col-wrap {
            white-space: normal;
        }
        .data-table tr:hover td {
            background-color: #fafafa;
        }
        .status-pending {
            color: #b45309;
            font-weight: bold;
        }
        .status-approved {
            color: #222222;
        }
        .status-rejected {
            color: #777777;
        }
        .link-detail {
            color: #0044cc;
            text-decoration: none;
            font-weight: bold;
        }
        .link-detail:hover {
            text-decoration: underline;
        }
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 6px;
        }
        .page-link {
            padding: 5px 11px;
            border: 1px solid #cccccc;
            text-decoration: none;
            color: #222222;
            font-size: 13px;
        }
        .page-link.current {
            background-color: #f0f0f0;
            border-color: #666666;
            font-weight: bold;
        }
    </style>
</head>
<body>

    <%@ include file="/unionAdminHeader.jsp" %>

    <div class="main-layout">
        <%@ include file="/unionAdminSidebar.jsp" %>

        <main class="main-content">
            <div class="page-container">

                <div class="page-header">
                    <h2 class="page-title">企業登録申請一覧</h2>
                </div>

                <div class="toolbar">
                    <div class="filter-tabs">
                        <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action"
                           class="filter-tab ${empty statusFilter ? 'active' : ''}">すべて</a>
                        <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action?status=PENDING"
                           class="filter-tab ${statusFilter == 'PENDING' ? 'active' : ''}">審査中</a>
                        <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action?status=APPROVED"
                           class="filter-tab ${statusFilter == 'APPROVED' ? 'active' : ''}">承認済み</a>
                        <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action?status=REJECTED"
                           class="filter-tab ${statusFilter == 'REJECTED' ? 'active' : ''}">拒否</a>
                    </div>
                    <p class="count-text">該当件数：${totalCount} 件</p>
                </div>

                <table class="data-table">
                    <thead>
                        <tr>
                            <th style="width: 65px;">ID</th>
                            <th style="width: 95px;">区分</th>
                            <th>企業名・屋号</th>
                            <th style="width: 110px;">担当者</th>
                            <th style="width: 210px;">メールアドレス</th>
                            <th style="width: 85px;">状態</th>
                            <th style="width: 70px; text-align: center;">詳細</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty applicationList}">
                                <tr>
                                    <td colspan="7" style="text-align: center; padding: 32px; color: #666666;">
                                        該当する企業登録申請はありません。
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="item" items="${applicationList}">
                                    <tr>
                                        <td>${item.companyAccountId}</td>
                                        <td><c:out value="${item.entityType}" /></td>
                                        <td class="col-wrap"><c:out value="${item.companyName}" /></td>
                                        <td><c:out value="${item.personName}" /></td>
                                        <td><c:out value="${item.email}" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${item.status == 'APPROVED'}">
                                                    <span class="status-approved">承認済み</span>
                                                </c:when>
                                                <c:when test="${item.status == 'REJECTED'}">
                                                    <span class="status-rejected">拒否</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="status-pending">審査中</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="text-align: center;">
                                            <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalDetail.action?id=${item.companyAccountId}" class="link-detail">詳細</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>

                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action?page=${currentPage - 1}&status=${statusFilter}" class="page-link">&lt;</a>
                        </c:if>
                        <c:forEach var="p" begin="1" end="${totalPages}">
                            <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action?page=${p}&status=${statusFilter}"
                               class="page-link ${p == currentPage ? 'current' : ''}">${p}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action?page=${currentPage + 1}&status=${statusFilter}" class="page-link">&gt;</a>
                        </c:if>
                    </div>
                </c:if>

            </div>
        </main>
    </div>

</body>
</html>