<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/portfolio/css/portfolioAdd.css">

<div class="tuika">
    <h1>作品追加</h1>
</div>

<form action="${pageContext.request.contextPath}/portfolio/portfolioAdd.action" method="post">

    <div class="form-box">
        
        <!-- 作品名 -->
        <label>作品名 (50字以内)</label>
        <input type="text" id="workName" name="work_name"
               maxlength="50" placeholder="作品名を入力してください">
        <p id="workNameError" class="error"></p>

        <!-- 説明文 -->
        <span class="setumei">説明文 (300字以内)</span>
        <textarea id="description" name="description"
                  maxlength="300" placeholder="説明文を入力してください"></textarea>
        <p id="descriptionError" class="error"></p>

        <!-- ファイル添付 -->
        <span>ファイル添付 (100MBまで)</span>
        <input type="file" name="work_file">
        <p id="fileError" class="error"></p>

    </div>

    <div class="button-area">
        <a href="${pageContext.request.contextPath}/portfolio/Portfolio.action" class="but">
            戻る
        </a>

        <button type="submit" class="but">
            登録する
        </button>
    </div>

</form>
<script src="${pageContext.request.contextPath}/portfolio/css/portfolioAdd.js" defer></script>
<%@ include file="../footer.jsp" %>