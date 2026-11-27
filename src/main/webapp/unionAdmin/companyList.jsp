<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:if test="${companyList == null}">
    <jsp:forward page="/unionAdminServlet/CompanyList.action" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>企業一覧 - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
            background-color: #f8f9fa;
            color: #333;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 60px);
            min-width: 1000px;
        }
        .main-content-area {
            flex: 1;
            padding: 40px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .page-title {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin-bottom: 30px;
        }
        .search-area {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
        }
        .search-label {
            font-size: 15px;
            margin-right: 15px;
            color: #333;
        }
        .search-input-wrapper {
            display: flex;
            align-items: center;
            border: 1px solid #ccc;
            border-radius: 4px;
            padding: 4px 8px;
            background-color: #fff;
            width: 300px;
        }
        .search-input {
            border: none;
            outline: none;
            width: 100%;
            font-size: 14px;
        }
        .search-icon-btn {
            background: none;
            border: none;
            cursor: pointer;
            color: #888;
            font-size: 16px;
        }
        .user-count {
            font-size: 14px;
            color: #555;
            margin-bottom: 10px;
            text-align: right;
        }
        .data-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 13px;
            margin-bottom: 30px;
        }
        .data-table th, .data-table td {
            border: 1px solid #e2e8f0;
            padding: 10px;
            text-align: left;
            vertical-align: middle;
        }
        .data-table th {
            background-color: #135e75;
            color: #ffffff;
            font-weight: normal;
            white-space: nowrap;
        }
        .data-table tr:nth-child(even) {
            background-color: #f1f5f9;
        }
        .data-table tr:nth-child(odd) {
            background-color: #e2e8f0;
        }
        .btn-detail {
            display: inline-block;
            padding: 4px 12px;
            background-color: #ffffff;
            border: 1px solid #ccc;
            color: #333;
            text-decoration: none;
            border-radius: 4px;
            font-size: 12px;
            cursor: pointer;
        }
        .btn-detail:hover {
            background-color: #f8f9fa;
        }
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
        }
        .page-link {
            display: inline-block;
            padding: 6px 12px;
            border: 1px solid #ccc;
            background-color: #fff;
            color: #333;
            text-decoration: none;
            font-size: 13px;
            border-radius: 4px;
        }
        .page-current {
            display: inline-block;
            padding: 6px 12px;
            background-color: #135e75;
            border: 1px solid #135e75;
            color: #fff;
            font-size: 13px;
            border-radius: 4px;
        }
    </style>
</head>
<body>

    <%@ include file="/unionAdminHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/unionAdminSidebar.jsp" %>

        <main class="main-content-area">
            <h1 class="page-title" name="user_list">企業一覧</h1>

            <!-- 企業名検索 -->
            <div class="search-area">
                <span class="search-label">企業名検索</span>
                <form action="${pageContext.request.contextPath}/unionAdminServlet/CompanySearch.action" method="post" class="search-input-wrapper">
                    <input type="text" name="company_name" value="${searchKeyword}" class="search-input">
                </form>
            </div>

            <!-- 件数表示 -->
            <div class="user-count" name="user_count">登録企業：${fn:length(companyList)} 件</div>

            <!-- データテーブル -->
            <table class="data-table">
                <thead>
                    <tr>
                        <th name="number">No</th>
                        <th name="company_name">企業・事業者名</th>
                        <th name="company_id">企業ID</th>
                        <th name="business_type">事業形態</th>
                        <th name="contact_name">担当者名</th>
                        <th name="email">メールアドレス</th>
                        <th name="registered_at">登録日</th>
                        <th name="verification_status">審査状態</th>
                        <th name="published_job_count">公開求人数</th>
                        <th name="status">アカウント状態</th>
                        <th name="detail">詳細</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty companyList}">
                            <tr>
                                <td colspan="11" style="text-align: center; background-color: #fff; padding: 20px;">該当する企業が見つかりません。</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="c" items="${companyList}" varStatus="st">
                                <tr>
                                    <td>${st.count}</td>
                                    <td>${c.company_name}</td>
                                    <td>${c.company_account_id}</td>
                                    <td>${c.business_type}</td>
                                    <td>${c.person_name}</td>
                                    <td>${c.email}</td>
                                    <td>${c.registered_at}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${c.review_status == null}">承認中</c:when>
                                            <c:when test="${c.review_status == true}">承認</c:when>
                                            <c:otherwise>否認</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${c.published_job_count}</td>
                                    <td>${c.account_status_name}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/unionAdminServlet/CompanyDetail.action?id=${c.company_account_id}" class="btn-detail" name="detail">詳細</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <!-- ページネーション -->
            <div class="pagination">
                <a href="#" class="page-link" name="previous">前ページ</a>
                <span class="page-current" name="page">1</span>
                <a href="#" class="page-link" name="next">次ページ</a>
            </div>
        </main>
    </div>

</body>
</html>