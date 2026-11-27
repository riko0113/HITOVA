<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 共通ヘッダー・サイドバーの読み込み --%>
<%@ include file="../head.jsp" %>
<%@ include file="../JobSeekerHeader.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- チャット画面専用CSS --%>
<link href="${pageContext.request.contextPath}/chatCss/chat.css" rel="stylesheet">

<div class="main-layout">
<%@ include file="../JobSeekerSidebar.jsp" %>

<div class="main-content">
	<%-- 選択中ルームIDをJSからも参照できるようdata属性に保持 --%>
	<div class="chat-container-wrapper" data-selected-room-id="${selectedRoomId}">
        
        <!-- 左側：会話相手・ルーム一覧 -->
        <div class="chat-room-list">
            <c:choose>
                <c:when test="${not empty roomList}">
                    <c:forEach var="roomMsg" items="${roomList}">
                        <%-- ルーム切り替えリンク（現在選択中のルームにはactiveクラスを付与） --%>
                        <a href="${pageContext.request.contextPath}/chat/candidate/ChatList.action?roomId=${roomMsg.room_id}"
                           class="chat-room-item ${roomMsg.room_id == selectedRoomId ? 'active' : ''}"
                           data-room-id="${roomMsg.room_id}">
                            <div class="room-name">
                                ルーム #<c:out value="${roomMsg.room_id}" />
                                <%-- 企業名が存在する場合は併記 --%>
                                <c:if test="${not empty roomMsg.chatRoom}">
                                    <span class="room-sub">
                                        (企業: <c:out value="${roomMsg.chatRoom.company_name}" />)
                                    </span>
                                </c:if>
                            </div>
                            <div class="room-last-message">
                                <%-- 最新メッセージ（未送信ルームは代替文言を表示） --%>
                                <c:choose>
                                    <c:when test="${not empty roomMsg.send_content}">
                                        <c:out value="${roomMsg.send_content}" />
                                    </c:when>
                                    <c:otherwise>
                                        メッセージはまだありません
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="room-time">
                                <%-- 最終送信日時（未送信時は非表示） --%>
                                <c:if test="${not empty roomMsg.send_at}">
                                    <c:out value="${roomMsg.send_at}" />
                                </c:if>
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
                            <%-- sender_typeに応じて左右の吹き出し表示を切り替え（求職者側なのでtrueが右側） --%>
                            <div class="chat-bubble ${msg.sender_type ? 'chat-right' : 'chat-left'}"
                                 data-message-id="${msg.message_id}">
                                <p class="chat-text"><c:out value="${msg.send_content}" /></p>
                                <span class="chat-time"><c:out value="${msg.send_at}" /></span>
                            </div>
                        </c:forEach>
                    </c:when>
                    <%-- ルームは選択されているがメッセージが0件の場合 --%>
                    <c:when test="${not empty selectedRoomId}">
                        <p class="chat-empty">メッセージはまだありません。</p>
                    </c:when>
                    <%-- ルーム自体が未選択の場合 --%>
                    <c:otherwise>
                        <p class="chat-empty">表示するチャットルームを選択してください。</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- 下部入力フォーム ＆ 送信ボタン -->
            <form class="chat-input-area" id="chatInputForm"
                  action="${pageContext.request.contextPath}/chat/candidate/SendMessage.action" method="post">
                <%-- 送信先ルームIDをhiddenで送信 --%>
                <input type="hidden" name="roomId" id="selectedRoomIdInput" value="${selectedRoomId}">
                <%-- 未選択時は入力欄・送信ボタンを無効化 --%>
                <input type="text" name="sendContent" id="sendContentInput" class="form-control" maxlength="500"
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
    mySenderType: true, // 求職者側画面の送信者種別
    initialMessages: [
        <c:forEach var="msg" items="${allMessages}" varStatus="st">
        {
            messageId: ${msg.message_id != null ? msg.message_id : 'null'},
            roomId: ${msg.room_id != null ? msg.room_id : 'null'},
            sendContent: <c:out value="${msg.send_content}" />,
            sendAt: "<c:out value='${msg.send_at}' />",
            senderType: ${msg.sender_type != null ? msg.sender_type : 'null'}
        }<c:if test="${!st.last}">,</c:if>
        </c:forEach>
    ]
};
</script>
<%-- チャット動作用JS --%>
<script src="${pageContext.request.contextPath}/chatCss/chat.js"></script>
<%@ include file="../footer.jsp" %>