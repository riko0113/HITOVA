<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登録完了 - HITOVA</title>
</head>
<body>
    <header style="display: flex; justify-content: space-between; padding: 20px;">
        <div><span style="color: #4CAF50; font-weight: bold; font-size: 24px;">HITOVA</span></div>
        <div>
            <a href="#">マイページ</a>
            <a href="#">ログアウト</a>
        </div>
    </header>

    <main style="text-align: center; margin-top: 100px;">
        <h3>正常にプロフィールに登録されました</h3>

        <div style="margin-top: 50px; display: flex; justify-content: center; gap: 20px;">
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/qualificationServlet/Qualification.action'" style="padding: 10px 40px; background: white; border: 1px solid gray;">戻る</button>
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/qualificationServlet/Qualification.action'" style="padding: 10px 40px; background: #4CAF50; color: white; border: none;">完了</button>
        </div>
    </main>
</body>
</html>