<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.candidate_account_id}">
    <c:redirect url="/login/login.jsp" />
</c:if>
<c:set var="targetJobId" value="${not empty param.job_id ? param.job_id : job_id}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>口コミ投稿 - HITOVA</title>
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
        .post-box {
            max-width: 860px;
            margin: 0 auto;
            border: 2px solid #002B49;
            padding: 36px 44px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .eval-select {
            width: 300px;
            height: 40px;
            padding: 0 12px;
            border: 1.5px solid #002B49;
            font-size: 14px;
            background-color: #ffffff;
            color: #000000;
            margin-bottom: 24px;
        }
        .input-label {
            display: block;
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .content-textarea {
            width: 100%;
            height: 160px;
            padding: 12px;
            border: 1.5px solid #002B49;
            font-size: 14px;
            font-family: inherit;
            box-sizing: border-box;
            resize: vertical;
        }
        .submit-area {
            margin-top: 24px;
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .btn-submit {
            padding: 10px 44px;
            border: 1.5px solid #002B49;
            border-radius: 10px;
            background-color: #ffffff;
            color: #000000;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-submit:hover {
            background-color: #f0f4f8;
        }
        .btn-cancel {
            padding: 10px 32px;
            border: 1px solid #888888;
            border-radius: 10px;
            background-color: #ffffff;
            color: #333333;
            font-size: 14px;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
        }
        .btn-cancel:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content">
            <div class="post-box">
                <c:if test="${not empty error}">
                    <p style="color: #cc0000; font-size: 14px; margin-top: 0;"><c:out value="${error}" /></p>
                </c:if>

                <form action="${pageContext.request.contextPath}/review/ReviewPost.action" method="post">
                    <input type="hidden" name="job_id" value="${targetJobId}">

                    <div>
                        <select name="evalutation" class="eval-select" required>
                            <option value="未経験の方におすすめ！">未経験の方におすすめ！</option>
                            <option value="スキルアップしたい方におすすめ！">スキルアップしたい方におすすめ！</option>
                            <option value="働きやすさ重視の方におすすめ！">働きやすさ重視の方におすすめ！</option>
                            <option value="アットホームな職場が好きな方におすすめ！">アットホームな職場が好きな方におすすめ！</option>
                            <option value="その他">その他</option>
                        </select>
                    </div>

                    <label for="review-content" class="input-label">入力内容（300文字以内）</label>
                    <textarea id="review-content" name="content" class="content-textarea" maxlength="300" required
                              placeholder="職場の雰囲気や働きやすさ、おすすめポイントなどを入力してください"></textarea>

                    <div class="submit-area">
                        <a href="${pageContext.request.contextPath}/review/ReviewList.action?job_id=${targetJobId}" class="btn-cancel">戻る</a>
                        <button type="submit" class="btn-submit">投稿する</button>
                    </div>
                </form>
            </div>
        </main>
    </div>

</body>
</html>