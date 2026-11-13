<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../../head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/portfolio/css/portfoliodetails.css">


<div class="title-area">
<h1>作品詳細</h1>
</div>

<div class="form-box">

    <!-- 作品名 -->
    <label>作品名</label><br>
    <span class="setumei"><c:out value="${portfolio.work_name}" /></span><br>

    <!-- 説明文 -->
    <label>説明文</label><br>
    <span class="setumei"><c:out value="${portfolio.description}" /></span><br>

    <!-- ファイルURL / ファイル名 -->
    <label>ファイル</label><br>
    <span class="setumei"><img src="<c:out value="${portfolio.file_url}" />" alt="作品画像" style="max-width: 300px; height: auto;" /></span><br>

    <div class="button-area">
        <!-- 編集画面へ-->
        <a href="${pageContext.request.contextPath}/portfolio/PortfolioDisplay.action?portfolio_id=${portfolio.portfolio_id}"
           class="hensyu">編集</a>

        <!-- 削除画面へ-->
        <a href="${pageContext.request.contextPath}/portfolio/portfoliodelete.jsp?portfolio_id=${portfolio.portfolio_id}"
           class="sakuzyo">削除</a>
           
        <!-- 一覧画面へ-->
        <a href="${pageContext.request.contextPath}/portfolio/Portfolio.action"
           class="sakuzyo">戻る</a>
    </div>

</div>

<%@ include file="../../footer.jsp" %>