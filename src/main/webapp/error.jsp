<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>

<h2>エラー</h2>
<%-- サーブレットから渡された errorMessage を表示し、なければデフォルトの文言を表示する --%>
<p>${not empty errorMessage ? errorMessage : "システムエラーが発生しました。しばらく経ってから再度お試しください。"}</p>

<p>以下のボタンからトップページへお戻りください。</p>
<a href="${pageContext.request.contextPath}/">トップページへ戻る</a>

<%@ include file="../footer.jsp" %>