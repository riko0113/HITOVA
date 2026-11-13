<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>組合管理者トップページ - HITOVA</title>
    <style>
        .layout-wrapper {
            display: flex;
            min-height: calc(100vh - 60px);
            min-width: 900px;
        }
        .main-content-area {
            flex: 1;
            padding: 40px;
            background-color: #ffffff;
            box-sizing: border-box;
        }
        .admin-top-title {
            text-align: center;
            font-size: 20px;
            font-weight: bold;
            color: #333;
            margin-bottom: 50px;
        }
        .admin-menu-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 40px 30px;
            max-width: 750px;
            margin: 0 auto;
        }
        .admin-menu-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #002B49;
            border-radius: 8px; /* 四角から角丸に変更 */
            background-color: #ffffff;
            color: #333;
            text-decoration: none;
            font-size: 15px;
            height: 70px;
            box-sizing: border-box;
            cursor: pointer;
            transition: background-color 0.15s;
        }
        .admin-menu-btn:hover {
            background-color: #f0f4f8;
        }
    </style>
</head>
<body>

    <!-- 組合管理者用ヘッダー -->
    <%@ include file="/unionAdminHeader.jsp" %>

    <div class="layout-wrapper">
        <!-- 組合管理者用サイドバー -->
        <%@ include file="/unionAdminSidebar.jsp" %>

        <main class="main-content-area">
            <div class="admin-top-title">組合管理者 トップページ</div>

            <div class="admin-menu-grid">
                <!-- 1段目 -->
                <a href="${pageContext.request.contextPath}/unionAdmin/companyList.jsp" class="admin-menu-btn" name="company-list">企業一覧</a>
                <a href="${pageContext.request.contextPath}/unionAdmin/companyApproval.jsp" class="admin-menu-btn" name="company-registration-approval">企業登録・承認</a>
                <a href="${pageContext.request.contextPath}/unionAdmin/performanceList.jsp" class="admin-menu-btn" name="performance-list">実績一覧</a>

                <!-- 2段目 -->
                <a href="${pageContext.request.contextPath}/unionAdmin/userAddition.jsp" class="admin-menu-btn" name="user-addition">ユーザー追加</a>
                <a href="${pageContext.request.contextPath}/unionAdmin/companyNotificationSend.jsp" class="admin-menu-btn" name="company-notification-send">企業通知送信</a>
                <a href="${pageContext.request.contextPath}/unionAdmin/submissionDisplay.jsp" class="admin-menu-btn" name="submission-display">提出物表示</a>
            </div>
        </main>
    </div>

</body>
</html>