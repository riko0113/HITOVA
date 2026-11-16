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
        .admin-sidebar-title {
        font-size: 16px;
        font-weight: bold;
        padding: 20px;
        border-bottom: 1px solid #e2e8f0;
        color: #333;
    }
    .admin-sidebar-link:hover {
        background-color: #f0f4f8;
        color: #00182b;
    }
    /* サブメニュー（課題提出一覧）用 */
    .admin-sidebar-sublink {
        display: block;
        padding: 12px 20px 12px 40px; 
        color: #4a5568;
        text-decoration: none;
        font-size: 13px;
        background-color: #f8fafc;
        transition: background-color 0.15s, color 0.15s;
    }
    .admin-sidebar-sublink:hover {
        background-color: #f0f4f8;
        color: #00182b;
    }
</style>

<aside class="admin-sidebar">
	<div class="admin-sidebar-title">管理者メニュー</div>
    <ul class="admin-sidebar-menu">
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/userList.jsp" class="admin-sidebar-link">ユーザー管理</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/userReviews.jsp" class="admin-sidebar-link">ユーザー口コミ</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/identityVerification.jsp" class="admin-sidebar-link">本人確認</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/companyManagement.jsp" class="admin-sidebar-link">企業管理</a>
        </li>
        
        <!-- 実績管理とそのサブメニュー -->
        <li class="admin-sidebar-item" style="border-bottom: none;">
            <a href="${pageContext.request.contextPath}/admin/taskManagement.jsp" class="admin-sidebar-link">実績管理</a>
        </li>
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/taskSubmissionList.jsp" class="admin-sidebar-link">課題提出一覧</a>
        </li>
        
        <li class="admin-sidebar-item">
            <a href="${pageContext.request.contextPath}/admin/reportList.jsp" class="admin-sidebar-link">通報・お問い合わせ</a>
        </li>
    </ul>
</aside>