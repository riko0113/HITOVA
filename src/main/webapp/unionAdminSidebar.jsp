<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .union-admin-sidebar {
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
    .union-admin-sidebar * {
        box-sizing: border-box !important;
        line-height: normal !important;
    }
    .union-admin-sidebar-list {
        list-style: none !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .union-admin-sidebar-item {
        border-bottom: 1px solid #e0e0e0 !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .union-admin-sidebar-link {
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
    .union-admin-sidebar-link:hover {
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

<aside class="union-admin-sidebar">
    <ul class="union-admin-sidebar-list">
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdminServlet/CompanyList.action" class="union-admin-sidebar-link">
                <span>企業一覧</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/companyApproval/companyApproval.jsp" class="union-admin-sidebar-link">
                <span>企業登録・承認</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/performanceList.jsp" class="union-admin-sidebar-link">
                <span>実績一覧</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/unionAdminCreate.jsp" class="union-admin-sidebar-link">
                <span>ユーザー追加</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/companyNotificationSend.jsp" class="union-admin-sidebar-link">
                <span>企業通知送信</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/submissionDisplay.jsp" class="union-admin-sidebar-link">
                <span>提出物表示</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/login/logout.jsp" class="union-admin-sidebar-link">
                <span>ログアウト・退会</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
    </ul>
</aside>