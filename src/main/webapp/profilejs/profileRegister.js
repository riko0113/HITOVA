document.addEventListener("DOMContentLoaded", function() {

    // JSONファイルのURL
    const jsonUrl = "/HITOVA/js/area.json";

    // 現住所のセレクトボックス
    const currentPrefecture = document.getElementById("current_prefecture");
    const currentMunicipality = document.getElementById("current_municipality");

    // 希望勤務地のセレクトボックス
    const desiredPrefecture = document.getElementById("workplace_prefecture");
    const desiredMunicipality = document.getElementById("workplace_municipality");

    // 都道府県と市区町村を設定する関数
    function setupAreaSelect(prefectureSelect, municipalitySelect, data) {

        Object.keys(data).forEach(function(prefecture) {
            prefectureSelect.add(new Option(prefecture, prefecture));
        });

        prefectureSelect.addEventListener("change", function() {
            municipalitySelect.innerHTML = '<option value="">----------</option>';

            (data[this.value] || []).forEach(function(city) {
                municipalitySelect.add(new Option(city, city));
            });
        });
    }

    // JSONファイルを読み込む
    fetch(jsonUrl)
        .then(function(response) {
            if (!response.ok) throw new Error("JSON読み込み失敗");
            return response.json();
        })
        .then(function(data) {
            if (currentPrefecture && currentMunicipality) {
                setupAreaSelect(currentPrefecture, currentMunicipality, data);
            }

            if (desiredPrefecture && desiredMunicipality) {
                setupAreaSelect(desiredPrefecture, desiredMunicipality, data);
            }
        })
        .catch(function() {
        });
});