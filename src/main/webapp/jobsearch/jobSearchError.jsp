<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検索エラー - HITOVA</title>
<style>
    body { font-family: sans-serif; text-align: center; margin-top: 100px; }
    .btn { padding: 10px 40px; background: white; border: 1px solid gray; cursor: pointer; text-decoration: none; color: black; display: inline-block; }
</style>
</head>
<body>
    <h3 style="color: red;">検索に失敗しました。</h3>
    <p>
        <c:choose>
            <c:when test="${not empty errorMessage}">
                <c:out value="${errorMessage}" />
            </c:when>
            <c:otherwise>
                検索処理中にシステムエラーが発生しました。
            </c:otherwise>
        </c:choose>
    </p>
    
    <div style="margin-top: 50px;">
        <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp" class="btn">検索画面へ戻る</a>
    </div>
</body>
</html>