<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>通知 - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
            background-color: #f8f9fa;
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
        .tuuti-container {
            max-width: 820px;
            margin: 0 auto;
        }
        .tuuti {
            font-size: 20px;
            font-weight: bold;
            padding-bottom: 12px;
            margin-bottom: 24px;
            border-bottom: 1px solid #333333;
        }
        .notification-container {
            display: flex;
            flex-direction: column;
            gap: 14px;
        }
        .notification-item {
            border: 1px solid #cccccc;
            background-color: #ffffff;
            padding: 18px 22px;
        }
        .notification-item h3 {
            font-size: 16px;
            font-weight: bold;
            margin: 0 0 8px 0;
            color: #111111;
        }
        .notification-item p {
            font-size: 14px;
            margin: 0 0 10px 0;
            line-height: 1.6;
            color: #222222;
        }
        .notification-item span {
            display: block;
            font-size: 12px;
            color: #666666;
        }
    </style>
</head>
<body>

    <%@ include file="/companyHeader.jsp" %>

    <div class="main-layout">

        <%@ include file="/companySidebar.jsp" %>

        <div class="main-content">
            <div class="tuuti-container">
                <div class="tuuti">通知</div>

                <div class="notification-container">
                    <c:if test="${empty notificationList}">
                        <p style="color: #666666; padding: 32px 0; text-align: center; border: 1px solid #dddddd;">
                            現在、通知はありません。
                        </p>
                    </c:if>

                    <c:forEach var="notification" items="${notificationList}">
                        <div class="notification-item">
                            <h3><c:out value="${notification.receiver_type_name}" />のお知らせ</h3>
                            <p><c:out value="${notification.message}" /></p>
                            <span>${notification.send_at}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

    </div>

</body>
</html>