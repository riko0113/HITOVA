<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<title>作品詳細</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/portfolio/css/portfoliodetails.css">
</head>
<body>

<div class="title-area">
<h1>作品詳細</h1>
</div>

<div class="form-box">

    <label>作品名</label><br>
    <span class="setumei">HITOVA</span><br>

    <label>説明文</label><br>
    <span class="setumei">JavaとJSP。</span><br>

    <label>ファイル</label><br>
    <span class="setumei">sample.pdf</span><br>

    <div class="button-area">
        <a href="${pageContext.request.contextPath}/portfolio/jsp/portfolioedit.jsp"
           class="hensyu">編集</a>

        <a href="${pageContext.request.contextPath}/portfolio/jsp/portfoliodelete.jsp"
           class="sakuzyo">削除</a>
    </div>

</div>

</body>
</html>