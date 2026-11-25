
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/accountCss/accountEdit.css">

<div class="main-layout">
    <%@ include file="../../jobSeekerSidebar.jsp" %>
    <div class="main-content">
        <main class="account-container">
            <div class="account-header">
                <h1>アカウント情報の編集</h1>
                <div class="button-container">
                	<button type="submit" form="accountEditForm" class="update-button">更新</button>
                    <a href="${pageContext.request.contextPath}/account/AccountInformation.action" class="cancel-button">キャンセル</a>
                </div>
            </div>

            <form id="accountEditForm" action="${pageContext.request.contextPath}/account/AccountEdit.action" method="post">
                <input type="hidden" name="mode" value="update">

                <!-- メールアドレス -->
                <div class="information-row">
                    <label>メールアドレス</label>
                    <span>：</span>
                    <span><c:out value="${account.email}"/></span>
                </div>

                <!-- ニックネーム -->
                <div class="information-row">
                    <label for="nickname">ニックネーム</label>
                    <span>：</span>
                    <div class="field-content">
                        <input type="text" id="nickname" name="nick_name"
                            value="<c:out value='${account.nick_name}'/>"
                            minlength="4" maxlength="20" required>
                    </div>
                </div>

                <!-- 本人確認 -->
                <div class="information-row">
                    <label>本人確認</label>
                    <span>：</span>
                    <span>
                        <c:choose>
                            <c:when test="${account.identity_verification}">済</c:when>
                            <c:otherwise>未</c:otherwise>
                        </c:choose>
                    </span>
                </div>

                <!-- スカウト機能 -->
                <div class="information-row">
                    <label for="scout">スカウト機能</label>
                    <span>：</span>
                    <div class="field-content">
                        <label class="scout-toggle">
                            <input type="checkbox" id="scout" name="scout" value="true"
                                ${account.scout ? 'checked' : ''}>
                            <input type="hidden" name="scout" value="false">
                            <span class="toggle-slider"></span>
                        </label>
                    </div>
                </div>

                <!-- 希望業種 -->
                <div class="information-row">
                    <label for="industry">希望業種</label>
                    <span>：</span>
                    <div class="field-content">
                        <select id="industry" name="desired_industry_id" required>
                            <option value="">----------------</option>
                            <c:forEach var="industry" items="${industryList}">
                                <option value="${industry.industry_id}"
                                    ${industry.industry_id == account.desired_industry_id ? 'selected' : ''}>
                                    <c:out value="${industry.industry_name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <!-- 希望職種 -->
                <div class="information-row">
                    <label for="jobType">希望職種</label>
                    <span>：</span>
                    <div class="field-content">
                        <select id="jobType" name="desired_occupation_id" required>
                            <option value="">----------------</option>
                            <c:forEach var="occupation" items="${occupationList}">
                                <option value="${occupation.occupation_id}"
                                    ${occupation.occupation_id == account.desired_occupation_id ? 'selected' : ''}>
                                    <c:out value="${occupation.occupation_name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <!-- エラーメッセージ -->
                <p class="error-message">${errorMessage}</p>
            </form>
        </main>
    </div>
</div>

<%@ include file="../../footer.jsp" %>
