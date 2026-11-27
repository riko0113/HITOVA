<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>通報詳細 - HITOVA 管理画面</title>
    <style>
        body {
            margin: 0;
            font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
            background-color: #ffffff;
            color: #111111;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 48px;
            box-sizing: border-box;
        }
        .detail-box {
            max-width: 820px;
            margin: 0 auto;
            border: 1px solid #cccccc;
            padding: 32px 40px;
            background-color: #ffffff;
        }
        .detail-title {
            font-size: 20px;
            font-weight: bold;
            margin: 0 0 24px 0;
            padding-bottom: 12px;
            border-bottom: 2px solid #111111;
        }
        .detail-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 32px;
            font-size: 14px;
        }
        .detail-table th,
        .detail-table td {
            border: 1px solid #cccccc;
            padding: 14px 16px;
            text-align: left;
            vertical-align: top;
        }
        .detail-table th {
            width: 180px;
            background-color: #f4f5f7;
            font-weight: bold;
        }
        .content-text {
            white-space: pre-wrap;
            word-break: break-word;
            line-height: 1.7;
            margin: 0;
        }
        .attachment-preview {
            max-width: 100%;
            max-height: 360px;
            border: 1px solid #cccccc;
            margin-top: 8px;
            display: block;
        }
        .action-row {
            display: flex;
            justify-content: center;
            gap: 24px;
        }
        .btn-back {
            padding: 10px 28px;
            border: 1px solid #666666;
            border-radius: 4px;
            background-color: #ffffff;
            color: #111111;
            text-decoration: none;
            font-size: 14px;
        }
        .btn-back:hover {
            background-color: #f2f2f2;
        }
        .btn-complete {
            padding: 10px 28px;
            border: 1px solid #0044cc;
            border-radius: 4px;
            background-color: #0044cc;
            color: #ffffff;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-complete:hover {
            background-color: #003399;
        }
    </style>
</head>
<body>

    <%@ include file="/adminHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/adminSidebar.jsp" %>

        <main class="main-content">
            <div class="detail-box">
                <h2 class="detail-title">通報詳細内容</h2>

                <table class="detail-table">
                    <tr>
                        <th>通報ID</th>
                        <td>${report.report_id}</td>
                    </tr>
                    <tr>
                        <th>通報日時</th>
                        <td>
                            <c:if test="${not empty report.reported_at}">
                                <fmt:formatDate value="${report.reported_at}" pattern="yyyy/MM/dd HH:mm" />
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>通報者</th>
                        <td><c:out value="${report.reporter_name}" />（ID: ${report.reporter_id}）</td>
                    </tr>
                    <tr>
                        <th>通報対象</th>
                        <td><c:out value="${report.reported_target_name}" />（ID: ${report.reported_user_id}）</td>
                    </tr>
                    <tr>
                        <th>詳細内容</th>
                        <td><p class="content-text"><c:out value="${report.content}" /></p></td>
                    </tr>
                    <tr>
                        <th>証拠添付ファイル</th>
                        <td>
                            <c:choose>
                                <c:when test="${not empty report.for_storing_evidence && report.for_storing_evidence != 'なし'}">
                                    <a href="${pageContext.request.contextPath}/${report.for_storing_evidence}" target="_blank">添付ファイルを開く</a>
                                    <img src="${pageContext.request.contextPath}/${report.for_storing_evidence}" alt="証拠画像" class="attachment-preview" onerror="this.style.display='none';">
                                </c:when>
                                <c:otherwise>添付なし</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>

                <div class="action-row">
                    <a href="${pageContext.request.contextPath}/admin/AdminReportList.action" class="btn-back">一覧へ戻る</a>
                    <form action="${pageContext.request.contextPath}/admin/AdminReportDelete.action" method="post" onsubmit="return confirm('この通報を対応完了として一覧から削除しますか？');">
                        <input type="hidden" name="report_id" value="${report.report_id}">
                        <button type="submit" class="btn-complete">対応完了（一覧から削除）</button>
                    </form>
                </div>
            </div>
        </main>
    </div>

</body>
</html>