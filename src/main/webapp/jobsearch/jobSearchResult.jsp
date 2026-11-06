<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="bean.Job" %>
<%
    // Actionから渡された jobList を受け取る
    List<Job> jobList = (List<Job>) request.getAttribute("jobList");
    
    // 検索窓に元のキーワードを残すための処理
    String keyword = request.getParameter("keyword");
    if (keyword == null) {
        keyword = "";
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>求人検索結果 - HITOVA</title>
<style>
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    th, td {
        border: 1px solid #ccc;
        padding: 10px;
        text-align: left;
    }
    th {
        background-color: #f4f4f4;
    }
</style>
</head>
<body>
    <h1>求人検索結果</h1>
    
    <!-- 再検索用フォーム -->
    <form action="../jobsearchServlet/JobSearchExecute.action" method="post">
        <input type="text" name="keyword" value="<%= keyword %>" placeholder="職種やキーワードを入力">
        <input type="submit" value="検索">
    </form>

    <hr>

    <!-- 検索結果の表示エリア -->
    <% if (jobList != null && !jobList.isEmpty()) { %>
        <table>
            <tr>
                <th>求人タイトル</th>
                <th>勤務地</th>
                <th>給与</th>
                <th>アクセス</th>
            </tr>
            <% for (Job job : jobList) { %>
                <tr>
                    <td><%= job.getTitle() != null ? job.getTitle() : "" %></td>
                    <td><%= job.getLocation() != null ? job.getLocation() : "" %></td>
                    <td><%= job.getSalary() != null ? job.getSalary() : "" %></td>
                    <td><%= job.getaccess() != null ? job.getaccess() : "" %></td>
                </tr>
            <% } %>
        </table>
    <% } else if (jobList != null && jobList.isEmpty()) { %>
        <p>条件に一致する求人は見つかりませんでした。</p>
    <% } else { %>
        <p>検索キーワードを入力して検索してください。</p>
    <% } %>
</body>
</html>