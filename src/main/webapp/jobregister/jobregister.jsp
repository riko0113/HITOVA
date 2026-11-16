<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>求人登録</title>
</head>
<style>
/* 各入力フォームの下に余白をつける */
.form,
.form2,
.form-row,
.form5,
.form6,
.form7,
.form8 {
    margin-bottom: 15px;
}
.form{
display: flex;
flex-direction: column;
gap: 5px;
}

.form label {
font-weight: bold;
}

.form input{
width: 97%;
padding: 17px;
}

.form2{
display: flex;
flex-direction: column;
gap: 5px;
}

.form-row {
    display: flex;
    flex-direction: row;
    gap: 20px;
}

.form3, .form4 {
    display: flex;
    flex-direction: column;
    gap: 5px;
    flex: 1;
}

.form3 input, .form4 input {
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
}

.form3 label, .form4 label {
    font-weight: bold;
}

.form5 textarea, .form6 textarea {
    width:  100%;
    padding: 17px;
    box-sizing: border-box;
}

.form6-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.sikaku-area {
    display: flex;
    align-items: center;
    gap: 5px;
}

.form6 textarea {
    width: 100%;
    padding: 17px;
    box-sizing: border-box;
}
select {
    color: gray;
}

select:valid {
    color: black;
}

.form7 textarea{
    width:  100%;
    padding: 17px;
    box-sizing: border-box;
}

.salary-row {
    display: flex;
    flex-direction: row;
    gap: 10px;
    align-items: center;
}

.salary-row select {
    width: 20%;
    padding: 10px;
}

.salary-row input {
    width: 80%;
    padding: 10px;
    box-sizing: border-box;
}

/* 加入保険全体 */
.form8 {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

/* チェックボックスを横並びにする */
.insurance-options {
    display: flex;
    gap: 20px;
}

/* それぞれの項目 */
.insurance-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex: 1;
    border: 1px solid gray;
    border-radius: 10px;
    padding: 10px 15px;
    font-size: 17px;
}

/* チェックボックス */
.insurance-item input {
    margin: 0;
}

/* 求人画像アップロード全体 */
.form9 {
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-bottom: 15px;
}

/* 画像アップロード枠 */
.image-upload {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 150px;
    border: 2px dashed gray;
    border-radius: 5px;
    cursor: pointer;
    box-sizing: border-box;
}

/* ファイル選択ボタンを非表示 */
.image-upload input {
    display: none;
}

.hutosi{
font-weight: bold;  /* 太字 */
font-size: 20px;    /* 文字サイズ */
}

.submit-area button {
    padding: 12px 40px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
    border: none;
    border-radius: 5px;
    background-color: skyblue;
    color: white;
}

.submit-area {
    display: flex;
    justify-content: flex-end;
    width: 100%;
    margin-top: 30px;
    margin-bottom: 20px;
}

/* 選考プロセス全体 */
.form10 {
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-bottom: 15px;
}

/* 入力欄と削除ボタン */
.process-row {
    display: flex;
    align-items: center;
    gap: 10px;
}

.process-row span {
    width: 25px;
    font-weight: bold;
}
/* 入力欄 */
.process-row input {
    flex: 1;
    padding: 12px;
    box-sizing: border-box;
}

/* 削除ボタン */
.delete-process {
    padding: 10px 15px;
    color: white;
    background-color: #e57373;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

/* 追加ボタン */
#add-process {
    display: block;
    margin-left: auto;
    padding: 5px 15px;
    font-size: 16px;
}
</style>
<body>

<div class="hutosi">求人登録</div>

<h1>新規求人情報入力</h1>

<div class="form">
<label >自動入力(登録情報)
</label>
<input type="text">
</div>

<b>基本情報</b>

<!-- タイトル表示 -->
  <div class="form5">
    <label>求人タイトル</label><br>
      <textarea></textarea>
  </div>

<!-- カテゴリ -->
<div class="form-row">

    <div class="form3">
        <label>カテゴリ</label>
        <select name="category" required>
            <option value="" disabled selected hidden>カテゴリ</option>
        </select>
    </div>
<!-- 雇用形態 -->
    <div class="form4">
        <label>雇用形態</label>
        <select name="employmentType" required>
            <option value="" disabled selected hidden>雇用形態</option>
        </select>
    </div>

</div>

<!--仕事内容入力フォーム-->
  <div class="form5">
    <label>仕事内容</label><br>
      <textarea></textarea>
  </div>

<div class="form6">
    <div class="form6-header">
        <label>応募資格・条件</label>
        <div class="sikaku-area">
            <label>資格検索・貼り付け</label>
            <select name="large">
                <option value="" disabled selected hidden>大分類</option>
            </select>
            <select name="middle">
                <option value="" disabled selected hidden>中分類</option>
            </select>
            <select name="small">
                <option value="" disabled selected hidden>小分類</option>
            </select>
            <button>貼り付け</button>
        </div>
    </div>
    <textarea></textarea>
</div>

<!--募集人数入力フォーム-->
  <div class="form7">
    <label>募集人数</label><br>
      <textarea></textarea>
  </div>
  
  <div class="hutosi">勤務条件・待遇</div>
  
  <!--勤務地入力フォーム-->
  <div class="form7">
    <label>勤務地(住所)</label><br>
      <textarea></textarea>
  </div>
  
  <!-- 最寄り駅・アクセス -->
<div class="form-row">
  <div class="form3">
    <label>最寄り駅・アクセス</label>
      <input type="text">
  </div>
  
<!-- 入力フォーム -->
<div class="form4">
    <label>給与・手当</label>
    <div class="salary-row">
        <select name="large">
            <option value="" disabled selected hidden>時給</option>
        </select>
        <input type="text">円
    </div>
  </div>
</div>

<!-- 交通費 -->
<div class="form-row">
    <div class="form3">
        <label>交通費</label>
        <select name="koutuuhi" required>
            <option value="" disabled selected hidden>交通費</option>
        </select>
    </div>
<!-- 雇用形態 -->
    <div class="form4">
        <label>受動喫煙対策</label>
        <select name="smoke" required>
            <option value="" disabled selected hidden>受動喫煙対策</option>
        </select>
    </div>
</div>
<!--勤務時間・シフト入力フォーム-->
  <div class="form5">
    <label>勤務時間・シフト</label><br>
      <textarea></textarea>
  </div>

<!--休日・休暇入力フォーム-->
  <div class="form5">
    <label>休日・休暇</label><br>
      <textarea></textarea>
  </div>
 
 <!-- 加入保険チェックボックス -->
<div class="form8">
    <label>加入保険</label>
    <div class="insurance-options">
        <label class="insurance-item">
            雇用保険
            <input type="checkbox">
        </label>
        <label class="insurance-item">
            労災保険
            <input type="checkbox">
        </label>
        <label class="insurance-item">
            健康保険
            <input type="checkbox">
        </label>
        <label class="insurance-item">
            厚生年金
            <input type="checkbox">
        </label>
    </div>
</div>

<!--福利厚生・その他入力フォーム-->
  <div class="form5">
    <label>福利厚生・その他</label><br>
      <textarea></textarea>
  </div>
  
<div class="hutosi">アピール要素・選考の流れ</div>

<!-- 求人画像アップロード -->
<div class="form9">
    <label>求人画像</label>
    <label class="image-upload">
        画像をアップロード（10MBまで）
        <input type="file" accept="image/*">
    </label>
</div>

<!--職場の特徴・PR入力フォーム-->
  <div class="form5">
    <label>職場の特徴・PR</label><br>
      <textarea></textarea>
  </div>
  
<!-- 選考プロセス入力フォーム -->
<div class="form10">
    <label>選考プロセス</label>
   <div id="process-list">

    <div class="process-row">
        <span>1.</span>
        <input type="text">

        <button type="button" class="delete-process">×</button>
    </div>

</div>
    <button type="button" id="add-process">追加</button>
</div>

  <!--面接場所入力フォーム-->
  <div class="form5">
    <label>面接場所</label><br>
      <textarea></textarea>
  </div>
  
  <!-- 登録ボタン -->
<div class="submit-area">
    <button type="submit">求人を登録・公開する</button>
</div>
<script>
//選考プロセスの入力欄を管理する場所
const processList = document.getElementById("process-list");

// 番号を振り直す関数
function updateProcessNumbers() {
    document.querySelectorAll(".process-row span").forEach(function(item, index) {
        item.textContent = (index + 1) + ".";
    });
}

// 追加ボタンを押したときの処理
document.getElementById("add-process").addEventListener("click", function() {

    // 新しい行を作成
    const row = document.createElement("div");
    row.className = "process-row";

    // 番号を作成
    const number = document.createElement("span");

    // 入力欄を作成
    const input = document.createElement("input");
    input.type = "text";

    // 削除ボタンを作成
    const deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.textContent = "×";
    deleteButton.className = "delete-process";

    // 作成した部品を行に追加
    row.appendChild(number);
    row.appendChild(input);
    row.appendChild(deleteButton);

    // 入力欄の一覧に追加
    processList.appendChild(row);

    // 番号を振り直す
    updateProcessNumbers();
});

// 削除ボタンを押したときの処理
processList.addEventListener("click", function(event) {

    // 押された場所が削除ボタンか確認
    if (event.target.classList.contains("delete-process")) {

        // 押されたボタンがある行を削除
        event.target.closest(".process-row").remove();

        // 番号を振り直す
        updateProcessNumbers();
    }
});
</script>
</body>
</html>