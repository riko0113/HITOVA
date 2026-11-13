<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>求人検索 - HITOVA</title>
    <style>
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 60px);
            min-width: 900px;
        }
        .main-content-area {
            flex: 1;
            min-width: 0;
            padding: 30px;
            background-color: #f5f5f5;
            box-sizing: border-box;
        }
        .search-container {
            max-width: 700px;
            margin: 0 auto;
            background-color: #fff;
            padding: 30px 40px;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            box-sizing: border-box;
        }
        .search-header {
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 30px;
            padding-bottom: 10px;
            border-bottom: 2px solid #333;
        }
        .search-bar-row {
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 35px;
        }
        .search-input {
            width: 100%;
            max-width: 450px;
            height: 40px;
            font-size: 15px;
            padding: 0 10px;
            border: 1px solid #002B49;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .search-btn {
            height: 40px;
            padding: 0 20px;
            margin-left: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 15px;
            white-space: nowrap;
        }
        .search-btn:hover {
            background-color: #43a047;
        }
        .filter-group {
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .filter-label {
            width: 120px;
            font-size: 14px;
            font-weight: bold;
        }
        .filter-select {
            width: 280px;
            height: 35px;
            padding: 5px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
    </style>
</head>
<body>

    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main class="main-content-area">
            <div class="search-container">
                <div class="search-header">求人検索</div>
                
                <form action="${pageContext.request.contextPath}/jobsearchServlet/JobSearchExecute.action" method="post">
                    <div class="search-bar-row">
                        <input type="text" name="keyword" class="search-input" placeholder="職種やキーワードを入力">
                        <button type="submit" class="search-btn">検索</button>
                    </div>

                    <div class="filter-group">
                        <label class="filter-label">職種</label>
                        <select name="occupation" class="filter-select">
                            <option value="">選択してください</option>
                        </select>
                    </div>
                    
                    <div class="filter-group">
                        <label class="filter-label">給与</label>
                        <select name="salary" class="filter-select">
                            <option value="">選択してください</option>
                        </select>
                    </div>
                    
                    <div class="filter-group">
                        <label class="filter-label">雇用形態</label>
                        <select name="employmentType" class="filter-select">
                            <option value="">選択してください</option>
                        </select>
                    </div>
                    
                    <div class="filter-group">
                        <label class="filter-label">勤務地</label>
                        <select name="location" class="filter-select">
                            <option value="">選択してください</option>
                        </select>
                    </div>
                    
                    <div class="filter-group">
                        <label class="filter-label">福利厚生など</label>
                        <select name="benefits" class="filter-select">
                            <option value="">選択してください</option>
                        </select>
                    </div>
                </form>
            </div>
        </main>
    </div>

</body>
</html>