<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>口コミ投稿完了 - HITOVA</title>
    <style>
        body {
            margin: 0;
            font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
            background-color: #ffffff;
            color: #000000;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 50px;
            box-sizing: border-box;
        }
        .complete-box {
            max-width: 860px;
            margin: 0 auto;
            border: 2px solid #002B49;
            padding: 64px 40px 56px;
            background-color: #ffffff;
            text-align: center;
            box-sizing: border-box;
        }
        .complete-title {
            font-size: 22px;
            font-weight: bold;
            margin: 0 0 16px 0;
        }
        .complete-note {
            font-size: 14px;
            color: #444444;
            margin: 0 0 56px 0;
            line-height: 1.6;
        }
        .complete-actions {
            display: flex;
            justify-content: center;
            gap: 48px;
        }
        .btn-nav {
            display: inline-block;
            min-width: 190px;
            padding: 12px 24px;
            border: 1.5px solid #002B49;
            border-radius: 10px;
            background-color: #ffffff;
            color: #000000;
            font-size: 14px;
            text-decoration: none;
        }
        .btn-nav:hover {
            background-color: #f0f4f8;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content">
            <div class="complete-box">
                <h2 class="complete-title">口コミを受け付けました！</h2>
                <p class="complete-note">
                    ご投稿ありがとうございます。<br>
                    内容審査が完了次第、口コミ一覧へ公開されます。
                </p>

                <div class="complete-actions">
                    <a href="${pageContext.request.contextPath}/toppage/candidateTop.jsp" class="btn-nav">トップページへ戻る</a>
                    <a href="${pageContext.request.contextPath}/review/ReviewList.action?job_id=${param.job_id}" class="btn-nav">口コミページへ戻る</a>
                </div>
            </div>
        </main>
    </div>

</body>
</html>