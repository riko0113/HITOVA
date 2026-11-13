<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>申請完了 - HITOVA</title>
</head>
<body>
    <%@ include file="/jobSeekerHeader.jsp" %>

    <div class="layout-wrapper" style="display: flex; min-height: calc(100vh - 60px);">
        <%@ include file="/jobSeekerSidebar.jsp" %>

        <main style="flex: 1; text-align: center; padding-top: 100px; background-color: #f5f5f5;">
            <h3 style="font-size: 22px;">課題の受付・登録が完了しました</h3>
            <p style="color: #666; margin-top: 15px;">
                対象課題: <strong><c:out value="${challengeName}" /></strong>
            </p>
            <p style="font-size: 13px; color: #888;">
                ※本審査・自動採点機能および決済機能は二次開発フェーズでの提供予定です。
            </p>

            <div style="margin-top: 40px;">
                <a href="${pageContext.request.contextPath}/achievement/challengeList.jsp" 
                   style="padding: 10px 30px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 4px; font-weight: bold;">
                    一覧へ戻る
                </a>
            </div>
        </main>
    </div>
</body>
</html>