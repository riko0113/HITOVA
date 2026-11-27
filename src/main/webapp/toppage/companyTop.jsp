<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%
    if (request.getAttribute("topLoaded") == null) {
        response.sendRedirect(request.getContextPath() + "/companyServlet/CompanyTop.action");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>企業トップページ - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
            background-color: #f8f9fa;
            color: #222222;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 48px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .dashboard-container {
            max-width: 960px;
            margin: 0 auto;
        }
        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 14px;
            margin-bottom: 24px;
            border-bottom: 1px solid #333333;
        }
        .page-heading {
            font-size: 18px;
            font-weight: bold;
            margin: 0;
            color: #111111;
        }
        .btn-outline {
            display: inline-block;
            padding: 7px 16px;
            background-color: #ffffff;
            border: 1px solid #333333;
            color: #222222;
            text-decoration: none;
            font-size: 13px;
        }
        .btn-outline:hover {
            background-color: #f2f2f2;
        }

        /* 数値サマリー（青いバーなしのシンプル枠） */
        .kpi-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 16px;
            margin-bottom: 28px;
        }
        .kpi-card {
            display: block;
            border: 1px solid #cccccc;
            background-color: #ffffff;
            padding: 18px 20px;
            text-decoration: none;
            color: #222222;
        }
        .kpi-card:hover {
            background-color: #f7f7f7;
        }
        .kpi-label {
            font-size: 13px;
            color: #555555;
            margin-bottom: 8px;
        }
        .kpi-value-row {
            display: flex;
            align-items: baseline;
            justify-content: space-between;
        }
        .kpi-value {
            font-size: 24px;
            font-weight: bold;
            color: #111111;
        }
        .kpi-unit {
            font-size: 13px;
            font-weight: normal;
            margin-left: 4px;
        }
        .kpi-link-text {
            font-size: 12px;
            color: #0044cc;
        }

        /* 下段2カラム */
        .bottom-grid {
            display: grid;
            grid-template-columns: 1.2fr 1fr;
            gap: 24px;
            align-items: start;
        }
        .panel-box {
            border: 1px solid #cccccc;
            background-color: #ffffff;
            padding: 20px 22px;
        }
        .panel-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #333333;
            padding-bottom: 8px;
            margin-bottom: 12px;
        }
        .panel-title {
            font-size: 14px;
            font-weight: bold;
            margin: 0;
        }
        .panel-more {
            font-size: 12px;
            color: #0044cc;
            text-decoration: none;
        }
        .panel-more:hover {
            text-decoration: underline;
        }
        .notice-list {
            list-style: none;
            margin: 0;
            padding: 0;
        }
        .notice-item {
            padding: 9px 0;
            border-bottom: 1px dashed #dddddd;
            font-size: 13px;
            line-height: 1.5;
        }
        .notice-item:last-child {
            border-bottom: none;
            padding-bottom: 0;
        }
        .notice-date {
            display: block;
            font-size: 11px;
            color: #666666;
            margin-bottom: 2px;
        }
        .notice-empty {
            color: #666666;
            font-size: 13px;
            padding: 20px 0;
            text-align: center;
            margin: 0;
        }

        /* 右カラム */
        .right-stack {
            display: flex;
            flex-direction: column;
            gap: 16px;
        }
        .search-form {
            display: flex;
            gap: 8px;
        }
        .search-input {
            flex: 1;
            height: 36px;
            padding: 0 10px;
            border: 1px solid #999999;
            font-size: 13px;
            box-sizing: border-box;
        }
        .search-btn {
            height: 36px;
            padding: 0 18px;
            background-color: #333333;
            color: #ffffff;
            border: none;
            font-size: 13px;
            cursor: pointer;
        }
        .search-btn:hover {
            background-color: #111111;
        }
        .menu-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }
        .menu-card {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 56px;
            border: 1px solid #cccccc;
            background-color: #ffffff;
            text-decoration: none;
            color: #222222;
            font-size: 14px;
            font-weight: bold;
        }
        .menu-card:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>

    <%@ include file="/companyHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/companySidebar.jsp" %>

        <main class="main-content">
            <div class="dashboard-container">

                <div class="top-bar">
                    <h2 class="page-heading">企業トップ</h2>
                    <a href="${pageContext.request.contextPath}/job/JobList.action" class="btn-outline">求人一覧・登録</a>
                </div>

                <div class="kpi-grid">
                    <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action?job_id=3001" class="kpi-card">
                        <div class="kpi-label">応募者数</div>
                        <div class="kpi-value-row">
                            <span class="kpi-value">${applicantCount}<span class="kpi-unit">名</span></span>
                            <span class="kpi-link-text">一覧 &gt;</span>
                        </div>
                    </a>

                    <a href="${pageContext.request.contextPath}/job/JobList.action" class="kpi-card">
                        <div class="kpi-label">公開中の求人</div>
                        <div class="kpi-value-row">
                            <span class="kpi-value">${publishedJobCount}<span class="kpi-unit">件</span></span>
                            <span class="kpi-link-text">管理 &gt;</span>
                        </div>
                    </a>

                    <a href="${pageContext.request.contextPath}/job/JobList.action" class="kpi-card">
                        <div class="kpi-label">登録済み求人</div>
                        <div class="kpi-value-row">
                            <span class="kpi-value">${totalJobCount}<span class="kpi-unit">件</span></span>
                            <span class="kpi-link-text">管理 &gt;</span>
                        </div>
                    </a>
                </div>

                <div class="bottom-grid">

                    <section class="panel-box">
                        <div class="panel-header">
                            <h3 class="panel-title">通知履歴</h3>
                            <a href="${pageContext.request.contextPath}/Notification/CompanyNotification.action" class="panel-more">すべて見る &gt;</a>
                        </div>

                        <c:choose>
                            <c:when test="${empty noticeList}">
                                <p class="notice-empty">現在、新しいお知らせはありません。</p>
                            </c:when>
                            <c:otherwise>
                                <ul class="notice-list">
                                    <c:forEach var="notice" items="${noticeList}">
                                        <li class="notice-item">
                                            <span class="notice-date">
                                                <fmt:formatDate value="${notice.send_at}" pattern="yyyy/MM/dd HH:mm" />
                                            </span>
                                            <span><c:out value="${notice.message}" /></span>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </section>

                    <div class="right-stack">
                        <section class="panel-box">
                            <div class="panel-header">
                                <h3 class="panel-title">求職者検索</h3>
                            </div>
                            <form action="${pageContext.request.contextPath}/company/scoutSearch.action" method="post" class="search-form">
                                <input type="text" name="searchBar" class="search-input" placeholder="スキルやタグで求職者を検索">
                                <button type="submit" name="search" class="search-btn">検索</button>
                            </form>
                        </section>

                        <div class="menu-grid">
                            <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action?job_id=3001" class="menu-card">
                                応募者一覧
                            </a>
                            <a href="${pageContext.request.contextPath}/chat/ChatList.action" class="menu-card">
                                メッセージ
                            </a>
                            <a href="${pageContext.request.contextPath}/companyServlet/AccountInfo.action" class="menu-card">
                                アカウント情報
                            </a>
                            <a href="${pageContext.request.contextPath}/contact/contact.jsp" class="menu-card">
                                お問い合わせ
                            </a>
                        </div>
                    </div>

                </div>

            </div>
        </main>
    </div>

</body>
</html>