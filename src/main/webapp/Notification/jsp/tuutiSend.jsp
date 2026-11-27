<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (request.getAttribute("sendPageLoaded") == null) {
        response.sendRedirect(request.getContextPath() + "/Notification/NotificationDisplay.action");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>通知送信 - HITOVA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;
            background-color: #ffffff;
            color: #222222;
        }
        .main-layout {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 48px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .page-container {
            max-width: 720px;
            margin: 0 auto;
        }
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 12px;
            margin-bottom: 24px;
            border-bottom: 1px solid #333333;
        }
        .page-title {
            font-size: 18px;
            font-weight: bold;
            margin: 0;
        }
        .btn-back {
            padding: 6px 14px;
            border: 1px solid #888888;
            background-color: #ffffff;
            color: #222222;
            text-decoration: none;
            font-size: 13px;
        }
        .btn-back:hover {
            background-color: #f5f5f5;
        }
        #resultMessage {
            margin-bottom: 18px;
            font-size: 14px;
            font-weight: bold;
        }
        .form-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
            margin-bottom: 28px;
        }
        .form-table th, .form-table td {
            border: 1px solid #cccccc;
            padding: 14px 16px;
            text-align: left;
            vertical-align: top;
        }
        .form-table th {
            width: 170px;
            background-color: #f5f5f5;
            font-weight: bold;
        }
        .form-control {
            width: 100%;
            padding: 9px 10px;
            box-sizing: border-box;
            border: 1px solid #999999;
            font-size: 14px;
            font-family: inherit;
            background-color: #ffffff;
        }
        textarea.form-control {
            height: 180px;
            resize: vertical;
            line-height: 1.6;
        }
        .hint-text {
            margin: 6px 0 0 0;
            font-size: 12px;
            color: #666666;
        }
        .button-area {
            text-align: center;
        }
        .btn-submit {
            padding: 10px 40px;
            border: 1px solid #333333;
            background-color: #333333;
            color: #ffffff;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-submit:hover {
            background-color: #111111;
        }
    </style>
</head>
<body>

    <%@ include file="/adminHeader.jsp" %>

    <div class="main-layout">
        <%@ include file="/adminSidebar.jsp" %>

        <main class="main-content">
            <div class="page-container">

                <div class="page-header">
                    <h2 class="page-title">通知送信</h2>
                    <a href="${pageContext.request.contextPath}/toppage/adminTop.jsp" class="btn-back">トップへ戻る</a>
                </div>

                <div id="resultMessage"></div>

                <form id="notificationForm"
                      action="${pageContext.request.contextPath}/Notification/NotificationSend.action"
                      method="post">

                    <table class="form-table">
                        <tr>
                            <th>宛先種別</th>
                            <td>
                                <select id="receiverType" name="receiverType" class="form-control" required>
                                    <option value="">選択してください</option>
                                    <option value="user">求職者</option>
                                    <option value="company">企業</option>
                                    <option value="union">組合</option>
                                    <option value="all">全体（求職者・企業・組合すべて）</option>
                                </select>
                            </td>
                        </tr>
                        <tr id="targetAccountRow" style="display: none;">
                            <th>送信先対象</th>
                            <td>
                                <select id="receiverAccountId" name="receiverAccountId" class="form-control">
                                    <option value="">全員へ一括送信</option>
                                </select>
                                <p class="hint-text">特定のアカウントのみに送る場合は一覧から選択してください。</p>
                            </td>
                        </tr>
                        <tr>
                            <th>メッセージ</th>
                            <td>
                                <textarea id="message" name="message" class="form-control"
                                          placeholder="通知内容を入力してください" required></textarea>
                            </td>
                        </tr>
                    </table>

                    <div class="button-area">
                        <button type="submit" class="btn-submit">送信する</button>
                    </div>

                </form>

                <!-- 各宛先種別の個別選択肢データ -->
                <select id="pool-user" style="display: none;">
                    <option value="">求職者全員へ一括送信</option>
                    <c:forEach var="u" items="${userList}">
                        <option value="${u.accountId}">ID:${u.accountId} - <c:out value="${u.name}" /></option>
                    </c:forEach>
                </select>

                <select id="pool-company" style="display: none;">
                    <option value="">企業全体へ一括送信</option>
                    <c:forEach var="cItem" items="${companyList}">
                        <option value="${cItem.accountId}">ID:${cItem.accountId} - <c:out value="${cItem.name}" /></option>
                    </c:forEach>
                </select>

                <select id="pool-union" style="display: none;">
                    <option value="">組合全体へ一括送信</option>
                    <c:forEach var="un" items="${unionList}">
                        <option value="${un.accountId}">ID:${un.accountId} - <c:out value="${un.name}" /></option>
                    </c:forEach>
                </select>

            </div>
        </main>
    </div>

    <script>
        const receiverTypeSelect = document.getElementById("receiverType");
        const targetAccountRow = document.getElementById("targetAccountRow");
        const receiverAccountSelect = document.getElementById("receiverAccountId");
        const form = document.getElementById("notificationForm");
        const resultMessage = document.getElementById("resultMessage");

        // 宛先種別の変更に応じて個別対象プルダウンを切り替え
        receiverTypeSelect.addEventListener("change", function() {
            const type = this.value;
            if (type === "user" || type === "company" || type === "union") {
                const pool = document.getElementById("pool-" + type);
                receiverAccountSelect.innerHTML = pool.innerHTML;
                targetAccountRow.style.display = "table-row";
            } else {
                receiverAccountSelect.innerHTML = '<option value="">全員へ一括送信</option>';
                targetAccountRow.style.display = "none";
            }
        });

        // 送信処理
        form.addEventListener("submit", async function(event) {
            event.preventDefault();

            if (!confirm("通知を送信しますか？")) {
                return;
            }

            const formData = new URLSearchParams(new FormData(form));

            try {
                const response = await fetch(form.action, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
                    },
                    body: formData
                });

                const responseText = await response.text();
                const result = JSON.parse(responseText);

                resultMessage.textContent = result.message;

                if (result.success) {
                    resultMessage.style.color = "#0044cc";
                    form.reset();
                    targetAccountRow.style.display = "none";
                } else {
                    resultMessage.style.color = "#cc0000";
                }
            } catch (error) {
                console.error("送信エラー:", error);
                resultMessage.textContent = "送信に失敗しました。";
                resultMessage.style.color = "#cc0000";
            }
        });
    </script>

</body>
</html>