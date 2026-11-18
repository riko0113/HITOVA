<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>

<style>
    .main-layout {
        display: flex;
        align-items: stretch;
        min-height: calc(100vh - 60px);
    }

    .main-content {
        flex: 1;
        min-width: 0;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .tuuti {
        width: 100%;
        text-align: center;
    }

    .notification-container {
        width: 800px;
        max-width: 95%;
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
        text-align: left;
    }

    .notification-item p {
        margin: 0 0 10px;
        white-space: pre-wrap;
        text-align: left;
    }

    .notification-item span {
        font-size: 13px;
        color: #777;
    }

    .no-notification {
        text-align: center;
    }
</style>

<div class="main-layout">

    <%@ include file="../../jobSeekerSidebar.jsp" %>

    <div class="main-content">

        <div class="tuuti">
            <h1>通知</h1>
        </div>

        <div class="notification-container">

            <!-- 通知が0件の場合 -->
            <c:if test="${empty notificationList}">
                <p class="no-notification">
                    現在、通知はありません。
                </p>
            </c:if>

            <!-- 通知を繰り返し表示 -->
            <c:forEach var="notification" items="${notificationList}">

                <div class="notification-item">

                    <h3>
                        ${notification.receiver_type_name}のお知らせ
                    </h3>

                    <p>
                        <c:out value="${notification.message}" />
                    </p>

                    <span>

                        <fmt:parseDate
                            value="${notification.send_at}"
                            pattern="yyyy-MM-dd'T'HH:mm"
                            var="sendDate" />

                        <fmt:formatDate
                            value="${sendDate}"
                            pattern="yyyy/MM/dd HH:mm" />

                    </span>

                </div>

            </c:forEach>

        </div>

    </div>

</div>

</body>
</html>