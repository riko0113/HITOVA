<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../../head.jsp" %>
<%@ include file="../../unionAdminHeader.jsp" %>

<style>
    .main-layout {
        display: flex;
        align-items: stretch;
        min-height: calc(100vh - 60px);
        position: relative;
    }

    .main-content {
        flex: 1;
        min-width: 0;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .detail-card {
        width: 420px;
        margin: 0;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 8px;
        box-sizing: border-box;
    }

    .back-button {
        display: inline-block;
        padding: 5px 15px;
        border: 1px solid #91B879;
        border-radius: 5px;
        color: #333;
        text-decoration: none;
    }

    .detail-title {
        text-align: center;
        margin: 25px 0;
    }

    .section-title {
        margin-top: 20px;
        margin-bottom: 10px;
        font-weight: bold;
    }

    .detail-item {
        margin-bottom: 8px;
    }

    .detail-item a {
        color: blue;
    }

    .business-content {
        margin-top: 10px;
        line-height: 1.6;
    }

    .approval-button {
        display: block;
        margin: 30px auto 10px;
        padding: 10px 25px;
        background-color: white;
        border: 2px solid #91B879;
        border-radius: 8px;
        cursor: pointer;
    }
</style>

<div class="main-layout">

    <%@ include file="../../unionAdminSidebar.jsp" %>

    <div class="main-content">

        <div class="detail-card">

            <a href="companyApproval.jsp" class="back-button">
                戻る
            </a>

            <h3 class="detail-title">企業登録申請詳細</h3>

            <div class="section-title">企業情報</div>

            <div class="detail-item">
                企業名：ABC株式会社
            </div>

            <div class="detail-item">
                メール：
                <a href="mailto:abc@xxx.jp">abc@xxx.jp</a>
            </div>

            <div class="detail-item">
                所在地：大阪府〇〇市
            </div>

            <div class="detail-item">
                電話番号：06-xxxx-xxxx
            </div>

            <div class="section-title">事業内容</div>

            <div class="business-content">
                ○○を中心としたサービスを提供
            </div>

            <div class="section-title">申請日</div>

            <div class="detail-item">
                2026/09/20
            </div>

            <div class="detail-item">
                状態：未審査
            </div>

            <button class="approval-button"
                onclick="location.href='companydetails.jsp'">
                企業認証へ
            </button>

        </div>

    </div>

</div>