<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スキル登録</title>
<style>
    body { font-family: sans-serif; margin-top: 50px; }
    .container { max-width: 600px; margin: 0 auto; text-align: left; border: 1px solid #ccc; padding: 30px; }
    h2 { margin-bottom: 5px; }
    .note { font-size: 0.85em; color: #333; margin-bottom: 20px; line-height: 1.5; }
    .form-group { margin-bottom: 15px; }
    input[type="text"], textarea { width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #333; }
    textarea { height: 100px; resize: vertical; }
    .add-more { display: block; text-align: right; margin-bottom: 30px; color: #0066cc; text-decoration: none; font-size: 0.9em; }
    .btn-group { display: flex; justify-content: space-between; }
    .btn { padding: 10px 0; width: 45%; text-align: center; cursor: pointer; text-decoration: none; box-sizing: border-box; font-size: 1em; }
    .btn-cancel { border: 1px solid #999; background-color: #fff; color: #0066cc; }
    .btn-submit { border: none; background-color: #4CAF50; color: white; }
</style>
</head>
<body>
<div class="container">
    <h2>スキル登録</h2>
    <div class="note">持っているスキルを自由に登録できます<br>例）趣味、海外留学、言語習得、アルバイト・サークル活動等アピールポイント</div>
    <form action="${pageContext.request.contextPath}/skillServlet/SkillRegisterExecute.action" method="post">
        <div class="form-group">
            <input type="text" name="skillTitle" placeholder="例）趣味のイラスト投稿でインプレッション〇万達成" required>
        </div>
        <div class="form-group">
            <label style="font-size: 0.9em; display: block; margin-bottom: 5px;">詳細</label>
            <textarea name="skillDetail"></textarea>
        </div>
        <a href="#" class="add-more">さらに追加</a>
        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/skillServlet/Skill.action" class="btn btn-cancel">キャンセル</a>
            <button type="submit" class="btn btn-submit">登録</button>
        </div>
    </form>
</div>
</body>
</html>