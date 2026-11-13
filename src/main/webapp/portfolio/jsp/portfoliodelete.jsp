<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>

<h2>削除してよろしいですか</h2>

<a herf="${pageContext.request.contextPath}/portfolio/PortfolioDelete.action?portfolio_id=${param.portfolio_id}">はい</a>
<a href="${pageContext.request.contextPath}/portfolio/PortfolioDetail.action?portfolio_id=${param.portfolio_id}">戻る</a>

<%@ include file="../footer.jsp" %>