
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/accountCss/accountEdit.css">

<main class="account-container">

    <!-- 画面タイトル・ボタン -->
    <div class="account-header">

        <h1>アカウント情報</h1>

        <div class="button-container">

            <!-- キャンセル -->
            <a href="${pageContext.request.contextPath}/account/AccountInformation.action"
                class="cancel-button">
                キャンセル
            </a>

            <!-- 更新 -->
            <button type="submit" form="accountEditForm"
                class="update-button">
                更新
            </button>

        </div>
    </div>

    <!-- 編集フォーム -->
    <form id="accountEditForm"
        action="${pageContext.request.contextPath}/account/AccountEdit.action"
        method="post">

        <input type="hidden" name="mode" value="update">

        <!-- メールアドレス -->
        <div class="information-row">
            <label>メールアドレス</label>
            <span>：</span>
            <span>
                <c:out value="${account.email}" />
            </span>
        </div>

        <!-- ニックネーム -->
        <div class="information-row">
            <label for="nickname">ニックネーム</label>
            <span>：</span>

            <div class="field-content">
                <input type="text"
                    id="nickname"
                    name="nickname"
                    value="<c:out value='${account.nick_name}'/>"
                    minlength="4"
                    maxlength="20"
                    required>
            </div>
        </div>

        <!-- 本人確認 -->
        <div class="information-row">
            <label>本人確認</label>
            <span>：</span>
            <span>
                <c:choose>
                    <c:when test="${account.identity_verification}">
                        済
                    </c:when>
                    <c:otherwise>
                        未
                    </c:otherwise>
                </c:choose>
            </span>
        </div>

        <!-- スカウト機能 -->
        <div class="information-row">
            <label for="toggle">スカウト機能</label>
            <span>：</span>

            <div class="field-content">

                <label class="scout-toggle">
                    <input type="checkbox"
                        id="toggle"
                        name="toggle"
                        value="on"
                        ${account.scout ? 'checked' : ''}>
                    <span class="toggle-slider"></span>
                </label>

            </div>
        </div>

        <!-- 希望業種 -->
        <div class="information-row">
            <label for="industry">希望業種</label>
            <span>：</span>

            <div class="field-content">

                <select id="industry" name="industry">

                    <option value="">----------------</option>

                    <c:forEach var="industry" items="${industryList}">
                        <option value="${industry.industry_id}"
                            ${industry.industry_id == account.desired_industry_id ? 'selected' : ''}>
                            <c:out value="${industry.industry_name}" />
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

                <select id="jobType" name="jobType">

                    <option value="">----------------</option>

                    <c:forEach var="occupation" items="${occupationList}">
                        <option value="${occupation.occupation_id}"
                            ${occupation.occupation_id == account.desired_occupation_id ? 'selected' : ''}>
                            <c:out value="${occupation.occupation_name}" />
                        </option>
                    </c:forEach>

                </select>

            </div>
        </div>

    </form>

</main>
