<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>求人検索 - HITOVA</title>
    <style>
        .search-container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }
        .search-header {
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 30px;
            padding-bottom: 10px;
            border-bottom: 1px solid #000;
        }
        .search-bar-row {
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 40px;
        }
        .search-input {
            width: 500px;
            height: 40px;
            font-size: 16px;
            padding: 0 10px;
            border: 1px solid #002B49;
            border-radius: 5px;
        }
        .search-btn {
            height: 44px;
            padding: 0 25px;
            margin-left: 15px;
            background-color: white;
            border: 1px solid #002B49;
            border-radius: 5px;
            cursor: pointer;
        }
        .filter-group {
            margin-bottom: 15px;
            display: flex;
            align-items: center;
        }
        .filter-label {
            width: 150px;
            font-size: 16px;
        }
        .filter-select {
            width: 250px;
            height: 30px;
        }
    </style>
</head>
<body>
    <div class="search-container">
        <div class="search-header">求人検索</div>
        
        <form action="../jobsearchServlet/JobSearchExecute.action" method="post">
            <!-- 検索バーとボタン -->
            <div class="search-bar-row">
                <!-- 虫眼鏡アイコン（絵文字で代用、必要に応じて画像タグに変更してください） -->
                <span style="font-size: 30px; margin-right: 15px; transform: rotate(-45deg);">🔍</span>
                <input type="text" name="keyword" class="search-input" placeholder="検索">
                <button type="submit" class="search-btn">検索</button>
            </div>

            <!-- ドロップダウン絞り込み項目 -->
            <div class="filter-group">
                <label class="filter-label">職種</label>
                <select name="occupation" class="filter-select">
                    <option value="">選択してください</option>
                    <!-- 以降、マスタテーブルから取得した職種をループで追加 -->
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
</body>
</html>