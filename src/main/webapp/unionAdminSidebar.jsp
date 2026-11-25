<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .union-admin-sidebar {
        width: 220px;
        min-width: 220px;
        background-color: #ffffff;
        border-right: 1px solid #cccccc;
        box-sizing: border-box;
        flex-shrink: 0;
    }
    .union-admin-sidebar-list {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .union-admin-sidebar-item {
        border-bottom: 1px solid #e0e0e0;
    }
    .union-admin-sidebar-link {
        display: block;
        padding: 16px 24px;
        color: #222222;
        text-decoration: none;
        font-size: 14px;
        font-weight: bold;
    }
    .union-admin-sidebar-link:hover {
        background-color: #f5f5f5;
    }
</style>

<aside class="union-admin-sidebar">
    <ul class="union-admin-sidebar-list">
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdminServlet/CompanyList.action" class="union-admin-sidebar-link">企業一覧</a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/companyApproval.jsp" class="union-admin-sidebar-link">企業登録・承認</a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/performanceList.jsp" class="union-admin-sidebar-link">実績一覧</a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/unionAdminCreate.jsp" class="union-admin-sidebar-link">ユーザー追加</a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/companyNotificationSend.jsp" class="union-admin-sidebar-link">企業通知送信</a>
        </li>
        <li class="union-admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/unionAdmin/submissionDisplay.jsp" class="union-admin-sidebar-link">提出物表示</a>
        </li>
    </ul>
</aside>