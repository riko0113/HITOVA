
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>通知送信</title>

<style>
h1 {
    text-align: center;
}

.notification-container {
    width: 600px;
    margin: 30px auto;
}

.form-group {
    margin-bottom: 25px;
}

label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
}

select, textarea {
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 5px;
}

textarea {
    height: 180px;
    resize: vertical;
}

button {
    display: block;
    margin: 0 auto;
    padding: 10px 35px;
    border: none;
    border-radius: 5px;
    background-color: #007bff;
    color: white;
    cursor: pointer;
}

button:hover {
    background-color: #0056b3;
}

#resultMessage {
    text-align: center;
    margin-bottom: 20px;
    font-weight: bold;
}
</style>
</head>

<body>

<h1>全体通知送信</h1>

<div class="notification-container">

    <!-- 送信結果 -->
    <div id="resultMessage"></div>

    <form id="notificationForm"
        action="${pageContext.request.contextPath}/Notification/NotificationSend.action"
        method="post">

        <!-- 宛先種別 -->
        <div class="form-group">
            <label for="receiverType">宛先種別</label>

            <select id="receiverType" name="receiverType" required>
                <option value="">選択してください</option>
                <option value="user">求職者</option>
                <option value="company">企業</option>
                <option value="all">全体（求職者・企業）</option>
            </select>
        </div>

        <!-- メッセージ -->
        <div class="form-group">
            <label for="message">メッセージ</label>

            <textarea id="message"
                name="message"
                placeholder="通知内容を入力してください"
                required></textarea>
        </div>

        <!-- 送信ボタン -->
        <button type="submit">送信</button>

    </form>
</div>

<script>

// フォーム
const form = document.getElementById("notificationForm");

// 送信結果を表示する場所
const resultMessage = document.getElementById("resultMessage");

// 送信ボタンが押されたとき
form.addEventListener("submit", async function(event) {

    // 通常の画面遷移を止める
    event.preventDefault();

    // 送信確認
    const answer = confirm("通知を送信しますか？");

    if (!answer) {
        return;
    }

    // 入力されたデータを取得
    const formData = new URLSearchParams(new FormData(form));

    try {

        // Actionにデータを送信
        const response = await fetch(form.action, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
            },
            body: formData
        });

        // Actionから返された結果を取得
        const responseText = await response.text();

        console.log("送信する宛先種別：", formData.get("receiverType"));
        console.log("HTTPステータス:", response.status);
        console.log("Actionからの返事:", responseText);

        // JSONに変換
        let result;

        try {
            result = JSON.parse(responseText);
        } catch (error) {
            console.error("JSON変換エラー:", error);
            console.error("受け取った文字列:", responseText);

            resultMessage.textContent = "サーバーからの返事を読み取れませんでした。";
            resultMessage.style.color = "red";
            return;
        }

        // メッセージを表示
        resultMessage.textContent = result.message;

        if (response.ok && result.success) {

            resultMessage.style.color = "green";

            // 入力内容をクリア
            form.reset();

        } else {

            resultMessage.style.color = "red";

        }

    } catch (error) {

        console.error("送信エラー:", error);

        resultMessage.textContent = "送信に失敗しました。";
        resultMessage.style.color = "red";

    }

});

</script>

</body>
</html>
