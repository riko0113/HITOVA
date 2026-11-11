<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/head.jsp" %>
<link rel="stylesheet" href="<c:url value='/login-logaut/verification.css' />">
<main class="auth-panel">
    <h1>認証が完了しました</h1>
    <p>メールアドレスの確認が完了しました。</p>
    <form method="post" action="<c:url value='/login/Logout.action' />">
        <button type="submit">ログアウト</button>
    </form>
</main>
<%@ include file="/footer.jsp" %>
