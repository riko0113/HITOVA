<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${jobList == null}">
    <jsp:forward page="/jobsearchServlet/JobSearch.action" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>求人検索 - HITOVA</title>
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

        /* 検索エリア全体を同じ横幅（560px）で中央揃えにして左右のラインを統一 */
        .search-section {
            max-width: 680px;
            margin: 0 auto 50px auto;
            text-align: center;
        }
        .search-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 25px;
            padding-bottom: 10px;
            border-bottom: 2px solid #333;
            width: 100%;
        }

        /* キーワード行とフィルター表の横幅を540pxでピタッと揃える */
        .search-form-inner {
            width: 540px;
            margin: 0 auto;
        }
        .keyword-row {
            display: flex;
            gap: 10px;
            margin-bottom: 28px;
            width: 100%;
        }
        .input-keyword {
            flex: 1;
            padding: 10px 14px;
            border: 1px solid #002B49;
            border-radius: 4px;
            font-size: 15px;
            box-sizing: border-box;
        }
        .btn-search {
            width: 96px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-search:hover {
            background-color: #43a047;
        }

        .filter-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0 14px;
            text-align: left;
        }
        .filter-table th {
            width: 140px;
            font-size: 15px;
            font-weight: bold;
            color: #333;
            padding-left: 4px;
        }
        .filter-table td {
            width: 400px;
        }
        .filter-table select {
            width: 100%;
            padding: 9px 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            background-color: #fff;
            box-sizing: border-box;
        }

        /* 求人一覧部分 */
        .list-section {
            max-width: 760px;
            margin: 0 auto;
        }
        .list-title {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
            border-bottom: 1px solid #ccc;
            padding-bottom: 10px;
        }
        .job-card {
            border: 1px solid #002B49;
            padding: 22px 26px;
            margin-bottom: 20px;
            border-radius: 4px;
            background-color: #fff;
        }
        .job-company {
            font-size: 15px;
            font-weight: bold;
            margin-bottom: 8px;
        }
       .job-title {
    		font-size: 18px;
    		font-weight: bold;
    		margin-bottom: 12px;
    		color: #333;
        }
        .job-meta {
            font-size: 13px;
            color: #666;
            margin-bottom: 12px;
        }
        .job-desc {
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 18px;
            color: #444;
        }
        .job-actions {
            display: flex;
            gap: 15px;
        }
        .btn-detail, .btn-apply {
            padding: 8px 32px;
            background-color: #fff;
            color: #333;
            border: 1px solid #002B49;
            text-decoration: none;
            border-radius: 4px;
            text-align: center;
            font-size: 14px;
        }
        .btn-detail:hover, .btn-apply:hover {
            background-color: #f0f4f8;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content-area">

            <!-- 検索フォーム -->
            <div class="search-section">
                <div class="search-title">求人検索</div>

                <form action="${pageContext.request.contextPath}/jobsearchServlet/JobSearch.action" method="post" class="search-form-inner">
                    <div class="keyword-row">
                        <input type="text" name="keyword" class="input-keyword" placeholder="職種やキーワードを入力" value="${keyword}">
                        <button type="submit" class="btn-search">検索</button>
                    </div>

                    <table class="filter-table">
                        <tr>
                            <th>職種</th>
                            <td>
                                <select name="occupationId">
                                    <option value="">選択してください</option>
                                    <option value="8" ${occupationId == 8 ? 'selected' : ''}>営業</option>
                                    <option value="16" ${occupationId == 16 ? 'selected' : ''}>事務</option>
                                    <option value="33" ${occupationId == 33 ? 'selected' : ''}>IT・エンジニア</option>
                                    <option value="54" ${occupationId == 54 ? 'selected' : ''}>販売</option>
                                    <option value="73" ${occupationId == 73 ? 'selected' : ''}>医療</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>給与</th>
                            <td>
                                <select name="salaryTypeId">
                                    <option value="">選択してください</option>
                                    <option value="1" ${salaryTypeId == 1 ? 'selected' : ''}>年収</option>
                                    <option value="2" ${salaryTypeId == 2 ? 'selected' : ''}>月収</option>
                                    <option value="4" ${salaryTypeId == 4 ? 'selected' : ''}>時給</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>雇用形態</th>
                            <td>
                                <select name="employmentTypeId">
                                    <option value="">選択してください</option>
                                    <option value="1" ${employmentTypeId == 1 ? 'selected' : ''}>正社員</option>
                                    <option value="2" ${employmentTypeId == 2 ? 'selected' : ''}>契約社員</option>
                                    <option value="3" ${employmentTypeId == 3 ? 'selected' : ''}>パート・アルバイト</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>勤務地</th>
                            <td>
                                <select name="location">
                                    <option value="">選択してください</option>
                                    <option value="広島" ${location == '広島' ? 'selected' : ''}>広島県</option>
                                    <option value="東京" ${location == '東京' ? 'selected' : ''}>東京都</option>
                                    <option value="大阪" ${location == '大阪' ? 'selected' : ''}>大阪府</option>
                                    <option value="福岡" ${location == '福岡' ? 'selected' : ''}>福岡県</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>福利厚生など</th>
                            <td>
                                <select name="benefit">
                                    <option value="">選択してください</option>
                                    <option value="交通費支給" ${benefit == '交通費支給' ? 'selected' : ''}>交通費支給あり</option>
                                    <option value="社会保険" ${benefit == '社会保険' ? 'selected' : ''}>社会保険完備</option>
                                    <option value="研修" ${benefit == '研修' ? 'selected' : ''}>研修制度あり</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>

            <!-- 求人一覧 -->
            <div class="list-section">
                <div class="list-title">求人一覧</div>

                <c:choose>
                    <c:when test="${empty jobList}">
                        <p style="text-align: center; color: #555;">条件に一致する求人が見つかりませんでした。</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="job" items="${jobList}">
                            <div class="job-card">
                                <div class="job-company">会社名 ${job.company_name}</div>
                                <div class="job-title">${job.title}</div>
                                <div class="job-meta">
                                    職種：${job.job_type_name} ／ 雇用形態：${job.employment_type_name} ／ 給与：${job.salary_type_name} ${job.salary} ／ 勤務地：${job.location}
                                </div>
                                <div class="job-desc">${job.description}</div>
                                <div class="job-actions">
                                    <a href="${pageContext.request.contextPath}/jobsearchServlet/JobDetail.action?id=${job.job_id}" class="btn-detail">詳細</a>
                                    <a href="${pageContext.request.contextPath}/jobsearchServlet/JobApplyConfirm.action?job_id=${job.job_id}" class="btn-apply">応募</a>
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