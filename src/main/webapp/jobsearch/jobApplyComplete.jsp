<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        padding: 80px 60px;
        background-color: #ffffff;
        box-sizing: border-box;
    }
    .complete-wrapper {
        max-width: 720px;
        margin: 0 auto;
        text-align: center;
    }
    .complete-message {
        font-size: 20px;
        font-weight: bold;
        color: #111;
        margin: 0 0 110px 0;
    }
    .btn-top-round {
        display: inline-block;
        width: 240px;
        padding: 13px 0;
        border: 1.5px solid #002B49;
        border-radius: 10px;
        background-color: #ffffff;
        color: #222;
        font-size: 15px;
        text-decoration: none;
        transition: background-color 0.15s;
    }
    .btn-top-round:hover {
        background-color: #f0f4f8;
    }
</style>

<div class="main-layout">
    <%@ include file="/jobSeekerSidebar.jsp" %>

    <div class="main-content">
        <div class="complete-wrapper">
            <p class="complete-message">
                ${job.company_name} への応募が完了しました！
            </p>

            <a href="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" class="btn-top-round">
                トップページへ戻る
            </a>
        </div>
    </div>
</div>

<%@ include file="/footer.jsp" %>