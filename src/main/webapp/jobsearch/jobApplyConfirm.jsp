<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/head.jsp" %>
<%@ include file="/jobSeekerHeader.jsp" %>

<style>
    .main-layout {
        display: flex;
        min-height: calc(100vh - 64px);
        background-color: #ffffff;
    }
    .main-content {
        flex: 1;
        padding: 50px 60px;
        background-color: #ffffff;
        box-sizing: border-box;
    }
    .confirm-wrapper {
        max-width: 780px;
        margin: 0 auto;
    }
    .company-heading {
        text-align: center;
        font-size: 20px;
        font-weight: bold;
        color: #111;
        margin: 0 0 36px 0;
    }
    .company-heading-label {
        margin-right: 18px;
    }
    .profile-section-label {
        font-size: 15px;
        color: #333;
        margin: 0 0 8px 6px;
    }
    .profile-box {
        border: 1.5px solid #002B49;
        padding: 20px 30px 26px 30px;
        background-color: #ffffff;
        box-sizing: border-box;
    }
    .profile-name {
        font-size: 16px;
        font-weight: bold;
        color: #222;
        margin: 0 0 14px 0;
    }
    .profile-body-row {
        display: flex;
        align-items: center;
        gap: 32px;
        padding-left: 14px;
    }
    .profile-avatar {
        width: 64px;
        height: 64px;
        color: #111;
        flex-shrink: 0;
    }
    .profile-text {
        font-size: 14px;
        line-height: 1.85;
        color: #333;
        white-space: pre-wrap;
    }
    .already-applied-note {
        text-align: center;
        color: #b45309;
        background-color: #fef3c7;
        border: 1px solid #f59e0b;
        padding: 10px 16px;
        border-radius: 6px;
        font-size: 14px;
        margin-top: 24px;
    }
    .submit-btn-area {
        margin-top: 34px;
        text-align: center;
    }
    .btn-send-round {
        display: inline-block;
        width: 155px;
        padding: 11px 0;
        border: 1.5px solid #002B49;
        border-radius: 10px;
        background-color: #ffffff;
        color: #222;
        font-size: 15px;
        font-weight: bold;
        cursor: pointer;
        text-decoration: none;
        transition: background-color 0.15s;
    }
    .btn-send-round:hover {
        background-color: #f0f4f8;
    }
</style>

<div class="main-layout">
    <%@ include file="/jobSeekerSidebar.jsp" %>

    <div class="main-content">
        <div class="confirm-wrapper">

            <h1 class="company-heading">
                <span class="company-heading-label">会社名</span>${job.company_name}
            </h1>

            <div class="profile-section-label">プロフィール</div>

            <div class="profile-box">
                <div class="profile-name">${profileData.fullName}</div>
                <div class="profile-body-row">
                    <svg class="profile-avatar" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                    <div class="profile-text">${profileData.profileSummary}</div>
                </div>
            </div>

            <c:choose>
                <c:when test="${alreadyApplied}">
                    <div class="already-applied-note">
                        この求人にはすでに応募済みです。
                    </div>
                    <div class="submit-btn-area">
                        <a href="${pageContext.request.contextPath}/jobsearchServlet/ApplicationHistoryList.action" class="btn-send-round">応募履歴を見る</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/jobsearchServlet/JobApplyExecute.action" method="post" class="submit-btn-area">
                        <input type="hidden" name="job_id" value="${job.job_id}">
                        <button type="submit" class="btn-send-round">送信</button>
                    </form>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>

<%@ include file="/footer.jsp" %>