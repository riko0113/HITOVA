<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者トップページ - HITOVA</title>
</head>
<body style="margin: 0; font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;">

    <%@ include file="/adminHeader.jsp" %>

    <div style="display: flex; min-height: calc(100vh - 64px);">
        <%@ include file="/adminSidebar.jsp" %>

        <main style="flex: 1; padding: 40px 30px;">
            <h2 style="text-align: center; margin-bottom: 36px;">管理者トップページ</h2>

            <div style="display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; max-width: 850px; margin: 0 auto;">
                <a href="${pageContext.request.contextPath}/admin/userList.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">ユーザー一覧</a>
                <a href="${pageContext.request.contextPath}/admin/userReviews.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">ユーザー口コミ</a>
                <a href="${pageContext.request.contextPath}/admin/userAuthority.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">ユーザー権限変更</a>
                <a href="${pageContext.request.contextPath}/Notification/NotificationDisplay.action" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">通知送信</a>

                <a href="${pageContext.request.contextPath}/admin/identityVerification.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">本人確認審査</a>
                <a href="${pageContext.request.contextPath}/companyApproval/CompanyApprovalList.action" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">企業認証</a>
                <a href="${pageContext.request.contextPath}/admin/taskRegister.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">課題登録</a>
                <div></div>

                <a href="${pageContext.request.contextPath}/admin/taskEdit.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">課題編集</a>
                <a href="${pageContext.request.contextPath}/admin/taskSubmissionList.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">提出物表示</a>
                <a href="${pageContext.request.contextPath}/admin/judgement.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">合否登録</a>
                <div></div>

                <a href="${pageContext.request.contextPath}/admin/reportList.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">通報一覧表示</a>
                <a href="${pageContext.request.contextPath}/admin/noticeList.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">通知一覧表示</a>
                <a href="${pageContext.request.contextPath}/admin/resultRegister.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">実績登録</a>
            </div>
        </main>
    </div>

</body>
</html>