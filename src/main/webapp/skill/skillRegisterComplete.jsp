<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登録完了 - HITOVA</title>
</head>
<body>
    <!-- ヘッダー（既存の共通ヘッダーがあれば読み替えてください） -->
    <header style="display: flex; justify-content: space-between; padding: 20px;">
        <div><img src="../画像のパス" alt="HITOVA"></div>
        <div>
            <a href="#">マイページ</a>
            <a href="#">ログアウト</a>
        </div>
    </header>

    <main style="text-align: center; margin-top: 100px;">
        <h3>正常にプロフィールに登録されました</h3>

        <div style="margin-top: 50px; display: flex; justify-content: center; gap: 20px;">
            <!-- スキル一覧（またはマイページ）へ遷移するボタン -->
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/skillServlet/Skill.action'" style="...">戻る</button>
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/skillServlet/Skill.action'" style="...">完了</button>
        </div>
    </main>
</body>
</html>