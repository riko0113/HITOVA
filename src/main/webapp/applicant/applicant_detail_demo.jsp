<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- CSSファイルのパスは環境に合わせて調整してください -->
<link href="../applicantCss/applicant_detail.css" rel="stylesheet">

<div class="header-container">
    <h2>求職者詳細</h2>
    <a href="#" target="_blank" class="btn-view btn-report">通報</a>
</div>

<!-- 基本情報 -->
<h3>基本情報</h3>
<ul>
    <li>氏名：<span id="nameArea">山田 太郎</span> さん</li>
    <li>生年月日：<span id="birthDateArea">1995年5月15日</span></li>
    <li>本人確認状況：済</li>
</ul>

<!-- プロフィール -->
<h3>プロフィール</h3>
<ul>
    <li>希望職種：<span id="desiredJobArea">フロントエンドエンジニア</span></li>
    <li>希望雇用形態：<span id="desiredEmploymentArea">正社員</span></li>
    <li>経歴／職歴：<span id="workHistoryArea">WEB制作会社で3年のコーディング経験があります。</span></li>
    <li>最終学歴：<span id="highestEducationArea">〇〇大学 卒業</span></li>
    <li>希望勤務地：<span id="desiredLocationArea">東京都、リモート</span></li>
</ul>

<!-- スキル -->
<h3>スキル</h3>
<ul>
    <li>登録したスキル：
        <ul>
            <li>HTML / CSS (実務3年)</li>
            <li>JavaScript (実務2年)</li>
            <li>React (基礎レベル)</li>
        </ul>
    </li>
</ul>

<!-- 保有資格 -->
<h3>保有資格</h3>
<ul>
    <li>登録した資格：
        <ul>
            <li>基本情報技術者試験</li>
            <li>Webクリエイター能力認定試験エキスパート</li>
        </ul>
    </li>
</ul>

<!-- 実績 -->
<h3>実績</h3>
<ul>
    <li>登録した実績：
        <ul>
            <li>ECサイトのリニューアルプロジェクトでコンバージョン率を15%改善</li>
            <li>社内向け業務効率化ツールのフロントエンド実装を担当</li>
        </ul>
    </li>
</ul>

<!-- ポートフォリオ -->
<h3>ポートフォリオ</h3>
<ul>
    <li>登録した作品：
        <div style="margin-top: 5px;">
            <span>ポートフォリオサイト（個人開発）</span><br>
            <a href="#" target="_blank" class="btn-view">作品を見る</a>
        </div>
        <div style="margin-top: 10px;">
            <span>タスク管理アプリ</span><br>
            <a href="#" target="_blank" class="btn-view">作品を見る</a>
        </div>
    </li>
</ul>

<!-- メッセージ -->
<h3>メッセージ</h3>
<a href="#" target="_blank" class="btn-view">メッセージを送る</a>

<form action="#" method="post" style="margin-top: 20px;">
    <input type="submit" name="search" value="戻る">
</form>

<script src="../applicantCss/applicant_detail.js"></script>