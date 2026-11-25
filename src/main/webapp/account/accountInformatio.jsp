
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/accountCss/accountInformation.css">

<div class="main-layout">
    <%@ include file="../../jobSeekerSidebar.jsp" %>
    <div class="main-content">
        <main class="account-container">
            <!-- 画面タイトル・ボタン -->
            <div class="account-header">
                <h1>アカウント情報</h1>
                <div class="button-container">
                    <!-- メールアドレス変更 -->
                    <a href="${pageContext.request.contextPath}/account/EmailChange.action" class="change-button">
                        メールアドレス変更
                    </a>
                    <!-- パスワード変更 -->
                    <a href="${pageContext.request.contextPath}/account/PasswordChange.action" class="change-button">
                        パスワード変更
                    </a>
                    <!-- 編集 -->
                    <a href="${pageContext.request.contextPath}/account/AccountEdit.action" class="edit-button">
                        編集
                    </a>
                </div>
            </div>

            <!-- メールアドレス -->
            <div class="information-row">
                <label>メールアドレス</label>
                <span>：</span>
                <span><c:out value="${account.email}"/></span>
            </div>

            <!-- ニックネーム -->
            <div class="information-row">
                <label>ニックネーム</label>
                <span>：</span>
                <span><c:out value="${account.nick_name}"/></span>
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
                <label>スカウト機能</label>
                <span>：</span>
                <span>
                    <c:choose>
                        <c:when test="${account.scout}">ON</c:when>
                        <c:otherwise>OFF</c:otherwise>
                    </c:choose>
                </span>
            </div>

            <!-- 希望業種 -->
            <div class="information-row">
                <label>希望業種</label>
                <span>：</span>
                <span>
                    <c:choose>
                        <c:when test="${not empty account.desired_industry_name}">
                            <c:out value="${account.desired_industry_name}"/>
                        </c:when>
                        <c:otherwise>未設定</c:otherwise>
                    </c:choose>
                </span>
            </div>

            <!-- 希望職種 -->
            <div class="information-row">
                <label>希望職種</label>
                <span>：</span>
                <span>
                    <c:choose>
                        <c:when test="${not empty account.desired_occupation_name}">
                            <c:out value="${account.desired_occupation_name}"/>
                        </c:when>
                        <c:otherwise>未設定</c:otherwise>
                    </c:choose>
                </span>
            </div>
        </main>
    </div>
</div>

<%@ include file="../../footer.jsp" %>
