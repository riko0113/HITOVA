<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    .company-sidebar {
        width: 220px;
        min-width: 220px;
        background-color: #ffffff;
        border-right: 1px solid #002B49;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
    }
    .company-sidebar-menu {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .company-sidebar-item {
        border-bottom: 1px solid #e2e8f0;
    }
    .company-sidebar-link {
        display: block;
        padding: 16px 20px;
        color: #002B49;
        text-decoration: none;
        font-size: 14px;
        font-weight: bold;
        transition: background-color 0.15s, color 0.15s;
    }
    .company-sidebar-link:hover {
        background-color: #f0f4f8;
        color: #00182b;
    }
</style>

<aside class="company-sidebar">
    <ul class="company-sidebar-menu">
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/company/companyTop.jsp" class="company-sidebar-link" name="topPage">
                トップページ
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/company/applicantList.jsp" class="company-sidebar-link" name="applicants">
                応募者一覧
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/company/chatList.jsp" class="company-sidebar-link" name="chat">
                チャット
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/company/contact.jsp" class="company-sidebar-link" name="contact">
                お問い合わせ
            </a>
        </li>
        <li class="company-sidebar-item">
            <a href="${pageContext.request.contextPath}/company/withdraw.jsp" class="company-sidebar-link" name="delete">
                退会
            </a>
        </li>
    </ul>
</aside>