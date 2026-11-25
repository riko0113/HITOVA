<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${reviews == null}">
    <c:redirect url="/admin/AdminReviewList.action" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザー口コミ申請一覧 - HITOVA</title>
    <style>
        body {
            margin: 0;
            font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
            background-color: #ffffff;
            color: #000000;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 40px;
            box-sizing: border-box;
        }
        .content-box {
            max-width: 900px;
            margin: 0 auto;
        }
        .page-title {
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            margin: 0 0 24px 0;
        }
        .count-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 14px;
            font-size: 15px;
        }
        .count-number {
            font-weight: bold;
        }
        .review-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
            background-color: #ffffff;
        }
        .review-table th {
            background-color: #f4f6f8;
            color: #000000;
            font-weight: bold;
            text-align: left;
            padding: 12px 16px;
            border-top: 1.5px solid #333333;
            border-bottom: 1.5px solid #333333;
        }
        .review-table td {
            padding: 12px 16px;
            border-bottom: 1px solid #dddddd;
            color: #000000;
        }
        .review-table tbody tr:hover {
            background-color: #f9fafb;
        }
        .btn-check {
            display: inline-block;
            padding: 5px 18px;
            border: 1px solid #0044cc;
            border-radius: 4px;
            background-color: #ffffff;
            color: #0044cc;
            font-size: 13px;
            text-decoration: none;
        }
        .btn-check:hover {
            background-color: #0044cc;
            color: #ffffff;
        }
        .pagination {
            margin-top: 28px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
        }
        .page-link {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 32px;
            height: 32px;
            padding: 0 8px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            background-color: #ffffff;
            color: #000000;
            text-decoration: none;
            font-size: 14px;
        }
        .page-link:hover {
            background-color: #f4f6f8;
        }
        .page-link.active {
            border-color: #0044cc;
            background-color: #0044cc;
            color: #ffffff;
            font-weight: bold;
        }
        .page-link.disabled {
            color: #aaaaaa;
            pointer-events: none;
            background-color: #fafafa;
        }
        .bottom-back {
            margin-top: 32px;
            text-align: center;
        }
        .btn-top-back {
            display: inline-block;
            padding: 9px 28px;
            border: 1px solid #888888;
            border-radius: 4px;
            background-color: #ffffff;
            color: #000000;
            font-size: 14px;
            text-decoration: none;
        }
        .btn-top-back:hover {
            background-color: #f4f6f8;
        }
    </style>
</head>
<body>

    <%@ include file="/adminHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/adminSidebar.jsp" %>

        <main class="main-content">
            <div class="content-box">
                <h2 class="page-title">ユーザー口コミ申請一覧</h2>

                <div class="count-row">
                    <div>申請件数：<span class="count-number">${totalCount}</span> 件</div>
                </div>

                <table class="review-table">
                    <thead>
                        <tr>
                            <th style="width: 60px;">No</th>
                            <th style="width: 180px;">投稿者</th>
                            <th>対象ユーザー</th>
                            <th style="width: 140px;">申請日</th>
                            <th style="width: 110px; text-align: center;">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty reviews}">
                                <tr>
                                    <td colspan="5" style="text-align: center; padding: 32px; color: #555555;">
                                        審査対象の口コミはありません。
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="r" items="${reviews}" varStatus="st">
                                    <tr>
                                        <td>${startNo + st.index}</td>
                                        <td><c:out value="${r.poster_name}" /></td>
                                        <td><c:out value="${r.target_name}" /></td>
                                        <td>${r.posted_at}</td>
                                        <td style="text-align: center;">
                                            <a href="${pageContext.request.contextPath}/admin/AdminReviewDetail.action?id=${r.job_review_id}" class="btn-check">確認</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>

                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <a href="${pageContext.request.contextPath}/admin/AdminReviewList.action?page=${currentPage - 1}"
                           class="page-link ${currentPage <= 1 ? 'disabled' : ''}">&lt;</a>

                        <c:forEach var="p" begin="1" end="${totalPages}">
                            <a href="${pageContext.request.contextPath}/admin/AdminReviewList.action?page=${p}"
                               class="page-link ${p == currentPage ? 'active' : ''}">${p}</a>
                        </c:forEach>

                        <a href="${pageContext.request.contextPath}/admin/AdminReviewList.action?page=${currentPage + 1}"
                           class="page-link ${currentPage >= totalPages ? 'disabled' : ''}">&gt;</a>
                    </div>
                </c:if>

                <div class="bottom-back">
                    <a href="${pageContext.request.contextPath}/toppage/adminTop.jsp" class="btn-top-back">トップページへ戻る</a>
                </div>
            </div>
        </main>
    </div>

</body>
</html>