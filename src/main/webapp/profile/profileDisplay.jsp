<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="profile-container">

    <h2>プロフィール</h2>

    <!-- 編集・戻るボタン -->
    <div class="register-button-area">
        <button type="button"
            onclick="location.href='/HITOVA/profile/ProfileEdit.action'">
            編集
        </button>
    </div>
    
    <!-- 基本情報 -->
	<h3>基本情報</h3>

	<div class="form-group">
   		<label>本人確認</label>
    	<span>${profile.verification_status}</span>
	</div>

	<div class="form-group">
    	<label>氏名</label>
    	<span>姓：${profile.last_name}　　名：${profile.first_name}</span>
	</div>

	<div class="form-group">
    	<label>カナ：</label>
    	<span>姓：${profile.last_name_kana}　　名：${profile.first_name_kana}</span>
	</div>

	<div class="form-group">
    	<label>生年月日：</label>
    	<span>${profile.birth_date}</span>
	</div>

	<div class="form-group">
    	<label>性別：</label>
    	<span>${profile.gender ? '男性' : '女性'}</span>
	</div>


    <!-- 住所情報 -->
    <h3>住所情報</h3>

    <div class="form-group">
        <label>郵便番号：</label>
        <span>${profile.current_postal_code}</span>
    </div>

    <div class="form-group">
        <label>現住所：</label>
        <span>${profile.current_address}</span>
    </div>

    <!-- 希望条件 -->
    <h3>希望条件</h3>

    <div class="form-group">
        <label>希望雇用形態：</label>
        <span>${profile.desired_employment_type}</span>
    </div>

    <div class="form-group">
        <label>希望勤務地：</label>
        <span>${profile.desired_work_location}</span>
    </div>

    <div class="form-group">
        <label>希望職種：</label>
        <span>${profile.desired_job_type}</span>
    </div>

    <!-- 学歴・職歴 -->
    <h3>学歴・職歴</h3>

    <div class="form-group">
        <label>学校名：</label>
        <span>${profile.school_name}</span>
    </div>

    <div class="form-group">
        <label>学部・学科：</label>
        <span>${profile.faculty_name}</span>
    </div>

    <div class="form-group">
        <label>卒業年月または卒業見込年月：</label>
        <span>${profile.graduation_date}</span>
    </div>

    <div class="form-group">
        <label>卒業・卒業見込：</label>
        <span>${profile.graduation_status}</span>
    </div>

    <div class="form-group">
        <label>職歴：</label>
        <span>${profile.work_history}</span>
        <p>※企業での職務経験がなければアルバイトも可</p>
    </div>

</div>