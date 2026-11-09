<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>求人検索 - HITOVA</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
        th { background-color: #f4f4f4; }
    </style>
</head>
<body>
    <div style="padding: 20px;">
        <h2>求人検索</h2>
        
        <!-- 検索フォーム -->
        <form action="../jobsearchServlet/JobSearchExecute.action" method="post">
            <input type="text" name="keyword" placeholder="求人タイトルで検索" style="padding: 5px; width: 250px;">
            <button type="submit" style="padding: 5px 15px; background: #4CAF50; color: white; border: none;">検索</button>
        </form>

        <hr style="margin: 20px 0;">

        <!-- 検索結果の表示 -->
        <h3>検索結果</h3>
        
        <c:choose>
            <c:when test="${empty jobList}">
                <p>該当する求人がありません。別のキーワードをお試しください。</p>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>求人タイトル</th>
                        <th>勤務地</th>
                        <th>給与</th>
                    </tr>
                    <!-- コントローラーから渡された jobList をループ表示 -->
                    <c:forEach var="job" items="${jobList}">
                        <tr>
                            <td>${job.title}</td>
                            <td>${job.location}</td>
                            <td>${job.salary}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>