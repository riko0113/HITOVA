<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>登録求人一覧</title>

<style>
body {
    font-family: sans-serif;
    margin: 30px;
    background-color: #1e9f8ec;
}

/* ページタイトル */
h1 {
    text-align: center;
    width: 950px;
    margin: 0 auto 30px;
    padding: 15px;
    background-color: #eeeeee;
    border: 2px solid #949fa8;
    border-radius: 5px;
}

/* 求人一覧 */
.job-list {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
    width: 950px;
    margin: 0 auto;
    align-items: start;
}

/* 求人カード */
.job-card {
    background-color: #f5f5f5;
    border: 2px solid #949fa8;
    border-radius: 5px;
    padding: 20px;
}

/* 公開・非公開エリア */
.status-area {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 20px;
}

/* 切り替えスイッチ */
.switch {
    position: relative;
    display: inline-block;
    width: 50px;
    height: 26px;
}

.switch input {
    opacity: 0;
    width: 0;
    height: 0;
}

.slider {
    position: absolute;
    cursor: pointer;
    inset: 0;
    background-color: #aaa;
    border-radius: 26px;
    transition: 0.3s;
}

.slider::before {
    position: absolute;
    content: "";
    height: 20px;
    width: 20px;
    left: 3px;
    bottom: 3px;
    background-color: white;
    border-radius: 50%;
    transition: 0.3s;
}

/* 公開中のスイッチ */
.switch input:checked + .slider {
    background-color: #4caf50;
}

/* 非公開のスイッチ */
.switch input:not(:checked) + .slider {
    background-color: #e57373;
}

.switch input:checked + .slider::before {
    transform: translateX(24px);
}

/* 公開・非公開の文字 */
.status-text {
    font-weight: bold;
    color: #4caf50;
}

/* 非公開の文字 */
.status-text.hidden {
    color: #e57373;
}

/* 求人情報 */
.job-info h2 {
    text-align: center;
    background-color: #bdbdbd;
    padding: 12px;
    margin: 0 0 15px;
    border-radius: 5px;
    font-size: 19px;
}

.job-info p {
    text-align: left;
    margin: 10px 0;
    padding: 8px;
    background-color: #eeeeee;
    border-radius: 5px;
    font-size: 15px;
}

/* ボタンエリア */
.button-area {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    gap: 15px;
    margin-top: 20px;
}

/* 削除ボタン */
.delete-button {
    border: none;
    background: none;
    cursor: pointer;
    font-size: 23px;
}

/* 編集ボタン */
.edit-button {
    padding: 9px 25px;
    background-color: #C8E6C9;
    color: black;
    border: 1px solid #888888;
    border-radius: 5px;
    cursor: pointer;
    font-size: 15px;
}

/* 確認画面 */
.modal {
    display: none;
    position: fixed;
    inset: 0;
    background-color: rgba(0, 0, 0, 0.5);
    justify-content: center;
    align-items: center;
    z-index: 10;
}

.modal-content {
    background-color: white;
    padding: 30px;
    border: 2px solid #949fa8;
    border-radius: 8px;
    width: 350px;
    max-width: 85%;
    text-align: center;
}

.modal-content h3 {
    margin-top: 0;
}

.target-title {
    font-weight: bold;
    margin: 15px 0;
    overflow-wrap: anywhere;
}

.modal-buttons {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 25px;
}

.cancel-button,
.confirm-button {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.cancel-button {
    background-color: #bdbdbd;
}

.confirm-button {
    background-color: #e57373;
    color: white;
}

/* 画面幅が狭い場合 */
@media (max-width: 1000px) {
    .job-list {
        width: 100%;
        grid-template-columns: repeat(2, 1fr);
    }

    h1 {
        width: auto;
    }
}

@media (max-width: 600px) {
    .job-list {
        grid-template-columns: 1fr;
    }
}
</style>
</head>

<body>

<h1>登録求人一覧</h1>

<div class="job-list" id="job-list">

    <!-- 求人1 -->
    <div class="job-card">
        <div class="status-area">
            <label class="switch">
                <input type="checkbox" class="public-toggle" checked>
                <span class="slider"></span>
            </label>
            <span class="status-text">公開中</span>
        </div>

        <div class="job-info">
            <h2>一般事務スタッフ</h2>
            <p>勤務地：大阪府大阪市</p>
            <p>時給：1,200円</p>
        </div>

        <div class="button-area">
            <button type="button" class="delete-button" title="削除">🗑</button>
            <button type="button" class="edit-button">編集</button>
        </div>
    </div>

    <!-- 求人2 -->
    <div class="job-card">
        <div class="status-area">
            <label class="switch">
                <input type="checkbox" class="public-toggle" checked>
                <span class="slider"></span>
            </label>
            <span class="status-text">公開中</span>
        </div>

        <div class="job-info">
            <h2>カフェスタッフ</h2>
            <p>勤務地：大阪府堺市</p>
            <p>時給：1,150円</p>
        </div>

        <div class="button-area">
            <button type="button" class="delete-button" title="削除">🗑</button>
            <button type="button" class="edit-button">編集</button>
        </div>
    </div>

    <!-- 求人3 -->
    <div class="job-card">
        <div class="status-area">
            <label class="switch">
                <input type="checkbox" class="public-toggle" checked>
                <span class="slider"></span>
            </label>
            <span class="status-text">公開中</span>
        </div>

        <div class="job-info">
            <h2>販売スタッフ</h2>
            <p>勤務地：兵庫県神戸市</p>
            <p>時給：1,300円</p>
        </div>

        <div class="button-area">
            <button type="button" class="delete-button" title="削除">🗑</button>
            <button type="button" class="edit-button">編集</button>
        </div>
    </div>

</div>

<!-- 確認画面 -->
<div class="modal" id="confirm-modal">
    <div class="modal-content">
        <h3 id="modal-heading"></h3>
        <p id="modal-message"></p>
        <p class="target-title" id="target-title"></p>

        <div class="modal-buttons">
            <button type="button" class="cancel-button" id="cancel-button">
                キャンセル
            </button>

            <button type="button" class="confirm-button" id="confirm-button">
                はい
            </button>
        </div>
    </div>
</div>

<script>

// 確認画面で操作する求人を保存
let selectedCard = null;
let selectedAction = "";

// 確認画面を開く関数
function openModal(card, action) {

    selectedCard = card;
    selectedAction = action;

    const title = card.querySelector(".job-info h2").textContent;

    document.getElementById("target-title").textContent =
        "対象求人：" + title;

    if (action === "hide") {

        document.getElementById("modal-heading").textContent =
            "非公開にしますか？";

        document.getElementById("modal-message").textContent =
            "非公開にすると応募者からの閲覧・応募ができなくなります。よろしいですか？";

    } else {

        document.getElementById("modal-heading").textContent =
            "削除しますか？";

        document.getElementById("modal-message").textContent =
            "削除すると求人一覧から見られなくなります。よろしいですか？";
    }

    document.getElementById("confirm-modal").style.display = "flex";
}

// 公開・非公開の切り替え
document.querySelectorAll(".public-toggle").forEach(function(toggle) {

    toggle.addEventListener("change", function() {

        const card = toggle.closest(".job-card");
        const statusText = card.querySelector(".status-text");

        if (!toggle.checked) {

            // 非公開にする前に確認
            toggle.checked = true;
            openModal(card, "hide");

        } else {

            // 公開に戻す
            statusText.textContent = "公開中";
            statusText.classList.remove("hidden");
        }
    });
});

// 削除ボタン
document.querySelectorAll(".delete-button").forEach(function(button) {

    button.addEventListener("click", function() {

        const card = button.closest(".job-card");
        openModal(card, "delete");

    });
});

// キャンセルボタン
document.getElementById("cancel-button").addEventListener("click", function() {

    document.getElementById("confirm-modal").style.display = "none";

    selectedCard = null;
    selectedAction = "";

});

// 確認ボタン
document.getElementById("confirm-button").addEventListener("click", function() {

    if (selectedCard === null) {
        return;
    }

    if (selectedAction === "hide") {

        // 非公開にする
        const toggle = selectedCard.querySelector(".public-toggle");
        const statusText = selectedCard.querySelector(".status-text");

        toggle.checked = false;
        statusText.textContent = "非公開";

        // 非公開の文字を赤色にする
        statusText.classList.add("hidden");

    } else if (selectedAction === "delete") {

        // 求人カードを削除
        selectedCard.remove();

    }

    document.getElementById("confirm-modal").style.display = "none";

    selectedCard = null;
    selectedAction = "";

});

// 編集ボタン
document.querySelectorAll(".edit-button").forEach(function(button) {

    button.addEventListener("click", function() {

        // 編集画面への遷移先は後で設定

    });

});

</script>

</body>
</html>