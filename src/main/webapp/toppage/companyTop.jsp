<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>企業トップページ - HITOVA</title>
</head>
<body style="margin: 0; font-family: 'Helvetica Neue', Arial, 'Hiragino Kaku Gothic ProN', 'Hiragino Sans', Meiryo, sans-serif;">

    <%@ include file="/companyHeader.jsp" %>

    <div style="display: flex; min-height: calc(100vh - 64px);">
        <%@ include file="/companySidebar.jsp" %>

        <main style="flex: 1; padding: 24px 30px;">
            <div style="height: 80px; border: 1px solid #ccc; display: flex; align-items: center; justify-content: center; margin-bottom: 24px;">
                画像
            </div>

            <div style="display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 32px;">
                <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action?job_id=${3001}" style="border: 1px solid #ccc; padding: 14px; text-align: center; text-decoration: none; color: #222;">
                    <div style="font-size: 13px; margin-bottom: 6px;">応募者数</div>
                    <div style="font-size: 22px; font-weight: bold;">3名</div>
                </a>
                <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action?job_id=${3001}&status=1" style="border: 1px solid #ccc; padding: 14px; text-align: center; text-decoration: none; color: #222;">
                    <div style="font-size: 13px; margin-bottom: 6px;">書類選考数</div>
                    <div style="font-size: 22px; font-weight: bold;">1名</div>
                </a>
                <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action?job_id=${3001}&status=2" style="border: 1px solid #ccc; padding: 14px; text-align: center; text-decoration: none; color: #222;">
                    <div style="font-size: 13px; margin-bottom: 6px;">面接者数</div>
                    <div style="font-size: 22px; font-weight: bold;">1名</div>
                </a>
                <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action?job_id=${3001}&status=3" style="border: 1px solid #ccc; padding: 14px; text-align: center; text-decoration: none; color: #222;">
                    <div style="font-size: 13px; margin-bottom: 6px;">内定者数</div>
                    <div style="font-size: 22px; font-weight: bold;">1名</div>
                </a>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 24px;">
                <div style="border: 1px solid #ccc; padding: 18px;">
                    <div style="font-weight: bold; border-bottom: 1px solid #333; padding-bottom: 6px; margin-bottom: 10px;">通知履歴</div>
                    <table style="width: 100%; border-collapse: collapse; font-size: 13px;">
                        <tr>
                            <th style="text-align: left; font-weight: normal; padding: 8px 0; border-bottom: 1px dashed #ccc; width: 130px;">2026/11/02 10:04</th>
                            <td style="padding: 8px 0; border-bottom: 1px dashed #ccc;">新しい応募が届いています。（佐藤 空 さん）</td>
                        </tr>
                        <tr>
                            <th style="text-align: left; font-weight: normal; padding: 8px 0; border-bottom: 1px dashed #ccc;">2026/11/01 15:30</th>
                            <td style="padding: 8px 0; border-bottom: 1px dashed #ccc;">求人掲載の審査が完了しました。</td>
                        </tr>
                        <tr>
                            <th style="text-align: left; font-weight: normal; padding: 8px 0;">2026/10/28 09:00</th>
                            <td style="padding: 8px 0;">システムメンテナンスのお知らせ</td>
                        </tr>
                    </table>
                </div>

                <div style="display: flex; flex-direction: column; gap: 20px;">
                    <form action="${pageContext.request.contextPath}/company/scoutSearch.action" method="post" style="display: flex; gap: 8px;">
                        <input type="text" name="searchBar" placeholder="スキルやタグで求職者を検索" style="flex: 1; height: 32px; padding: 0 8px;">
                        <button type="submit" name="search" style="height: 36px; padding: 0 18px; cursor: pointer;">検索</button>
                    </form>

                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px;">
                        <a href="${pageContext.request.contextPath}/job/JobList.action" style="border: 1px solid #888; height: 70px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-weight: bold; font-size: 14px;">
                            掲載中求人表示
                        </a>
                        <a href="${pageContext.request.contextPath}/contact/contact.jsp" style="border: 1px solid #888; height: 70px; display: flex; align-items: center; justify-content: center; text-decoration: none; color: #222; font-weight: bold; font-size: 14px;">
                            お問い合わせ
                        </a>
                    </div>
                </div>
            </div>
        </main>
    </div>

</body>
</html>