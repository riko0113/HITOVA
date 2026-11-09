<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録エラー - HITOVA</title>
<style>
    body { font-family: sans-serif; text-align: center; margin-top: 100px; }
    .btn { padding: 10px 40px; background: white; border: 1px solid gray; cursor: pointer; text-decoration: none; color: black; }
</style>
</head>
<body>
    <h3 style="color: red;">登録に失敗しました。</h3>
    <p>入力内容に誤りがあるか、システムエラーが発生しました。</p>
    
    <div style="margin-top: 50px;">
        <!-- 戻るボタンのリンク先をスキルの登録画面(SkillRegister.action)に変更 -->
        <a href="${pageContext.request.contextPath}/skillServlet/SkillRegister.action" class="btn">入力画面へ戻る</a>
    </div>
</body>
</html>