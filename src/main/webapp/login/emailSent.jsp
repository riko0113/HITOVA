<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty requestScope.authEmail}">
    <c:redirect url="/login/login.jsp" />
</c:if>
<%@ include file="/head.jsp" %>
<link rel="stylesheet" href="<c:url value='/login/verification.css' />">
<main class="auth-panel">
    <h1>メールアドレスの確認</h1>
    <p><c:out value="${requestScope.authEmail}" /></p>
    <c:if test="${requestScope.mailSent}">
        <p role="status">確認メールを送信しました。</p>
    </c:if>
    <p>この画面は開いたまま、スマートフォンや別のタブで確認メールのリンクを開いてください。</p>
    <p>確認できたら、この画面で「確認をする」を押してください。</p>
    <form method="post" action="<c:url value='/login/Verify.action' />">
        <button type="submit">確認をする</button>
    </form>
    <form method="post" action="<c:url value='/login/Resend.action' />">
        <button type="submit" id="resend-button" aria-describedby="resend-time"
                data-resend-millis="<c:out value='${requestScope.resendMillis}' default='0' />">確認メールを再送する</button>
        <p id="resend-time" class="resend-time" hidden></p>
    </form>
    <p>メールが見つからない場合は、迷惑メールフォルダーも確認してください。</p>
    <p><a href="<c:url value='/login/login.jsp' />">ログイン画面へ戻る</a></p>
</main>
<script>
    const resend_button = document.getElementById("resend-button");
    const resend_time = document.getElementById("resend-time");
    const resend_until = performance.now() + Number(resend_button.dataset.resendMillis);

    function updateResendTime() {
        const seconds = Math.max(0, Math.ceil((resend_until - performance.now()) / 1000));
        resend_button.disabled = seconds > 0;
        resend_time.hidden = seconds === 0;

        if (seconds > 0) {
            resend_time.textContent = "再送可能時間まで" + seconds + "秒";
        } else {
            resend_time.textContent = "";
            clearInterval(timer);
        }
    }

    const timer = setInterval(updateResendTime, 250);
    updateResendTime();
</script>
<%@ include file="/footer.jsp" %>
