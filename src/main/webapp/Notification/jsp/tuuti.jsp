
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>通知</title>

<style>
h1 {
    text-align: center;
}

.notification-container {
    width: 800px;
    margin: 30px auto;
}

.notification-item {
    padding: 20px;
    margin-bottom: 15px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: white;
}

.notification-item h3 {
    margin: 0 0 10px;
}

.notification-item p {
    margin: 0 0 10px;
    white-space: pre-wrap;
}

.notification-item span {
    font-size: 13px;
    color: #777;
}
p {
text-align: center;
}
</style>
</head>

<body>

<h1>通知</h1>

<div class="notification-container">

    <!-- 通知が0件の場合 -->
    <c:if test="${empty notificationList}">
        <p>現在、通知はありません。</p>
    </c:if>

    <!-- 通知を繰り返し表示 -->
    <c:forEach var="notification" items="${notificationList}">

        <div class="notification-item">

            <h3>${notification.receiver_type_name}のお知らせ</h3>

            <p><c:out value="${notification.message}" /></p>

            <span>
                <fmt:parseDate value="${notification.send_at}"
                    pattern="yyyy-MM-dd'T'HH:mm"
                    var="sendDate" />
                <fmt:formatDate value="${sendDate}"
                    pattern="yyyy/MM/dd HH:mm" />
            </span>

        </div>

    </c:forEach>

</div>

</body>
</html>
