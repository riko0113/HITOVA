
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
<div class="tuika">
<h1>作品追加</h1>
</div>
<form action="${pageContext.request.contextPath}/portfolio/portfolioAdd"
      method="post" enctype="multipart/form-data">

    <div class="form-box">

<label>作品名 (50字以内)</label>

<input type="text" id="workName" name="workName"
       maxlength="50" placeholder="作品名を入力してください"
       value="${workName}">

<p id="workNameError" class="error">${workNameError}</p>

        <span class="setumei">説明文 (300字以内)</span>
		<textarea id="description" name="description"
        		  maxlength="300" placeholder="説明文を入力してください">${description}</textarea>
<p id="descriptionError" class="error">${descriptionError}</p>

        <span>ファイル添付 (100MBまで)</span>
        <input type="file" name="workFile">
        <p class="error">${fileError}</p>

    </div>

    <div class="button-area">

        <a href="${pageContext.request.contextPath}/portfolio/jsp/portfolio.jsp" class="but">
            戻る
        </a>

        <button type="submit" class="but">
            登録する
        </button>

    </div>

</form>


</body>
</html>
