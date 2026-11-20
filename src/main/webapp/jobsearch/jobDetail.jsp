<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>求人詳細 - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
            background-color: #ffffff;
            color: #333;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 60px);
        }
        .main-content-area {
            flex: 1;
            padding: 40px 50px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .page-title {
            text-align: center;
            font-size: 26px;
            font-weight: normal;
            margin: 0 0 25px 0;
            letter-spacing: 2px;
        }

        /* メイン枠線（ワイヤーフレーム準拠） */
        .detail-box {
            max-width: 860px;
            margin: 0 auto;
            border: 2px solid #002B49;
            padding: 30px 40px 40px 40px;
            background-color: #fff;
            box-sizing: border-box;
        }

        /* 上部エリア：左にタブメニュー、右にアクションボタン3つ */
        .top-area {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 20px;
        }
        .left-main {
            flex: 1;
        }

        /* タブメニュー */
        .tab-menu {
            display: flex;
            gap: 32px;
            margin-bottom: 28px;
            flex-wrap: wrap;
        }
        .tab-item {
            font-size: 16px;
            color: #b0b0b0;
            cursor: pointer;
            padding-bottom: 4px;
            border-bottom: 2px solid transparent;
            user-select: none;
        }
        .tab-item:hover {
            color: #666;
        }
        .tab-item.active {
            color: #e60012;
            font-weight: bold;
            border-bottom: 2px solid #000;
        }

        /* 会社名・求人タイトル */
        .company-name-heading {
            font-size: 18px;
            font-weight: bold;
            margin: 0 0 6px 0;
        }
        .job-title-heading {
    		font-size: 20px;
    		font-weight: bold;
    		color: #333;
    		margin: 0 0 22px 0;
        }

        /* 会社概要テーブル */
        .overview-table {
            border-collapse: collapse;
            font-size: 15px;
            margin-bottom: 20px;
        }
        .overview-table th {
            text-align: left;
            font-weight: normal;
            color: #555;
            padding: 7px 24px 7px 14px;
            width: 110px;
            vertical-align: top;
        }
        .overview-table td {
            padding: 7px 10px;
            color: #333;
            line-height: 1.6;
        }

        /* 右側ボタン3つ（求人保存・口コミ・通報） */
        .right-buttons {
            display: flex;
            flex-direction: column;
            gap: 14px;
            width: 155px;
            flex-shrink: 0;
        }
        .side-action-btn {
            display: block;
            width: 100%;
            padding: 11px 0;
            text-align: center;
            border: 1.5px solid #002B49;
            border-radius: 10px;
            background-color: #fff;
            color: #333;
            font-size: 15px;
            text-decoration: none;
            cursor: pointer;
            box-sizing: border-box;
        }
        .side-action-btn:hover {
            background-color: #f0f4f8;
        }
        .side-action-btn.saved {
            background-color: #fff8e1;
            border-color: #f59e0b;
            color: #b45309;
            font-weight: bold;
        }

        /* タブ中身 */
        .tab-panel {
            display: none;
        }
        .tab-panel.active {
            display: block;
        }

        /* 募集要項セクション */
        .job-spec-section {
            margin-top: 30px;
            padding-top: 24px;
            border-top: 1px solid #d0d7de;
        }
        .spec-heading {
            font-size: 17px;
            font-weight: bold;
            color: #002B49;
            margin: 0 0 14px 0;
        }
        .spec-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }
        .spec-table th {
            width: 160px;
            text-align: left;
            padding: 10px 14px;
            background-color: #f4f7f9;
            border: 1px solid #d0d7de;
            font-weight: bold;
            vertical-align: top;
        }
        .spec-table td {
            padding: 10px 14px;
            border: 1px solid #d0d7de;
            line-height: 1.6;
            white-space: pre-wrap;
        }

        /* 下部ナビゲーションボタン */
        .bottom-actions {
            margin-top: 32px;
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .btn-apply-main {
            padding: 12px 48px;
            background-color: #135e75;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: bold;
            text-decoration: none;
            cursor: pointer;
        }
        .btn-apply-main:hover {
            background-color: #0e485a;
        }
        .btn-back {
            padding: 12px 36px;
            background-color: #fff;
            color: #333;
            border: 1px solid #002B49;
            border-radius: 6px;
            font-size: 15px;
            text-decoration: none;
        }
        .btn-back:hover {
            background-color: #f0f4f8;
        }
        .btn-apply-bottom {
            padding: 12px 36px;
            background-color: #135e75;
            color: #fff;
            border: 1px solid #135e75;
            border-radius: 6px;
            font-size: 15px;
            font-weight: bold;
            text-decoration: none;
        }
        .btn-apply-bottom:hover {
            background-color: #0e485a;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content-area">
            <h1 class="page-title">求人詳細</h1>

            <div class="detail-box">
                <div class="top-area">
                    <div class="left-main">
                        <!-- タブメニュー -->
                        <div class="tab-menu">
                            <div class="tab-item active" onclick="switchTab(this, 'tab-overview')">会社概要</div>
                            <div class="tab-item" onclick="switchTab(this, 'tab-hiring')">採用データ</div>
                            <div class="tab-item" onclick="switchTab(this, 'tab-seminar')">説明会・セミナー</div>
                            <div class="tab-item" onclick="switchTab(this, 'tab-question')">企業に質問</div>
                        </div>

                        <div class="company-name-heading">${job.company_name}</div>
                        <div class="job-title-heading">${job.title}</div>

                        <!-- タブ1：会社概要 -->
                        <div id="tab-overview" class="tab-panel active">
                            <table class="overview-table">
                                <tr>
                                    <th>業種</th>
                                    <td>${ca.industry_name}</td>
                                </tr>
                                <tr>
                                    <th>所在地</th>
                                    <td>
                                        <c:choose>
                                            <c:when test="${spi != null}">${spi.business_office_address}</c:when>
                                            <c:otherwise>${job.location}</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <th>設立年</th>
                                    <td><c:if test="${ca.established_year > 0}">${ca.established_year}年</c:if></td>
                                </tr>
                                <tr>
                                    <th>従業員</th>
                                    <td><c:if test="${ca.number_of_employee > 0}">${ca.number_of_employee}名</c:if></td>
                                </tr>
                                <tr>
                                    <th>事業内容</th>
                                    <td>${ca.business_description}</td>
                                </tr>
                                <c:if test="${ci != null && not empty ci.company_website_url}">
                                    <tr>
                                        <th>Webサイト</th>
                                        <td><a href="${ci.company_website_url}" target="_blank" style="color:#0066cc;">${ci.company_website_url}</a></td>
                                    </tr>
                                </c:if>
                            </table>
                        </div>

                        <!-- タブ2：採用データ -->
                        <div id="tab-hiring" class="tab-panel">
                            <table class="overview-table">
                                <tr>
                                    <th>募集職種</th>
                                    <td>${job.job_type_name}</td>
                                </tr>
                                <tr>
                                    <th>雇用形態</th>
                                    <td>${job.employment_type_name}</td>
                                </tr>
                                <tr>
                                    <th>募集人数</th>
                                    <td>${not empty job.headcount ? job.headcount : '若干名'}</td>
                                </tr>
                                <tr>
                                    <th>選考プロセス</th>
                                    <td>${job.selection_process}</td>
                                </tr>
                                <tr>
                                    <th>面接場所</th>
                                    <td>${job.interview_location}</td>
                                </tr>
                            </table>
                        </div>

                        <!-- タブ3：説明会・セミナー -->
                        <div id="tab-seminar" class="tab-panel">
                            <p style="font-size:14px; color:#555; padding-left:14px;">
                                現在予定されている説明会・セミナー情報はありません。個別面談・職場見学は応募またはチャットよりご相談ください。
                            </p>
                        </div>

                        <!-- タブ4：企業に質問 -->
                        <div id="tab-question" class="tab-panel">
                            <p style="font-size:14px; color:#555; padding-left:14px; margin-bottom:14px;">
                                求人内容や働き方について気になる点があれば、企業担当者へ直接メッセージを送れます。
                            </p>
                            <div style="padding-left:14px;">
                                <a href="${pageContext.request.contextPath}/chat/chatRoom.jsp" class="btn-back" style="display:inline-block; padding:8px 20px;">チャットで質問する</a>
                            </div>
                        </div>
                    </div>

                    <!-- 右上の3つのボタン -->
                    <!-- 右上の3つのボタン -->
		<div class="right-buttons">
    		<form action="${pageContext.request.contextPath}/jobsearchServlet/FavoriteJobToggle.action" method="post" style="margin:0;">
        		<input type="hidden" name="job_id" value="${job.job_id}">
        		<button type="submit" class="side-action-btn ${isFavorite ? 'saved' : ''}">
            		${isFavorite ? '保存済み ★' : '求人保存'}
        		</button>
    		</form>
    		<a href="#review-area" class="side-action-btn" onclick="alert('口コミ一覧画面へ遷移します')">口コミ</a>
    		<a href="#report-area" class="side-action-btn" onclick="alert('通報フォーム画面へ遷移します')">通報</a>
		</div>
                </div>

                <!-- 求人募集要項（詳細スペック） -->
                <div class="job-spec-section">
                    <h2 class="spec-heading">■ 募集要項</h2>
                    <table class="spec-table">
                        <tr>
                            <th>仕事内容</th>
                            <td>${job.description}</td>
                        </tr>
                        <tr>
                            <th>応募資格・条件</th>
                            <td>${job.requirement}</td>
                        </tr>
                        <tr>
                            <th>給与</th>
                            <td>【${job.salary_type_name}】 ${job.salary}</td>
                        </tr>
                        <tr>
                            <th>諸手当・交通費</th>
                            <td>${job.allowance}（交通費：${job.transportation_fee ? '支給あり' : 'なし'}）</td>
                        </tr>
                        <tr>
                            <th>勤務地・アクセス</th>
                            <td>${job.location}<c:if test="${not empty job.access}">（最寄：${job.access}）</c:if></td>
                        </tr>
                        <tr>
                            <th>勤務時間</th>
                            <td>${job.working_hours}</td>
                        </tr>
                        <tr>
                            <th>休日・休暇</th>
                            <td>${job.holiday}</td>
                        </tr>
                        <tr>
                            <th>待遇・福利厚生</th>
                            <td>${job.benefits}</td>
                        </tr>
                        <tr>
                            <th>受動喫煙対策</th>
                            <td>${job.smoking_prevention}</td>
                        </tr>
                        <c:if test="${not empty job.workplace_pr}">
                            <tr>
                                <th>職場PR</th>
                                <td>${job.workplace_pr}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>

                <!-- 下部ボタン -->
                <div class="bottom-actions">
                    <a href="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" class="btn-back">一覧へ戻る</a>
                    <a href="${pageContext.request.contextPath}/jobsearchServlet/JobApplyConfirm.action?job_id=${job.job_id}" class="btn-apply-bottom">応募</a>
                </div>
            </div>
        </main>
    </div>

    <script>
        function switchTab(el, panelId) {
            document.querySelectorAll('.tab-item').forEach(t => t.classList.remove('active'));
            document.querySelectorAll('.tab-panel').forEach(p => p.classList.remove('active'));
            el.classList.add('active');
            document.getElementById(panelId).classList.add('active');
        }

        function toggleSave(btn) {
            btn.classList.toggle('saved');
            btn.textContent = btn.classList.contains('saved') ? '保存済み ★' : '求人保存';
        }
    </script>
</body>
</html>