<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<style>
.search-area {
    border: 1px solid #1a2b4c;
    padding: 30px;
    max-width: 800px;
    background-color: #fff;
}
.search-box-row {
    display: flex;
    align-items: center;
    margin-bottom: 30px;
}
.search-input {
    flex-grow: 1;
    padding: 10px;
    margin-right: 15px;
    border: 1px solid #1a2b4c;
    border-radius: 4px;
}
.search-btn {
    padding: 10px 25px;
    border: 1px solid #1a2b4c;
    background-color: #fff;
    border-radius: 4px;
    cursor: pointer;
}
.filter-item {
    margin-bottom: 15px;
    display: block;
    padding: 5px;
    border: none;
    background-color: transparent;
}
</style>
<h1>求人検索</h1>
<div class="search-area">
    <form action="${pageContext.request.contextPath}/JobSearch.action" method="get">
        <div class="search-box-row">
            <span style="font-size: 24px; margin-right: 15px;"></span>
            <input type="text" name="keyword" class="search-input" placeholder="検索">
            <button type="submit" class="search-btn">検索</button>
        </div>
        <div class="filters">
            <select name="jobType" class="filter-item"><option value="">職種</option></select>
            <select name="salary" class="filter-item"><option value="">給与</option></select>
            <select name="employmentType" class="filter-item">
    				<option value="">雇用形態</option>
    				<option value="1">正社員</option>
    				<option value="2">契約社員</option>
    				<option value="3">アルバイト・パート</option></select>
            <select name="location" class="filter-item"><option value="">勤務地</option></select>
            <select name="benefits" class="filter-item"><option value="">福利厚生など</option></select>
        </div>
    </form>
</div>