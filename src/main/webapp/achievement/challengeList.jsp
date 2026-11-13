<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャレンジ・認定一覧 - HITOVA</title>
    <style>
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 60px);
            min-width: 900px;
        }
        .main-content-area {
            flex: 1;
            min-width: 0;
            padding: 40px;
            background-color: #f5f5f5;
            box-sizing: border-box;
        }
        .challenge-container {
            max-width: 750px;
            margin: 0 auto;
        }
        .page-title {
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 25px;
            letter-spacing: 1px;
        }
        .column-header {
            display: flex;
            justify-content: space-between;
            padding: 0 45px;
            font-weight: bold;
            font-size: 15px;
            margin-bottom: 12px;
            color: #333;
        }
        .accordion-item {
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-radius: 12px;
            margin-bottom: 15px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
            overflow: hidden;
        }
        .accordion-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 22px 35px;
            cursor: pointer;
            user-select: none;
            background-color: #fff;
        }
        .challenge-name {
            font-size: 18px;
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 6px;
        }
        .header-right {
            display: flex;
            align-items: center;
            gap: 40px;
        }
        .challenge-price {
            font-size: 17px;
            font-weight: bold;
            min-width: 60px;
            text-align: right;
        }
        .toggle-icon {
            font-size: 22px;
            font-weight: bold;
            color: #333;
            width: 20px;
            text-align: center;
            line-height: 1;
        }
        .accordion-content {
            display: none;
            padding: 10px 35px 25px 35px;
            border-top: 1px solid #f0f0f0;
            background-color: #fafafa;
        }
        .accordion-content.open {
            display: block;
        }
        .task-list {
            list-style: none;
            padding: 0;
            margin: 0 0 20px 0;
        }
        .task-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px 0;
            border-bottom: 1px dashed #dcdcdc;
            font-size: 14px;
            color: #444;
        }
        .task-item:last-child {
            border-bottom: none;
        }
        .btn-detail {
            color: #0077b6;
            text-decoration: underline;
            font-size: 13px;
            background: none;
            border: none;
            cursor: pointer;
            padding: 0;
        }
        .btn-detail:hover {
            color: #005f92;
        }
        .apply-row {
            text-align: right;
            margin-top: 15px;
        }
        .btn-apply {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 9px 24px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            font-size: 14px;
        }
        .btn-apply:hover {
            background-color: #43a047;
        }
        .btn-area {
            text-align: center;
            margin-top: 35px;
        }
        .btn-back {
            display: inline-block;
            width: 140px;
            padding: 10px 0;
            border: 1px solid #0077b6;
            background-color: #ffffff;
            color: #0077b6;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
        }
        .btn-back:hover {
            background-color: #f0f7fb;
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
            <div class="challenge-container">
                <div class="page-title">チャレンジ・認定一覧</div>

                <div class="column-header">
                    <span>内容</span>
                    <span>料金</span>
                </div>

                <div class="accordion-item">
                    <div class="accordion-header" onclick="toggleAccordion(this)">
                        <span class="challenge-name">練習チャレンジ</span>
                        <div class="header-right">
                            <span class="challenge-price">無料</span>
                            <span class="toggle-icon">+</span>
                        </div>
                    </div>
                    <div class="accordion-content">
                        <ul class="task-list">
                            <li class="task-item">
                                <span>Webサイト制作実績の確認（HTML/CSS/JS）</span>
                                <button type="button" class="btn-detail" onclick="alert('制作サイトの目的と担当範囲を説明し、公開URLを提出します。')">詳細</button>
                            </li>
                            <li class="task-item">
                                <span>基礎プログラミング適性テスト</span>
                                <button type="button" class="btn-detail" onclick="alert('条件分岐やループを用いた基本ロジック確認テストです。')">詳細</button>
                            </li>
                        </ul>
                        <div class="apply-row">
                            <form action="${pageContext.request.contextPath}/achievementServlet/ChallengeApplyExecute.action" method="post">
                                <input type="hidden" name="task_id" value="10001">
                                <input type="hidden" name="challenge_name" value="Webサイト制作実績（練習）">
                                <button type="submit" class="btn-apply">この課題を受験・登録する</button>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="accordion-item">
                    <div class="accordion-header" onclick="toggleAccordion(this)">
                        <span class="challenge-name">スタンダード認定</span>
                        <div class="header-right">
                            <span class="challenge-price">¥1500</span>
                            <span class="toggle-icon">+</span>
                        </div>
                    </div>
                    <div class="accordion-content">
                        <ul class="task-list">
                            <li class="task-item">
                                <span>製造現場での改善事例レポート</span>
                                <button type="button" class="btn-detail" onclick="alert('改善前後の作業内容と効果を説明するA4資料を提出します。')">詳細</button>
                            </li>
                            <li class="task-item">
                                <span>業務フロー改善の実技確認</span>
                                <button type="button" class="btn-detail" onclick="alert('現場のボトルネック分析と解決策の論理構成を確認します。')">詳細</button>
                            </li>
                        </ul>
                        <div class="apply-row">
                            <form action="${pageContext.request.contextPath}/achievementServlet/ChallengeApplyExecute.action" method="post">
                                <input type="hidden" name="task_id" value="10002">
                                <input type="hidden" name="challenge_name" value="製造現場での改善事例（スタンダード）">
                                <button type="submit" class="btn-apply">この課題を受験・登録する</button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- 戻るボタン -->
                <div class="btn-area">
				<a href="${pageContext.request.contextPath}/toppage/candidateTop.jsp" class="btn-back">戻る</a>                </div>
            </div>
        </main>
    </div>

    <script>
        function toggleAccordion(headerElement) {
            const content = headerElement.nextElementSibling;
            const icon = headerElement.querySelector('.toggle-icon');
            const isOpen = content.classList.contains('open');

            if (isOpen) {
                content.classList.remove('open');
                icon.textContent = '+';
            } else {
                content.classList.add('open');
                icon.textContent = 'ー';
            }
        }
    </script>
</body>
</html>