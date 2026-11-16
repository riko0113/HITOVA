<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>アカウント情報表示 - HITOVA</title>
    <style>
        body { margin: 0; padding: 0; font-family: sans-serif; background-color: #f8f9fa; color: #333; }
        .layout-wrapper { display: flex; min-height: calc(100vh - 60px); min-width: 1000px; }
        .main-content-area { flex: 1; padding: 40px 60px; background-color: #ffffff; box-sizing: border-box; }
        .detail-container { max-width: 820px; }
        .account-header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .section-title-account { font-size: 18px; font-weight: normal; margin: 0; }
        .action-btn-group { display: flex; gap: 10px; }
        .btn-outline { padding: 6px 14px; background-color: #ffffff; border: 1px solid #333; color: #333; text-decoration: none; font-size: 13px; cursor: pointer; }
        .btn-outline:hover { background-color: #f0f4f8; }
        .section-block { margin-bottom: 35px; }
        .section-title-box { font-size: 16px; font-weight: bold; margin-bottom: 15px; }
        .info-table { width: 100%; border-collapse: collapse; font-size: 14px; }
        .info-table th { width: 180px; text-align: left; padding: 8px 10px 8px 18px; font-weight: normal; vertical-align: top; }
        .info-table td { padding: 8px 10px; vertical-align: top; line-height: 1.6; }
        .account-table th { padding-left: 0; width: 150px; }
        .colon { margin-right: 15px; }
    </style>
</head>
<body>

    <!-- 企業用ヘッダー -->
    <%@ include file="/companyHeader.jsp" %>

    <div class="layout-wrapper">
        <!-- 企業用サイドバー -->
        <%@ include file="/companySidebar.jsp" %>

        <main class="main-content-area">
            <div class="detail-container">

                <div class="section-block">
                    <div class="account-header-row">
                        <h2 class="section-title-account">アカウント情報</h2>
                        <div class="action-btn-group">
                            <a href="${pageContext.request.contextPath}/company/emailChange.jsp" class="btn-outline">メールアドレス変更</a>
                            <a href="${pageContext.request.contextPath}/company/passwordChange.jsp" class="btn-outline">パスワード変更</a>
                            <a href="${pageContext.request.contextPath}/companyServlet/AccountEdit.action" class="btn-outline">編集</a>
                        </div>
                    </div>

                    <table class="info-table account-table">
                        <tr><th>メールアドレス</th><td><span class="colon">：</span>${ca.email}</td></tr>
                        <tr><th>担当者氏名</th><td><span class="colon">：</span>${ca.person_name}</td></tr>
                        <tr><th>部署名</th><td><span class="colon">：</span>${ca.department}</td></tr>
                        <tr><th>役職</th><td><span class="colon">：</span>${ca.position}</td></tr>
                    </table>
                </div>

                <div class="section-block">
                    <div class="section-title-box">■ 企業基本情報</div>
                    <table class="info-table">
                        <c:choose>
                            <c:when test="${ci != null}">
                                <tr><th>会社名</th><td>${ci.official_company_name}</td></tr>
                                <tr><th>法人番号</th><td>${ci.corporate_number}</td></tr>
                                <tr><th>電話番号</th><td>${ci.main_phone_number}</td></tr>
                                <tr><th>URL</th><td><a href="${ci.company_website_url}" target="_blank" style="color: #0066cc;">${ci.company_website_url}</a></td></tr>
                            </c:when>
                            <c:when test="${spi != null}">
                                <tr><th>屋号</th><td>${spi.trade_name}</td></tr>
                                <tr><th>代表者名</th><td>${spi.company_representative_name}</td></tr>
                                <tr><th>所在地</th><td>${spi.business_office_address}</td></tr>
                                <tr><th>電話番号</th><td>${spi.business_office_phone_number}</td></tr>
                                <tr><th>店舗確認URL</th><td><a href="${spi.store_verification_url}" target="_blank" style="color: #0066cc;">${spi.store_verification_url}</a></td></tr>
                            </c:when>
                        </c:choose>
                    </table>
                </div>

                <div class="section-block">
                    <div class="section-title-box">■ 事業内容</div>
                    <table class="info-table">
                        <tr><th>業種</th><td>${ca.industry_name}</td></tr>
                        <tr><th>従業員数</th><td>${ca.number_of_employee}名</td></tr>
                        <tr><th>設立年</th><td>${ca.established_year}年</td></tr>
                        <tr><th>事業内容</th><td>${ca.business_description}</td></tr>
                    </table>
                </div>

            </div>
        </main>
    </div>

</body>
</html>