<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>登録求人一覧</title>
<%@ include file="../companyHeader.jsp" %>

<style>
body {
    font-family: sans-serif;
    margin: 30px;
    background-color: #1e9f8ec;
}

/* ページタイトル */
h1 {
    text-align: center;
    width: 950px;
    margin: 0 auto 30px;
    padding: 15px;
    background-color: #eeeeee;
    border: 2px solid #949fa8;
    border-radius: 5px;
}

/* 求人一覧 */
.job-list {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
    width: 950px;
    margin: 0 auto;
    align-items: start;
}

/* 求人カード */
.job-card {
    background-color: #f5f5f5;
    border: 2px solid #949fa8;
    border-radius: 5px;
    padding: 20px;
}

/* 公開・非公開エリア */
.status-area {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 20px;
}

/* 切り替えスイッチ */
.switch {
    position: relative;
    display: inline-block;
    width: 50px;
    height: 26px;
}

.switch input {
    opacity: 0;
    width: 0;
    height: 0;
}

.slider {
    position: absolute;
    cursor: pointer;
    inset: 0;
    background-color: #aaa;
    border-radius: 26px;
    transition: 0.3s;
}

.slider::before {
    position: absolute;
    content: "";
    height: 20px;
    width: 20px;
    left: 3px;
    bottom: 3px;
    background-color: white;
    border-radius: 50%;
    transition: 0.3s;
}

/* 公開中のスイッチ */
.switch input:checked + .slider {
    background-color: #4caf50;
}

/* 非公開のスイッチ */
.switch input:not(:checked) + .slider {
    background-color: #e57373;
}

.switch input:checked + .slider::before {
    transform: translateX(24px);
}

/* 公開・非公開の文字 */
.status-text {
    font-weight: bold;
    color: #4caf50;
}

/* 非公開の文字 */
.status-text.hidden {
    color: #e57373;
}

/* 求人情報 */
.job-info h2 {
    text-align: center;
    background-color: #bdbdbd;
    padding: 12px;
    margin: 0 0 15px;
    border-radius: 5px;
    font-size: 19px;
}

.job-info p {
    text-align: left;
    margin: 10px 0;
    padding: 8px;
    background-color: #eeeeee;
    border-radius: 5px;
    font-size: 15px;
}

/* ボタンエリア */
.button-area {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 15px;
    margin-top: 20px;
}

/* 削除ボタン */
.delete-button {
    border: none;
    background: none;
    cursor: pointer;
    font-size: 23px;
}

/* 編集ボタン */
.edit-button {
    padding: 9px 25px;
    background-color: #C8E6C9;
    color: black;
    border: 1px solid #888888;
    border-radius: 5px;
    cursor: pointer;
    font-size: 15px;
}

/* 確認画面 */
.modal {
    display: none;
    position: fixed;
    inset: 0;
    background-color: rgba(0, 0, 0, 0.5);
    justify-content: center;
    align-items: center;
    z-index: 10;
}

.modal-content {
    background-color: white;
    padding: 30px;
    border: 2px solid #949fa8;
    border-radius: 8px;
    width: 350px;
    max-width: 85%;
    text-align: center;
}

.modal-content h3 {
    margin-top: 0;
}

.target-title {
    font-weight: bold;
    margin: 15px 0;
    overflow-wrap: anywhere;
}

.modal-buttons {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 25px;
}

.cancel-button,
.confirm-button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.cancel-button {
    background-color: #bdbdbd;
}

.confirm-button {
    background-color: #e57373;
    color: white;
}

/* 画面幅が狭い場合 */
@media (max-width: 1000px) {
    .job-list {
        width: 100%;
        grid-template-columns: repeat(2, 1fr);
    }

    h1 {
        width: auto;
    }
}

@media (max-width: 600px) {
    .job-list {
        grid-template-columns: 1fr;
    }
}

/* 新規登録ボタンエリア */
.register-button-area {
    width: 950px;
    margin: 0 auto 20px;
    display: flex;
    justify-content: flex-end; /* 右側に配置する場合。中央にしたい場合は center に変更 */
}

/* 新規登録ボタン */
.register-button {
    display: inline-block;
    padding: 12px 25px;
    background-color: #4caf50; /* 緑系の目立つ色 */
    color: white;
    text-decoration: none;
    font-weight: bold;
    border-radius: 5px;
    border: 1px solid #388e3c;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    transition: 0.2s;
}

.register-button:hover {
    background-color: #43a047;
}

/* レスポンシブ対応（画面幅が狭い場合、タイトルの幅に合わせて調整） */
@media (max-width: 1000px) {
    .register-button-area {
        width: 100%;
        padding: 0 20px;
        box-sizing: border-box;
    }
}

/* 下線を入れるクラス */
.job-info p.with-border {
    border-bottom: 2px solid #d0d7de; /* 線の太さと色 */
    padding-bottom: 12px;
    margin-bottom: 15px;
}
</style>
</head>

<body>

<div class="main-layout">
<%@ include file="../companySidebar.jsp" %>
 
<div class="main-content">

<h1>登録求人一覧</h1>

<!-- 新規求人登録ボタンエリア -->
<div class="register-button-area">
    <a href="${pageContext.request.contextPath}/job/JobInsert.action" class="register-button">
        ＋ 新規求人登録
    </a>
</div>

<div class="job-list" id="job-list">

    <c:forEach var="job" items="${jobList}">
        <!-- 求人カード -->
        <div class="job-card" data-job-id="${job.job_id}">
            <div class="status-area">
                <label class="switch">
                    <input type="checkbox" class="public-toggle" <c:if test="${job.publication_status}">checked</c:if>>
                    <span class="slider"></span>
                </label>
                <span class="status-text <c:if test="${!job.publication_status}">hidden</c:if>">
                    <c:choose>
                        <c:when test="${job.publication_status}">公開中</c:when>
                        <c:otherwise>非公開</c:otherwise>
                    </c:choose>
                </span>
            </div>

            <div class="job-info">
                <h2><c:out value="${job.title}" /></h2>
                <p>勤務地：<c:out value="${job.location}" /></p>
                <p class="with-border">給与：<c:out value="${job.salary_type_name}" /> <c:out value="${job.salary}" /></p>
                <p>応募：<c:out value="${job.applicant_count}" />名</p>
                <p>更新日：<c:out value="${job.updated_at}" /></p>
            </div>

            <div class="button-area">
                <button type="button" class="delete-button" title="削除">🗑</button>
                <button type="button" class="edit-button" data-job-id="${job.job_id}">編集</button>
            </div>
        </div>
    </c:forEach>

</div>

<!-- 確認画面 -->
<div class="modal" id="confirm-modal">
    <div class="modal-content">
        <h3 id="modal-heading"></h3>
        <p id="modal-message"></p>
        <p class="target-title" id="target-title"></p>

        <div class="modal-buttons">
            <button type="button" class="cancel-button" id="cancel-button">
                キャンセル
            </button>

            <button type="button" class="confirm-button" id="confirm-button">
                はい
            </button>
        </div>
    </div>
</div>

</div>
</div>

<script src="../jobregisterJs/jobregisterlist.js"></script>
<%@ include file="../footer.jsp" %>