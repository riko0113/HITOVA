/**
 * 登録求人一覧画面用スクリプト (job-list.js)
 */

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

// DOM読み込み完了後にイベントリスナーを登録
document.addEventListener("DOMContentLoaded", function() {

    // 公開・非公開の切り替え
    document.querySelectorAll(".public-toggle").forEach(function(toggle) {
        toggle.addEventListener("change", function() {
            const card = toggle.closest(".job-card");
            const statusText = card.querySelector(".status-text");
            const jobId = card.getAttribute("data-job-id");

            if (!toggle.checked) {
                // 非公開にする前に確認（モーダルを表示）
                toggle.checked = true; // 一旦チェックを戻す（確認画面でOKを押したら外すため）
                openModal(card, "hide");
            } else {
                // 公開に戻す場合
                statusText.textContent = "公開中";
                statusText.classList.remove("hidden");

                // サーブレットへ非同期で「公開（true）」を送信
                updateJobStatus(jobId, true);
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
    const cancelButton = document.getElementById("cancel-button");
    if (cancelButton) {
        cancelButton.addEventListener("click", function() {
            document.getElementById("confirm-modal").style.display = "none";
            selectedCard = null;
            selectedAction = "";
        });
    }

    // 確認ボタン（「非公開にする」または「削除する」の確認モーダル）
    const confirmButton = document.getElementById("confirm-button");
    if (confirmButton) {
        confirmButton.addEventListener("click", function() {
            if (selectedCard === null) {
                return;
            }

            const jobId = selectedCard.getAttribute("data-job-id");

            if (selectedAction === "hide") {
                // --- 非公開にする処理 ---
                updateJobStatus(jobId, false);

                const toggle = selectedCard.querySelector(".public-toggle");
                const statusText = selectedCard.querySelector(".status-text");

                toggle.checked = false;
                statusText.textContent = "非公開";
                statusText.classList.add("hidden");

            } else if (selectedAction === "delete") {
                // --- 削除する処理 ---
                // JobDeleteAction サーブレットへ非同期で削除リクエストを送信
                deleteJob(jobId, selectedCard);
            }

            document.getElementById("confirm-modal").style.display = "none";
            selectedCard = null;
            selectedAction = "";
        });
    }

    // 編集ボタン
    document.querySelectorAll(".edit-button").forEach(function (button) {
    button.addEventListener("click", function () {

        window.location.href = `/job/JobDisplay.action?job_id=${encodeURIComponent(jobId)}`;
    	});

	});
});

// サーブレットへステータスを送信する共通関数（公開・非公開用）
function updateJobStatus(jobId, status) {
    const url = '/job/JobStatus.action'; 

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        },
        body: 'job_id=' + encodeURIComponent(jobId) + '&status=' + encodeURIComponent(status)
    })
    .then(response => {
        if (!response.ok) {
            alert('ステータスの更新に失敗しました。');
        }
    })
    .catch(error => {
        console.error('通信エラー:', error);
        alert('通信エラーが発生しました。');
    });
}

// サーブレットへ削除を依頼する共通関数（JobDeleteAction用）
function deleteJob(jobId, cardElement) {
    const url = '/job/JobDelete.action'; 

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        },
        body: 'job_id=' + encodeURIComponent(jobId)
    })
    .then(response => {
        if (response.ok) {
            // 通信が成功したら画面からカードを削除
            cardElement.remove();
        } else {
            alert('求人の削除に失敗しました。');
        }
    })
    .catch(error => {
        console.error('通信エラー:', error);
        alert('通信エラーが発生しました。');
    });
}