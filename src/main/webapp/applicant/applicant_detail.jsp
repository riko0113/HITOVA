<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link href="../applicantCss/applicant_detail.css" rel="stylesheet">


<c:forEach var="detail" items="${detailList}">
    <!-- 応募履歴・アカウント取得 -->
    <c:set var="app" value="${detail.applicationHistory}" />
    <c:set var="account" value="${app.jobSeekerAccount}" />
	
	<div class="header-container">
		<h2>求職者詳細</h2>
		<a href="" target="_blank" class="btn-view btn-report">通報</a>
	</div>
	
    <!-- 基本情報 -->
    <h3>基本情報</h3>
    <ul>
        <li>氏名：<span id="nameArea"></span> さん</li>
        <li>生年月日：<span id="birthDateArea"></span></li>
        <li>本人確認状況：
            <c:choose>
                <c:when test="${account.identity_verification}">済</c:when>
                <c:otherwise>未</c:otherwise>
            </c:choose>
        </li>
    </ul>

    <!-- プロフィール -->
    <h3>プロフィール</h3>
    <ul>
        <li>希望職種：<span id="desiredJobArea"></span></li>
        <li>希望雇用形態：<span id="desiredEmploymentArea"></span></li>
        <li>経歴／職歴：<span id="workHistoryArea"></span></li>
        <li>最終学歴：<span id="highestEducationArea"></span></li>
        <li>希望勤務地：<span id="desiredLocationArea"></span></li>
    </ul>

    <!-- スキル -->
    <h3>スキル</h3>
    <ul>
        <li>登録したスキル：
            <c:choose>
                <c:when test="${not empty detail.skills}">
                    <ul>
                        <c:forEach var="skill" items="${detail.skills}">
                            <li>${skill.skill_name} <c:if test="${not empty skill.skill_content}">(${skill.skill_content})</c:if></li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>なし</c:otherwise>
            </c:choose>
        </li>
    </ul>

    <!-- 保有資格 -->
    <h3>保有資格</h3>
    <ul>
        <li>登録した資格：
            <c:choose>
                <c:when test="${not empty detail.qualifications}">
                    <ul>
                        <c:forEach var="qual" items="${detail.qualifications}">
                            <li>${qual.qualification_name}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>なし</c:otherwise>
            </c:choose>
        </li>
    </ul>

    <!-- 実績 -->
    <h3>実績</h3>
    <ul>
        <li>取得した実績：
            <c:choose>
                <c:when test="${not empty detail.achievements}">
                    <ul>
                        <c:forEach var="achieve" items="${detail.achievements}">
                            <li>${achieve.achievement}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>なし</c:otherwise>
            </c:choose>
        </li>
    </ul>

    <!-- ポートフォリオ -->
    <h3>ポートフォリオ</h3>
    <ul>
        <li>登録した作品：
            <c:choose>
                <c:when test="${not empty detail.portfolios}">
                    <c:forEach var="pf" items="${detail.portfolios}">
                        <div style="margin-top: 5px;">
                            <span>${pf.work_name}</span><br>
                            <a href="${pf.file_url}" target="_blank" class="btn-view">作品を見る</a>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>なし</c:otherwise>
            </c:choose>
        </li>
    </ul>
    
    <!-- メッセージ -->
    <h3>メッセージ</h3>
    <a href="" target="_blank" class="btn-view">メッセージを送る</a>
    
    <form action="${pageContext.request.contextPath}/applicant/ApplicantList.action" method="post">
    	<input type="submit" name="search" value="戻る">
    	<input type="hidden" name="job_id" value="${jobId}">
	</form>
</c:forEach>

<!-- 非表示のデータ格納要素（fn:escapeXmlで特殊文字対策＋style="display:none;"を追加） -->
<div id="profileDataContainer" data-profile="${fn:escapeXml(profileJson)}" style="display: none;"></div>

<script src="../applicantCss/applicant_detail.js"></script>
<%@ include file="../footer.jsp" %>