<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/head.jsp" %>
<%@ include file="/unionAdminHeader.jsp" %>

<style>
    .main-layout {
        display: flex;
        min-height: calc(100vh - 64px);
        min-width: 1000px;
        background-color: #ffffff;
    }
    .main-content {
        flex: 1;
        padding: 40px 60px;
        background-color: #ffffff;
        box-sizing: border-box;
    }
    .page-title-area h1 {
        font-size: 24px;
        font-weight: bold;
        color: #111;
        margin: 0 0 28px 0;
    }

    /* 完了・全体エラーメッセージ */
    .msg-error {
        max-width: 720px;
        padding: 12px 16px;
        margin-bottom: 20px;
        background-color: #fef2f2;
        border: 1px solid #ef4444;
        color: #b91c1c;
        font-size: 14px;
        border-radius: 4px;
    }
    .msg-success {
        max-width: 720px;
        padding: 12px 16px;
        margin-bottom: 20px;
        background-color: #f0fdf4;
        border: 1px solid #22c55e;
        color: #15803d;
        font-size: 14px;
        border-radius: 4px;
    }

    /* フォーム枠 */
    .form-box {
        max-width: 720px;
        border: 1px solid #d1d5db;
        background-color: #ffffff;
        box-sizing: border-box;
    }
    .form-box-header {
        background-color: #e5e7eb;
        padding: 14px 24px;
        font-size: 18px;
        font-weight: bold;
        color: #111;
        border-bottom: 1px solid #d1d5db;
    }
    .form-box-body {
        padding: 32px 48px 40px 48px;
    }
    .form-row {
        display: flex;
        align-items: flex-start;
        margin-bottom: 20px;
    }
    .form-row:last-child {
        margin-bottom: 0;
    }
    .form-label {
        width: 180px;
        padding-top: 8px;
        font-size: 14px;
        font-weight: bold;
        color: #222;
        flex-shrink: 0;
    }
    .form-input-group {
        display: flex;
        flex-direction: column;
    }
    .form-input {
        width: 340px;
        height: 36px;
        padding: 6px 12px;
        border: 1px solid #d1d5db;
        border-radius: 2px;
        font-size: 14px;
        box-sizing: border-box;
        outline: none;
    }
    .form-input:focus {
        border-color: #135e75;
    }
    .error {
        margin: 4px 0 0 0;
        font-size: 12px;
        color: #dc2626;
        min-height: 16px;
    }

    /* ボタンエリア */
    .button-area {
        max-width: 720px;
        margin-top: 32px;
        display: flex;
        justify-content: center;
        gap: 110px;
    }
    .btn-cancel {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: 150px;
        height: 40px;
        background-color: #e5e7eb;
        color: #111;
        font-size: 15px;
        font-weight: bold;
        text-decoration: none;
        border: none;
        cursor: pointer;
        transition: background-color 0.15s;
    }
    .btn-cancel:hover {
        background-color: #d1d5db;
    }
    .btn-submit {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        width: 150px;
        height: 40px;
        background-color: #000000;
        color: #ffffff;
        font-size: 15px;
        font-weight: bold;
        border: none;
        cursor: pointer;
        transition: background-color 0.15s;
    }
    .btn-submit:hover {
        background-color: #333333;
    }
</style>

<div class="main-layout">
    <%@ include file="/unionAdminSidebar.jsp" %>

    <div class="main-content">
        <div class="page-title-area">
            <h1>組合管理者新規作成</h1>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="msg-error">${errorMessage}</div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="msg-success">${successMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/unionAdminServlet/UnionAdminCreate.action" method="post">
            <div class="form-box">
                <div class="form-box-header">新規組合管理者情報入力</div>
                <div class="form-box-body">

                    <!-- 氏名 -->
                    <div class="form-row">
                        <label class="form-label" for="name">氏名</label>
                        <div class="form-input-group">
                            <input type="text" id="name" name="name" maxlength="20" class="form-input"
                                   value="${not empty param.name ? param.name : name}" required>
                            <p class="error">${nameError}</p>
                        </div>
                    </div>

                    <!-- メールアドレス -->
                    <div class="form-row">
                        <label class="form-label" for="email">メールアドレス</label>
                        <div class="form-input-group">
                            <input type="email" id="email" name="email" class="form-input"
                                   value="${not empty param.email ? param.email : email}" required>
                            <p class="error">${emailError}</p>
                        </div>
                    </div>

                    <!-- パスワード設定 -->
                    <div class="form-row">
                        <label class="form-label" for="password">パスワード設定</label>
                        <div class="form-input-group">
                            <input type="password" id="password" name="password" class="form-input" required>
                            <p class="error">${passwordError}</p>
                        </div>
                    </div>

                    <!-- パスワード確認 -->
                    <div class="form-row">
                        <label class="form-label" for="password_confirm">パスワード確認</label>
                        <div class="form-input-group">
                            <input type="password" id="password_confirm" name="password_confirm" class="form-input" required>
                            <p class="error">${passwordConfirmError}</p>
                        </div>
                    </div>

                </div>
            </div>

            <div class="button-area">
                <a href="${pageContext.request.contextPath}/unionAdminServlet/CompanyList.action" class="btn-cancel">
                    キャンセル
                </a>
                <button type="submit" class="btn-submit">
                    新規作成
                </button>
            </div>
        </form>
    </div>
</div>

<%@ include file="/footer.jsp" %>