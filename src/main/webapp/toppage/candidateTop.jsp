<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${recommendedJobs == null}">
    <jsp:forward page="/jobsearchServlet/CandidateTop.action" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>求職者トップページ - HITOVA</title>
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
            padding: 32px 44px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .top-container {
            max-width: 1000px;
            margin: 0 auto;
        }
        .search-box {
            border: 1px solid #cccccc;
            background-color: #f8f8f8;
            padding: 18px 22px;
            margin-bottom: 32px;
        }
        .search-form {
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .search-input {
            flex: 1;
            height: 36px;
            padding: 0 12px;
            border: 1px solid #999999;
            background-color: #ffffff;
            font-size: 14px;
            box-sizing: border-box;
        }
        .search-btn {
            height: 36px;
            padding: 0 24px;
            border: 1px solid #333333;
            background-color: #333333;
            color: #ffffff;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
        }
        .search-btn:hover {
            background-color: #111111;
        }
        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #333333;
            padding-bottom: 8px;
            margin-bottom: 18px;
        }
        .section-title {
            font-size: 16px;
            font-weight: bold;
            margin: 0;
        }
        .section-more {
            color: #0044cc;
            text-decoration: none;
            font-size: 13px;
            font-weight: bold;
        }
        .section-more:hover {
            text-decoration: underline;
        }
        .job-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 18px;
        }
        .job-card {
            border: 1px solid #cccccc;
            padding: 18px;
            background-color: #ffffff;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            box-sizing: border-box;
        }
        .job-card:hover {
            border-color: #888888;
        }
        .job-badge {
            display: inline-block;
            font-size: 11px;
            color: #333333;
            background-color: #f2f2f2;
            border: 1px solid #cccccc;
            padding: 2px 8px;
            margin-bottom: 10px;
        }
        .job-title {
            font-size: 15px;
            font-weight: bold;
            color: #111111;
            margin: 0 0 12px 0;
            line-height: 1.4;
        }
        .job-meta {
            font-size: 13px;
            color: #444444;
            margin: 0 0 6px 0;
            line-height: 1.5;
        }
        .job-card-footer {
            margin-top: 14px;
            padding-top: 10px;
            border-top: 1px solid #eeeeee;
            text-align: right;
        }
        .job-detail-link {
            color: #0044cc;
            text-decoration: none;
            font-size: 13px;
            font-weight: bold;
        }
        .job-detail-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="main-layout">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content">
            <div class="top-container">

                <div class="search-box">
                    <form action="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" method="post" class="search-form">
                        <input type="text" name="keyword" class="search-input" placeholder="職種・勤務地・キーワードで求人を検索">
                        <button type="submit" class="search-btn">検索</button>
                    </form>
                </div>

                <div class="section-header">
                    <h2 class="section-title">新着・おすすめ求人</h2>
                    <a href="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" class="section-more">すべての求人を見る &gt;</a>
                </div>

                <c:choose>
                    <c:when test="${empty recommendedJobs}">
                        <p style="color: #666666; font-size: 14px; padding: 32px; text-align: center; border: 1px solid #dddddd;">
                            現在公開中の求人はありません。
                        </p>
                    </c:when>
                    <c:otherwise>
                        <div class="job-grid">
                            <c:forEach var="job" items="${recommendedJobs}">
                                <div class="job-card">
                                    <div>
                                        <span class="job-badge"><c:out value="${job.employment_type_name}" /></span>
                                        <h3 class="job-title"><c:out value="${job.title}" /></h3>
                                        <p class="job-meta">給与：<c:out value="${job.salary_type_name}" /> <c:out value="${job.salary}" /></p>
                                        <p class="job-meta">勤務地：<c:out value="${job.location}" /></p>
                                    </div>
                                    <div class="job-card-footer">
                                        <a href="${pageContext.request.contextPath}/jobsearchServlet/JobDetail.action?id=${job.job_id}" class="job-detail-link">詳細を見る &gt;</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </main>
    </div>

</body>
</html>