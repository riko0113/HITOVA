<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    if (request.getAttribute("unionSendLoaded") == null) {
        response.sendRedirect(request.getContextPath() + "/Notification/UnionCompanyNotificationDisplay.action");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>企業通知送信 - HITOVA</title>
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
        .radio-group {
            display: flex;
            gap: 20px;
            align-items: center;
        }
        .radio-label {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            cursor: pointer;
            font-size: 14px;
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

    <%@ include file="/unionAdminHeader.jsp" %>

    <div class="main-layout">
        <%@ include file="/unionAdminSidebar.jsp" %>

        <main class="main-content">
            <div class="page-container">

                <div class="page-header">
                    <h2 class="page-title">企業通知送信</h2>
                    <a href="${pageContext.request.contextPath}/toppage/unionAdminTop.jsp" class="btn-back">トップへ戻る</a>
                </div>

                <div id="resultMessage"></div>

                <form id="unionNotificationForm"
                      action="${pageContext.request.contextPath}/Notification/UnionCompanyNotificationSend.action"
                      method="post">

                    <table class="form-table">
                        <tr>
                            <th>送信対象</th>
                            <td>
                                <div class="radio-group">
                                    <label class="radio-label">
                                        <input type="radio" name="targetMode" value="all" checked>
                                        <span>所属企業すべてに一括送信</span>
                                    </label>
                                    <label class="radio-label">
                                        <input type="radio" name="targetMode" value="single">
                                        <span>特定の企業を指定</span>
                                    </label>
                                </div>
                            </td>
                        </tr>
                        <tr id="singleCompanyRow" style="display: none;">
                            <th>送信先企業</th>
                            <td>
                                <select id="companyAccountId" name="companyAccountId" class="form-control">
                                    <option value="">企業を選択してください</option>
                                    <c:forEach var="comp" items="${companyList}">
                                        <option value="${comp.accountId}">ID:${comp.accountId} - <c:out value="${comp.name}" /></option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>メッセージ</th>
                            <td>
                                <textarea id="message" name="message" class="form-control"
                                          placeholder="企業へ送信する通知内容を入力してください" required></textarea>
                            </td>
                        </tr>
                    </table>

                    <div class="button-area">
                        <button type="submit" class="btn-submit">送信する</button>
                    </div>

                </form>

            </div>
        </main>
    </div>

    <script>
        const radios = document.querySelectorAll('input[name="targetMode"]');
        const singleCompanyRow = document.getElementById("singleCompanyRow");
        const companySelect = document.getElementById("companyAccountId");
        const form = document.getElementById("unionNotificationForm");
        const resultMessage = document.getElementById("resultMessage");

        radios.forEach(function(radio) {
            radio.addEventListener("change", function() {
                if (this.value === "single") {
                    singleCompanyRow.style.display = "table-row";
                    companySelect.required = true;
                } else {
                    singleCompanyRow.style.display = "none";
                    companySelect.required = false;
                    companySelect.value = "";
                }
            });
        });

        form.addEventListener("submit", async function(event) {
            event.preventDefault();

            if (!confirm("企業へ通知を送信しますか？")) {
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
                    singleCompanyRow.style.display = "none";
                    companySelect.required = false;
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