<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>企業トップページ - HITOVA</title>
    <style>
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

        /* 画像バナー */
        .top-hero-banner {
            width: 100%;
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
            margin-bottom: 24px;
            box-sizing: border-box;
        }

        /* ①〜④ ステータスボタン */
        .status-section {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 16px;
            margin-bottom: 32px;
        }
        .status-box-btn {
            border: 1px solid #002B49;
            background-color: #ffffff;
            padding: 14px 10px;
            text-align: center;
            text-decoration: none;
            color: inherit;
            display: block;
            box-sizing: border-box;
            transition: background-color 0.15s;
        }
        .status-box-btn:hover {
            background-color: #f0f4f8;
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

        /* 下部エリア */
        .bottom-content-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 24px;
        }

        /* ⑤ 通知履歴 */
        .notice-container {
            border: 1px solid #002B49;
            border-radius: 8px;
            padding: 18px 20px;
            min-height: 260px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .section-label {
            font-size: 15px;
            font-weight: bold;
            color: #002B49;
            margin-bottom: 12px;
            padding-bottom: 4px;
            border-bottom: 2px solid #002B49;
        }
        .notice-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 12px;
        }
        .notice-table th, .notice-table td {
            padding: 9px 4px;
            text-align: left;
            border-bottom: 1px dashed #dcdcdc;
        }
        .notice-table th {
            color: #666666;
            font-weight: bold;
            width: 120px;
            white-space: nowrap;
        }
        .notice-table td {
            color: #222222;
        }
        .notice-table tr:last-child td, .notice-table tr:last-child th {
            border-bottom: none;
        }

        /* 右側エリア（検索バー ＆ ボタン） */
        .right-action-container {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        /* ⑥⑦ スカウト検索 */
        .scout-search-form {
            display: flex;
            align-items: center;
            gap: 8px;
            width: 100%;
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
            box-sizing: border-box;
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

        /* ⑧⑨ アクションボタン */
        .action-button-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px;
            margin-top: 4px;
        }
        .action-card-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #002B49;
            border-radius: 4px;
            background-color: #ffffff;
            color: #002B49;
            text-decoration: none;
            font-size: 13px;
            font-weight: bold;
            height: 80px;
            box-sizing: border-box;
            cursor: pointer;
            transition: background-color 0.15s;
        }
        .action-card-btn:hover {
            background-color: #f0f4f8;
        }
    </style>
</head>
<body>

    <%@ include file="/companyHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/companySidebar.jsp" %>

        <main class="main-content-area">

            <!-- 画像バナー -->
            <div class="top-hero-banner">
                画像
            </div>

            <!-- ①〜④ ステータスボタン -->
            <div class="status-section">
                <a href="${pageContext.request.contextPath}/company/applicantList.jsp" class="status-box-btn" name="applicants">
                    <div class="status-title">応募者数</div>
                    <div class="status-value">3<span class="status-unit">名</span></div>
                </a>

                <a href="${pageContext.request.contextPath}/company/applicantList.jsp?status=1" class="status-box-btn" name="documentation">
                    <div class="status-title">書類選考数</div>
                    <div class="status-value">1<span class="status-unit">名</span></div>
                </a>

                <a href="${pageContext.request.contextPath}/company/applicantList.jsp?status=2" class="status-box-btn" name="interview">
                    <div class="status-title">面接者数</div>
                    <div class="status-value">1<span class="status-unit">名</span></div>
                </a>

                <a href="${pageContext.request.contextPath}/company/applicantList.jsp?status=3" class="status-box-btn" name="jobOffer">
                    <div class="status-title">内定者数</div>
                    <div class="status-value">1<span class="status-unit">名</span></div>
                </a>
            </div>

            <!-- 下部コンテンツ -->
            <div class="bottom-content-grid">

                <!-- ⑤ 通知履歴 -->
                <div class="notice-container">
                    <div class="section-label">通知履歴</div>
                    <table class="notice-table" id="noticeHistory">
                        <tbody>
                            <tr>
                                <th>2026/11/02 10:04</th>
                                <td>新しい応募が届いています。（佐藤 空 さん）</td>
                            </tr>
                            <tr>
                                <th>2026/11/01 15:30</th>
                                <td>求人掲載の審査が完了しました。</td>
                            </tr>
                            <tr>
                                <th>2026/10/28 09:00</th>
                                <td>システムメンテナンスのお知らせ</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <!-- 右側: ⑥⑦ 検索 ＆ ⑧⑨ アクションボタン -->
                <div class="right-action-container">

                    <form action="${pageContext.request.contextPath}/company/scoutSearch.action" method="post" class="scout-search-form">
                        <div class="search-box-wrapper">
                            <span style="font-size: 14px; margin-right: 8px; color: #555;">🔍</span>
                            <input type="text" name="searchBar" class="search-input" placeholder="スキルやタグで求職者を検索">
                        </div>
                        <button type="submit" name="search" class="btn-search">検索</button>
                    </form>

                    <div class="action-button-row">
                        <a href="${pageContext.request.contextPath}/company/jobList.jsp" class="action-card-btn" name="publication">
                            掲載中求人表示
                        </a>

                        <a href="${pageContext.request.contextPath}/company/contact.jsp" class="action-card-btn" name="contact">
                            お問い合わせ
                        </a>
                    </div>

                </div>

            </div>

        </main>
    </div>

</body>
</html>