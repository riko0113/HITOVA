<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/head.jsp" %>
<%@ include file="/jobSeekerHeader.jsp" %>
<style>
.main {
text-align: center;
font-size: 20px;
}

.a {
text-align: center;
font-size: 20px;
}

.ai {
text-align: center;
font-size: 20px;
}
.aiu {
text-align: center;
font-size: 20px;
}
.aiue {
text-align: center;
font-size: 20px;
}

.kozin {
margin: 20px;
margin-left: 80px;
font-size: 20px;
}

.image-upload {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 150px;
    border: 2px dashed gray;
    border-radius: 5px;
    cursor: pointer;
    box-sizing: border-box;
}
.submit-area button {
    display: flex;
    flex-direction: row;
    padding: 12px 40px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
    border: none;
    border-radius: 5px;
    background-color: skyblue;
    color: white;
    gap: 20px;
}

.submit-area {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    width: 100%;
    margin-top: 30px;
    margin-bottom: 20px;
    gap: 50px;
    justify-content: center;
}
.imagen {
    text-align: center;
    width: 100%;
    margin: 20px auto;
}

.imagen img {
    width: 800px;
    max-width: 100%;
    height: auto;
}
</style>
<div class="main-layout">

    <%@ include file="/jobSeekerSidebar.jsp" %>

    <div class="main-content">
		<div class="main">本人確認証明申請ページ</div>
			
			<div class="kozin">氏名：</div>
			
			<div class="kozin">生年月日：</div>
			
			<div class="kozin">現住所：</div>
		
		<div class="a">マイナンバーカードか運転免許所の写真をアップロードしてください。　(合計20MB 以内)</div><br>	
	    
	    <div class="ai">マイナンバーカードを選んだ方：表面の写真と斜めからの写真の2点をアップロードしてください
	                    <br>⚠裏面の写真はアップロードしないでください</div><br>
	<div class="aiu">運転免許証を選んだ方：表面の写真と斜めからの写真の２点をアップロードしてください</div><br>
	<div class="aiue">住所の変更の記載がある場合は表面の写真も併せた3点をアップロードしてください</div>

<div class="imagen">
    <img src="${pageContext.request.contextPath}/images/mainanba.png" alt="写真の取り方">
</div>

<div class="form9">
    <label class="image-upload">
        ファイルアップロード場所<input type="file" accept="image/*"></label>
        
        <div class="submit-area">
    <button type="submit">キャンセル</button>
    <button type="submit">確認</button>
</div>
</div>
</div>	