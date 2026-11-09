<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- CSSファイルのパスは環境に合わせて調整してください -->
<link href="../applicantCss/applicant_list.css" rel="stylesheet">

<h2>応募者一覧</h2>

    <div class="user-list-container">
        <!-- ダミーデータ1（本人確認済み・長めの名前） -->
        <div class="user-row">
            <div class="user-left">
                <div class="user-icon">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                </div>
                <div class="user-info">
                    <h3>山田 太郎（テストユーザー）</h3>
                    <p>生年月日：1995年5月15日</p>
                    <p>応募日時：2026-06-01 14:30</p>
                    <p>✓ 本人確認済み</p>
                </div>
            </div>
            <div>
                <form action="#" method="get">
                    <input type="submit" value="詳細を見る" class="detail-btn">
                </form>
            </div>
        </div>

        <!-- ダミーデータ2（本人確認前） -->
        <div class="user-row">
            <div class="user-left">
                <div class="user-icon">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                </div>
                <div class="user-info">
                    <h3>鈴木 花子</h3>
                    <p>生年月日：2000年12月25日</p>
                    <p>応募日時：2026-06-02 09:15</p>
                    <p>○ 本人確認前</p>
                </div>
            </div>
            <div>
                <form action="#" method="get">
                    <input type="submit" value="詳細を見る" class="detail-btn">
                </form>
            </div>
        </div>

    </div>