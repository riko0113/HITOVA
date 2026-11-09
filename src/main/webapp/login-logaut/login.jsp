<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../head.jsp" %>
<style>
    body {
        background-color: rgb(241, 253, 239);
    }

    header img {
        width: 180px;
        height: auto;
    }

    main {
        zoom: 80%;
        width: 580px;
        margin: 24px auto;
        background-color: white;
        border: 1px solid rgb(200, 200, 200);
    }

    main label {
        display: block;
        width: 400px;
        margin: 24px auto;
        font-size: 16px;
    }

    main input:not([type="checkbox"]) {
        display: block;
        width: 380px;
        margin: 8px auto 0;
        padding: 9px;
        font-size: 16px;
        background-color: rgb(250, 250, 250);
        border: 1px solid rgb(180, 180, 180);
    }

    main input[type="checkbox"] {
        margin: 0 6px 0 0;
        padding: 0;
    }

    .button-container {
        display: flex;
    }

    .button-container button {
        flex: 1;
        padding: 16px 20px;
        font-size: 18px;
        background-color: rgb(245, 245, 245);
        color: rgb(50, 50, 50);
        border: none;
        border-bottom: 1px solid rgb(200, 200, 200);
    }

    .button-container button:nth-child(2) {
        border-left: 1px solid rgb(190, 190, 190);
    }

    .button-container button:hover {
        background-color: rgb(220, 220, 220);
    }


    .button-container button.active {
        background-color: rgb(110, 110, 110);
        color: white;
    }

    .terms {
        width: 400px;
        margin: 16px auto;
        font-size: 13px;
    }



    .endbu {
        text-align: center;
    }

    /* 確認ボタン：通常 */
    .endbu button {
        width: 200px;
        height: 46px;
        margin: 16px 0 28px;
        font-size: 16px;
        background-color: rgb(100, 100, 100);
        color: white;
        border: none;
    }

    /* 確認ボタン：ホバー */
    .endbu button:hover {
        background-color: rgb(80, 80, 80);
    }
</style>
<header>
    <h1><a href="#"><img src="<c:url value='/login-logaut/img/image.png' />" alt="HITOVA"></a></h1>
</header>
<main>
    <form id="auth-form" method="post">
        <div class="button-container">
            <button type="button">ログイン</button>
            <button type="button">新規登録</button>
        </div>

        <div>
            <label for="email">メールアドレス<br>
                <input type="email" id="email" name="email" maxlength="254" required
                       autocomplete="email">
            </label>
            <label for="password">パスワード（8字以上20字以下）<br>
                <input type="password" id="password" name="password" required autocomplete="current-password">
            </label>
        </div>
        <div id="ll">
            <label><a href="#">＞メールアドレス・パスワードを忘れた方はこちら</a></label>
            <label><a href="#">＞企業のアカウント新規登録はこちら</a></label>
        </div>
        <div class="new">
            <label for="password-confirm">確認用パスワード（パスワードの再入力）<br>
                <input type="password" id="password-confirm" name="passwordConfirm"
                       minlength="8" maxlength="20" autocomplete="new-password">
            </label>
            <label for="nickname">ニックネーム（英数字を組み合わせた4字以上20字以下）<br>
                <input type="text" id="nickname" name="nickname" minlength="4" maxlength="20"
                       pattern="(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{4,20}">
            </label>
            <label class="terms">
                <input type="checkbox" name="agreeTerms" value="on">利用規約に同意する（<a href="#">利用規約全文はこちら</a>）
            </label>
            <label class="terms">
                <input type="checkbox" name="agreePrivacy" value="on">プライバシーポリシーに同意する（<a href="#">プライバシーポリシー全文はこちら</a>）
            </label>
        </div>
        <div class="endbu">
            <button type="submit">ログイン</button>
        </div>
    </form>
</main>
<script>
    let mode = "ログイン";

    const form = document.getElementById("auth-form");
    const buttons = document.querySelectorAll(".button-container button");
    const newArea = document.querySelector(".new");
    const newInputs = document.querySelectorAll(".new input");
    const endButton = document.querySelector(".endbu button");
    const password = document.getElementById("password");
    const passwordConfirm = document.getElementById("password-confirm");
    const ll = document.getElementById("ll");

    function updateMode() {
        buttons.forEach(function(button) {
            const selected = button.textContent.trim() === mode;
            if (selected) {
                button.classList.add("active");
            } else {
                button.classList.remove("active");
            }
        });

        if (mode === "新規登録") {
            newArea.style.display = "block";
            ll.style.display = "none";
            password.minLength = 8;
            password.maxLength = 20;
            password.autocomplete = "new-password";
            endButton.textContent = "確認用メール送信";
        } else {
            newArea.style.display = "none";
            ll.style.display = "block";
            password.minLength = 8;
            password.maxLength = 20;
            password.autocomplete = "current-password";
            endButton.textContent = "ログイン";
        }

        newInputs.forEach(function(input) {
            input.disabled = mode !== "新規登録";
            input.required = mode === "新規登録";
        });
    }

    buttons.forEach(function(button) {
        button.addEventListener("click", function() {
            mode = button.textContent.trim();
            updateMode();
        });
    });

    endButton.addEventListener("click", function() {
        if (mode === "新規登録") {
            form.action = "${pageContext.request.contextPath}/auth/register";
        } else {
            form.action = "${pageContext.request.contextPath}/auth/login";
        }

        passwordConfirm.setCustomValidity("");
        if (mode === "新規登録" && password.value !== passwordConfirm.value) {
            passwordConfirm.setCustomValidity("確認用パスワードが一致していません。");
        }
    });

    function clearPasswordError() {
        passwordConfirm.setCustomValidity("");
    }

    password.addEventListener("input", clearPasswordError);
    passwordConfirm.addEventListener("input", clearPasswordError);

    updateMode();
</script>
<%@ include file="../footer.jsp" %>
