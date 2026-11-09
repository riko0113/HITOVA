<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>求人検索結果 - HITOVA</title>
<style>
    body { font-family: sans-serif; padding: 20px; }
    .header-area { display: flex; justify-content: space-between; margin-bottom: 20px; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
    th { background-color: #f4f4f4; }
    .error-msg { color: #d9534f; margin-bottom: 20px; font-weight: bold; }
</style>
</head>
<body>
    <header class="header-area">
        <div><span style="color: #4CAF50; font-weight: bold; font-size: 24px;">HITOVA</span></div>
        <div style="font-size: 0.9em; color: #666;">マイページ ログアウト</div>
    </header>

    <h2>求人検索結果</h2>
    
    <c:if test="${not empty errorMessage}">
        <div class="error-msg">${errorMessage}</div>
    </c:if>

    <!-- 再検索用フォーム -->
    <form action="${pageContext.request.contextPath}/jobsearchServlet/JobSearchExecute.action" method="post">
        <input type="text" name="keyword" value="${keyword}" placeholder="職種やキーワードを入力">
        <input type="submit" value="検索">
    </form>

    <hr>

    <!-- 検索結果の表示エリア -->
    <c:choose>
        <c:when test="${not empty jobList}">
            <table>
                <tr>
                    <th>求人タイトル</th>
                    <th>勤務地</th>
                    <th>給与</th>
                    <th>アクセス</th>
                </tr>
                <c:forEach var="job" items="${jobList}">
                    <tr>
                        <td><c:out value="${job.title}" /></td>
                        <td><c:out value="${job.location}" /></td>
                        <td><c:out value="${job.salary}" /></td>
                        <td><c:out value="${job.access}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p>条件に一致する求人は見つかりませんでした。</p>
        </c:otherwise>
    </c:choose>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp">検索画面へ戻る</a>
    </div>
</body>
</html>