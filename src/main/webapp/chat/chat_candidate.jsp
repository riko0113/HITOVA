<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<%@ include file="../JobSeekerHeader.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link href="../chatCss/chat.css" rel="stylesheet">

<div class="main-layout">
<%@ include file="../JobSeekerSidebar.jsp" %>

<div class="main-content">
	<div class="chat-container-wrapper" data-selected-room-id="${selectedRoomId}">
        
        <!-- 左側：会話相手・ルーム一覧 -->
        <div class="chat-room-list">
            <c:choose>
                <c:when test="${not empty roomList}">
                    <c:forEach var="roomMsg" items="${roomList}">
                        <a href="${pageContext.request.contextPath}/chat/candidate/ChatList.action?roomId=${roomMsg.room_id}"
                           class="chat-room-item ${roomMsg.room_id == selectedRoomId ? 'active' : ''}"
                           data-room-id="${roomMsg.room_id}">
                            <div class="room-name">
                                ルーム #<c:out value="${roomMsg.room_id}" />
                                <c:if test="${not empty roomMsg.chatRoom}">
                                    <span class="room-sub">
                                        (企業: <c:out value="${roomMsg.chatRoom.company_name}" />)
                                    </span>
                                </c:if>
                            </div>
                            <div class="room-last-message">
                                <c:out value="${roomMsg.send_content}" />
                            </div>
                            <div class="room-time">
                                <c:out value="${roomMsg.send_at}" />
                            </div>
                        </a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="chat-room-item">チャットルームがありません</div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- 右側：チャットやり取りエリア -->
        <div class="chat-main-pane">
            <!-- メッセージ表示エリア -->
            <div class="chat-messages-area" id="chatMessagesArea">
                <c:choose>
                    <c:when test="${not empty messageList}">
                        <c:forEach var="msg" items="${messageList}">
                            <div class="chat-bubble ${msg.sender_type ? 'chat-right' : 'chat-left'}"
                                 data-message-id="${msg.message_id}">
                                <p class="chat-text"><c:out value="${msg.send_content}" /></p>
                                <span class="chat-time"><c:out value="${msg.send_at}" /></span>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:when test="${not empty selectedRoomId}">
                        <p class="chat-empty">メッセージはまだありません。</p>
                    </c:when>
                    <c:otherwise>
                        <p class="chat-empty">表示するチャットルームを選択してください。</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- 下部入力フォーム ＆ 送信ボタン -->
            <form class="chat-input-area" id="chatInputForm"
                  action="${pageContext.request.contextPath}/chat/candidate/SendMessage.action" method="post">
                <input type="hidden" name="roomId" id="selectedRoomIdInput" value="${selectedRoomId}">
                <input type="text" name="sendContent" id="sendContentInput" class="form-control" max_length=500
                       placeholder="メッセージを入力..."
                       <c:if test="${empty selectedRoomId}">disabled</c:if> required>
                <button type="submit" class="btn btn-primary"
                        <c:if test="${empty selectedRoomId}">disabled</c:if>>送信</button>
            </form>
        </div>

    </div>
</div>
</div>

<!-- 初期表示時に取得した全ルームのメッセージをJS側でも保持 -->
<script>
window.CHAT_CONFIG = {
	    contextPath: "${pageContext.request.contextPath}",
	    selectedRoomId: ${not empty selectedRoomId ? selectedRoomId : 'null'},
	    mySenderType: true,
	    initialMessages: [
	        <c:forEach var="msg" items="${allMessages}" varStatus="st">
	        {
	            messageId: ${msg.message_id != null ? msg.message_id : 'null'},
	            roomId: ${msg.room_id != null ? msg.room_id : 'null'},
	            sendContent: "<c:out value='${fn:escapeXml(msg.send_content)}' />",
	            sendAt: "<c:out value='${msg.send_at}' />",
	            senderType: ${msg.sender_type}
	        }<c:if test="${!st.last}">,</c:if>
	        </c:forEach>
	    ]
	};
</script>
<script src="${pageContext.request.contextPath}/chatCss/chat.js"></script>
<%@ include file="../footer.jsp" %>