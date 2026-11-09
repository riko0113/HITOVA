<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    body {
        margin: 0;
        padding: 0;
        font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
        background-color: #f5f5f5;
        color: #333;
    }
    .hitova-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        background-color: #ffffff;
        border-bottom: 1px solid #000000;
        padding: 0 40px;
        height: 60px;
        min-width: 900px;
        box-sizing: border-box;
    }
    .hitova-logo a {
        font-size: 32px;
        font-weight: 900;
        color: #55a359;
        text-decoration: none;
        letter-spacing: 1px;
        font-family: sans-serif;
        white-space: nowrap;
    }
    .hitova-nav {
        display: flex;
        align-items: center;
        gap: 25px;
        white-space: nowrap;
    }
    .hitova-nav-link {
        color: #2b6cb0;
        text-decoration: none;
        font-size: 15px;
        white-space: nowrap;
    }
    .hitova-nav-link:hover {
        text-decoration: underline;
    }
    .icon-badge-container {
        position: relative;
        display: inline-flex;
        align-items: center;
        cursor: pointer;
    }
    .notification-badge {
        position: absolute;
        top: -4px;
        right: -6px;
        background-color: #e53e3e;
        color: #ffffff;
        font-size: 11px;
        font-weight: bold;
        border-radius: 50%;
        width: 16px;
        height: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .user-icon {
        width: 24px;
        height: 24px;
        fill: #0077b6;
        display: block;
    }
    .bell-icon {
        width: 24px;
        height: 24px;
        fill: #718096;
        display: block;
    }
</style>

<header class="hitova-header">
    <div class="hitova-logo">
        <a href="${pageContext.request.contextPath}/toppage/candidateTop.jsp">HITOVA</a>
    </div>

    <nav class="hitova-nav">
        <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp" class="hitova-nav-link">求人検索</a>
        <a href="#" class="hitova-nav-link">履歴管理</a>
        <a href="#" class="hitova-nav-link">チャット</a>
        <a href="#" class="hitova-nav-link">お問い合わせ</a>

        <div class="icon-badge-container">
            <svg class="bell-icon" viewBox="0 0 24 24">
                <path d="M12 22c1.1 0 2-.9 2-2h-4c0 1.1.9 2 2 2zm6-6v-5c0-3.07-1.63-5.64-4.5-6.32V4c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v.68C7.64 5.36 6 7.92 6 11v5l-2 2v1h16v-1l-2-2z"/>
            </svg>
            <span class="notification-badge">1</span>
        </div>

        <div class="icon-badge-container">
            <svg class="user-icon" viewBox="0 0 24 24">
                <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
            </svg>
        </div>

        <a href="${pageContext.request.contextPath}/login/login.jsp" class="hitova-nav-link">ログイン</a>
    </nav>
</header>