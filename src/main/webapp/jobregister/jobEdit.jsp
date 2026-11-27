<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>求人編集</title>
<%@ include file="../companyHeader.jsp" %>
<link href="../jobregisterJs/jobregister.css" rel="stylesheet">
</head>

<body>

<div class="main-layout">
    <%@ include file="../companySidebar.jsp" %>

    <div class="main-content">

        <div class="hutosi">求人編集</div>

        <h1>求人情報編集</h1>

        <div class="form">
            <label>自動入力(登録情報)</label>
            <c:choose>
                <c:when test="${companyInfo.business_type == '法人'}">
                    <div>
                        <label>企業名：</label>
                        <span><c:out value="${companyInfo.name}" /></span>
                    </div>
                    <div>
                        <label>メールアドレス：</label>
                        <span><c:out value="${companyInfo.email}" /></span>
                    </div>
                    <div>
                        <label>会社概要：</label>
                        <span><c:out value="${companyInfo.business_description}" /></span>
                    </div>
                    <div>
                        <label>関連URL：</label>
                        <a href="<c:out value='${companyInfo.url}' />" target="_blank"><c:out value="${companyInfo.company_website_url}" /></a>
                    </div>
                </c:when>

                <c:otherwise>
                    <div>
                        <label>企業名：</label>
                        <span><c:out value="${companyInfo.name}" /></span>
                    </div>
                    <div>
                        <label>メールアドレス：</label>
                        <span><c:out value="${companyInfo.email}" /></span>
                    </div>
                    <div>
                        <label>会社概要：</label>
                        <span><c:out value="${companyInfo.business_description}" /></span>
                    </div>
                    <div>
                        <label>関連URL：</label>
                        <a href="<c:out value='${companyInfo.url}' />" target="_blank"><c:out value="${companyInfo.store_verification_url}" /></a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <form action="${pageContext.request.contextPath}/job/JobEdit.action" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="job_id" value="<c:out value='${job.job_id}' />">
            <input type="hidden" name="current_image_url" value="<c:out value='${job.image_url}' />">

            <b>基本情報</b>

            <!-- タイトル (DB: varchar(20)) -->
            <div class="form5">
                <label>求人タイトル (20文字まで)</label><br>
                <input type="text" name="title" maxlength="20" required style="width: 100%;" value="<c:out value='${job.title}' />">
            </div>

            <!-- カテゴリ・雇用形態 -->
            <div class="form-row">
                <div class="form3">
                    <label>カテゴリ</label>
                    <select name="job_type_id" required>
                        <option value="" disabled hidden>カテゴリ選択</option>
                        <c:forEach var="info" items="${infoList}">
                            <option value="${info.job_type_id}" <c:if test="${job.job_type_id == info.job_type_id}">selected</c:if>>
                                <c:out value="${info.job_type_name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form4">
                    <label>雇用形態</label>
                    <select name="employment_type_id" required>
                        <option value="" disabled hidden>雇用形態選択</option>
                        <c:forEach var="info" items="${infoList}">
                            <option value="${info.employment_type_id}" <c:if test="${job.employment_type_id == info.employment_type_id}">selected</c:if>>
                                <c:out value="${info.employment_type_name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- 仕事内容 (DB: text) -->
            <div class="form5">
                <label>仕事内容</label><br>
                <textarea name="description" required><c:out value="${job.description}" /></textarea>
            </div>

            <!-- 応募資格・条件 (DB: varchar(500)) -->
            <div class="form6">
                <div class="form6-header">
                    <label>応募資格・条件 (500文字まで)</label>
                    <div class="sikaku-area">
                        <label>資格検索・貼り付け</label>
                        <select name="large">
                            <option value="" disabled selected hidden>大分類</option>
                        </select>
                        <select name="middle">
                            <option value="" disabled selected hidden>中分類</option>
                        </select>
                        <select name="small">
                            <option value="" disabled selected hidden>小分類</option>
                        </select>
                        <button type="button">貼り付け</button>
                    </div>
                </div>
                <textarea name="requirement" maxlength="500" required><c:out value="${job.requirement}" /></textarea>
            </div>

            <!-- 募集人数 (DB: varchar(10)) -->
            <div class="form7">
                <label>募集人数 (10文字まで)</label><br>
                <input type="text" name="headcount" maxlength="10" value="<c:out value='${job.headcount}' />">
            </div>

            <div class="hutosi">勤務条件・待遇</div>

            <!-- 勤務地 (DB: text) -->
            <div class="form7">
                <label>勤務地(住所)</label><br>
                <textarea name="location" required><c:out value="${job.location}" /></textarea>
            </div>

            <!-- 最寄り駅・アクセス / 給与 -->
            <div class="form-row">
                <div class="form3">
                    <label>最寄り駅・アクセス (50文字まで)</label>
                    <input type="text" name="access" maxlength="50" value="<c:out value='${job.access}' />">
                </div>

                <div class="form4">
                    <label>給与・手当 (数値等30文字まで)</label>
                    <div class="salary-row">
                        <select name="salary_type_id" required>
                            <option value="" disabled hidden>給与区分</option>
                            <c:forEach var="info" items="${infoList}">
                                <option value="${info.salary_type_id}" <c:if test="${job.salary_type_id == info.salary_type_id}">selected</c:if>>
                                    <c:out value="${info.salary_type_name}" />
                                </option>
                            </c:forEach>
                        </select>
                        <input type="text" name="salary" maxlength="30" required value="<c:out value='${job.salary}' />">円
                    </div>
                </div>
            </div>

            <!-- 諸手当 (DB: varchar(200)) -->
            <div class="form5">
                <label>諸手当 (200文字まで)</label><br>
                <textarea name="allowance" maxlength="200" required><c:out value="${job.allowance}" /></textarea>
            </div>

            <!-- 交通費 / 受動喫煙対策 -->
            <div class="form-row">
                <div class="form3">
                    <label>交通費</label>
                    <select name="transportation_fee" required>
                        <option value="" disabled <c:if test="${job.transportation_fee == null}">selected</c:if> hidden>交通費</option>
                        <option value="true" <c:if test="${job.transportation_fee == true}">selected</c:if>>支給あり</option>
                        <option value="false" <c:if test="${job.transportation_fee == false}">selected</c:if>>支給なし</option>
                    </select>
                </div>

                <div class="form4">
                    <label>受動喫煙対策</label>
                    <select name="smoking_prevention" required>
                        <option value="" disabled <c:if test="${empty job.smoking_prevention}">selected</c:if> hidden>受動喫煙対策</option>
                        <option value="屋内禁煙" <c:if test="${job.smoking_prevention == '屋内禁煙'}">selected</c:if>>屋内禁煙</option>
                        <option value="屋内禁煙（喫煙室あり）" <c:if test="${job.smoking_prevention == '屋内禁煙（喫煙室あり）'}">selected</c:if>>屋内禁煙（喫煙室あり）</option>
                        <option value="敷地内禁煙" <c:if test="${job.smoking_prevention == '敷地内禁煙'}">selected</c:if>>敷地内禁煙</option>
                    </select>
                </div>
            </div>

            <!-- 勤務時間・シフト (DB: varchar(50)) -->
            <div class="form5">
                <label>勤務時間・シフト (50文字まで)</label><br>
                <textarea name="working_hours" maxlength="50" required><c:out value="${job.working_hours}" /></textarea>
            </div>

            <!-- 休日・休暇 (DB: varchar(30)) -->
            <div class="form5">
                <label>休日・休暇 (30文字まで)</label><br>
                <textarea name="holiday" maxlength="30" required><c:out value="${job.holiday}" /></textarea>
            </div>

            <!-- 加入保険チェックボックス (DB: jsonb) -->
            <div class="form8">
                <label>加入保険</label>
                <div class="insurance-options">
                    <label class="insurance-item">
                        雇用保険
                        <input type="checkbox" name="insurance" value="雇用保険" <c:if test="${fn:contains(job.insurance, '雇用保険')}">checked</c:if>>
                    </label>
                    <label class="insurance-item">
                        労災保険
                        <input type="checkbox" name="insurance" value="労災保険" <c:if test="${fn:contains(job.insurance, '労災保険')}">checked</c:if>>
                    </label>
                    <label class="insurance-item">
                        健康保険
                        <input type="checkbox" name="insurance" value="健康保険" <c:if test="${fn:contains(job.insurance, '健康保険')}">checked</c:if>>
                    </label>
                    <label class="insurance-item">
                        厚生年金
                        <input type="checkbox" name="insurance" value="厚生年金" <c:if test="${fn:contains(job.insurance, '厚生年金')}">checked</c:if>>
                    </label>
                </div>
            </div>

            <!-- 福利厚生・その他 (DB: varchar(200)) -->
            <div class="form5">
                <label>福利厚生・その他 (200文字まで)</label><br>
                <textarea name="benefits" maxlength="200"><c:out value="${job.benefits}" /></textarea>
            </div>

            <div class="hutosi">アピール要素・選考の流れ</div>

            <!-- 求人画像アップロード -->
            <div class="form9">
                <label>求人画像</label>
                <c:if test="${not empty job.image_url}">
    				<div>現在の登録ファイル：<c:out value="${job.image_url}" /></div>
    				<c:if test="${not empty presignedUrl}">
        				<div>
           			 		<img src="<c:out value='${presignedUrl}' />" alt="現在の求人画像" style="max-width: 240px; height: auto;">
        				</div>
    				</c:if>
				</c:if>
                <label class="image-upload">
                    画像をアップロード（10MBまで）
                    <input type="file" name="image_url" accept="image/png,image/jpeg,image/webp,video/mp4,video/webm,video/quicktime">
                </label>
            </div>

            <!-- 職場の特徴・PR (DB: varchar(200)) -->
            <div class="form5">
                <label>職場の特徴・PR (200文字まで)</label><br>
                <textarea name="workplace_pr" maxlength="200"><c:out value="${job.workplace_pr}" /></textarea>
            </div>

            <!-- 選考プロセス (DB: varchar(150)) -->
            <div class="form10">
                <label>選考プロセス (全体で150文字以内で調整を推奨)</label>

                <div id="process-list">
                    <c:set var="normalizedProcess" value="${fn:replace(job.selection_process, ' → ', ',')}" />
                    <c:set var="normalizedProcess" value="${fn:replace(normalizedProcess, '&#10;', ',')}" />
                    <c:set var="renderedAny" value="false" />

                    <c:if test="${not empty normalizedProcess}">
                        <c:forEach var="step" items="${fn:split(normalizedProcess, ',')}">
                            <c:set var="trimmedStep" value="${fn:trim(step)}" />
                            <c:if test="${not empty trimmedStep}">
                                <div class="process-row">
                                    <span>1.</span>
                                    <input type="text" name="selection_process" maxlength="150" required value="<c:out value='${trimmedStep}' />">
                                    <button type="button" class="delete-process">×</button>
                                </div>
                                <c:set var="renderedAny" value="true" />
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${not renderedAny}">
                        <div class="process-row">
                            <span>1.</span>
                            <input type="text" name="selection_process" maxlength="150" required>
                            <button type="button" class="delete-process">×</button>
                        </div>
                    </c:if>
                </div>

                <button type="button" id="add-process">+</button>
            </div>

            <!-- 面接場所 (DB: text) -->
            <div class="form5">
                <label>面接場所</label><br>
                <textarea name="interview_location" required><c:out value="${job.interview_location}" /></textarea>
            </div>

            <!-- 公開ステータス (DB: boolean) -->
            <div class="form">
                <label>公開ステータス</label>
                <select name="publication_status" required>
                    <option value="" disabled <c:if test="${job.publication_status == null}">selected</c:if> hidden>ステータス選択</option>
                    <option value="true" <c:if test="${job.publication_status == true}">selected</c:if>>公開する</option>
                    <option value="false" <c:if test="${job.publication_status == false}">selected</c:if>>下書き保存</option>
                </select>
            </div>

            <!-- 更新ボタン -->
            <div class="submit-area">
                <button type="submit">更新</button>
            </div>
        </form>

    </div><!-- /.main-content -->
</div><!-- /.main-layout -->

<script>
const processList = document.getElementById("process-list");

function updateProcessNumbers() {
    document.querySelectorAll(".process-row span").forEach(function(item, index) {
        item.textContent = (index + 1) + ".";
    });
}

document.getElementById("add-process").addEventListener("click", function() {
    const row = document.createElement("div");
    row.className = "process-row";

    const number = document.createElement("span");

    const input = document.createElement("input");
    input.type = "text";
    input.name = "selection_process";
    input.maxLength = 150;
    input.required = true;

    const deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.textContent = "×";
    deleteButton.className = "delete-process";

    row.appendChild(number);
    row.appendChild(input);
    row.appendChild(deleteButton);

    processList.appendChild(row);
    updateProcessNumbers();
});

processList.addEventListener("click", function(event) {
    if (event.target.classList.contains("delete-process")) {
        event.target.closest(".process-row").remove();
        updateProcessNumbers();
    }
});

// 初期表示時の番号振り直し
updateProcessNumbers();
</script>

</body>
</html>