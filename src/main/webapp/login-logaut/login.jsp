<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../head.jsp" %>
<style>
    
    body{
        background-color: rgb(241, 253, 239);
    }
    header img{
        width: 180px;
        height: auto;
    }
    main{
        zoom: 80%;
        background-color: white;
        margin: auto;
        
        width: 580px;
        border: 2px solid black;
        
    }
    main label {
        display: block;
        width: auto;
        margin: 24px 40px;
        font-size: 16px;
    }

    main input:not([type="checkbox"]) {
        display: block;
        width: 300px;
        margin: 8px auto 0;
        padding: 8px;
    }
    main input[type="checkbox"]{
        margin-left: ;
        padding: 8px;    
    }
    .button-container {
        display: flex;
    }

    .button-container button {
        flex: 1;
        padding: 10px 20px;
        font-size: 16px;
        
        border: none;
        border-bottom: 1px solid black;
    }

    .button-container button:nth-child(2) {
        border-left: 1px solid black;
    }
    .terms{
        font-size: 13px;
    }
    button{
        background-color: white;
    }
    
    .endbu{
        text-align: center;
        
    }
    .endbu button{
        width: 130px;
        height: 50px;
        margin-bottom: 20px;
    }
    .new{
        
    }

</style>
<header>
    <h1><a href="#"><img src="img/image.png" alt="HITOVA"></a></h1>
</header>
<main>
    <div class="button-container">
        <button>ログイン</button>
        <button>新規登録</button>
    </div>
    <div class="">
        <label for="email">メールアドレス<br><input type="email" id="email" name="mail" ></label>
        <label for="password">パスワード（8字以上20字以下）<br><input type="password" id="password" name="password" ></label>
    </div>
    <div class="new">
        <label for="">確認用パスワード（パスワードの再入力）<br><input type="password"></label>
        <label for="">ニックネーム(英数字を組み合わせた4字以上20字以下)<br><input type="text"></label>
        <label class="terms" for=""><input type="checkbox">利用規約に同意する（<a href="#">利用規約全文はこちら</a>）</label>
        <label class="terms" for=""><input type="checkbox">プライバシーポリシーに同意する（<a href="#">プライバシーポリシー全文はこちら</a>）</label>
    </div>
    <div class="endbu">
        <button>確認用メール送信</button>
    </div>
</main>





