<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${appliedList == null}">
    <jsp:forward page="/jobsearchServlet/ApplicationHistoryList.action" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>応募履歴 - HITOVA</title>
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
        .history-box {
            max-width: 880px;
            margin: 0 auto;
            border: 1.5px solid #002B49;
            padding: 36px 40px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .history-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 15px;
            color: #222;
        }
        .history-table td {
            padding: 18px 10px;
            vertical-align: middle;
        }
        .col-company {
            width: 30%;
            font-weight: bold;
        }
        .col-company a {
            color: #222;
            text-decoration: none;
        }
        .col-company a:hover {
            text-decoration: underline;
        }
        .col-status {
            width: 14%;
            text-align: center;
        }
        .col-updated {
            width: 19%;
            text-align: center;
        }
        .col-deadline {
            width: 19%;
            text-align: center;
        }
        .col-entry {
            width: 18%;
            text-align: right;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content-area">
            <div class="history-box">
                <c:choose>
                    <c:when test="${empty appliedList}">
                        <p style="text-align:center; color:#666; padding: 30px 0;">
                            応募履歴はありません。
                        </p>
                    </c:when>
                    <c:otherwise>
                        <table class="history-table">
                            <tbody>
                                <c:forEach var="row" items="${appliedList}">
                                    <tr>
                                        <td class="col-company">
                                            <a href="${pageContext.request.contextPath}/jobsearchServlet/JobDetail.action?id=${row.job_id}">
                                                ${row.company_name}
                                            </a>
                                        </td>
                                        <td class="col-status">${row.status_label}</td>
                                        <td class="col-updated">最終更新日：<br>${row.updated_date}</td>
                                        <td class="col-deadline">締め切り日：<br>${row.deadline_date}</td>
                                        <td class="col-entry">エントリー人数：<br>${row.entry_count}名</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>

</body>
</html>