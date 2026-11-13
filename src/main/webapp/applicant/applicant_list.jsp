<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link href="../applicantCss/applicant_list.css" rel="stylesheet">

<h2>応募者一覧</h2>

    <div class="user-list-container">
        <!-- サーブレットから受け取る -->
        <c:forEach var="history" items="${historyList}">
            <div class="user-row">
                <!-- 左側：アイコン、名前、詳細情報 -->
                <div class="user-left">
                    <div class="user-icon">
                        <!-- 人物アイコンのSVG -->
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                        </svg>
                    </div>
                    <div class="user-info">
                        <h3><c:out value="${history.jobSeekerAccount.nick_name}" /></h3>
                        <p>生年月日：<span class="birth-date" data-profile='<c:out value="${history.profile}" />'></span></p>
                        <p>応募日時：<c:out value="${history.application_at}" /></p>
                        
                        <p>
                            <c:choose>
                                <c:when test="${history.jobSeekerAccount.identity_verification}">
                                    ✓ 本人確認済み
                                </c:when>
                                <c:otherwise>
                                    ○ 本人確認前
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>

                <!-- 右側：詳細ボタン（応募履歴IDやアカウントIDを渡すように変更） -->
                <div>
                    <form action="${pageContext.request.contextPath}/applicant/ApplicantDetail.action" method="get">
    					<input type="submit" value="詳細を見る" class="detail-btn">
    					<input type="hidden" name="job_id" value="${history.job_id}">
    					<input type="hidden" name="application_history_id" value="${history.application_history_id}">
					</form>
                </div>
            </div>
        </c:forEach>
        
        <c:if test="${empty historyList}">
            <p>データが存在しません。</p>
        </c:if>
    </div>

<script src="../applicantCss/applicant_lists.js"></script> 
<%@ include file="../footer.jsp" %>