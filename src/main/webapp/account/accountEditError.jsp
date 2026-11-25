
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/accountCss/accountEditError.css">

<div class="main-layout">
    <%@ include file="../../jobSeekerSidebar.jsp" %>
    <div class="main-content">
        <main class="account-container">

            <!-- 画面タイトル・ボタン -->
            <div class="account-header">
                <h1>アカウント情報の編集</h1>
                <div class="button-container">
                    <button type="submit" form="accountEditForm" class="update-button">更新</button>
                    <a href="${pageContext.request.contextPath}/account/AccountInformation.action" class="cancel-button">キャンセル</a>
                </div>
            </div>

            <!-- 編集フォーム -->
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
                            value="<c:out value='${enteredNickName}'/>"
                            minlength="4" maxlength="20" required>

                        <c:if test="${nicknameDuplicateError}">
                            <small class="error-message">※既に使用されています。</small>
                        </c:if>

                        <c:if test="${nicknameFormatError}">
                            <small class="error-message">※大文字アルファベット・小文字アルファベット・数字のいずれかを含めてください。</small>
                        </c:if>
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
                                ${enteredScout == 'true' ? 'checked' : ''}>
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
                        <select id="industry" name="desired_industry_id">
                            <option value="">----------------</option>
                            <c:forEach var="industry" items="${industryList}">
                                <option value="${industry.industry_id}"
                                    ${industry.industry_id == enteredIndustryId ? 'selected' : ''}>
                                    <c:out value="${industry.industry_name}"/>
                                </option>
                            </c:forEach>
                        </select>

                        <c:if test="${industryError}">
                            <small class="error-message">※希望業種を選択してください。</small>
                        </c:if>
                    </div>
                </div>

                <!-- 希望職種 -->
                <div class="information-row">
                    <label for="jobType">希望職種</label>
                    <span>：</span>
                    <div class="field-content">
                        <select id="jobType" name="desired_occupation_id">
                            <option value="">----------------</option>
                            <c:forEach var="occupation" items="${occupationList}">
                                <option value="${occupation.occupation_id}"
                                    ${occupation.occupation_id == enteredOccupationId ? 'selected' : ''}>
                                    <c:out value="${occupation.occupation_name}"/>
                                </option>
                            </c:forEach>
                        </select>

                        <c:if test="${occupationError}">
                            <small class="error-message">※希望職種を選択してください。</small>
                        </c:if>
                    </div>
                </div>

                <c:if test="${not empty errorMessage}">
                    <p class="error-message"><c:out value="${errorMessage}"/></p>
                </c:if>

            </form>
        </main>
    </div>
</div>

<%@ include file="../../footer.jsp" %>
