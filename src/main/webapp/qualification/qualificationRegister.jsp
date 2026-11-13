<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>資格登録 - HITOVA</title>
<style>
    body { font-family: sans-serif; margin-top: 30px; text-align: center; }
    .header-area { display: flex; justify-content: space-between; padding: 0 50px; margin-bottom: 40px; }
    .container { max-width: 800px; margin: 0 auto; text-align: left; }
    .page-title { display: flex; align-items: baseline; justify-content: center; gap: 20px; margin-bottom: 40px; }
    .page-title h2 { margin: 0; font-size: 1.2em; }
    .page-title p { margin: 0; font-size: 0.8em; color: #666; }
    
    .form-row { display: flex; justify-content: center; gap: 10px; margin-bottom: 10px; align-items: center; }
    select, input[type="date"] { padding: 5px; border: 1px solid #333; height: 35px; box-sizing: border-box; }
    select { width: 120px; }
    
    .add-link { text-align: right; margin-right: 50px; margin-bottom: 40px; }
    .add-link a { color: #0066cc; text-decoration: none; font-size: 0.9em; }
    
    .btn-group { display: flex; justify-content: center; gap: 50px; }
    .btn { padding: 10px 0; width: 150px; text-align: center; cursor: pointer; border: 1px solid #ccc; background-color: #fff; color: #0066cc; text-decoration: none; }
    .btn-submit { background-color: #4CAF50; color: white; border: none; }
    .error-msg { color: #d9534f; text-align: center; margin-bottom: 20px; font-weight: bold; }
</style>
</head>
<body>
    <header class="header-area">
        <div><span style="color: #4CAF50; font-weight: bold; font-size: 24px;">HITOVA</span></div>
        <div style="font-size: 0.9em; color: #666;">
            マイページ ログアウト
        </div>
    </header>

    <div class="container">
        <div class="page-title">
            <h2>資格登録</h2>
            <p>持っている資格を登録できます</p>
        </div>
        
        <!-- エラーメッセージ表示エリア（登録失敗時に表示） -->
        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/qualificationServlet/QualificationRegisterExecute.action" method="post">
            <div class="form-row">
                <!-- 大・中・小分類（小分類の値を qualification_detail_id として扱います） -->
                <select name="qualification_large" required>
                    <option value="">大分類</option>
                    <option value="IT">IT</option>
                </select>
                <select name="qualification_middle" required>
                    <option value="">中分類</option>
                    <option value="国家資格">国家資格</option>
                </select>
                <select name="qualification_small" required>
                    <option value="">小分類</option>
                    <option value="3">ITパスポート</option>
                    <option value="2">基本情報技術者</option>
                </select>

                <!-- 取得年月日（カレンダー入力） -->
                <input type="date" name="qualification_date" required>
            </div>

            <div class="add-link">
                <a href="#">さらに追加</a>
            </div>

            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/qualificationServlet/Qualification.action" class="btn">キャンセル</a>
                <button type="submit" class="btn btn-submit">登録</button>
            </div>
        </form>
    </div>
</body>
</html>