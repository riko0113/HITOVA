<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>追加</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/portfolio/css/portfolioAdd.css">
</head>
<body>
<h1>作品追加</h1>
<div class="form-box">
	<label>作品名 (50字以内)</label>
		<textarea maxlength="50" placeholder="作品名を入力してください"></textarea><br>
<span class="setumei">説明文 (300字以内)</span>
	<textarea maxlength="300"placeholder="説明文を入力してください"></textarea><br>
<span class="">ファイル添付(100MBまで)</span>
	<input type="file">
</div>


</body>
</html>