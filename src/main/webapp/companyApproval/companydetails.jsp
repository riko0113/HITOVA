<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>

<style>
    .main-layout {
        display: flex;
        align-items: stretch;
        min-height: calc(100vh - 60px);
    }

    .main-content {
        flex: 1;
        min-width: 0;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .certification-card {
        width: 420px;
        min-height: 300px;
        margin: 0;
        padding: 20px;
        border: 2px solid #999;
        box-sizing: border-box;
    }

    .back-button {
        display: inline-block;
        padding: 5px 15px;
        border: 2px solid #91B879;
        border-radius: 6px;
        text-decoration: none;
        color: #333;
    }

    .certification-title {
        text-align: center;
        margin: 15px 0 25px;
    }

    .company-name {
        margin-bottom: 25px;
    }

    .confirmation-text {
        margin-bottom: 30px;
    }

    .button-area {
        display: flex;
        justify-content: space-around;
    }

    .action-button {
        padding: 8px 25px;
        background-color: white;
        border: 2px solid #91B879;
        border-radius: 6px;
        cursor: pointer;
    }

    .action-button:hover {
        background-color: #eaf3e5;
    }

    .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.3);
        justify-content: center;
        align-items: center;
    }

    .modal-content {
        width: 300px;
        padding: 20px;
        background-color: white;
        border: 2px solid #999;
        text-align: center;
    }

    .modal-buttons {
        display: flex;
        justify-content: center;
        gap: 25px;
        margin-top: 25px;
    }

    .modal-buttons button {
        padding: 7px 15px;
        background-color: white;
        border: 2px solid #91B879;
        border-radius: 5px;
        cursor: pointer;
    }

    .ok-button {
        background-color: #91B879 !important;
    }
</style>

<div class="main-layout">

    <%@ include file="../../jobSeekerSidebar.jsp" %>

    <div class="main-content">

        <div class="certification-card">

            <a href="companyapply.jsp" class="back-button">戻る</a>

            <h3 class="certification-title">企業認証</h3>

            <p class="company-name">
                企業名：ABC株式会社
            </p>

            <p class="confirmation-text">
                企業登録申請の内容を確認しました。
            </p>

            <div class="button-area">

                <button class="action-button"
                    onclick="openConfirm('認証')">
                    認証
                </button>

                <button class="action-button"
                    onclick="openConfirm('拒否')">
                    拒否
                </button>

            </div>

        </div>

    </div>

</div>

<!-- 確認ダイアログ -->
<div class="modal" id="confirmModal">

    <div class="modal-content">

        <p id="confirmMessage"></p>

        <div class="modal-buttons">

            <button onclick="closeConfirm()">
                キャンセル
            </button>

            <button onclick="executeAction()">
                実行する
            </button>

        </div>

    </div>

</div>

<!-- 完了ダイアログ -->
<div class="modal" id="resultModal">

    <div class="modal-content">

        <p id="resultMessage"></p>

        <div class="modal-buttons">

            <button class="ok-button" onclick="closeResult()">
                OK
            </button>

        </div>

    </div>

</div>

<script>
    let selectedAction = "";

    // 確認ダイアログを開く
    function openConfirm(action) {

        selectedAction = action;

        document.getElementById("confirmMessage").textContent =
            "この企業を" + action + "しますか？";

        document.getElementById("confirmModal").style.display = "flex";
    }

    // 確認ダイアログを閉じる
    function closeConfirm() {

        document.getElementById("confirmModal").style.display = "none";
    }

    // 認証・拒否を実行
    function executeAction() {

        closeConfirm();

        document.getElementById("resultMessage").textContent =
            "企業を" + selectedAction + "しました。";

        document.getElementById("resultModal").style.display = "flex";
    }

    // 完了後、申請一覧画面に戻る
    function closeResult() {

        window.location.href = "companyApproval.jsp";
    }
</script>