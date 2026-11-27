<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (request.getAttribute("unionNotifLoaded") == null) {
        response.sendRedirect(request.getContextPath() + "/Notification/UnionAdminNotification.action");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>通知一覧 - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
            background-color: #ffffff;
            color: #222222;
        }
        .main-layout {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 48px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .page-container {
            max-width: 820px;
            margin: 0 auto;
        }
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 12px;
            margin-bottom: 24px;
            border-bottom: 1px solid #333333;
        }
        .page-title {
            font-size: 18px;
            font-weight: bold;
            margin: 0;
        }
        .btn-back {
            padding: 6px 14px;
            border: 1px solid #888888;
            background-color: #ffffff;
            color: #222222;
            text-decoration: none;
            font-size: 13px;
        }
        .btn-back:hover {
            background-color: #f5f5f5;
        }
        .notification-list {
            display: flex;
            flex-direction: column;
            gap: 14px;
        }
        .notification-item {
            border: 1px solid #cccccc;
            padding: 16px 20px;
            background-color: #ffffff;
        }
        .notification-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 8px;
            font-size: 12px;
            color: #666666;
        }
        .notification-tag {
            display: inline-block;
            padding: 2px 8px;
            border: 1px solid #cccccc;
            background-color: #f5f5f5;
            color: #333333;
            font-weight: bold;
        }
        .notification-message {
            margin: 0;
            font-size: 14px;
            line-height: 1.6;
            white-space: pre-wrap;
            color: #222222;
        }
    </style>
</head>
<body>

    <%@ include file="/unionAdminHeader.jsp" %>

    <div class="main-layout">
        <%@ include file="/unionAdminSidebar.jsp" %>

        <main class="main-content">
            <div class="page-container">

                <div class="page-header">
                    <h2 class="page-title">通知一覧</h2>
                    <a href="${pageContext.request.contextPath}/toppage/unionAdminTop.jsp" class="btn-back">トップへ戻る</a>
                </div>

                <c:choose>
                    <c:when test="${empty notificationList}">
                        <p style="color: #666666; font-size: 14px; text-align: center; padding: 36px; border: 1px solid #dddddd;">
                            現在、通知はありません。
                        </p>
                    </c:when>
                    <c:otherwise>
                        <div class="notification-list">
                            <c:forEach var="notification" items="${notificationList}">
                                <div class="notification-item">
                                    <div class="notification-meta">
                                        <span class="notification-tag"><c:out value="${notification.receiver_type_name}" />向けのお知らせ</span>
                                        <span>${notification.send_at}</span>
                                    </div>
                                    <p class="notification-message"><c:out value="${notification.message}" /></p>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </main>
    </div>

</body>
</html>