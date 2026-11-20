<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${favoriteList == null}">
    <jsp:forward page="/jobsearchServlet/FavoriteJobList.action" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>保存求人一覧 - HITOVA</title>
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

        /* ワイヤーフレームの外枠 */
        .favorite-container-box {
            max-width: 820px;
            margin: 0 auto;
            border: 1.5px solid #002B49;
            padding: 28px 45px 45px 45px;
            background-color: #fff;
            box-sizing: border-box;
        }
        .box-title {
            text-align: center;
            font-size: 20px;
            font-weight: bold;
            margin: 0 0 30px 0;
            color: #111;
        }

        /* 各求人ブロック */
        .favorite-item {
            padding-bottom: 28px;
            margin-bottom: 28px;
            border-bottom: 1px solid #e0e6ed;
        }
        .favorite-item:last-child {
            border-bottom: none;
            margin-bottom: 0;
            padding-bottom: 0;
        }
        .company-row {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 12px;
            color: #111;
        }
        .company-label {
            margin-right: 16px;
        }
        .job-summary {
            font-size: 14px;
            line-height: 1.7;
            color: #444;
            padding-left: 14px;
            margin-bottom: 20px;
            white-space: pre-wrap;
        }
        .action-row {
            display: flex;
            gap: 28px;
            padding-left: 14px;
        }
        .btn-outline-round {
            display: inline-block;
            width: 125px;
            padding: 9px 0;
            text-align: center;
            border: 1.5px solid #002B49;
            border-radius: 10px;
            background-color: #fff;
            color: #333;
            font-size: 14px;
            text-decoration: none;
            box-sizing: border-box;
        }
        .btn-outline-round:hover {
            background-color: #f0f4f8;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content-area">
            <div class="favorite-container-box">
                <h1 class="box-title">保存求人一覧</h1>

                <c:choose>
                    <c:when test="${empty favoriteList}">
                        <p style="text-align:center; color:#666; padding: 30px 0;">
                            保存されている求人はありません。
                        </p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="job" items="${favoriteList}">
                            <div class="favorite-item">
                                <div class="company-row">
                                    <span class="company-label">会社名</span>${job.company_name}
                                </div>
                                <div class="job-summary">${job.title}
${job.description}</div>
                                <div class="action-row">
                                    <a href="${pageContext.request.contextPath}/jobsearchServlet/JobDetail.action?id=${job.job_id}" class="btn-outline-round">詳細</a>
                                    <a href="${pageContext.request.contextPath}/jobsearchServlet/JobApplyConfirm.action?job_id=${job.job_id}" class="btn-outline-round">応募</a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>

</body>
</html>