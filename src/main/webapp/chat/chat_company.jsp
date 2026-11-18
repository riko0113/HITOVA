<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<%@ include file="../companyHeader.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link href="../chatCss/chat.css" rel="stylesheet">

<div class="main-layout">
<%@ include file="../companySidebar.jsp" %>

<div class="main-content">
	<div class="chat-container-wrapper">
        
        <!--  左側：会話相手・ルーム一覧 -->
        <div class="chat-room-list">
            <div class="chat-room-item active">会話相手 1</div>
            <div class="chat-room-item">会話相手 2</div>
            <div class="chat-room-item">会話相手 3</div>
        </div>

        <!-- 右側：チャットやり取りエリア -->
        <div class="chat-main-pane">
            <!-- メッセージ表示エリア -->
            <div class="chat-messages-area">
                <p></p>
                <div class="chat-bubble chat-right"></div>
            </div>

            <!-- 下部入力フォーム ＆ 送信ボタン -->
            <form class="chat-input-area">
                <input type="text" class="form-control" placeholder="メッセージを入力...">
                <button type="submit" class="btn btn-primary">送信</button> 
            </form>
        </div>

    </div>
</div>
<script src="../applicantCss/applicant_lists.js"></script> 
<%@ include file="../footer.jsp" %>