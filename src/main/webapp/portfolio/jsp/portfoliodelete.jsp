<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>
<style>
h2 {
	text-align: center;

}

.main-content{
	text-align: center;
}

.button-area {
    display: flex;
    justify-content: center;
    gap: 30px;
    margin-top: 30px;
}

.button {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    width: 120px;
    height: 45px;
    box-sizing: border-box;
    background-color: white;
    border: 2px solid black;
    border-radius: 5px;
    text-decoration: none;
    color: black;
    font-size: 16px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
    transition: 0.2s;
    cursor: pointer;
}

.button:hover {
    background-color: rgb(182, 241, 188);
}
.info-card {
    width: 600px;
    margin: 20px auto;
    padding: 20px;
    box-sizing: border-box;
    background-color: white;
    border: 2px solid black;
    border-radius: 5px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
}

label {
    font-size: 20px;
}

.setumei {
    font-size: 20px;
}
</style>

<body>
<div class="main-layout">
<%@ include file="../../jobSeekerSidebar.jsp" %>

	<div class="main-content">
		<h2>削除してよろしいですか</h2>

<div class="info-card">
	<label>作品名</label><br>
    <span class="setumei"><c:out value="${portfolio.work_name}" /></span><br>

    <!-- 説明文 -->
    <label>説明文</label><br>
    <span class="setumei"><c:out value="${portfolio.description}" /></span><br>
    
    <label>ファイル</label>
    <span class="setumei"><c:out value="${portfolio.file_url}" /></span><br>
</div>

	<div class="button-area">
		<a class="button" href="${pageContext.request.contextPath}/portfolio/PortfolioDelete.action?portfolio_id=${param.portfolio_id}">はい</a>
		<a class="button"href="${pageContext.request.contextPath}/portfolio/PortfolioDetail.action?portfolio_id=${param.portfolio_id}">戻る</a>
	</div>
	
	</div>

</div>

<%@ include file="../../footer.jsp" %>
</body>