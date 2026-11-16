<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .admin-sidebar {
        width: 220px;
        min-width: 220px;
        background-color: #ffffff;
        border-right: 1px solid #002B49;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
    }
    .admin-sidebar-title {
        font-size: 16px;
        font-weight: bold;
        padding: 20px;
        border-bottom: 1px solid #e2e8f0;
        color: #333;
    }
    .admin-sidebar-menu {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .admin-sidebar-item {
        border-bottom: 1px solid #e2e8f0;
    }
    .admin-sidebar-link {
        display: block;
        padding: 16px 20px;
        color: #002B49;
        text-decoration: none;
        font-size: 14px;
        font-weight: bold;
        transition: background-color 0.15s, color 0.15s;
    }
    .admin-sidebar-link:hover {
        background-color: #f0f4f8;
        color: #00182b;
    }
</style>

<aside class="admin-sidebar">
    <div class="admin-sidebar-title">組合管理者メニュー</div>
    <ul class="admin-sidebar-menu">
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/companyList.jsp" class="admin-sidebar-link" name="company-list">企業一覧</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/companyApproval.jsp" class="admin-sidebar-link" name="company-registration-approval">企業登録・承認</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/performanceList.jsp" class="admin-sidebar-link" name="performance-list">実績一覧</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/userAddition.jsp" class="admin-sidebar-link" name="user-addition">ユーザー追加</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/companyNotificationSend.jsp" class="admin-sidebar-link" name="company-notification-send">企業通知送信</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/submissionDisplay.jsp" class="admin-sidebar-link" name="submission-display">提出物表示</a>
        </li>
    </ul>
</aside>