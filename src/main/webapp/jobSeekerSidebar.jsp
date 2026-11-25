<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .jobseeker-sidebar {
        width: 220px;
        min-width: 220px;
        background-color: #ffffff;
        border-right: 1px solid #cccccc;
        box-sizing: border-box;
        flex-shrink: 0;
    }
    .jobseeker-sidebar-list {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .jobseeker-sidebar-item {
        border-bottom: 1px solid #e0e0e0;
    }
    .jobseeker-sidebar-link {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 16px 24px;
        color: #000000;
        text-decoration: none;
        font-size: 14px;
        font-weight: bold;
    }
    .jobseeker-sidebar-link:hover {
        background-color: #f5f5f5;
    }
    .sidebar-arrow {
        color: #0044cc;
        font-weight: bold;
    }
</style>

<aside class="jobseeker-sidebar">
    <ul class="jobseeker-sidebar-list">
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" class="jobseeker-sidebar-link">
                <span>求人を探す</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/jobsearchServlet/FavoriteJobList.action" class="jobseeker-sidebar-link">
                <span>保存求人一覧</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/achievementServlet/ChallengeList.action" class="jobseeker-sidebar-link">
                <span>実績・チャレンジ認定</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/jobsearchServlet/ApplicationHistoryList.action" class="jobseeker-sidebar-link">
                <span>応募管理</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/chat/ChatList.action" class="jobseeker-sidebar-link">
                <span>メッセージ</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/profile/ProfileDisplay.action" class="jobseeker-sidebar-link">
                <span>プロフィール確認</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/login/logout.jsp" class="jobseeker-sidebar-link">
                <span>ログアウト・退会</span>
                <span class="sidebar-arrow">&gt;</span>
            </a>
        </li>
        <li class="jobseeker-sidebar-item">
            <a href="${pageContext.request.contextPath}/portfolio/Portfolio.action" class="jobseeker-sidebar-link">作品管理</a>
        </li>
    </ul>
</aside>