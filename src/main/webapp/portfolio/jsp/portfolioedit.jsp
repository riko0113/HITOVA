<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/portfolio/css/portfolioAdd.css">

<div class="main-layout">
<%@ include file="../../jobSeekerSidebar.jsp" %>

<div class="main-content">

<div class="tuika">
    <h1>作品編集</h1>
</div>

<form action="${pageContext.request.contextPath}/portfolio/PortfolioEdit.action" method="post" enctype="multipart/form-data">
    <!-- 編集対象のポートフォリオIDを送信 -->
    <input type="hidden" name="portfolio_id" value="<c:out value="${not empty param.portfolio_id ? param.portfolio_id : portfolio.portfolio_id}" />">

    <div class="form-box">

        <!-- 作品名 -->
        <label>作品名 (50字以内)</label>
        <!-- 入力値を保持する（送信直後＞再入力＞DB既存値の順で表示） -->
        <c:set var="currentWorkName" value="${not empty param.work_name ? param.work_name : (not empty workName ? workName : portfolio.work_name)}" />
        <input type="text" id="workName" name="work_name"
               maxlength="50" placeholder="作品名を入力してください"
               value="<c:out value="${currentWorkName}" />">
        <!-- サーブレットから渡されたエラーメッセージを表示 -->
        <p id="workNameError" class="error"><c:out value="${workNameError}" /></p>

        <!-- 説明文 -->
        <span class="setumei">説明文 (300字以内)</span>
        <!-- 入力値を保持する -->
        <c:set var="currentDescription" value="${not empty param.description ? param.description : (not empty description ? description : portfolio.description)}" />
        <textarea id="description" name="description"
                  maxlength="300" placeholder="説明文を入力してください"><c:out value="${currentDescription}" /></textarea>
        <!-- サーブレットから渡されたエラーメッセージを表示 -->
        <p id="descriptionError" class="error"><c:out value="${descriptionError}" /></p>

        <!-- ファイル添付 -->
        <span>ファイル添付 (100MBまで)</span>

        <!-- すでにファイルが登録されている場合のプレビュー表示（署名付きURL・画像/動画出し分け） -->
        <c:if test="${not empty presignedUrl}">
            <div class="current-file-preview" style="margin-bottom: 10px;">
                <p>現在のファイル:</p>
                <c:choose>
                    <c:when test="${isVideo}">
                        <video src="<c:out value="${presignedUrl}" />" controls playsinline style="max-width: 240px; height: auto; display: block; margin-bottom: 5px;"></video>
                    </c:when>
                    <c:otherwise>
                        <img src="<c:out value="${presignedUrl}" />" alt="登録済みファイル" style="max-width: 200px; height: auto; display: block; margin-bottom: 5px;" />
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <!-- 新しくファイルをアップロードするための入力欄 -->
        <input type="file" name="work_file" accept="image/png,image/jpeg,image/webp,video/mp4,video/webm,video/quicktime">
        <p><span class="setumei">※ファイルを変更しない場合は、選択せずそのままで構いません。</span></p>

        <!-- サーブレットから渡されたエラーメッセージを表示 -->
        <p id="fileError" class="error"><c:out value="${fileError}" /></p>

    </div>

    <div class="button-area">
        <a href="${pageContext.request.contextPath}/portfolio/Portfolio.action" class="but">
            戻る
        </a>

        <button type="submit" class="but">
            変更する
        </button>
    </div>

</form>

</div>
</div>
<script src="${pageContext.request.contextPath}/portfolio/css/portfolioAdd.js" defer></script>
<%@ include file="../../footer.jsp" %>