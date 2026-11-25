<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .company-sidebar {
        width: 220px !important;
        min-width: 220px !important;
        background-color: #ffffff !important;
        border-right: 1px solid #cccccc !important;
        box-sizing: border-box !important;
        flex-shrink: 0 !important;
        margin: 0 !important;
        padding: 0 !important;
        line-height: normal !important;
        font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif !important;
    }
    .company-sidebar-list {
        list-style: none !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .company-sidebar-item {
        border-bottom: 1px solid #e0e0e0 !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .company-sidebar-link {
        display: flex !important;
        justify-content: space-between !important;
        align-items: center !important;
        padding: 16px 24px !important;
        margin: 0 !important;
        color: #000000 !important;
        text-decoration: none !important;
        font-size: 14px !important;
        font-weight: bold !important;
        line-height: 1.4 !important;
        font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif !important;
    }
    .company-sidebar-link:hover {
        background-color: #f5f5f5 !important;
        color: #000000 !important;
        text-decoration: none !important;
    }
    .company-sidebar .sidebar-arrow {
        color: #0044cc !important;
        font-weight: bold !important;
    }
</style>

<aside class="company-sidebar">
    <ul class="company-sidebar-list">
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/toppage/companyTop.jsp" class="company-sidebar-link">
                <span>トップページ</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/job/JobList.action" class="company-sidebar-link">
                <span>求人一覧・登録</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action?job_id=${3001}" class="company-sidebar-link">
                <span>応募者一覧</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/chat/ChatList.action" class="company-sidebar-link">
                <span>メッセージ</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/companyServlet/AccountInfo.action" class="company-sidebar-link">
                <span>アカウント情報</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/login/logout.jsp" class="company-sidebar-link">
                <span>ログアウト・退会</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
    </ul>
</aside>