<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>

<div class="main-layout">
<%@ include file="../../jobSeekerSidebar.jsp" %>

	<div class="main-content">
		<h2>削除してよろしいですか</h2>

		<a href="${pageContext.request.contextPath}/portfolio/PortfolioDelete.action?portfolio_id=${param.portfolio_id}">はい</a>
		<a href="${pageContext.request.contextPath}/portfolio/PortfolioDetail.action?portfolio_id=${param.portfolio_id}">戻る</a>
		
	</div>
</div>

<%@ include file="../../footer.jsp" %>