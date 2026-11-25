<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.candidate_account_id}">
    <c:redirect url="/login/login.jsp" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>通報 - HITOVA</title>
    <style>
        body {
            margin: 0;
            font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
            background-color: #ffffff;
            color: #000000;
        }
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 64px);
        }
        .main-content {
            flex: 1;
            padding: 36px 50px;
            box-sizing: border-box;
        }
        .report-box {
            max-width: 860px;
            margin: 0 auto;
            border: 2px solid #002B49;
            padding: 30px 44px 40px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .target-box {
            width: 380px;
            max-width: 100%;
            margin: 0 auto 24px auto;
            border: 1px solid #cccccc;
            padding: 14px 18px;
            font-size: 16px;
            font-weight: bold;
            box-sizing: border-box;
        }
        .type-select {
            width: 240px;
            height: 36px;
            padding: 0 10px;
            border: 1.5px solid #002B49;
            background-color: #ffffff;
            color: #000000;
            font-size: 14px;
            margin-bottom: 20px;
        }
        .content-label {
            display: block;
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .content-textarea {
            width: 100%;
            height: 150px;
            padding: 12px;
            border: 1.5px solid #002B49;
            font-size: 14px;
            font-family: inherit;
            box-sizing: border-box;
            resize: vertical;
        }
        .file-row {
            margin-top: 16px;
            display: flex;
            align-items: center;
            gap: 12px;
        }
        .file-btn {
            display: inline-block;
            padding: 6px 16px;
            border: 1.5px solid #002B49;
            background-color: #ffffff;
            color: #000000;
            font-size: 13px;
            cursor: pointer;
        }
        .file-btn:hover {
            background-color: #f0f4f8;
        }
        .file-name {
            font-size: 13px;
            color: #333333;
        }
        .submit-row {
            margin-top: 24px;
            display: flex;
            justify-content: center;
            gap: 20px;
        }
        .btn-submit {
            padding: 10px 52px;
            border: 1.5px solid #002B49;
            border-radius: 10px;
            background-color: #ffffff;
            color: #000000;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-submit:hover {
            background-color: #f0f4f8;
        }
        .btn-back {
            padding: 10px 28px;
            border: 1px solid #888888;
            border-radius: 10px;
            background-color: #ffffff;
            color: #333333;
            font-size: 14px;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
        }
        .btn-back:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content">
            <div class="report-box">
                <c:if test="${not empty error}">
                    <p style="color: #cc0000; font-size: 14px; margin-top: 0;"><c:out value="${error}" /></p>
                </c:if>

                <div class="target-box">
                    通報対象：<c:out value="${target.company_name}" />（<c:out value="${target.job_title}" />）
                </div>

                <form action="${pageContext.request.contextPath}/report/ReportSend.action" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="job_id" value="${target.job_id}">
                    <input type="hidden" name="company_account_id" value="${target.company_account_id}">

                    <div>
                        <select name="report_type" class="type-select" required>
                            <option value="">通報種類を選択</option>
                            <option value="虚偽の求人内容">虚偽の求人内容</option>
                            <option value="法令違反・労働条件の相違">法令違反・労働条件の相違</option>
                            <option value="不適切な言動・ハラスメント">不適切な言動・ハラスメント</option>
                            <option value="スパム・勧誘行為">スパム・勧誘行為</option>
                            <option value="その他">その他</option>
                        </select>
                    </div>

                    <label for="report-content" class="content-label">通報内容</label>
                    <textarea id="report-content" name="content" class="content-textarea" maxlength="480" required
                              placeholder="具体的な状況や問題点をご記入ください"></textarea>

                    <div class="file-row">
                        <label for="evidence-file" class="file-btn">ファイルを添付</label>
                        <input type="file" id="evidence-file" name="evidence_file" style="display: none;"
                               onchange="document.getElementById('selectedFileName').textContent = this.files.length > 0 ? this.files[0].name : '選択されていません';">
                        <span id="selectedFileName" class="file-name">選択されていません</span>
                    </div>

                    <div class="submit-row">
                        <a href="${pageContext.request.contextPath}/jobsearchServlet/JobDetail.action?id=${target.job_id}" class="btn-back">戻る</a>
                        <button type="submit" class="btn-submit">送信</button>
                    </div>
                </form>
            </div>
        </main>
    </div>

</body>
</html>