<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../head.jsp" %>
<%@ include file="../../jobSeekerHeader.jsp" %>
 
<div class="main-layout">
<%@ include file="../../jobSeekerSidebar.jsp" %>
 
<div class="main-content">
<style>
.kigyou {
    display: flex;
    align-items: center;
    position: relative;
}

.kigyou h1 {
    margin: 0 auto;
}

.kigyou a {
    border: 2px solid #91B879;
    border-radius: 10px;
    padding: 5px 12px;
    text-decoration: none;
    color: #333;
    position: relative;
    left: 20px;  
}

.box {
    width: 200px;
    height: 40px;
    border: 1px solid black;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding-left: 10px;
}

.box-container {
    display: flex;
    flex-direction: row;
}

.main-layout {
    display: flex;
    align-items: flex-start;
}

.main-layout {
    display: flex;
    align-items: stretch;
    min-height: calc(100vh - 60px);
}

.application-row {
    display: flex;
    flex-direction: row;
}

.application-row .box {
    box-sizing: border-box;
}

.application-count {
    text-align: left;
    font-size: 16px;
    margin-top: 15px;
    margin-bottom: 15px;
}

.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 15px;
    margin-top: 30px;
}

.pagination a {
    text-decoration: none;
    color: #333;
    border: 1px solid #91B879;
    padding: 6px 12px;
    border-radius: 5px;
}
</style>
<div class="kigyou">
    <a href="${pageContext.request.contextPath}/Notification/jsp/tuuti.jsp">🔔通知</a>
    <h1>企業登録申請一覧</h1>
</div>
<p class="application-count">
    現在の登録申請件数：3件
</p>
  
  <div class="box-container">
    <div class="box">No.</div>
    <div class="box">企業名</div>
    <div class="box">申請日</div>
    <div class="box">状態</div>
    <div class="box">詳細</div>
  </div>

<div class="application-list">

    <div class="application-row">
        <div class="box">1</div>
        <div class="box">株式会社A</div>
        <div class="box">2026/11/18</div>
        <div class="box">審査中</div>
        <div class="box"><a href="companyapply.jsp">詳細</a></div>
    </div>

    <div class="application-row">
        <div class="box">2</div>
        <div class="box">株式会社B</div>
        <div class="box">2026/11/17</div>
        <div class="box">承認済み</div>
        <div class="box"><a href="companyapply.jsp">詳細</a></div>
    </div>

    <div class="application-row">
        <div class="box">3</div>
        <div class="box">株式会社C</div>
        <div class="box">2026/11/16</div>
        <div class="box">審査中</div>
        <div class="box"><a href="companyapply.jsp">詳細</a></div>
    </div>

<div class="pagination">
    <a href="#">＜</a>
    <a href="#">1</a>
    <a href="#">2</a>
    <a href="#">3</a>
    <a href="#">4</a>
    <a href="#">＞</a>
</div>
</div>
</div>
</div>
