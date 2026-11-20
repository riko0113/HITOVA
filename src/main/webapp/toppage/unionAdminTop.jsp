<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>組合管理者トップページ - HITOVA</title>
</head>
<body style="margin: 0; font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;">

    <%@ include file="/unionAdminHeader.jsp" %>

    <div style="display: flex; min-height: calc(100vh - 64px);">
        <%@ include file="/unionAdminSidebar.jsp" %>

        <main style="flex: 1; padding: 40px 30px;">
            <h2 style="text-align: center; margin-bottom: 36px;">組合管理者 トップページ</h2>

            <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 24px 20px; max-width: 750px; margin: 0 auto;">
                <a href="${pageContext.request.contextPath}/unionAdminServlet/CompanyList.action" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">企業一覧</a>
                <a href="${pageContext.request.contextPath}/unionAdmin/companyApproval.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">企業登録・承認</a>
                <a href="${pageContext.request.contextPath}/unionAdmin/performanceList.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">実績一覧</a>

                <a href="${pageContext.request.contextPath}/unionAdmin/unionAdminCreate.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">ユーザー追加</a>
                <a href="${pageContext.request.contextPath}/unionAdmin/companyNotificationSend.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">企業通知送信</a>
                <a href="${pageContext.request.contextPath}/unionAdmin/submissionDisplay.jsp" style="border: 1px solid #888; height: 56px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-size: 14px;">提出物表示</a>
            </div>
        </main>
    </div>

</body>
</html>