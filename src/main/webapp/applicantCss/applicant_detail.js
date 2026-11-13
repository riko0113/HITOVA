document.addEventListener("DOMContentLoaded", function() {
    // HTML要素のdata属性からJSON文字列を取得
    var container = document.getElementById("profileDataContainer");
    
    if (container) {
        var jsonString = container.getAttribute("data-profile");

        if (jsonString) {
            try {
                var profile = JSON.parse(jsonString);

                // 各要素へ値をセット
                document.getElementById("nameArea").innerText = (profile.last_name || "") + " " + (profile.first_name || "");
                
                // 生年月日のフォーマット整え（必要に応じて。例: "2002-05-15T00:00:00" から日付部分のみ抽出する場合）
                var birthDate = profile.birth_date ? profile.birth_date.split("T")[0] : "";
                document.getElementById("birthDateArea").innerText = birthDate;
                
                document.getElementById("desiredJobArea").innerText = profile.desired_job_type_name || "";
                document.getElementById("desiredEmploymentArea").innerText = profile.desired_employment_type_name || "";
                document.getElementById("workHistoryArea").innerText = profile.work_history || "";
                document.getElementById("highestEducationArea").innerText = profile.highest_education || "";
                document.getElementById("desiredLocationArea").innerText = profile.desired_work_location || "";
                
            } catch (e) {
                console.error("JSONのパースに失敗しました:", e);
            }
        } else {
            console.warn("data-profile 속性が空です。");
        }
    } else {
        console.warn("profileDataContainer が見つかりません。");
    }
});