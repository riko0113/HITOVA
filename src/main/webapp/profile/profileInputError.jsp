
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<link rel="stylesheet" href="/HITOVA/profileCss/profileInputError.css">
<script src="/HITOVA/js/profileRegister.js"></script>

<div class="profile-container">

    <h2>プロフィール</h2>

    <form action="/HITOVA/profile/ProfileRegister.action" method="post">

        <!-- ボタン -->
        <div class="register-button-area">
            <button type="button" onclick="history.back()">キャンセル</button>
            <button type="submit" name="register" value="register">更新or登録</button>
        </div>

        <!-- 基本情報 -->
        <h3>○ 基本情報</h3>

        <div class="form-group">
            <label>本人確認：</label>
            <span>未 or 済</span>
        </div>

        <div class="form-group">
            <label>氏名：</label>

            <span>姓</span>
            <input type="text" name="last_name"
                value="${param.last_name}" maxlength="50">

            <span>名</span>
            <input type="text" name="first_name"
                value="${param.first_name}" maxlength="50">

            <p class="input-note">※50字以内での入力をお願いします</p>
            <p class="error">${lastNameError}</p>
            <p class="error">${firstNameError}</p>
        </div>

        <div class="form-group">
            <label>カナ：</label>

            <span>姓</span>
            <input type="text" name="last_name_kana"
                value="${param.last_name_kana}" maxlength="50">

            <span>名</span>
            <input type="text" name="first_name_kana"
                value="${param.first_name_kana}" maxlength="50">

            <p class="input-note">※50字以内での入力をお願いします</p>
            <p class="error">${lastNameKanaError}</p>
            <p class="error">${firstNameKanaError}</p>
        </div>

        <div class="form-group">
            <label>生年月日：</label>
            <input type="date" name="birth_date"
                value="${param.birth_date}">
            <p class="error">${birthDateError}</p>
        </div>

        <div class="form-group">
            <label>性別：</label>

            <label>
                <input type="radio" name="gender" value="true"
                    ${param.gender == 'true' ? 'checked' : ''}> 男性
            </label>

            <label>
                <input type="radio" name="gender" value="false"
                    ${param.gender == 'false' ? 'checked' : ''}> 女性
            </label>

            <p class="error">${genderError}</p>
        </div>


        <!-- 現住所 -->
        <h3>○ 現住所</h3>

        <div class="form-group">
            <label>郵便番号：</label>

            <input type="text" name="current_postal_code"
                value="${param.current_postal_code}"
                maxlength="50" placeholder="ハイフンなし">

            <p class="input-note">※7字で入力してください</p>
            <p class="error">${postalCodeSpaceError}</p>
            <p class="error">${postalCodeHyphenError}</p>
            <p class="error">${postalCodeLengthError}</p>
        </div>

        <div class="form-group">
            <label>都道府県：</label>

            <select name="current_prefecture" id="current_prefecture">
                <option value="">----------</option>
            </select>

            <p class="error">${prefectureError}</p>
        </div>

        <div class="form-group">
            <label>市区町村：</label>

            <select name="current_municipality" id="current_municipality">
                <option value="">----------</option>
            </select>

            <p class="error">${municipalityError}</p>
        </div>

        <div class="form-group">
            <label>それ以降の住所：</label>

            <input type="text" name="current_address_detail"
                value="${param.current_address_detail}" maxlength="50">

            <p class="input-note">※50字以内で入力をお願いいたします</p>
            <p class="error">${addressError}</p>
        </div>


        <!-- 希望条件 -->
        <h3>○ 希望条件</h3>

        <div class="form-group">
            <label>希望雇用形態：</label>

            <label>
                <input type="radio" name="desired_employment_type_id"
                    value="regular"
                    ${param.desired_employment_type_id == 'regular' ? 'checked' : ''}>
                正社員
            </label>

            <label>
                <input type="radio" name="desired_employment_type_id"
                    value="contract"
                    ${param.desired_employment_type_id == 'contract' ? 'checked' : ''}>
                契約社員
            </label>

            <label>
                <input type="radio" name="desired_employment_type_id"
                    value="partTime"
                    ${param.desired_employment_type_id == 'partTime' ? 'checked' : ''}>
                アルバイト
            </label>

            <p class="error">${workingStatusError}</p>
        </div>

        <div class="form-group">
            <label>希望勤務地：</label>

            <label>都道府県</label>
            <select name="workplace_prefecture" id="workplace_prefecture">
                <option value="">----------</option>
            </select>

            <p class="error">${workplacePrefectureError}</p>

            <label>市区町村</label>
            <select name="workplace_municipality" id="workplace_municipality">
                <option value="">----------</option>
            </select>

            <p class="error">${workplaceMunicipalityError}</p>
        </div>

        <div class="form-group">
            <label>希望職種：</label>

            <select name="desired_job_type_id">
                <option value="">----------</option>
                <!-- 職種データをここに設定 -->
            </select>

            <p class="error">${jobTypeError}</p>
        </div>


        <!-- 学歴・職歴 -->
        <h3>○ 学歴・職歴</h3>

        <div class="form-group">
            <label>学歴：</label>

            <label>学校名</label>
            <input type="text" name="school_name"
                value="${param.school_name}" maxlength="30">

            <p class="input-note">※30字以内での入力をお願いします</p>
            <p class="error">${schoolNameError}</p>
        </div>

        <div class="form-group">
            <label>学部・学科：</label>

            <input type="text" name="faculty_name"
                value="${param.faculty_name}" maxlength="40">

            <p class="input-note">※40字以内での入力をお願いします</p>
            <p class="error">${facultyNameError}</p>
        </div>

        <div class="form-group">
            <label>卒業年月または卒業見込年月：</label>

            <input type="date" name="graduation_date"
                value="${param.graduation_date}">

            <label>
                <input type="radio" name="graduation_status"
                    value="graduated"
                    ${param.graduation_status == 'graduated' ? 'checked' : ''}>
                卒業
            </label>

            <label>
                <input type="radio" name="graduation_status"
                    value="will-graduate"
                    ${param.graduation_status == 'will-graduate' ? 'checked' : ''}>
                卒業見込
            </label>

            <p class="error">${graduationDateError}</p>
            <p class="error">${graduationError}</p>
        </div>

        <div class="form-group">
            <label>職歴：</label>

            <textarea name="work_history" maxlength="500">${param.work_history}</textarea>

            <p class="input-note">
                （企業での職務経験がなければアルバイトも可）<br>
                ※500字以内での入力をお願いします<br>
                ※入力されていません、職歴がなければ「なし」と入力をしてください
            </p>

            <p class="error">${workHistoryError}</p>
        </div>

    </form>

</div>

