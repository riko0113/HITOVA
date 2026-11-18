<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .jobseeker-sidebar {
        width: 220px;
        min-width: 220px;
        background-color: #ffffff;
        border-right: 1px solid #cbd5e1;
        box-sizing: border-box;
        flex-shrink: 0;
    }
    .jobseeker-sidebar-list {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .jobseeker-sidebar-item {
        border-bottom: 1px solid #e2e8f0;
    }
    .jobseeker-sidebar-link {
        display: block;
        padding: 16px 24px;
        color: #002B49;
        text-decoration: none;
        font-size: 14px;
        font-weight: bold;
        transition: background-color 0.15s;
    }
    .jobseeker-sidebar-link:hover {
        background-color: #f1f5f9;
    }
</style>

<aside class="jobseeker-sidebar">
    <ul class="jobseeker-sidebar-list">
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" class="jobseeker-sidebar-link">求人を探す</a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/jobsearchServlet/FavoriteJobList.action" class="jobseeker-sidebar-link">保存求人一覧</a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/achievement/achievementList.jsp" class="jobseeker-sidebar-link">実績・チャレンジ認定</a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/applicant/applicationList.jsp" class="jobseeker-sidebar-link">応募管理</a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/chat/chatList.jsp" class="jobseeker-sidebar-link">メッセージ</a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/profile/profileView.jsp" class="jobseeker-sidebar-link">プロフィール確認</a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/portfolio/Portfolio.action" class="jobseeker-sidebar-link">作品管理</a>
        </li>
    </ul>
</aside>