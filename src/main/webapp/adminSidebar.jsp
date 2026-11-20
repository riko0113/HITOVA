<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .admin-sidebar {
        width: 220px;
        min-width: 220px;
        background-color: #ffffff;
        border-right: 1px solid #cccccc;
        box-sizing: border-box;
        flex-shrink: 0;
    }
    .admin-sidebar-list {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .admin-sidebar-item {
        border-bottom: 1px solid #e0e0e0;
    }
    .admin-sidebar-link {
        display: block;
        padding: 16px 24px;
        color: #222222;
        text-decoration: none;
        font-size: 14px;
        font-weight: bold;
    }
    .admin-sidebar-link:hover {
        background-color: #f5f5f5;
    }
</style>

<aside class="admin-sidebar">
    <ul class="admin-sidebar-list">
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/userList.jsp" class="admin-sidebar-link">ユーザー管理</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/userReviews.jsp" class="admin-sidebar-link">ユーザー口コミ管理</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/identityVerification.jsp" class="admin-sidebar-link">本人確認審査</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/companyManagement.jsp" class="admin-sidebar-link">企業管理</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/resultRegister.jsp" class="admin-sidebar-link">実績管理</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/taskSubmissionList.jsp" class="admin-sidebar-link">課題提出一覧</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/reportList.jsp" class="admin-sidebar-link">通報一覧</a>
        </li>
    </ul>
</aside>