<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スキル登録 - HITOVA</title>
<style>
    body { font-family: sans-serif; margin-top: 30px; text-align: center; }
    .header-area { display: flex; justify-content: space-between; padding: 0 50px; margin-bottom: 40px; }
    .container { max-width: 600px; margin: 0 auto; text-align: left; }
    .page-title { margin-bottom: 20px; }
    .page-title h2 { margin: 0 0 5px 0; font-size: 1.2em; }
    .page-title p { margin: 0; font-size: 0.8em; color: #666; line-height: 1.4; }
    
    .form-group { margin-bottom: 20px; }
    .form-group label { display: block; margin-bottom: 5px; font-size: 0.9em; }
    input[type="text"], textarea { width: 100%; padding: 10px; border: 1px solid #333; box-sizing: border-box; font-size: 1em; }
    textarea { height: 100px; resize: vertical; }
    
    .add-link { text-align: right; margin-bottom: 40px; }
    .add-link a { color: #0066cc; text-decoration: underline; font-size: 0.9em; }
    
    .btn-group { display: flex; justify-content: center; gap: 50px; }
    .btn { padding: 10px 0; width: 150px; text-align: center; cursor: pointer; border: 1px solid #ccc; background-color: #fff; color: #0066cc; text-decoration: none; display: inline-block; }
    .btn-submit { background-color: #4CAF50; color: white; border: none; }
    .error-msg { color: #d9534f; margin-bottom: 20px; font-weight: bold; }
</style>
</head>
<body>
    <header class="header-area">
        <div><span style="color: #4CAF50; font-weight: bold; font-size: 24px;">HITOVA</span></div>
        <div style="font-size: 0.9em; color: #666;">マイページ ログアウト</div>
    </header>

    <div class="container">
        <div class="page-title">
            <h2>スキル登録</h2>
            <p>持っているスキルを自由に登録できます<br>例）趣味、海外留学、言語習得、アルバイト・サークル活動等アピールポイント</p>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/skillServlet/SkillRegisterExecute.action" method="post">
            
            <div class="form-group">
                <input type="text" name="skill_name" placeholder="例）趣味のイラスト投稿でインプレッション〇万達成" required>
            </div>

            <div class="form-group">
                <label>詳細</label>
                <textarea name="skill_content" required></textarea>
            </div>

            <div class="add-link">
                <a href="#">さらに追加</a>
            </div>

            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/skillServlet/Skill.action" class="btn">キャンセル</a>
                <button type="submit" class="btn btn-submit">登録</button>
            </div>
        </form>
    </div>
</body>
</html>