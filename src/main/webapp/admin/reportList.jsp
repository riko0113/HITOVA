<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%
    if (request.getAttribute("reportList") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/AdminReportList.action");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>通報一覧 - HITOVA 管理画面</title>
    <style>
        body {
            margin: 0;
            font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
            background-color: #ffffff;
            color: #111111;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 48px;
            box-sizing: border-box;
        }
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
            padding-bottom: 12px;
            border-bottom: 2px solid #111111;
        }
        .page-title {
            font-size: 22px;
            font-weight: bold;
            margin: 0;
        }
        .count-badge {
            font-size: 14px;
            color: #444444;
        }
        .alert-banner {
            padding: 12px 16px;
            margin-bottom: 20px;
            border: 1px solid #0044cc;
            background-color: #f0f5ff;
            color: #003399;
            font-size: 14px;
            border-radius: 4px;
        }
        .report-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 28px;
            font-size: 14px;
        }
        .report-table th,
        .report-table td {
            border: 1px solid #cccccc;
            padding: 12px 14px;
            text-align: left;
            vertical-align: middle;
        }
        .report-table th {
            background-color: #f4f5f7;
            font-weight: bold;
            color: #111111;
            white-space: nowrap;
        }
        .report-table tr:hover td {
            background-color: #fafafa;
        }
        .col-center {
            text-align: center !important;
            white-space: nowrap;
        }
        .btn-detail {
            display: inline-block;
            padding: 6px 16px;
            border: 1px solid #0044cc;
            border-radius: 4px;
            background-color: #ffffff;
            color: #0044cc;
            text-decoration: none;
            font-size: 13px;
            font-weight: bold;
        }
        .btn-detail:hover {
            background-color: #0044cc;
            color: #ffffff;
        }
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
            margin-top: 24px;
        }
        .page-link {
            display: inline-block;
            padding: 6px 12px;
            border: 1px solid #cccccc;
            color: #111111;
            text-decoration: none;
            font-size: 13px;
            border-radius: 4px;
        }
        .page-link.active {
            background-color: #0044cc;
            color: #ffffff;
            border-color: #0044cc;
            font-weight: bold;
        }
        .page-link:hover:not(.active) {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>

    <%@ include file="/adminHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/adminSidebar.jsp" %>

        <main class="main-content">
            <div class="page-header">
                <h2 class="page-title">ユーザー通報一覧</h2>
                <span class="count-badge">全 ${totalItems} 件</span>
            </div>

            <c:if test="${param.result == 'deleted'}">
                <div class="alert-banner">通報の対応を完了（一覧から削除）しました。</div>
            </c:if>

            <c:choose>
                <c:when test="${empty reportList}">
                    <p style="text-align: center; color: #555555; padding: 48px 0; border: 1px solid #dddddd;">
                        現在、未対応の通報はありません。
                    </p>
                </c:when>
                <c:otherwise>
                    <table class="report-table">
                        <thead>
                            <tr>
                                <th class="col-center">通報ID</th>
                                <th>通報日時</th>
                                <th>通報者</th>
                                <th>通報対象</th>
                                <th>通報内容</th>
                                <th class="col-center">証拠添付</th>
                                <th class="col-center">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${reportList}">
                                <tr>
                                    <td class="col-center">${item.report_id}</td>
                                    <td style="white-space: nowrap;">
                                        <c:if test="${not empty item.reported_at}">
                                            <fmt:formatDate value="${item.reported_at}" pattern="yyyy/MM/dd HH:mm" />
                                        </c:if>
                                    </td>
                                    <td><c:out value="${item.reporter_name}" /></td>
                                    <td style="font-weight: bold;"><c:out value="${item.reported_target_name}" /></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:length(item.content) > 35}">
                                                <c:out value="${fn:substring(item.content, 0, 35)}" />...
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${item.content}" />
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="col-center">
                                        <c:choose>
                                            <c:when test="${not empty item.for_storing_evidence && item.for_storing_evidence != 'なし'}">あり</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="col-center">
                                        <a href="${pageContext.request.contextPath}/admin/AdminReportDetail.action?report_id=${item.report_id}" class="btn-detail">詳細確認</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <c:if test="${totalPages > 1}">
                        <div class="pagination">
                            <c:forEach var="p" begin="1" end="${totalPages}">
                                <a href="${pageContext.request.contextPath}/admin/AdminReportList.action?page=${p}"
                                   class="page-link ${p == currentPage ? 'active' : ''}">${p}</a>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </main>
    </div>

</body>
</html>