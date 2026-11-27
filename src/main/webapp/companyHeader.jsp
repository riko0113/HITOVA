<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="DAO.NotificationDAO" %>
<%@ page import="bean.CompanyAccount" %>
<%
    int companyUnreadNotifCount = 0;
    try {
        Long cid = null;
        if (session.getAttribute("company_account_id") instanceof Long) {
            cid = (Long) session.getAttribute("company_account_id");
        } else if (session.getAttribute("companyAccount") instanceof CompanyAccount) {
            cid = ((CompanyAccount) session.getAttribute("companyAccount")).getCompany_account_id();
        }
        if (cid == null) {
            cid = 2001L;
        }

        Long lastReadCompanyIdObj = (Long) session.getAttribute("lastReadCompanyNotifId");
        long lastReadCompanyId = (lastReadCompanyIdObj != null) ? lastReadCompanyIdObj : 0L;

        NotificationDAO nDao = new NotificationDAO();
        companyUnreadNotifCount = nDao.countUnreadCompanyAfterId(cid, lastReadCompanyId);
    } catch (Exception e) {
        companyUnreadNotifCount = 0;
    }
    request.setAttribute("companyUnreadNotifCount", companyUnreadNotifCount);
%>
<style>
    html, body {
        margin: 0 !important;
        padding: 0 !important;
    }
    .hitova-header {
        width: 100% !important;
        height: 64px !important;
        min-height: 64px !important;
        max-height: 64px !important;
        margin: 0 !important;
        padding: 0 36px !important;
        background-color: #ffffff !important;
        border-bottom: 1px solid #cccccc !important;
        display: flex !important;
        justify-content: space-between !important;
        align-items: center !important;
        box-sizing: border-box !important;
        line-height: normal !important;
        font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif !important;
    }
    .hitova-header * {
        box-sizing: border-box !important;
        line-height: normal !important;
    }
    .hitova-header .header-logo {
        margin: 0 !important;
        padding: 0 !important;
        display: flex !important;
        align-items: center !important;
    }
    .hitova-logo-link {
        display: inline-flex !important;
        align-items: center !important;
        text-decoration: none !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .hitova-logo-img {
        height: 40px !important;
        width: auto !important;
        display: block !important;
        margin: 0 !important;
        padding: 0 !important;
        vertical-align: middle !important;
    }
    .hitova-header-nav {
        display: flex !important;
        align-items: center !important;
        gap: 26px !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .hitova-header-link {
        color: #0044cc !important;
        text-decoration: none !important;
        font-size: 14px !important;
        font-weight: normal !important;
        margin: 0 !important;
        padding: 0 !important;
        font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif !important;
    }
    .hitova-header-link:hover {
        text-decoration: underline !important;
    }
    .header-icon-btn {
        position: relative !important;
        display: inline-flex !important;
        align-items: center !important;
        justify-content: center !important;
        color: #333333 !important;
        text-decoration: none !important;
        width: 28px !important;
        height: 28px !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .header-icon-btn:hover {
        color: #000000 !important;
    }
    .notif-badge {
        position: absolute !important;
        top: -4px !important;
        right: -5px !important;
        background-color: #ef4444 !important;
        color: #ffffff !important;
        font-size: 10px !important;
        font-weight: bold !important;
        min-width: 16px !important;
        height: 16px !important;
        padding: 0 3px !important;
        border-radius: 50% !important;
        display: flex !important;
        align-items: center !important;
        justify-content: center !important;
    }
</style>

<header class="hitova-header">
    <div class="header-logo">
        <a href="${pageContext.request.contextPath}/toppage/companyTop.jsp" class="hitova-logo-link">
            <img src="${pageContext.request.contextPath}/images/Image.png" alt="HITOVA" class="hitova-logo-img">
        </a>
    </div>

    <nav class="hitova-header-nav">
        <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action?job_id=${3001}" class="hitova-header-link">応募者一覧</a>
        <a href="${pageContext.request.contextPath}/chat/ChatList.action" class="hitova-header-link">チャット</a>
        <a href="${pageContext.request.contextPath}/contact/contact.jsp" class="hitova-header-link">お問い合わせ</a>

        <!-- 通知アイコン（未読がある時だけ件数バッジ表示） -->
        <a href="${pageContext.request.contextPath}/Notification/CompanyNotification.action" class="header-icon-btn" title="通知">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 22c1.1 0 2-.9 2-2h-4c0 1.1.9 2 2 2zm6-6v-5c0-3.07-1.63-5.64-4.5-6.32V4c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v.68C7.64 5.36 6 7.92 6 11v5l-2 2v1h16v-1l-2-2z"/>
            </svg>
            <c:if test="${companyUnreadNotifCount > 0}">
                <span class="notif-badge">${companyUnreadNotifCount}</span>
            </c:if>
        </a>

        <!-- アカウントアイコン -->
        <a href="${pageContext.request.contextPath}/companyServlet/AccountInfo.action" class="header-icon-btn" title="アカウント情報" style="color:#0044cc !important;">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
            </svg>
        </a>

        <a href="${pageContext.request.contextPath}/login/login.jsp" class="hitova-header-link">ログイン</a>
    </nav>
</header>