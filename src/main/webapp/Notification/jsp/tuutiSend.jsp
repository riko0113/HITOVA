
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

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

.hidden {
    display: none;
}

#resultMessage {
    text-align: center;
    margin-bottom: 20px;
    font-weight: bold;
}
</style>

</head>

<body>

<h1>通知送信</h1>

<div class="notification-container">

    <!-- 送信結果を表示する場所 -->
    <div id="resultMessage"></div>

    <form id="notificationForm"
          action="${pageContext.request.contextPath}/notification"
          method="post">

        <!-- 宛先種別 -->
        <div class="form-group">

            <label for="receiverType">宛先種別</label>

            <select id="receiverType" name="receiverType" required>
                <option value="">選択してください</option>
                <option value="user">求職者</option>
                <option value="company">企業アカウント</option>
                <option value="all">全体</option>
            </select>

        </div>

        <!-- 送信する人 -->
        <div class="form-group hidden" id="receiverGroup">

            <label>送信する人</label>

            <!-- 求職者一覧 -->
            <select id="userSelect" name="receiverAccountId" disabled>

                <option value="">送信先を選択してください</option>

                <c:forEach var="receiver" items="${userList}">
                    <option value="${receiver.accountId}">
                        <c:out value="${receiver.name}"/>
                    </option>
                </c:forEach>

            </select>

            <!-- 企業一覧 -->
            <select id="companySelect" name="receiverAccountId" disabled>

                <option value="">送信先を選択してください</option>

                <c:forEach var="receiver" items="${companyList}">
                    <option value="${receiver.accountId}">
                        <c:out value="${receiver.name}"/>
                    </option>
                </c:forEach>

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

// 宛先種別の選択欄
const receiverType = document.getElementById("receiverType");

// 送信する人の欄
const receiverGroup = document.getElementById("receiverGroup");

// 求職者の選択欄
const userSelect = document.getElementById("userSelect");

// 企業の選択欄
const companySelect = document.getElementById("companySelect");

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

    // 「いいえ」を押した場合
    if (!answer) {
        return;
    }

    // 入力されたデータを取得
    const formData = new URLSearchParams(new FormData(form));

    try {

        // Servletにデータを送信
        const response = await fetch(form.action, {

            method: "POST",

            headers: {
                "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
            },

            body: formData

        });

        // Servletから返された結果を取得
        const responseText = await response.text();

        // 送信内容を確認
        console.log("送信する宛先種別：", formData.get("receiverType"));

        for (const [key, value] of formData.entries()) {
            console.log(key, "=", value);
        }

        // Servletからの返事を確認
        console.log("HTTPステータス:", response.status);
        console.log("Servletからの返事:", responseText);

        // JSONに変換
        const result = JSON.parse(responseText);

        // メッセージを表示
        resultMessage.textContent = result.message;

        if (result.success) {

            resultMessage.style.color = "green";

            // 入力内容をクリア
            form.reset();

            userSelect.disabled = true;
            companySelect.disabled = true;

            receiverGroup.classList.add("hidden");

        } else {

            resultMessage.style.color = "red";

        }

    } catch (error) {

        console.error("送信エラー:", error);

        resultMessage.textContent = "送信に失敗しました。";
        resultMessage.style.color = "red";

    }

});


// 宛先種別が変更されたとき
receiverType.addEventListener("change", function() {

    // 両方の選択欄を無効にする
    userSelect.disabled = true;
    companySelect.disabled = true;

    // 選択を解除する
    userSelect.value = "";
    companySelect.value = "";

    // 必須入力を解除する
    userSelect.required = false;
    companySelect.required = false;

    // 求職者が選択された場合
    if (this.value === "user") {

        receiverGroup.classList.remove("hidden");

        userSelect.disabled = false;
        userSelect.required = true;

    }

    // 企業が選択された場合
    else if (this.value === "company") {

        receiverGroup.classList.remove("hidden");

        companySelect.disabled = false;
        companySelect.required = true;

    }

    // 全体または未選択の場合
    else {

        receiverGroup.classList.add("hidden");

    }

});

</script>

</body>
</html>
