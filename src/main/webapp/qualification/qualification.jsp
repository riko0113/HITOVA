<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>資格一覧 - HITOVA</title>
<style>
    body { font-family: sans-serif; text-align: center; margin-top: 30px; }
    .container { max-width: 600px; margin: 0 auto; text-align: left; }
    .header-area { display: flex; justify-content: space-between; padding: 0 50px; margin-bottom: 40px; }
    h3 { margin-bottom: 30px; text-align: center; font-size: 1.2em;}
    
    .list-wrapper { display: flex; flex-direction: column; align-items: center; gap: 10px; }
    .list-item { display: flex; align-items: center; gap: 15px; }
    
    /* 入力枠のような見た目 */
    .item-input { 
        padding: 5px 10px; 
        border: 1px solid #ddd; 
        height: 35px; 
        box-sizing: border-box; 
        background-color: #fff;
        color: #333;
        font-size: 14px;
        display: flex;
        align-items: center;
    }
    .name-input { width: 160px; }
    .date-input { width: 120px; justify-content: center;}
    
    /* 削除ボタン・テキスト */
    .action-area { width: 180px; text-align: left; font-size: 14px;}
    .delete-link { color: #5196d4; text-decoration: underline; cursor: pointer; }
    .restore-text { color: #666; display: none; }
    .restore-link { color: #5196d4; text-decoration: underline; cursor: pointer; margin-left: 5px; }
    
    /* 取り消し線のスタイル */
    .deleted-row { position: relative; }
    .deleted-row::after {
        content: '';
        position: absolute;
        top: 50%;
        left: -10px;
        right: -10px;
        height: 1.5px;
        background-color: #333;
    }
    
    .empty-msg { text-align: center; color: #666; margin-bottom: 20px; }

    /* 保存・キャンセルボタン */
    .btn-group { display: flex; justify-content: center; gap: 50px; margin-top: 50px;}
    .btn { padding: 10px 0; width: 140px; text-align: center; cursor: pointer; border: 1px solid #999; background-color: #fff; color: #5196d4; text-decoration: none; font-size: 14px; box-sizing: border-box;}
    .btn-save { background-color: #4CAF50; color: white; border: none; }
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
    <h3>資格一覧</h3>

    <c:choose>
        <c:when test="${empty qualificationList}">
            <!-- 資格が0件の場合の表示 -->
            <div class="empty-msg" style="margin-top: 60px;">登録されている資格はありません。</div>
            <div style="text-align: center; margin-top: 40px;">
                <a href="${pageContext.request.contextPath}/qualificationServlet/QualificationRegister.action" style="padding: 10px 40px; background-color: #4CAF50; color: white; text-decoration: none; border: none; font-size: 14px;">新規資格登録へ</a>
            </div>
        </c:when>
        
        <c:otherwise>
            <!-- 資格がある場合の表示（フォームと削除ロジック） -->
            <form action="${pageContext.request.contextPath}/qualificationServlet/QualificationDeleteExecute.action" method="post">
                <div class="list-wrapper">
                    <c:forEach var="q" items="${qualificationList}">
                        <div class="list-item" id="row-${q.qualification_id}">
                            <div class="data-wrapper" style="display: flex; gap: 10px;">
                                <div class="item-input name-input">${q.qualification_name}</div>
                                <div class="item-input date-input">${q.qualification_date}</div>
                            </div>
                            <div class="action-area">
                                <a class="delete-link" id="del-btn-${q.qualification_id}" onclick="markAsDeleted(${q.qualification_id})">削除</a>
                                <span class="restore-text" id="restore-txt-${q.qualification_id}">
                                    削除しました。<a class="restore-link" onclick="restoreRow(${q.qualification_id})">元に戻す</a>
                                </span>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- 新規追加へのリンク（リストのすぐ下） -->
                <div style="text-align: right; width: 100%; max-width: 480px; margin: 15px auto 0;">
                    <a href="${pageContext.request.contextPath}/qualificationServlet/QualificationRegister.action" style="color: #5196d4; text-decoration: underline; font-size: 14px;">+ 新規資格を追加</a>
                </div>

                <div id="deleted-ids-container"></div>

                <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/qualificationServlet/Qualification.action" class="btn">キャンセル</a>
                    <button type="submit" class="btn btn-save">保存</button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<script>
    function markAsDeleted(id) {
        const row = document.getElementById('row-' + id);
        const dataWrapper = row.querySelector('.data-wrapper');
        const delBtn = document.getElementById('del-btn-' + id);
        const restoreTxt = document.getElementById('restore-txt-' + id);
        const hiddenContainer = document.getElementById('deleted-ids-container');

        dataWrapper.classList.add('deleted-row');
        delBtn.style.display = 'none';
        restoreTxt.style.display = 'inline';

        if (!document.getElementById('hidden-del-' + id)) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'delete_ids';
            input.value = id;
            input.id = 'hidden-del-' + id;
            hiddenContainer.appendChild(input);
        }
    }

    function restoreRow(id) {
        const row = document.getElementById('row-' + id);
        const dataWrapper = row.querySelector('.data-wrapper');
        const delBtn = document.getElementById('del-btn-' + id);
        const restoreTxt = document.getElementById('restore-txt-' + id);
        const hiddenInput = document.getElementById('hidden-del-' + id);

        dataWrapper.classList.remove('deleted-row');
        restoreTxt.style.display = 'none';
        delBtn.style.display = 'inline';

        if (hiddenInput) {
            hiddenInput.remove();
        }
    }
</script>
</body>
</html>