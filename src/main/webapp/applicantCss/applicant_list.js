document.addEventListener("DOMContentLoaded", function () {
    // 生年月日を表示する要素をすべて取得
    const birthDateElements = document.querySelectorAll(".birth-date");

    birthDateElements.forEach(function (element) {
        const profileJsonStr = element.getAttribute("data-profile");

        if (!profileJsonStr) {
            element.textContent = "未設定";
            return;
        }

        try {
            // JSON文字列をオブジェクトに変換
            const profileObj = JSON.parse(profileJsonStr);

            // birth_dateプロパティが存在するか確認
            if (profileObj && profileObj.birth_date) {
                element.textContent = profileObj.birth_date;
            } else {
                element.textContent = "未設定";
            }
        } catch (e) {
            console.error("プロフィール情報のJSONパースに失敗しました:", e);
            element.textContent = "取得エラー";
        }
    });
});