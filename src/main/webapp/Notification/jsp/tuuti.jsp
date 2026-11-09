
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>通知</title>

<style>
h1 {
    text-align: center;
}

.notification-container {
    width: 800px;
    margin: 30px auto;
}

.notification-item {
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: white;
}

.notification-item h3 {
    margin: 0 0 10px;
}

.notification-item p {
    margin: 0 0 10px;
}

.notification-item span {
    font-size: 13px;
    color: #777;
}
</style>

</head>
<body>

<h1>通知</h1>

<div class="notification-container">

    <div class="notification-item">
        <h3>通知のタイトル</h3>
        <p>通知の内容をここに表示してもらう</p>
        <span>2026/11/06 10:30（ここに時間をかく）</span>
    </div>

</div>

</body>
</html>
