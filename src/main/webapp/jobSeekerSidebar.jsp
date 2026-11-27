<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .jobseeker-sidebar {
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
    .jobseeker-sidebar * {
        box-sizing: border-box !important;
        line-height: normal !important;
    }
    .jobseeker-sidebar-list {
        list-style: none !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .jobseeker-sidebar-item {
        border-bottom: 1px solid #e0e0e0 !important;
        margin: 0 !important;
        padding: 0 !important;
    }
    .jobseeker-sidebar-link {
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
    .jobseeker-sidebar-link:hover {
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
            <a href="${pageContext.request.contextPath}/portfolio/Portfolio.action" class="jobseeker-sidebar-link">
                <span>作品管理</span>
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
    </ul>
</aside>