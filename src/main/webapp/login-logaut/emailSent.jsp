<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/head.jsp" %>
<link rel="stylesheet" href="<c:url value='/login-logaut/verification.css' />">
<main class="auth-panel">
    <h1>メールアドレスの確認</h1>
    <p><c:out value="${requestScope.authEmail}" /></p>
    <c:if test="${not empty requestScope.authMessage}">
        <p role="status"><c:out value="${requestScope.authMessage}" /></p>
    </c:if>
    <p>スマートフォンなどでメール内のリンクを開いて、メールアドレスを確認してください。</p>
    <p>確認できたら、この画面で「確認をする」を押してください。</p>
    <form method="post" action="<c:url value='/auth/check' />">
        <button type="submit">確認をする</button>
    </form>
    <form method="post" action="<c:url value='/auth/resend' />">
        <button type="submit">確認メールを再送する</button>
    </form>
    <p>メールが見つからない場合は、迷惑メールフォルダーも確認してください。</p>
    <p><a href="<c:url value='/auth/login' />">ログイン画面へ戻る</a></p>
</main>
<%@ include file="/footer.jsp" %>
