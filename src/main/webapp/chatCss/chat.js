document.addEventListener("DOMContentLoaded", function () {
    var config = window.CHAT_CONFIG || {};
    var contextPath = config.contextPath || "";
    var selectedRoomId = config.selectedRoomId || null;
    var mySenderType = (config.mySenderType !== undefined) ? Boolean(config.mySenderType) : true;
    var initialMessages = Array.isArray(config.initialMessages) ? config.initialMessages : [];

    // ルームごとに配列へ整理
    var messagesByRoom = {};
    for (var i = 0; i < initialMessages.length; i++) {
        var m = initialMessages[i];
        if (m.roomId === null || m.roomId === undefined) continue;
        if (!messagesByRoom[m.roomId]) {
            messagesByRoom[m.roomId] = [];
        }
        messagesByRoom[m.roomId].push(m);
    }

    // 吹き出しDOMを生成する関数
    function createBubbleElement(msg) {
        var bubble = document.createElement("div");
        var isMine = (Boolean(msg.senderType) === mySenderType);
        bubble.className = "chat-bubble " + (isMine ? "chat-right" : "chat-left");
        if (msg.messageId !== null && msg.messageId !== undefined) {
            bubble.dataset.messageId = msg.messageId;
        }

        var p = document.createElement("p");
        p.className = "chat-text";
        p.textContent = msg.sendContent;

        var span = document.createElement("span");
        span.className = "chat-time";
        span.textContent = msg.sendAt || "";

        bubble.appendChild(p);
        bubble.appendChild(span);
        return bubble;
    }

    // 指定ルームの会話を描画
    function renderRoomMessages(roomId) {
        var area = document.getElementById("chatMessagesArea");
        if (!area) return;
        area.innerHTML = "";

        var list = messagesByRoom[roomId] || [];
        if (list.length === 0) {
            var p = document.createElement("p");
            p.className = "chat-empty";
            p.textContent = "メッセージはまだありません。";
            area.appendChild(p);
            return;
        }

        for (var j = 0; j < list.length; j++) {
            area.appendChild(createBubbleElement(list[j]));
        }
        area.scrollTop = area.scrollHeight;
    }

    // 左側のルームクリック時にJSで即切り替え（URLも更新）
    var roomItems = document.querySelectorAll(".chat-room-item[data-room-id]");
    for (var k = 0; k < roomItems.length; k++) {
        roomItems[k].addEventListener("click", function (e) {
            e.preventDefault();
            var roomId = Number(this.dataset.roomId);
            selectedRoomId = roomId;

            var allItems = document.querySelectorAll(".chat-room-item");
            for (var n = 0; n < allItems.length; n++) {
                allItems[n].classList.remove("active");
            }
            this.classList.add("active");

            var hiddenInput = document.getElementById("selectedRoomIdInput");
            if (hiddenInput) hiddenInput.value = roomId;

            // ルーム未選択状態から切り替えた場合でも入力欄・送信ボタンを有効化
            var contentInput = document.getElementById("sendContentInput");
            var submitBtn = document.querySelector("#chatInputForm button[type='submit']");
            if (contentInput) contentInput.disabled = false;
            if (submitBtn) submitBtn.disabled = false;

            renderRoomMessages(roomId);

            var url = new URL(window.location.href);
            url.searchParams.set("roomId", roomId);
            history.replaceState(null, "", url.toString());
        });
    }

    // 新着メッセージ取得用（サーバー側に JSON を返すエンドポイントを用意したら有効化）
    function getLastMessageId(roomId) {
        var list = messagesByRoom[roomId] || [];
        if (list.length === 0) return 0;
        var last = list[list.length - 1];
        return last.messageId || 0;
    }

    function pollNewMessages() {
        if (!selectedRoomId) return;
        var lastId = getLastMessageId(selectedRoomId);

        var url = contextPath + "/chat/GetMessageAjax.action?roomId=" + encodeURIComponent(selectedRoomId)
            + "&lastMessageId=" + encodeURIComponent(lastId);

        fetch(url)
            .then(function (res) {
                if (!res.ok) return null;
                return res.json();
            })
            .then(function (newMsgs) {
                if (!Array.isArray(newMsgs) || newMsgs.length === 0) return;

                if (!messagesByRoom[selectedRoomId]) {
                    messagesByRoom[selectedRoomId] = [];
                }

                var area = document.getElementById("chatMessagesArea");
                if (!area) return;

                var emptyMsg = area.querySelector(".chat-empty");
                if (emptyMsg) emptyMsg.remove();

                for (var pIdx = 0; pIdx < newMsgs.length; pIdx++) {
                    var msg = newMsgs[pIdx];
                    var exists = false;
                    for (var qIdx = 0; qIdx < messagesByRoom[selectedRoomId].length; qIdx++) {
                        if (messagesByRoom[selectedRoomId][qIdx].messageId === msg.messageId) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) continue;

                    messagesByRoom[selectedRoomId].push(msg);
                    if (Number(msg.roomId) === Number(selectedRoomId)) {
                        area.appendChild(createBubbleElement(msg));
                    }
                }
                area.scrollTop = area.scrollHeight;
            })
            .catch(function (err) {
                console.error("新着取得エラー:", err);
            });
    }

    // 10秒おきに新着チェック（新着エンドポイント作成後にコメント解除して使用）
	 setInterval(pollNewMessages, 10000);
});