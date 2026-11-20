<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/head.jsp" %>
<link rel="stylesheet" href="<c:url value='/login/verification.css' />">
<main class="auth-panel">
    <h1>処理を完了できませんでした</h1>
    <p class="auth-error" role="alert">入力内容やアカウントの状態を確認してください。</p>
    <p>メール確認がまだの場合は、確認メールのリンクを開いてからログインしてください。</p>
    <p>再送は前回の送信から30秒以上あけてください。処理できない場合は、時間をおいてお試しください。</p>
    <form method="get" action="<c:url value='/login/login.jsp' />">
        <button type="submit">ログイン画面へ戻る</button>
    </form>
</main>
<%@ include file="/footer.jsp" %>
