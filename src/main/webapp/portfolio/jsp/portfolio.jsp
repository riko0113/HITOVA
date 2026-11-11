<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 

<%@ include file="../head.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/portfolio/css/portfolio.css">

    <h2>作品一覧</h2>

    <div class="add-area">
        <a href="${pageContext.request.contextPath}/portfolio/portfolioAdd.jsp" class="add">作品追加＋</a>
    </div>

    <!-- 作品が1件以上あるか、空（またはnull）かで分岐 -->
    <c:choose>
        <%-- リストが空の場合 --%>
        <c:when test="${empty portfolioList}">
            <p class="no-portfolio">登録されている作品はありません</p>
        </c:when>
        
        <%-- リストに作品が存在する場合 --%>
        <c:otherwise>
            <c:forEach var="portfolio" items="${portfolioList}">
                <div class="work-card">
                    <!-- 作品名を表示 -->
                    <h3><c:out value="${portfolio.work_name}" /></h3>
                    
                    <!-- 詳細画面へ -->
                    <a href="${pageContext.request.contextPath}/portfolio/PortfolioDetail.action?portfolio_id=${portfolio.portfolio_id}" class="details">作品詳細</a>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

<%@ include file="../footer.jsp" %>