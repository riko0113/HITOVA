<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>申請完了 - HITOVA</title>
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
        .result-title {
            font-size: 20px;
            font-weight: bold;
            color: #111111;
            margin: 0 0 16px 0;
        }
        .result-target {
            font-size: 15px;
            color: #333333;
            margin-bottom: 12px;
        }
        .result-note {
            font-size: 13px;
            color: #666666;
            margin-bottom: 32px;
        }
        .btn-return {
            display: inline-block;
            padding: 9px 32px;
            background-color: #4CAF50;
            color: #ffffff;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
            font-weight: bold;
        }
        .btn-return:hover {
            background-color: #43a047;
        }
    </style>
</head>
<body>
    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content-area">
            <div class="result-box">
                <h3 class="result-title">課題の受付・登録が完了しました</h3>
                <p class="result-target">
                    対象課題: <strong><c:out value="${challengeName}" /></strong>
                </p>
                <p class="result-note">
                    ※本審査・自動採点機能および決済機能は二次開発フェーズでの提供予定です。
                </p>
                <a href="${pageContext.request.contextPath}/achievementServlet/ChallengeList.action" class="btn-return">
                    一覧へ戻る
                </a>
            </div>
        </main>
    </div>
</body>
</html>