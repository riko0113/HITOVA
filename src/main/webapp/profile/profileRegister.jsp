<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link rel="stylesheet" href="/HITOVA/profileCss/profileInputError.css">
<script src="/HITOVA/js/profileRegister.js"></script>


<div class="profile-container">

    <h2>プロフィール登録</h2>

    <form action="/HITOVA/profile/ProfileRegister.action" method="post">
    

        <!-- 登録ボタン -->
        <div class="register-button-area">
            <button type="submit" name="register" value="register">登録 </button>
        </div>

        <!-- 基本情報 -->
        <h3>基本情報</h3>

        <div class="form-group">
            <label>本人確認：</label>
            <span>未</span>
        </div>

        <div class="form-group">
            <label for="last_name">氏名（姓）：</label>
            <input type="text" id="last_name"  name="last_name" maxlength="20" placeholder="20字以内" required>
        </div>

        <div class="form-group">
            <label for="first_name">氏名（名）：</label>
            <input type="text" id="first_name" name="first_name" maxlength="20" placeholder="20字以内" required>
        </div>

        <div class="form-group">
            <label for="last_name_kana">氏名（姓カナ）：</label>
            <input type="text" id="last_name_kana" name="last_name_kana" maxlength="20" placeholder="20字以内" required>
        </div>

        <div class="form-group">
            <label for="first_name_kana">氏名（名カナ）：</label>
            <input type="text" id="first_name_kana" name="first_name_kana" maxlength="20" placeholder="20字以内" required>
        </div>

        <div class="form-group">
            <label for="birth_date">生年月日：</label>
            <input type="date" id="birth_date" name="birth_date" required>
        </div>

        <div class="form-group">
            <label>性別：</label>

            <label>
                <input type="radio" id="gender_man" name="gender" value="true" required> 男性 </label>

            <label>
                <input type="radio" id="gender_woman" name="gender" value="false"> 女性 </label>
        </div>


        <!-- 住所情報 -->
        <h3>住所情報</h3>

        <div class="form-group">
            <label for="current_postal_code">郵便番号：</label>
            <input type="text" id="current_postal_code" name="current_postal_code" maxlength="7" placeholder="ハイフンなし">
        </div>

        <div class="form-group">
            <label for="current_prefecture">都道府県：</label>
            <select id="current_prefecture" name="current_prefecture"><option value="">----------</option></select>
        </div>

        <div class="form-group">
            <label for="current_municipality">市区町村：</label>
            <select id="current_municipality" name="current_municipality"><option value="">----------</option></select>
        </div>

        <div class="form-group">
            <label for="current_address_detail">それ以降の番地等：</label>
            <input type="text" id="current_address_detail" name="current_address_detail" maxlength="50" placeholder="50字以内" required>
        </div>


        <!-- 希望条件 -->
        <h3>希望条件</h3>

        <div class="form-group">
            <label>希望雇用形態</label>

            <label>
                <input type="radio" id="employment_regular" name="desired_employment_type_id" value="regular" required> 正社員
            </label>

            <label>
                <input type="radio" id="employment_contract" name="desired_employment_type_id" value="contract">  契約社員
            </label>

            <label>
                <input type="radio" id="employment_part_time" name="desired_employment_type_id" value="partTime">アルバイト
            </label>
        </div>

        <div class="form-group">
            <label for="workplace_prefecture">希望勤務地（都道府県）：</label>
            <select id="workplace_prefecture" name="workplace_prefecture"> <option value="">----------</option></select>
        </div>

        <div class="form-group">
            <label for="workplace_municipality">希望勤務地（市区町村）：</label>
            <select id="workplace_municipality" name="workplace_municipality"> <option value="">----------</option></select>
        </div>

        <div class="form-group">
            <label for="desired_job_type_id">希望職種：</label>
            <select id="desired_job_type_id" name="desired_job_type_id"><option value="">----------</option></select>
        </div>


        <!-- 学歴・職歴 -->
        <h3>学歴・職歴</h3>

        <div class="form-group">
            <label for="school_name">学校名：</label>
            <input type="text" id="school_name" name="school_name" maxlength="30" placeholder="30字以内">
        </div>

        <div class="form-group">
            <label for="faculty_name">学部・学科：</label>
            <input type="text" id="faculty_name" name="faculty_name" maxlength="40" placeholder="40字以内">
        </div>

        <div class="form-group">
            <label for="graduation_date">卒業年月または卒業見込年月：</label>
            <input type="date" id="graduation_date" name="graduation_date">
        </div>

        <div class="form-group">
            <label>卒業・卒業見込：</label>

            <label>
                <input type="radio" id="graduation_completed" name="graduation_status" value="graduated">卒業
            </label>

            <label>
                <input type="radio" id="graduation_expected" name="graduation_status" value="will-graduate">卒業見込
            </label>
        </div>

        <div class="form-group">
            <label for="work_history">職歴：</label>
            <textarea id="work_history" name="work_history" maxlength="500" placeholder="500字以内"></textarea>

            <p>※企業での職務経験がなければアルバイトも可</p>
        </div>

    </form>
    

</div>


