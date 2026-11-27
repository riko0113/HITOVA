<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .admin-sidebar {
        width: 220px !important;
        min-width: 220px !important;
        max-width: 220px !important;
        background-color: #ffffff !important;
        border-right: 1px solid #cccccc !important;
        box-sizing: border-box !important;
        flex-shrink: 0 !important;
        margin: 0 !important;
        padding: 0 !important;
        line-height: normal !important;
        font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif !important;
    }
    .admin-sidebar * {
        box-sizing: border-box !important;
        line-height: normal !important;
    }
    .admin-sidebar-list {
        list-style: none !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .admin-sidebar-item {
        border-bottom: 1px solid #e0e0e0 !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .admin-sidebar-link {
        display: flex !important;
        justify-content: space-between !important;
        align-items: center !important;
        padding: 16px 24px !important;
        margin: 0 !important;
        color: #000000 !important;
        text-decoration: none !important;
        font-size: 14px !important;
        font-weight: bold !important;
        background-color: transparent !important;
        font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif !important;
    }
    .admin-sidebar-link:hover {
        background-color: #f5f5f5 !important;
        color: #000000 !important;
        text-decoration: none !important;
    }
    .sidebar-arrow {
        color: #0044cc !important;
        font-weight: bold !important;
        font-size: 14px !important;
    }
</style>

<aside class="admin-sidebar">
    <ul class="admin-sidebar-list">
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/userList.jsp" class="admin-sidebar-link">
                <span>ユーザー管理</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/userReviews.jsp" class="admin-sidebar-link">
                <span>ユーザー口コミ管理</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/identityVerification.jsp" class="admin-sidebar-link">
                <span>本人確認審査</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action" class="admin-sidebar-link">
                <span>企業管理・認証</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/Notification/NotificationDisplay.action" class="admin-sidebar-link">
                <span>通知送信</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/resultRegister.jsp" class="admin-sidebar-link">
                <span>実績管理</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/taskSubmissionList.jsp" class="admin-sidebar-link">
                <span>課題提出一覧</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/reportList.jsp" class="admin-sidebar-link">
                <span>通報一覧</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/login/logout.jsp" class="admin-sidebar-link">
                <span>ログアウト・退会</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
    </ul>
</aside>