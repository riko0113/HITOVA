document.addEventListener("DOMContentLoaded", function() {
    // HTML要素のdata属性からJSON文字列を取得
    var container = document.getElementById("profileDataContainer");
    var jsonString = container.getAttribute("data-profile");

    if (jsonString) {
        var profile = JSON.parse(jsonString);

        // 各要素へ値をセット
        document.getElementById("nameArea").innerText = profile.last_name + " " + profile.first_name;
        document.getElementById("birthDateArea").innerText = profile.birth_date;
        document.getElementById("desiredJobArea").innerText = profile.desired_job_type_name;
        document.getElementById("desiredEmploymentArea").innerText = profile.desired_employment_type_name;
        document.getElementById("workHistoryArea").innerText = profile.work_history;
        document.getElementById("highestEducationArea").innerText = profile.highest_education;
        document.getElementById("desiredLocationArea").innerText = profile.desired_work_location;
    }
});