<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${review == null}">
    <c:redirect url="/admin/AdminReviewList.action" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザー口コミ審査 - HITOVA</title>
    <style>
        body {
            margin: 0;
            font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
            background-color: #ffffff;
            color: #000000;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 40px;
            box-sizing: border-box;
        }
        .judge-card {
            max-width: 680px;
            margin: 0 auto;
            border: 1px solid #cccccc;
            border-radius: 6px;
            padding: 28px 36px 36px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .top-row {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 24px;
        }
        .btn-back {
            display: inline-block;
            padding: 6px 18px;
            border: 1px solid #888888;
            border-radius: 4px;
            background-color: #ffffff;
            color: #000000;
            font-size: 13px;
            text-decoration: none;
        }
        .btn-back:hover {
            background-color: #f4f6f8;
        }
        .card-title {
            font-size: 20px;
            font-weight: bold;
            margin: 0;
        }
        .field-group {
            margin-bottom: 18px;
        }
        .field-label {
            display: block;
            font-size: 13px;
            font-weight: bold;
            color: #333333;
            margin-bottom: 6px;
        }
        .field-box {
            width: 100%;
            padding: 10px 14px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            background-color: #f9fafb;
            font-size: 14px;
            color: #000000;
            box-sizing: border-box;
        }
        .field-box.content-area {
            min-height: 110px;
            line-height: 1.6;
            white-space: pre-wrap;
            word-break: break-word;
        }
        .action-row {
            margin-top: 30px;
            display: flex;
            justify-content: center;
            gap: 32px;
        }
        .btn-approve {
            min-width: 140px;
            padding: 10px 28px;
            border: 1px solid #0044cc;
            border-radius: 4px;
            background-color: #0044cc;
            color: #ffffff;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-approve:hover {
            background-color: #003399;
        }
        .btn-reject {
            min-width: 140px;
            padding: 10px 28px;
            border: 1px solid #666666;
            border-radius: 4px;
            background-color: #ffffff;
            color: #000000;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-reject:hover {
            background-color: #f2f2f2;
        }

        /* 確認・完了モーダル */
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
            width: 380px;
            max-width: 90%;
            background-color: #ffffff;
            border: 1px solid #999999;
            border-radius: 6px;
            padding: 28px 24px;
            text-align: center;
            box-sizing: border-box;
        }
        .modal-title {
            font-size: 17px;
            font-weight: bold;
            margin: 0 0 14px 0;
        }
        .modal-text {
            font-size: 14px;
            margin: 0 0 24px 0;
            color: #222222;
        }
        .modal-buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .modal-btn-cancel {
            padding: 7px 20px;
            border: 1px solid #888888;
            border-radius: 4px;
            background-color: #ffffff;
            color: #000000;
            font-size: 14px;
            cursor: pointer;
        }
        .modal-btn-cancel:hover {
            background-color: #f4f6f8;
        }
        .modal-btn-ok {
            padding: 7px 24px;
            border: 1px solid #0044cc;
            border-radius: 4px;
            background-color: #0044cc;
            color: #ffffff;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
        }
        .modal-btn-ok:hover {
            background-color: #003399;
        }
    </style>
</head>
<body>

    <%@ include file="/adminHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/adminSidebar.jsp" %>

        <main class="main-content">
            <div class="judge-card">
                <div class="top-row">
                    <a href="${pageContext.request.contextPath}/admin/AdminReviewList.action" class="btn-back">戻る</a>
                    <h2 class="card-title">ユーザー口コミ審査</h2>
                    <div style="width: 64px;"></div>
                </div>

                <div class="field-group">
                    <label class="field-label">投稿者</label>
                    <div class="field-box"><c:out value="${review.poster_name}" /></div>
                </div>

                <div class="field-group">
                    <label class="field-label">対象ユーザー</label>
                    <div class="field-box">
                        <c:out value="${review.target_name}" />
                        <c:if test="${not empty review.job_title}">
                            （<c:out value="${review.job_title}" />）
                        </c:if>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">申請日</label>
                    <div class="field-box">${review.posted_at}</div>
                </div>

                <div class="field-group">
                    <label class="field-label">口コミ内容</label>
                    <div class="field-box content-area"><c:out value="${review.content}" /></div>
                </div>

                <div class="field-group">
                    <label class="field-label">評価</label>
                    <div class="field-box"><c:out value="${review.evalutation}" /></div>
                </div>

                <div class="action-row">
                    <button type="button" class="btn-approve" onclick="openConfirm('approve')">承認</button>
                    <button type="button" class="btn-reject" onclick="openConfirm('reject')">拒否</button>
                </div>
            </div>
        </main>
    </div>

    <!-- 確認モーダル -->
    <div class="modal-overlay" id="confirmModal">
        <div class="modal-box">
            <h3 class="modal-title" id="confirmTitle"></h3>
            <p class="modal-text" id="confirmMessage"></p>
            <div class="modal-buttons">
                <button type="button" class="modal-btn-cancel" onclick="closeConfirm()">キャンセル</button>
                <button type="button" class="modal-btn-ok" id="confirmExecBtn" onclick="showResult()">承認する</button>
            </div>
        </div>
    </div>

    <!-- 完了モーダル -->
    <div class="modal-overlay" id="resultModal">
        <div class="modal-box">
            <p class="modal-text" id="resultMessage" style="margin-top: 8px; font-weight: bold;"></p>
            <form action="${pageContext.request.contextPath}/admin/AdminReviewJudge.action" method="post" style="margin: 0;">
                <input type="hidden" name="job_review_id" value="${review.job_review_id}">
                <input type="hidden" name="decision" id="decisionInput">
                <button type="submit" class="modal-btn-ok">OK</button>
            </form>
        </div>
    </div>

    <script>
        let currentDecision = "";

        function openConfirm(decision) {
            currentDecision = decision;
            if (decision === "approve") {
                document.getElementById("confirmTitle").textContent = "承認確認";
                document.getElementById("confirmMessage").textContent = "この口コミを承認しますか？";
                document.getElementById("confirmExecBtn").textContent = "承認する";
            } else {
                document.getElementById("confirmTitle").textContent = "拒否確認";
                document.getElementById("confirmMessage").textContent = "この口コミを拒否しますか？";
                document.getElementById("confirmExecBtn").textContent = "拒否する";
            }
            document.getElementById("confirmModal").style.display = "flex";
        }

        function closeConfirm() {
            document.getElementById("confirmModal").style.display = "none";
        }

        function showResult() {
            closeConfirm();
            document.getElementById("decisionInput").value = currentDecision;
            document.getElementById("resultMessage").textContent =
                (currentDecision === "approve") ? "口コミを承認しました。" : "口コミを拒否しました。";
            document.getElementById("resultModal").style.display = "flex";
        }
    </script>
</body>
</html>