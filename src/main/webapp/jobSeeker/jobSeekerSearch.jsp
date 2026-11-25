
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!-- ヘッダー -->
<%@ include page="../../head.jsp"  %>
<link rel="stylesheet" href="jobSeekerSearch.css">

<div class="main-layout">
<%@ include file="../../jobSeekerSidebar.jsp" %>
<div class="main-content">

<div class="job-seeker-search-container">

    <h2>求職者検索</h2>

    <!-- 検索条件 -->
    <section class="search-condition">

        <h3>検索条件</h3>

        <form action="${pageContext.request.contextPath}/jobSeeker/JobSeekerSearch.action" method="post">

            <!-- 希望雇用形態 -->
            <div class="search-row">
                <label for="employment_type">希望雇用形態</label>
                <select name="employment_type" id="employment_type">
                    <option value="">選択してください</option>
                    <c:forEach var="item" items="${employmentTypeList}">
                        <option value="${item['id']}"
                            <c:if test="${search.employmentType == item['id']}">selected</c:if>>
                            <c:out value="${item['name']}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- 希望職種 -->
            <div class="search-row">
                <label for="job_type">希望職種</label>
                <select name="job_type" id="job_type">
                    <option value="">選択してください</option>
                    <c:forEach var="item" items="${jobTypeList}">
                        <option value="${item['id']}"
                            <c:if test="${search.jobType == item['id']}">selected</c:if>>
                            <c:out value="${item['name']}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- 業種 -->
            <div class="search-row">
                <label for="industry">業種</label>
                <select name="industry" id="industry">
                    <option value="">選択してください</option>
                    <c:forEach var="item" items="${industryList}">
                        <option value="${item['id']}"
                            <c:if test="${search.industry == item['id']}">selected</c:if>>
                            <c:out value="${item['name']}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- 保有資格種類 -->
            <div class="search-group">

                <div class="search-row">
                    <label for="qualification_type">保有資格種類</label>
                    <select name="qualification_type" id="qualification_type">
                        <option value="">選択してください</option>
                        <c:forEach var="item" items="${qualificationTypeList}">
                            <option value="${item['id']}"
                                <c:if test="${search.qualificationType == item['id']}">selected</c:if>>
                                <c:out value="${item['name']}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 資格 -->
                <div class="search-sub-row">
                    <label for="qualification">資格</label>
                    <select name="qualification" id="qualification">
                        <option value="">選択してください</option>
                        <c:forEach var="item" items="${qualificationList}">
                            <option value="${item['id']}"
                                <c:if test="${search.qualification == item['id']}">selected</c:if>>
                                <c:out value="${item['name']}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 資格名を追加 -->
                <div class="add-qualification-area">

                    <button type="button"
                            id="addQualificationButton"
                            class="add-qualification-button">
                        ＋ さらに追加
                    </button>

                    <div id="customQualificationArea"
                         class="custom-qualification-area"
                         style="display: none;">

                        <label for="custom_qualification">
                            追加する資格
                        </label>

                        <input type="text"
                               name="custom_qualification"
                               id="custom_qualification"
                               value="${param.custom_qualification}"
                               placeholder="資格名を入力してください">

                    </div>
                </div>
            </div>

            <!-- 実績種類 -->
            <div class="search-group">

                <div class="search-row">
                    <label for="achievement_type">実績種類</label>
                    <select name="achievement_type" id="achievement_type">
                        <option value="">選択してください</option>
                        <c:forEach var="item" items="${achievementTypeList}">
                            <option value="${item['id']}"
                                <c:if test="${search.achievementType == item['id']}">selected</c:if>>
                                <c:out value="${item['name']}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 実績 -->
                <div class="search-sub-row">
                    <label for="achievement">実績</label>
                    <select name="achievement" id="achievement">
                        <option value="">選択してください</option>
                        <c:forEach var="item" items="${achievementList}">
                            <option value="${item['id']}"
                                <c:if test="${search.achievement == item['id']}">selected</c:if>>
                                <c:out value="${item['name']}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

            </div>

            <!-- エラーメッセージ -->
            <c:if test="${not empty errorMessage}">
                <p class="error-message">
                    ${errorMessage}
                </p>
            </c:if>

            <!-- 検索・クリアボタン -->
            <div class="search-button-area">

                <button type="submit" class="search-button">
                    検索
                </button>

               	<button type="button" class="clear-button"
 						onclick="location.href='${pageContext.request.contextPath}/jobSeeker/JobSeekerSearch.action'">
    				クリア
				</button>

            </div>

        </form>

    </section>

    <!-- 検索結果 -->
    <section class="search-result">

        <h3>検索結果</h3>

        <p class="result-count">
            該当件数：${resultCount} 件
        </p>

        <c:choose>

            <c:when test="${not empty candidateList}">

                <c:forEach var="candidate" items="${candidateList}">

                    <div class="candidate-card">

                        <!-- 求職者の画像 -->
                        <div class="candidate-image">
                            <span>👤</span>
                        </div>

                        <!-- 求職者情報 -->
                        <div class="candidate-info">

                            <p class="candidate-name">
                                ${candidate.name}
                            </p>

                            <p>
                                生年月日：${candidate.birthDate}
                            </p>

                            <p>
                                本人確認：
                                <c:choose>
                                    <c:when test="${candidate.identityVerification}">
                                        済
                                    </c:when>
                                    <c:otherwise>
                                        未確認
                                    </c:otherwise>
                                </c:choose>
                            </p>

                        </div>

                        <!-- 詳細を見るボタン -->
                        <div class="candidate-detail">

                            <a href="${pageContext.request.contextPath}/jobSeeker/jobSeekerDetail.action?candidate_id=${candidate.candidateId}">
                                詳細を見る
                            </a>

                        </div>

                    </div>

                </c:forEach>

            </c:when>

            <c:otherwise>

                <p class="no-result">
                    該当する求職者はいません。
                </p>

            </c:otherwise>

        </c:choose>

    </section>

    <!-- 戻るボタン -->
    <div class="back-button-area">

        <button type="button"
                class="back-button"
                onclick="history.back()">
            戻る
        </button>
    </div>
</div>
</div>
<script src="${pageContext.request.contextPath}/jobSeeker/jobSeekerSearch.js"></script>
<%@ include file="../../footer.jsp" %>