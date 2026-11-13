<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>資格一覧 - HITOVA</title>
<style>
    body { font-family: sans-serif; text-align: center; margin-top: 30px; }
    .header-area { display: flex; justify-content: space-between; padding: 0 50px; margin-bottom: 40px; }
    .container { max-width: 700px; margin: 0 auto; text-align: left; border: 1px solid #ccc; padding: 30px; box-sizing: border-box; }
    h2 { margin-bottom: 30px; text-align: center; font-size: 1.2em; }
    
    .list-item { display: flex; align-items: center; justify-content: space-between; margin-bottom: 15px; border-bottom: 1px solid #eee; padding-bottom: 10px; }
    .col-name { width: 40%; padding: 5px; border: 1px solid #ccc; background: #fff; box-sizing: border-box; }
    .col-date { width: 30%; padding: 5px; border: 1px solid #ccc; background: #fff; box-sizing: border-box; text-align: center; }
    .col-action { width: 25%; text-align: right; font-size: 0.9em; }
    
    .deleted .col-name, .deleted .col-date { text-decoration: line-through; color: #888; background: #f9f9f9; }
    .deleted .action-link { color: #0066cc; cursor: pointer; text-decoration: underline; }

    .btn-group { display: flex; justify-content: center; gap: 50px; margin-top: 40px; }
    .btn { padding: 10px 0; width: 150px; text-align: center; cursor: pointer; border: 1px solid #ccc; background-color: #fff; color: #0066cc; text-decoration: none; box-sizing: border-box; font-size: 1em; }
    .btn-save { background-color: #4CAF50; color: white; border: none; }
    
    .empty-msg { text-align: center; color: #666; margin-bottom: 20px; }
    .error-msg { color: #d9534f; text-align: center; margin-bottom: 20px; font-weight: bold; }
</style>
</head>
<body>
    <header class="header-area">
        <div><span style="color: #4CAF50; font-weight: bold; font-size: 24px;">HITOVA</span></div>
        <div style="font-size: 0.9em; color: #666;">マイページ ログアウト</div>
    </header>

<div class="container">
    <h2>資格一覧</h2>

    <!-- エラーメッセージ表示エリア -->
    <c:if test="${not empty errorMessage}">
        <div class="error-msg">${errorMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/qualificationServlet/QualificationUpdate.action" method="post">
        <c:choose>
            <c:when test="${empty qualificationList}">
                <div class="empty-msg">登録されている資格はありません。</div>
            </c:when>
            <c:otherwise>
                <c:forEach var="q" items="${qualificationList}">
                    <div class="list-item" id="row-${q.qualification_id}">
                        <div class="col-name">${q.qualification_name}</div>
                        <div class="col-date">${q.qualification_date}</div>
                        <div class="col-action" id="action-${q.qualification_id}">
                            <a href="javascript:void(0);" onclick="markDeleted(${q.qualification_id})" style="color: #0066cc; text-decoration: none;">削除</a>
                            <input type="hidden" name="delete_ids" id="del-input-${q.qualification_id}" value="">
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <div style="text-align: right; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/qualificationServlet/QualificationRegister.action" style="color: #0066cc; text-decoration: none; font-size: 0.9em;">新規資格登録へ</a>
        </div>

        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/qualificationServlet/Qualification.action" class="btn">キャンセル</a>
            <button type="submit" class="btn btn-save">保存</button>
        </div>
    </form>
</div>

<script>
    function markDeleted(id) {
        const row = document.getElementById('row-' + id);
        const actionArea = document.getElementById('action-' + id);

        row.classList.add('deleted');
        actionArea.innerHTML = '削除しました。 <span class="action-link" onclick="restore(' + id + ')">元に戻す</span>' +
                               '<input type="hidden" name="delete_ids" id="del-input-' + id + '" value="' + id + '">';
    }

    function restore(id) {
        const row = document.getElementById('row-' + id);
        const actionArea = document.getElementById('action-' + id);

        row.classList.remove('deleted');
        actionArea.innerHTML = '<a href="javascript:void(0);" onclick="markDeleted(' + id + ')" style="color: #0066cc; text-decoration: none;">削除</a>' +
                               '<input type="hidden" name="delete_ids" id="del-input-' + id + '" value="">';
    }
</script>
</body>
</html>