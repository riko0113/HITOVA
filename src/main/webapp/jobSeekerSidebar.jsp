<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .jobseeker-sidebar {
        width: 220px;
        min-width: 220px;
        background-color: #ffffff;
        border-right: 1px solid #002B49;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
    }
    .sidebar-menu-list {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .sidebar-menu-item {
        border-bottom: 1px solid #e2e8f0;
    }
    .sidebar-menu-link {
        display: block;
        padding: 16px 20px;
        color: #002B49;
        text-decoration: none;
        font-size: 14px;
        font-weight: bold;
        transition: background-color 0.15s, color 0.15s;
    }
    .sidebar-menu-link:hover {
        background-color: #f0f4f8;
        color: #00182b;
    }
    .sidebar-menu-link.active {
        background-color: #002B49;
        color: #ffffff;
    }
</style>

<aside class="jobseeker-sidebar">
    <ul class="sidebar-menu-list">

        <!-- 求人検索 -->
        <li class="sidebar-menu-item">
            <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp" class="sidebar-menu-link">
                求人を探す
            </a>
        </li>

        <!-- 実績・チャレンジ認定（追加項目） -->
        <li class="sidebar-menu-item">
            <a href="${pageContext.request.contextPath}/achievement/challengeList.jsp" class="sidebar-menu-link">
                実績・チャレンジ認定
            </a>
        </li>

        <!-- 応募履歴 -->
        <li class="sidebar-menu-item">
            <a href="${pageContext.request.contextPath}/applicant/applicant.jsp" class="sidebar-menu-link">
                応募管理
            </a>
        </li>

        <!-- メッセージ・チャット -->
        <li class="sidebar-menu-item">
            <a href="${pageContext.request.contextPath}/chat/chatList.jsp" class="sidebar-menu-link">
                メッセージ
            </a>
        </li>

        <!-- プロフィール確認・編集 -->
        <li class="sidebar-menu-item">
            <a href="${pageContext.request.contextPath}/profile/profileDisplay.jsp" class="sidebar-menu-link">
                プロフィール確認
            </a>
        </li>
    </ul>
</aside>