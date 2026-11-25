
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@include page="../../header.jsp" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/jobSeeker/jobSeekerDetail.css">

<div class="main-layout">
<%@include file="../../jobSeekerSidebar.jsp" %>
<div class="main-content">
<div class="detail-container">
    <h2>求職者詳細</h2>
    <a class="back-link" href="${pageContext.request.contextPath}/jobSeeker/JobSeekerSearch.action">← 検索結果に戻る</a>

    <c:if test="${not empty errorMessage}">
        <p class="error-message"><c:out value="${errorMessage}"/></p>
    </c:if>

    <c:if test="${not empty detail}">
        <div class="detail-layout">

            <!-- 求職者カード -->
            <aside class="candidate-card">
                <div class="profile-icon">👤</div>
                <h3><c:out value="${detail.basicInfo.nick_name}"/></h3>
                <p class="candidate-label">求職者</p>

                <c:choose>
                    <c:when test="${detail.basicInfo.identity_verification}">
                        <span class="verified">✓ 本人確認済み</span>
                    </c:when>
                    <c:otherwise>
                        <span class="unverified">本人未確認</span>
                    </c:otherwise>
                </c:choose>

                <a class="chat-button"
                   href="${pageContext.request.contextPath}/chat/ChatRoom.action?candidate_id=${detail.basicInfo.candidate_id}">
                    チャットする
                </a>
            </aside>

            <!-- 詳細情報 -->
            <main class="detail-content">

                <!-- 基本情報 -->
                <section class="detail-section">
                    <h3>基本情報</h3>
                    <div class="info-row">
                        <span>ニックネーム</span>
                        <strong><c:out value="${detail.basicInfo.nick_name}"/></strong>
                    </div>
                    <div class="info-row">
                        <span>本人確認</span>
                        <strong>
                            <c:choose>
                                <c:when test="${detail.basicInfo.identity_verification}">確認済み</c:when>
                                <c:otherwise>未確認</c:otherwise>
                            </c:choose>
                        </strong>
                    </div>
                </section>

                <!-- プロフィール情報 -->
                <section class="detail-section">
                    <h3>プロフィール情報</h3>
                    <div class="info-row"><span>氏名</span><strong><c:out value="${detail.profileInfo.last_name} ${detail.profileInfo.first_name}"/></strong></div>
                    <div class="info-row"><span>フリガナ</span><strong><c:out value="${detail.profileInfo.last_name_kana} ${detail.profileInfo.first_name_kana}"/></strong></div>
                    <div class="info-row"><span>生年月日</span><strong><c:out value="${detail.profileInfo.birth_day}"/></strong></div>
                    <div class="info-row">
                        <span>性別</span>
                        <strong><c:choose><c:when test="${detail.profileInfo.gender}">男性</c:when><c:otherwise>女性</c:otherwise></c:choose></strong>
                    </div>
                    <div class="info-row"><span>郵便番号</span><strong><c:out value="${detail.profileInfo.current_postal_code}"/></strong></div>
                    <div class="info-row"><span>住所</span><strong><c:out value="${detail.profileInfo.current_address}"/></strong></div>
                    <div class="info-row"><span>最終学歴</span><strong><c:out value="${detail.profileInfo.highest_education}"/></strong></div>
                    <div class="info-row"><span>職歴</span><strong><c:out value="${detail.profileInfo.work_history}"/></strong></div>
                </section>

                <!-- 希望条件 -->
                <section class="detail-section">
                    <h3>検索関連情報・希望条件</h3>
                    <div class="info-row"><span>希望雇用形態</span><strong><c:out value="${detail.searchInfo.employment_type_name}"/></strong></div>
                    <div class="info-row"><span>希望業種</span><strong><c:out value="${detail.searchInfo.industry_name}"/></strong></div>
                    <div class="info-row"><span>希望職種</span><strong><c:out value="${detail.searchInfo.profile_occupation_name}"/></strong></div>
                    <div class="info-row"><span>希望勤務地</span><strong><c:out value="${detail.searchInfo.desired_work_location}"/></strong></div>
                </section>

                <!-- スキル -->
                <section class="detail-section">
                    <h3>スキル情報</h3>
                    <c:forEach var="skill" items="${detail.skillList}">
                        <div class="item-block">
                            <strong><c:out value="${skill.skill_name}"/></strong>
                            <p><c:out value="${skill.skill_content}"/></p>
                        </div>
                    </c:forEach>
                    <c:if test="${empty detail.skillList}">
                        <p class="empty-message">登録されたスキルはありません。</p>
                    </c:if>
                </section>

                <!-- 資格 -->
                <section class="detail-section">
                    <h3>資格情報</h3>
                    <c:forEach var="qualification" items="${detail.qualificationList}">
                        <div class="item-block">
                            <strong><c:out value="${qualification.qualification_name}"/></strong>
                            <p>分野：<c:out value="${qualification.field_name}"/></p>
                            <p>取得日：<c:out value="${qualification.qualification_date}"/></p>
                        </div>
                    </c:forEach>
                    <c:if test="${empty detail.qualificationList}">
                        <p class="empty-message">登録された資格はありません。</p>
                    </c:if>
                </section>

                <!-- 実績 -->
                <section class="detail-section">
                    <h3>実績情報</h3>
                    <c:forEach var="achievement" items="${detail.achievementList}">
                        <div class="item-block">
                            <strong><c:out value="${achievement.achievement}"/></strong>
                            <p><c:out value="${achievement.achievement_content}"/></p>
                            <p>分野：<c:out value="${achievement.field_name}"/></p>
                            <p>カテゴリー：<c:out value="${achievement.category_name}"/></p>
                            <p>合否：<c:out value="${achievement.passorfail_name}"/></p>
                            <p>評価：<c:out value="${achievement.evaluation}"/></p>
                            <p>取得日：<c:out value="${achievement.achievement_date}"/></p>
                        </div>
                    </c:forEach>
                    <c:if test="${empty detail.achievementList}">
                        <p class="empty-message">登録された実績はありません。</p>
                    </c:if>
                </section>

                <!-- ポートフォリオ -->
                <section class="detail-section">
                    <h3>ポートフォリオ・作品</h3>
                    <c:forEach var="portfolio" items="${detail.portfolioList}">
                        <div class="item-block">
                            <strong><c:out value="${portfolio.work_name}"/></strong>
                            <p><c:out value="${portfolio.description}"/></p>
                            <a class="work-link" href="${portfolio.file_url}" target="_blank" rel="noopener noreferrer">作品を見る →</a>
                        </div>
                    </c:forEach>
                    <c:if test="${empty detail.portfolioList}">
                        <p class="empty-message">登録された作品はありません。</p>
                    </c:if>
                </section>

            </main>
        </div>
    </c:if>
</div>
</div>
<%@ include file="../footer.jsp" %>


