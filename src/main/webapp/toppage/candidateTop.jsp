<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>求職者トップページ - HITOVA</title>
    <style>
        /* 全体レイアウト */
        body {
            margin: 0;
            padding: 0;
            font-family: "Hiragino Kaku Gothic ProN", "Yu Gothic", "Meiryo", sans-serif;
            color: #333333;
            background-color: #f8f9fa;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 60px);
            min-width: 960px;
        }
        .main-content-area {
            flex: 1;
            padding: 24px 30px;
            background-color: #ffffff;
            box-sizing: border-box;
        }

        /* ①② 上部検索・バナー領域 */
        .top-hero-section {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 20px 24px;
            border: 1px solid #002B49;
            background-color: #ffffff;
            margin-bottom: 24px;
            gap: 20px;
        }
        .search-container {
            display: flex;
            align-items: center;
            gap: 8px;
            flex: 1;
            max-width: 480px;
        }
        .search-box-wrapper {
            display: flex;
            align-items: center;
            border: 1px solid #002B49;
            border-radius: 4px;
            padding: 0 10px;
            background-color: #ffffff;
            flex: 1;
            height: 38px;
        }
        .search-icon {
            font-size: 16px;
            margin-right: 8px;
            color: #555;
            user-select: none;
        }
        .search-input {
            border: none;
            outline: none;
            width: 100%;
            font-size: 14px;
        }
        .btn-search {
            height: 38px;
            padding: 0 20px;
            background-color: #ffffff;
            color: #002B49;
            border: 1px solid #002B49;
            border-radius: 4px;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            white-space: nowrap;
        }
        .btn-search:hover {
            background-color: #f0f4f8;
        }
        .banner-placeholder {
            width: 320px;
            height: 80px;
            border: 1px solid #002B49;
            background-color: #f1f3f5;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
            font-weight: bold;
            color: #495057;
            letter-spacing: 2px;
        }

        /* ③④⑤⑥ ステータス表示ボックス */
        .status-section {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 16px;
            margin-bottom: 32px;
        }
        .status-box {
            border: 1px solid #002B49;
            background-color: #ffffff;
            padding: 14px 10px;
            text-align: center;
        }
        .status-title {
            font-size: 13px;
            font-weight: bold;
            color: #333333;
            margin-bottom: 6px;
        }
        .status-value {
            font-size: 22px;
            font-weight: bold;
            color: #002B49;
        }
        .status-unit {
            font-size: 12px;
            font-weight: normal;
            margin-left: 2px;
            color: #666666;
        }

        /* ⑦⑧ おすすめ求人ブロック */
        .section-label {
            font-size: 15px;
            font-weight: bold;
            color: #002B49;
            margin-bottom: 12px;
            padding-bottom: 4px;
            border-bottom: 2px solid #002B49;
            display: flex;
            justify-content: space-between;
            align-items: flex-end;
        }
        .section-more-link {
            font-size: 12px;
            font-weight: normal;
            color: #002B49;
            text-decoration: underline;
        }
        .job-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 16px;
        }
        .job-box {
            border: 1px solid #002B49;
            background-color: #ffffff;
            padding: 14px;
            box-sizing: border-box;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            min-height: 150px;
        }
        .job-tag {
            display: inline-block;
            font-size: 11px;
            border: 1px solid #002B49;
            color: #002B49;
            padding: 1px 6px;
            margin-bottom: 8px;
            width: fit-content;
        }
        .job-name {
            font-size: 13px;
            font-weight: bold;
            color: #111111;
            margin-bottom: 8px;
            line-height: 1.4;
        }
        .job-detail-row {
            font-size: 11px;
            color: #555555;
            margin-bottom: 2px;
        }
        .job-link-btn {
            display: block;
            margin-top: 10px;
            padding: 5px 0;
            text-align: center;
            border: 1px solid #002B49;
            background-color: #f8f9fa;
            color: #002B49;
            text-decoration: none;
            font-size: 11px;
            font-weight: bold;
        }
        .job-link-btn:hover {
            background-color: #e9ecef;
        }
    </style>
</head>
<body>

    <!-- webapp直下のヘッダー -->
    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <!-- webapp直下のサイドバー -->
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content-area">

            <!-- ①② 検索欄 & 画像バナー -->
            <div class="top-hero-section">
                <form action="${pageContext.request.contextPath}/jobsearchServlet/JobSearchExecute.action" method="post" class="search-container">
                    <div class="search-box-wrapper">
                      <input type="text" name="keyword" class="search-input" placeholder="検索">
                    </div>
                    <button type="submit" class="btn-search">検索</button>
                </form>

                <div class="banner-placeholder">
                    画像
                </div>
            </div>

            <!-- ③④⑤⑥ ステータス表示 -->
            <div class="status-section">
                <div class="status-box">
                    <div class="status-title">エントリー件数</div>
                    <div class="status-value">2<span class="status-unit">件</span></div>
                </div>
                <div class="status-box">
                    <div class="status-title">書類選考中</div>
                    <div class="status-value">1<span class="status-unit">件</span></div>
                </div>
                <div class="status-box">
                    <div class="status-title">面接調整中</div>
                    <div class="status-value">1<span class="status-unit">件</span></div>
                </div>
                <div class="status-box">
                    <div class="status-title">内定数</div>
                    <div class="status-value">0<span class="status-unit">件</span></div>
                </div>
            </div>

            <!-- ⑦ おすすめ求人 -->
            <div class="section-label">
                <span>おすすめ求人</span>
                <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp" class="section-more-link">求人一覧へ</a>
            </div>

            <!-- ⑧ 求人カード -->
            <div class="job-grid">
                <!-- 3001: Webエンジニア -->
                <div class="job-box">
                    <div>
                        <span class="job-tag">正社員</span>
                        <div class="job-name">Webエンジニア募集</div>
                        <div class="job-detail-row">給与: 月給25万～35万円</div>
                        <div class="job-detail-row">勤務地: 大阪府大阪市中央区</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp" class="job-link-btn">詳細</a>
                </div>

                <!-- 3002: 製造スタッフ -->
                <div class="job-box">
                    <div>
                        <span class="job-tag">パート・バイト</span>
                        <div class="job-name">製造スタッフ募集</div>
                        <div class="job-detail-row">給与: 時給1,300円～</div>
                        <div class="job-detail-row">勤務地: 大阪府大阪市北区</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp" class="job-link-btn">詳細</a>
                </div>

                <!-- 求人枠 3 -->
                <div class="job-box">
                    <div>
                        <span class="job-tag">正社員</span>
                        <div class="job-name">業務システム開発エンジニア</div>
                        <div class="job-detail-row">給与: 月給26万円〜</div>
                        <div class="job-detail-row">勤務地: 大阪府大阪市</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp" class="job-link-btn">詳細</a>
                </div>

                <!-- 求人枠 4 -->
                <div class="job-box">
                    <div>
                        <span class="job-tag">正社員</span>
                        <div class="job-name">社内SE・サーバー運用管理</div>
                        <div class="job-detail-row">給与: 月給24万円〜</div>
                        <div class="job-detail-row">勤務地: 大阪府堺市</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/jobsearch/jobSearch.jsp" class="job-link-btn">詳細</a>
                </div>
            </div>

        </main>
    </div>

</body>
</html>