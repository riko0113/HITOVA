
document.addEventListener("DOMContentLoaded", function () {
    const addButton = document.getElementById("addQualificationButton");
    const qualificationArea = document.getElementById("customQualificationArea");
    const clearButton = document.getElementById("clearButton");

    if (addButton && qualificationArea) {
        addButton.addEventListener("click", function () {
            qualificationArea.style.display =
                qualificationArea.style.display === "none" ? "block" : "none";
        });
    }

    if (clearButton) {
        clearButton.addEventListener("click", function () {
            document.querySelectorAll(".search-condition select").forEach(s => s.selectedIndex = -1);
            document.getElementById("custom_qualification").value = "";
            if (qualificationArea) qualificationArea.hidden = true;
        });
    }
});
