<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者トップページ - HITOVA</title>
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
            font-size: 18px;
            font-weight: bold;
            color: #555;
            margin-bottom: 40px;
        }
        .admin-menu-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            max-width: 850px;
            margin: 0 auto;
        }
        .admin-menu-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #333;
            border-radius: 8px;
            background-color: #ffffff;
            color: #333;
            text-decoration: none;
            font-size: 14px;
            height: 60px;
            box-sizing: border-box;
            cursor: pointer;
            transition: background-color 0.15s;
        }
        .admin-menu-btn:hover {
            background-color: #f5f5f5;
        }
        /* 一部グリッドの配置調整（ワイヤーフレームの並びに近づけるため） */
        .offset-1 {
            grid-column: 1 / 2;
        }
        .offset-2 {
            grid-column: 2 / 3;
        }
        .offset-3 {
            grid-column: 3 / 4;
        }
        .offset-4 {
            grid-column: 4 / 5;
        }
    </style>
</head>
<body>

    <!-- 管理者用ヘッダー -->
    <%@ include file="/adminHeader.jsp" %>

    <div class="layout-wrapper">
        <!-- 管理者用サイドバー -->
        <%@ include file="/adminSidebar.jsp" %>

        <main class="main-content-area">
            <div class="admin-top-title">管理者トップページ</div>

            <div class="admin-menu-grid">
                <!-- 1段目 -->
                <a href="${pageContext.request.contextPath}/admin/userList.jsp" class="admin-menu-btn offset-1" name="userList">ユーザー一覧</a>
                <a href="${pageContext.request.contextPath}/admin/userReviews.jsp" class="admin-menu-btn offset-2" name="userLeviews">ユーザー口コミ</a>
                <a href="${pageContext.request.contextPath}/admin/userAuthority.jsp" class="admin-menu-btn offset-3" name="userAuthority">ユーザー権限変更</a>
                <a href="${pageContext.request.contextPath}/admin/noticeSending.jsp" class="admin-menu-btn offset-4" name="noticeSending">通知送信</a>

                <!-- 2段目 -->
                <a href="${pageContext.request.contextPath}/admin/identityVerification.jsp" class="admin-menu-btn offset-1" name="dentityVerificatio">本人確認審査</a>
                <a href="${pageContext.request.contextPath}/admin/companyManagement.jsp" class="admin-menu-btn offset-2" name="companyManagement">企業認証</a>
                <a href="${pageContext.request.contextPath}/admin/taskRegister.jsp" class="admin-menu-btn offset-3" name="taskRegister">課題登録</a>
                <!-- 空白(4列目) -->
                <div></div>

                <!-- 3段目 -->
                <a href="${pageContext.request.contextPath}/admin/taskEdit.jsp" class="admin-menu-btn offset-1" name="taskEdit">課題編集</a>
                <a href="${pageContext.request.contextPath}/admin/taskSubmissionList.jsp" class="admin-menu-btn offset-2" name="taskSubmissionList">提出物表示</a>
                <a href="${pageContext.request.contextPath}/admin/judgement.jsp" class="admin-menu-btn offset-3" name="judgement">合否登録</a>
                <!-- 空白(4列目) -->
                <div></div>

                <!-- 4段目 -->
                <a href="${pageContext.request.contextPath}/admin/reportList.jsp" class="admin-menu-btn offset-1" name="reportList">通報一覧表示</a>
                <a href="${pageContext.request.contextPath}/admin/noticeList.jsp" class="admin-menu-btn offset-2" name="noticeList">通知一覧表示</a>
                <a href="${pageContext.request.contextPath}/admin/resultRegister.jsp" class="admin-menu-btn offset-3" name="resultRegister">実績登録</a>
            </div>
        </main>
    </div>

</body>
</html>