<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (request.getAttribute("detailLoaded") == null) {
        response.sendRedirect(request.getContextPath() + "/companyApproval/CompanyApprovalList.action");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>企業認証 - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
            background-color: #f8f9fa;
            color: #222222;
        }
        .main-layout {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 48px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .cert-container {
            max-width: 600px;
            margin: 0 auto;
        }
        .top-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 14px;
            margin-bottom: 24px;
            border-bottom: 1px solid #333333;
        }
        .page-title {
            font-size: 18px;
            font-weight: bold;
            margin: 0;
        }
        .btn-back {
            padding: 6px 14px;
            border: 1px solid #888888;
            color: #222222;
            text-decoration: none;
            font-size: 13px;
        }
        .cert-box {
            border: 1px solid #cccccc;
            padding: 28px 32px;
            background-color: #ffffff;
        }
        .target-company {
            font-size: 16px;
            font-weight: bold;
            margin: 0 0 12px 0;
        }
        .cert-desc {
            font-size: 14px;
            color: #444444;
            line-height: 1.6;
            margin: 0 0 28px 0;
        }
        .button-area {
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .btn-approve {
            padding: 10px 32px;
            background-color: #0044cc;
            color: #ffffff;
            border: none;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-approve:hover {
            background-color: #003399;
        }
        .btn-reject {
            padding: 10px 32px;
            background-color: #ffffff;
            color: #cc0000;
            border: 1px solid #cc0000;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-reject:hover {
            background-color: #fff5f5;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.4);
            justify-content: center;
            align-items: center;
        }
        .modal-content {
            width: 340px;
            padding: 24px;
            background-color: #ffffff;
            border: 1px solid #888888;
            text-align: center;
        }
        .modal-buttons {
            display: flex;
            justify-content: center;
            gap: 16px;
            margin-top: 20px;
        }
        .modal-btn-cancel {
            padding: 8px 18px;
            background-color: #ffffff;
            border: 1px solid #888888;
            cursor: pointer;
        }
        .modal-btn-exec {
            padding: 8px 20px;
            background-color: #222222;
            color: #ffffff;
            border: none;
            font-weight: bold;
            cursor: pointer;
        }
    </style>
</head>
<body>

    <%@ include file="/unionAdminHeader.jsp" %>

    <div class="main-layout">
        <%@ include file="/unionAdminSidebar.jsp" %>

        <main class="main-content">
            <div class="cert-container">

                <div class="top-actions">
                    <h2 class="page-title">企業認証</h2>
                    <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalDetail.action?id=${company.companyAccountId}" class="btn-back">詳細へ戻る</a>
                </div>

                <div class="cert-box">
                    <p class="target-company">対象企業：<c:out value="${company.companyName}" />（ID: ${company.companyAccountId}）</p>
                    <p class="cert-desc">
                        企業登録申請の内容を確認のうえ、「認証」または「拒否」を選択してください。<br>
                        実行するとデータベースの審査ステータスが更新されます。
                    </p>

                    <form id="approvalForm" action="${pageContext.request.contextPath}/companyApproval/CompanyApprovalUpdate.action" method="post">
                        <input type="hidden" name="id" value="${company.companyAccountId}">
                        <input type="hidden" name="actionType" id="actionTypeInput" value="">

                        <div class="button-area">
                            <button type="button" class="btn-approve" onclick="openConfirm('APPROVED', '認証')">認証する</button>
                            <button type="button" class="btn-reject" onclick="openConfirm('REJECTED', '拒否')">拒否する</button>
                        </div>
                    </form>
                </div>

            </div>
        </main>
    </div>

    <div class="modal" id="confirmModal">
        <div class="modal-content">
            <p id="confirmMessage" style="font-size: 14px; font-weight: bold;"></p>
            <div class="modal-buttons">
                <button type="button" class="modal-btn-cancel" onclick="closeConfirm()">キャンセル</button>
                <button type="button" class="modal-btn-exec" onclick="submitApproval()">実行する</button>
            </div>
        </div>
    </div>

    <script>
        function openConfirm(actionValue, label) {
            document.getElementById("actionTypeInput").value = actionValue;
            document.getElementById("confirmMessage").textContent = "この企業を「" + label + "」しますか？";
            document.getElementById("confirmModal").style.display = "flex";
        }
        function closeConfirm() {
            document.getElementById("confirmModal").style.display = "none";
        }
        function submitApproval() {
            document.getElementById("approvalForm").submit();
        }
    </script>

</body>
</html>