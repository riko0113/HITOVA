<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>チャレンジ・認定一覧 - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            color: #222222;
            background-color: #ffffff;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
            min-width: 960px;
        }
        .main-content-area {
            flex: 1;
            min-width: 0;
            padding: 32px 40px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .challenge-container {
            max-width: 800px;
            margin: 0 auto;
        }
        .page-title {
            font-size: 20px;
            font-weight: bold;
            color: #111111;
            margin-bottom: 24px;
            padding-bottom: 8px;
            border-bottom: 1px solid #333333;
        }
        .column-header {
            display: flex;
            justify-content: space-between;
            padding: 0 32px;
            font-weight: bold;
            font-size: 14px;
            margin-bottom: 12px;
            color: #333333;
        }
        .accordion-item {
            background-color: #ffffff;
            border: 1px solid #cccccc;
            margin-bottom: 16px;
        }
        .accordion-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 18px 32px;
            cursor: pointer;
            user-select: none;
            background-color: #ffffff;
        }
        .accordion-header:hover {
            background-color: #f5f5f5;
        }
        .challenge-name {
            font-size: 16px;
            font-weight: bold;
            color: #111111;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .header-right {
            display: flex;
            align-items: center;
            gap: 36px;
        }
        .challenge-price {
            font-size: 16px;
            font-weight: bold;
            color: #111111;
            min-width: 64px;
            text-align: right;
        }
        .toggle-icon {
            font-size: 20px;
            font-weight: bold;
            color: #333333;
            width: 20px;
            text-align: center;
            line-height: 1;
        }
        .accordion-content {
            display: none;
            padding: 16px 32px 24px 32px;
            border-top: 1px solid #e0e0e0;
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
            border-bottom: 1px dashed #cccccc;
            font-size: 14px;
            color: #333333;
        }
        .task-item:last-child {
            border-bottom: none;
        }
        .btn-detail {
            color: #0044cc;
            text-decoration: underline;
            font-size: 13px;
            background: none;
            border: none;
            cursor: pointer;
            padding: 0;
        }
        .btn-detail:hover {
            color: #002288;
        }
        .apply-row {
            text-align: right;
            margin-top: 12px;
        }
        .btn-apply {
            background-color: #4CAF50;
            color: #ffffff;
            border: none;
            padding: 9px 24px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            font-size: 14px;
        }
        .btn-apply:hover {
            background-color: #43a047;
        }
        .btn-area {
            text-align: center;
            margin-top: 32px;
        }
        .btn-back {
            display: inline-block;
            width: 140px;
            padding: 9px 0;
            border: 1px solid #888888;
            background-color: #f5f5f5;
            color: #222222;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .btn-back:hover {
            background-color: #e8e8e8;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
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
                            <span class="challenge-price">¥1,500</span>
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

                <div class="btn-area">
                    <a href="${pageContext.request.contextPath}/toppage/candidateTop.jsp" class="btn-back">戻る</a>
                </div>
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
                icon.textContent = '－';
            }
        }
    </script>
</body>
</html>