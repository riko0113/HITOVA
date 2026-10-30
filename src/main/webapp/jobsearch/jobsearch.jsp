<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>求人検索-HITOVA</title>
</head>

<body>
<h1>求人検索</h1>
<div class="search-area">
			<form action="${pageContext.request.contextPath}/JobSearch.action" method="get">        <div class="search-box">
            <input type="text" name="keyword" placeholder="検索">
            <button type="submit">検索</button>
        </div>
        <div class="filters">
            <select name="jobType">
                <option value="">職種</option>
            </select>
            <select name="salary">
                <option value="">給与</option>
            </select>
            <select name="employmentType">
                <option value="">雇用形態</option>
            </select>
            <select name="location">
                <option value="">勤務地</option>
            </select>
            <select name="benefits">
                <option value="">福利厚生など</option>
            </select>
        </div>
    </form>
</div>
</body>
</html>
    