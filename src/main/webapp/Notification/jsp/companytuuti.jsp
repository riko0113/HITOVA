<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../../head.jsp" %>
<%@ include file="../../companyHeader.jsp" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="main-layout">

    <%@ include file="../../companySidebar.jsp" %>

    <div class="main-content">

        <div class="tuuti">通知</div>

        <div class="notification-container">

            <c:if test="${empty notificationList}">
                <p>現在、通知はありません。</p>
            </c:if>

            <c:forEach var="notification" items="${notificationList}">

                <div class="notification-item">

                    <h3>${notification.receiver_type_name}のお知らせ</h3>

                    <p><c:out value="${notification.message}" /></p>

                    <span>${notification.send_at}</span>

                </div>

            </c:forEach>

        </div>

    </div>

</div>