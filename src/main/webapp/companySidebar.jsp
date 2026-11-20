<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .company-sidebar {
        width: 220px;
        min-width: 220px;
        background-color: #ffffff;
        border-right: 1px solid #cbd5e1;
        box-sizing: border-box;
        flex-shrink: 0;
    }
    .company-sidebar-list {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .company-sidebar-item {
        border-bottom: 1px solid #e2e8f0;
    }
    .company-sidebar-link {
        display: block;
        padding: 16px 24px;
        color: #002B49;
        text-decoration: none;
        font-size: 14px;
        font-weight: bold;
        transition: background-color 0.15s;
    }
    .company-sidebar-link:hover {
        background-color: #f1f5f9;
    }
</style>

<aside class="company-sidebar">
    <ul class="company-sidebar-list">
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/company/companyTop.jsp" class="company-sidebar-link">トップページ</a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/applicant/ApplicantList.action" class="company-sidebar-link" name="applicants">
                応募者一覧
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/company/applicantList.jsp" class="company-sidebar-link">応募者一覧</a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/chat/chatList.jsp" class="company-sidebar-link">チャット</a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/contact/contact.jsp" class="company-sidebar-link">お問い合わせ</a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/company/withdraw.jsp" class="company-sidebar-link">退会</a>
        </li>
    </ul>
</aside>