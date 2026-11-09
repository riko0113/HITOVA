<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/head.jsp" %>
<link rel="stylesheet" href="<c:url value='/login-logaut/verification.css' />">
<main class="auth-panel">
    <h1>処理を完了できませんでした</h1>
    <p class="auth-error" role="alert"><c:out value="${requestScope.authError}" /></p>
    <p><a href="<c:url value='${requestScope.authReturnPath}' />">前の画面に戻る</a></p>
</main>
<%@ include file="/footer.jsp" %>
