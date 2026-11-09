<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登録エラー - HITOVA</title>
</head>
<body>
    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper" style="display: flex; min-height: calc(100vh - 60px);">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main style="flex: 1; text-align: center; padding-top: 100px; background-color: #f5f5f5;">
            <h3 style="color: red; font-size: 22px;">登録に失敗しました</h3>
            <p style="color: #666; margin-top: 15px;">
                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        <c:out value="${errorMessage}" />
                    </c:when>
                    <c:otherwise>
                        システムエラーが発生しました。
                    </c:otherwise>
                </c:choose>
            </p>

            <div style="margin-top: 40px;">
                <a href="${pageContext.request.contextPath}/achievement/challengeList.jsp" 
                   style="padding: 10px 30px; background-color: #fff; border: 1px solid gray; color: #333; text-decoration: none; border-radius: 4px;">
                    一覧へ戻る
                </a>
            </div>
        </main>
    </div>
</body>
</html>