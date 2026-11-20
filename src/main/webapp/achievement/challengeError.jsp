<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登録エラー - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            color: #222222;
            background-color: #ffffff;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
            min-width: 960px;
        }
        .main-content-area {
            flex: 1;
            padding: 80px 40px;
            background-color: #ffffff;
            text-align: center;
            box-sizing: border-box;
        }
        .result-box {
            max-width: 600px;
            margin: 0 auto;
            padding: 40px 32px;
            border: 1px solid #cccccc;
            background-color: #ffffff;
        }
        .error-title {
            font-size: 20px;
            font-weight: bold;
            color: #d32f2f;
            margin: 0 0 16px 0;
        }
        .error-message {
            font-size: 14px;
            color: #555555;
            margin-bottom: 32px;
        }
        .btn-return {
            display: inline-block;
            padding: 9px 32px;
            background-color: #f5f5f5;
            border: 1px solid #888888;
            color: #222222;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .btn-return:hover {
            background-color: #e8e8e8;
        }
    </style>
</head>
<body>
    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content-area">
            <div class="result-box">
                <h3 class="error-title">登録に失敗しました</h3>
                <p class="error-message">
                    <c:choose>
                        <c:when test="${not empty errorMessage}">
                            <c:out value="${errorMessage}" />
                        </c:when>
                        <c:otherwise>
                            システムエラーが発生しました。
                        </c:otherwise>
                    </c:choose>
                </p>
                <a href="${pageContext.request.contextPath}/achievementServlet/ChallengeList.action" class="btn-return">
                    一覧へ戻る
                </a>
            </div>
        </main>
    </div>
</body>
</html>