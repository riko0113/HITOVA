document.addEventListener("DOMContentLoaded", function () {
    // フォーム要素とエラー表示要素を取得
    const form = document.querySelector("form");
    const workNameInput = document.getElementById("workName");
    const descriptionInput = document.getElementById("description");
    const fileInput = document.querySelector('input[type="file"]'); 
    
    const workNameError = document.getElementById("workNameError");
    const descriptionError = document.getElementById("descriptionError");
    const fileError = document.getElementById("fileError"); 

    // フォームが送信される時のイベントリスナー
    form.addEventListener("submit", function (event) {
        let isValid = true;

        // --- 作品名のバリデーション ---
        const workName = workNameInput.value.trim();
        workNameError.textContent = ""; // エラーメッセージを一旦クリア

        if (workName === "") {
            workNameError.textContent = "作品名を入力してください。";
            isValid = false;
        } else if (workName.length > 50) {
            workNameError.textContent = "作品名は50文字以内で入力してください。";
            isValid = false;
        }

        // --- 説明文のバリデーション ---
        const description = descriptionInput.value.trim();
        descriptionError.textContent = ""; // エラーメッセージを一旦クリア

        if (description.length > 300) {
            descriptionError.textContent = "説明文は300文字以内で入力してください。";
            isValid = false;
        }

        // --- ファイルサイズのバリデーション ---
        if (fileError) {
            fileError.textContent = ""; // エラーメッセージを一旦クリア
        }

        if (fileInput && fileInput.files.length > 0) {
            const file = fileInput.files[0];
            const fileSize = file.size; // バイト単位
            const maxSize = 100 * 1024 * 1024; // 100MBをバイトに換算

            if (fileSize > maxSize) {
                if (fileError) {
                    fileError.textContent = "ファイルサイズは100MB以下にしてください。";
                }
                isValid = false;
            }
        }

        // バリデーションにひっかかった場合は送信をブロックする
        if (!isValid) {
            event.preventDefault();
        }
    });
});