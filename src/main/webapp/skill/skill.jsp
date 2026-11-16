<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${skillList == null}">
    <jsp:forward page="/skillServlet/SkillUpdate.action" />
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スキル一覧 - HITOVA</title>
<style>
    body { font-family: sans-serif; text-align: center; margin-top: 30px; }
    .header-area { display: flex; justify-content: space-between; padding: 0 50px; margin-bottom: 40px; }
    .container { max-width: 700px; margin: 0 auto; text-align: left; border: 1px solid #ccc; padding: 30px; box-sizing: border-box; }
    h2 { margin-bottom: 30px; text-align: center; font-size: 1.2em; }
    
    .list-item { display: flex; align-items: center; justify-content: space-between; margin-bottom: 15px; border-bottom: 1px solid #eee; padding-bottom: 10px; }
    .col-name { width: 70%; padding: 5px; border: 1px solid #ccc; background: #fff; box-sizing: border-box; }
    .col-action { width: 25%; text-align: right; font-size: 0.9em; }
    
    /* 削除時のグレーアウト設定 */
    .deleted .col-name { text-decoration: line-through; color: #888; background: #f9f9f9; }
    .deleted .col-content { text-decoration: line-through; color: #888; background-color: #f9f9f9 !important; }
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
    <h2>スキル一覧</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-msg">${errorMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/skillServlet/SkillUpdate.action" method="post">
        <c:choose>
            <c:when test="${empty skillList}">
                <div class="empty-msg">登録されているスキルはありません。</div>
            </c:when>
            <c:otherwise>
                <c:forEach var="s" items="${skillList}">
                    
                    <!-- 削除ボタンが上にくるように align-items: flex-start を追加 -->
                    <div class="list-item" id="row-${s.skill_id}" style="align-items: flex-start;">
                        
                        <!-- 左側：タイトルと詳細をまとめるコンテナ -->
                        <div style="width: 70%;">
                            <!-- タイトル -->
                            <div class="col-name" style="width: 100%; margin-bottom: 8px;">${s.skill_name}</div>
                            
                            <!-- 詳細 -->
                            <textarea class="col-content" readonly style="width: 100%; padding: 8px; border: 1px solid #ccc; box-sizing: border-box; font-size: 0.9em; height: 60px; resize: vertical; background-color: #fff;">${s.skill_content}</textarea>
                        </div>

                        <!-- 右側：削除アクション -->
                        <div class="col-action" id="action-${s.skill_id}" style="padding-top: 5px;">
                            <a href="javascript:void(0);" onclick="markDeleted(${s.skill_id})" style="color: #0066cc; text-decoration: none;">削除</a>
                            <input type="hidden" name="delete_ids" id="del-input-${s.skill_id}" value="">
                        </div>
                    </div>
                    
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <div style="text-align: right; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/skillServlet/SkillRegister.action" style="color: #0066cc; text-decoration: none; font-size: 0.9em;">新規スキル登録へ</a>
        </div>

        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/skillServlet/Skill.action" class="btn">キャンセル</a>
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