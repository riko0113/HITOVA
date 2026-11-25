<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>口コミ一覧 - HITOVA</title>
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
        .review-container {
            max-width: 860px;
            margin: 0 auto;
            border: 2px solid #002B49;
            padding: 30px 40px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .review-header-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 16px;
            margin-bottom: 28px;
            padding-bottom: 16px;
            border-bottom: 1px solid #d0d7de;
        }
        .company-title {
            font-size: 20px;
            font-weight: bold;
            margin: 0;
        }
        .post-action-wrap {
            display: flex;
            flex-direction: column;
            align-items: flex-end;
            gap: 6px;
        }
        .btn-post-open {
            display: inline-block;
            padding: 10px 32px;
            border: 1.5px solid #135e75;
            border-radius: 10px;
            background-color: #ffffff;
            color: #000000;
            font-size: 15px;
            font-weight: bold;
            text-decoration: none;
            cursor: pointer;
        }
        .btn-post-open:hover {
            background-color: #f0f4f8;
        }
        .btn-post-disabled {
            display: inline-block;
            padding: 10px 32px;
            border: 1.5px solid #bbbbbb;
            border-radius: 10px;
            background-color: #f3f4f6;
            color: #888888;
            font-size: 15px;
            font-weight: bold;
            cursor: not-allowed;
        }
        .profile-notice {
            font-size: 12px;
            color: #cc0000;
            margin: 0;
        }
        .profile-notice a {
            color: #0044cc;
            text-decoration: underline;
        }

        .review-item {
            display: flex;
            align-items: flex-start;
            gap: 24px;
            padding: 20px 0;
            border-bottom: 1px solid #e5e7eb;
        }
        .review-item:last-child {
            border-bottom: none;
        }
        .user-col {
            width: 88px;
            flex-shrink: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
        }
        .self-badge {
            border: 1px solid #cccccc;
            background-color: #ffffff;
            font-size: 12px;
            font-weight: bold;
            padding: 2px 16px;
            margin-bottom: 6px;
        }
        .avatar-circle {
            width: 52px;
            height: 52px;
            border: 2px solid #002B49;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #000000;
            margin-bottom: 6px;
        }
        .user-meta {
            font-size: 12px;
            color: #333333;
            line-height: 1.4;
        }
        .content-col {
            flex: 1;
            min-width: 0;
        }
        .eval-row {
            display: flex;
            align-items: center;
            gap: 16px;
            margin-bottom: 10px;
        }
        .eval-label {
            font-size: 15px;
            font-weight: bold;
        }
        .btn-delete-open {
            padding: 4px 16px;
            border: 1.5px solid #002B49;
            border-radius: 8px;
            background-color: #ffffff;
            color: #000000;
            font-size: 13px;
            cursor: pointer;
        }
        .btn-delete-open:hover {
            background-color: #fee2e2;
            border-color: #cc0000;
            color: #cc0000;
        }
        .review-body {
            font-size: 14px;
            line-height: 1.7;
            white-space: pre-wrap;
            word-break: break-word;
            margin: 0;
        }
        .bottom-nav {
            margin-top: 28px;
            text-align: center;
        }
        .btn-back-detail {
            display: inline-block;
            padding: 10px 32px;
            border: 1px solid #002B49;
            border-radius: 6px;
            background-color: #ffffff;
            color: #000000;
            text-decoration: none;
            font-size: 14px;
        }
        .btn-back-detail:hover {
            background-color: #f0f4f8;
        }

        .modal-overlay {
            display: none;
            position: fixed;
            z-index: 1000;
            inset: 0;
            background-color: rgba(0, 0, 0, 0.35);
            justify-content: center;
            align-items: center;
        }
        .modal-box {
            width: 540px;
            max-width: 90%;
            background-color: #ffffff;
            border: 2px solid #002B49;
            padding: 32px 36px;
            box-sizing: border-box;
        }
        .modal-title {
            text-align: center;
            font-size: 18px;
            font-weight: bold;
            margin: 0 0 24px 0;
        }
        .modal-preview {
            display: flex;
            align-items: center;
            gap: 18px;
            margin-bottom: 28px;
            padding: 12px;
            background-color: #f9fafb;
            border: 1px solid #e5e7eb;
        }
        .modal-preview-text {
            font-size: 14px;
            line-height: 1.5;
            word-break: break-word;
            margin: 0;
        }
        .modal-actions {
            display: flex;
            justify-content: center;
            gap: 36px;
        }
        .modal-btn {
            padding: 8px 28px;
            border: 1.5px solid #002B49;
            border-radius: 8px;
            background-color: #ffffff;
            color: #000000;
            font-size: 14px;
            cursor: pointer;
        }
        .modal-btn:hover {
            background-color: #f0f4f8;
        }
        .modal-btn-danger:hover {
            background-color: #fee2e2;
            border-color: #cc0000;
            color: #cc0000;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content">
            <div class="review-container">
                <div class="review-header-row">
                    <h2 class="company-title"><c:out value="${company_name}" /></h2>
                    <c:if test="${not empty sessionScope.candidate_account_id}">
                        <div class="post-action-wrap">
                            <c:choose>
                                <c:when test="${hasProfile}">
                                    <a href="${pageContext.request.contextPath}/review/reviewPost.jsp?job_id=${job_id}" class="btn-post-open">口コミを書く</a>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn-post-disabled" disabled>口コミを書く</button>
                                    <p class="profile-notice">
                                        口コミを書くには<a href="${pageContext.request.contextPath}/profile/ProfileDisplay.action">プロフィール</a>を登録してください。
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>
                </div>

                <c:choose>
                    <c:when test="${empty reviews}">
                        <p style="text-align: center; color: #555555; padding: 30px 0;">
                            まだこの求人の口コミは投稿されていません。
                        </p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="r" items="${reviews}">
                            <c:set var="isMine" value="${not empty sessionScope.candidate_account_id && sessionScope.candidate_account_id == r.candidate_account_id}" />
                            <div class="review-item">
                                <div class="user-col">
                                    <c:if test="${isMine}">
                                        <span class="self-badge">自分</span>
                                    </c:if>
                                    <div class="avatar-circle">
                                        <svg width="28" height="28" viewBox="0 0 24 24" fill="currentColor">
                                            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                                        </svg>
                                    </div>
                                    <div class="user-meta">
                                        <c:choose>
                                            <c:when test="${not empty r.nick_name}">
                                                <c:out value="${r.nick_name}" />
                                            </c:when>
                                            <c:otherwise>匿名ユーザー</c:otherwise>
                                        </c:choose>
                                        <c:if test="${r.age > 0 || r.gender != null}">
                                            <div style="border: 1px solid #cccccc; margin-top: 4px; padding: 1px 4px; font-size: 11px;">
                                                <c:if test="${r.gender != null}">${r.gender ? '男性' : '女性'}</c:if>
                                                <c:if test="${r.gender != null && r.age > 0}">/</c:if>
                                                <c:if test="${r.age > 0}">${r.age}歳</c:if>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="content-col">
                                    <div class="eval-row">
                                        <span class="eval-label"><c:out value="${r.evalutation}" /></span>
                                        <c:if test="${isMine}">
                                            <button type="button" class="btn-delete-open"
                                                    data-id="${r.job_review_id}"
                                                    data-content="<c:out value='${r.content}' />"
                                                    onclick="openDeleteModal(this)">削除する</button>
                                        </c:if>
                                    </div>
                                    <p class="review-body"><c:out value="${r.content}" /></p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

                <div class="bottom-nav">
                    <a href="${pageContext.request.contextPath}/jobsearchServlet/JobDetail.action?id=${job_id}" class="btn-back-detail">求人詳細へ戻る</a>
                </div>
            </div>
        </main>
    </div>

    <div class="modal-overlay" id="deleteModal">
        <div class="modal-box">
            <h3 class="modal-title">投稿を削除しますか？</h3>
            <div class="modal-preview">
                <div class="avatar-circle" style="flex-shrink: 0; margin: 0;">
                    <svg width="28" height="28" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                </div>
                <p class="modal-preview-text" id="deletePreviewText"></p>
            </div>
            <form action="${pageContext.request.contextPath}/review/ReviewDelete.action" method="post" class="modal-actions">
                <input type="hidden" name="job_id" value="${job_id}">
                <input type="hidden" name="job_review_id" id="deleteReviewId">
                <button type="button" class="modal-btn" onclick="closeDeleteModal()">キャンセル</button>
                <button type="submit" class="modal-btn modal-btn-danger">削除する</button>
            </form>
        </div>
    </div>

    <script>
        function openDeleteModal(btn) {
            document.getElementById("deleteReviewId").value = btn.getAttribute("data-id");
            document.getElementById("deletePreviewText").textContent = btn.getAttribute("data-content");
            document.getElementById("deleteModal").style.display = "flex";
        }
        function closeDeleteModal() {
            document.getElementById("deleteModal").style.display = "none";
        }
    </script>
</body>
</html>