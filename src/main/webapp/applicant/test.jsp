<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<form action="${pageContext.request.contextPath}/applicant/ApplicantList.action" method="post">
    <input type="submit" name="search" value="一覧">
    <input type="hidden" name="job_id" value="3001">
</form>
    
<%@ include file="../footer.jsp" %>