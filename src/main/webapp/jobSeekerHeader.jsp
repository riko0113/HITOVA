<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .hitova-header {
        width: 100%;
        height: 64px;
        background-color: #ffffff;
        border-bottom: 1px solid #cbd5e1;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 36px;
        box-sizing: border-box;
    }
    .hitova-logo-link {
        display: inline-flex;
        align-items: center;
        text-decoration: none;
    }
    .hitova-logo-img {
        height: 40px;
        width: auto;
        vertical-align: middle;
    }
    .hitova-header-nav {
        display: flex;
        align-items: center;
        gap: 26px;
    }
    .hitova-header-link {
        color: #0044cc;
        text-decoration: none;
        font-size: 14px;
        font-weight: 500;
    }
    .hitova-header-link:hover {
        text-decoration: underline;
    }
    .header-icon-btn {
        position: relative;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        color: #475569;
        text-decoration: none;
        width: 28px;
        height: 28px;
    }
    .header-icon-btn:hover {
        color: #002B49;
    }
    .notif-badge {
        position: absolute;
        top: -4px;
        right: -5px;
        background-color: #ef4444;
        color: #ffffff;
        font-size: 10px;
        font-weight: bold;
        width: 16px;
        height: 16px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
    }
</style>

<header class="hitova-header">
    <div class="header-logo">
        <a href="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" class="hitova-logo-link">
            <img src="${pageContext.request.contextPath}/images/Image.png" alt="HITOVA" class="hitova-logo-img">
        </a>
    </div>

    <nav class="hitova-header-nav">
        <a href="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" class="hitova-header-link">求人検索</a>
        <a href="${pageContext.request.contextPath}/applicant/applicationList.jsp" class="hitova-header-link">履歴管理</a>
        <a href="${pageContext.request.contextPath}/chat/chatList.jsp" class="hitova-header-link">チャット</a>
        <a href="${pageContext.request.contextPath}/contact/contact.jsp" class="hitova-header-link">お問い合わせ</a>

        <!-- 通知アイコン -->
        <a href="${pageContext.request.contextPath}/notification/notificationList.jsp" class="header-icon-btn" title="通知">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 22c1.1 0 2-.9 2-2h-4c0 1.1.9 2 2 2zm6-6v-5c0-3.07-1.63-5.64-4.5-6.32V4c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v.68C7.64 5.36 6 7.92 6 11v5l-2 2v1h16v-1l-2-2z"/>
            </svg>
            <span class="notif-badge">1</span>
        </a>

        <!-- プロフィールアイコン -->
        <a href="${pageContext.request.contextPath}/profile/profileView.jsp" class="header-icon-btn" title="プロフィール確認" style="color:#0066cc;">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
            </svg>
        </a>

        <a href="${pageContext.request.contextPath}/login_logout/login.jsp" class="hitova-header-link">ログイン</a>
    </nav>
</header>