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
</head>
<body style="margin: 0; font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;">

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div style="display: flex; min-height: calc(100vh - 64px);">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main style="flex: 1; padding: 24px 30px;">
            <div style="display: flex; justify-content: space-between; align-items: center; border: 1px solid #ccc; padding: 20px; margin-bottom: 24px;">
                <form action="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" method="post" style="display: flex; gap: 8px; flex: 1; max-width: 450px;">
                    <input type="text" name="keyword" placeholder="検索" style="flex: 1; height: 32px; padding: 0 8px;">
                    <button type="submit" style="height: 36px; padding: 0 18px; cursor: pointer;">検索</button>
                </form>
                <div style="width: 300px; height: 70px; border: 1px solid #ccc; display: flex; align-items: center; justify-content: center;">
                    画像
                </div>
            </div>

            <div style="display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 32px;">
                <div style="border: 1px solid #ccc; padding: 14px; text-align: center;">
                    <div style="font-size: 13px; margin-bottom: 6px;">エントリー件数</div>
                    <div style="font-size: 22px; font-weight: bold;">2件</div>
                </div>
                <div style="border: 1px solid #ccc; padding: 14px; text-align: center;">
                    <div style="font-size: 13px; margin-bottom: 6px;">書類選考中</div>
                    <div style="font-size: 22px; font-weight: bold;">1件</div>
                </div>
                <div style="border: 1px solid #ccc; padding: 14px; text-align: center;">
                    <div style="font-size: 13px; margin-bottom: 6px;">面接調整中</div>
                    <div style="font-size: 22px; font-weight: bold;">1件</div>
                </div>
                <div style="border: 1px solid #ccc; padding: 14px; text-align: center;">
                    <div style="font-size: 13px; margin-bottom: 6px;">内定数</div>
                    <div style="font-size: 22px; font-weight: bold;">0件</div>
                </div>
            </div>

            <div style="display: flex; justify-content: space-between; border-bottom: 1px solid #333; padding-bottom: 6px; margin-bottom: 14px;">
                <strong>おすすめ求人</strong>
                <a href="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" style="color: #0044cc; font-size: 13px;">求人一覧へ</a>
            </div>

            <c:choose>
                <c:when test="${empty recommendedJobs}">
                    <p style="color: #555; font-size: 14px;">現在公開中の求人はありません。</p>
                </c:when>
                <c:otherwise>
                    <div style="display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px;">
                        <c:forEach var="job" items="${recommendedJobs}">
                            <div style="border: 1px solid #ccc; padding: 14px; display: flex; flex-direction: column; justify-content: space-between;">
                                <div>
                                    <div style="font-size: 11px; border: 1px solid #888; display: inline-block; padding: 1px 6px; margin-bottom: 6px;">${job.employment_type_name}</div>
                                    <div style="font-weight: bold; margin-bottom: 6px;">${job.title}</div>
                                    <div style="font-size: 12px;">給与: ${job.salary_type_name} ${job.salary}</div>
                                    <div style="font-size: 12px; margin-bottom: 10px;">勤務地: ${job.location}</div>
                                </div>
                                <a href="${pageContext.request.contextPath}/jobsearchServlet/JobDetail.action?id=${job.job_id}" style="display: block; text-align: center; border: 1px solid #888; padding: 4px 0; color: #222; text-decoration: none; font-size: 12px;">詳細</a>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
    </div>

</body>
</html>