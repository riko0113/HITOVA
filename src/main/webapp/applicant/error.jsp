<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>

<h2>エラーが発生しました</h2>
    
<p>
    <% 
        // サーブレット側からセットされた個別メッセージがあれば表示、なければデフォルト文言を表示
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage == null) {
            errorMessage = "指定されたURLやIDが無効です。正しい手順でアクセスし直してください。";
        }
    %>
    <%= errorMessage %>
</p>

<hr>

<p>以下のリンクからお戻りください。</p>
<a href="${pageContext.request.contextPath}/">トップページへ戻る</a>

</body>
</html>

<%@ include file="../footer.jsp" %>