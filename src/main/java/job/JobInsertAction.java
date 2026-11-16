package job;


import DAO.JobDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class JobInsertAction extends Action {
    // executeメソッド：FrontControllerから呼ばれるメイン処理
    public String execute(
        HttpServletRequest request, HttpServletResponse response
    ) {
    	
    	try {

    		// セッションからユーザーIDを取得して未ログインチェック
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("company_account_id");
            
            if (userId == null) {
            	// ログイン画面へリダイレクトする処理
            	response.sendRedirect(request.getContextPath() + "");
                return "null"; 
            }

            // JSPから入力内容を受け取る
            int jobTypeId = Integer.parseInt(request.getParameter("job_type_id"));
            String title = request.getParameter("title");
            int employmentTypeId = Integer.parseInt(request.getParameter("employment_type_id"));
            String description = request.getParameter("description");
            String requirement = request.getParameter("requirement");
            String headcount = request.getParameter("headcount");
            String location = request.getParameter("location");
            String access = request.getParameter("access");
            int salaryTypeId = Integer.parseInt(request.getParameter("salary_type_id"));
            String salary = request.getParameter("salary");
            String allowance = request.getParameter("allowance");
            String transportationFeeStr = request.getParameter("transportation_fee");
            String workingHours = request.getParameter("working_hours");
            String holiday = request.getParameter("holiday");
            String smokingPrevention = request.getParameter("smoking_prevention");
            String[] insurance = request.getParameterValues("insurance");
            String benefits = request.getParameter("benefits");
            String imageUrl = request.getParameter("image_url");
            String workplacePr = request.getParameter("workplace_pr");
            String selectionProcess = request.getParameter("selection_process");
            String interviewLocation = request.getParameter("interview_location");
            String publicationStatusStr = request.getParameter("publication_status");
            
            
            // エラー有無を管理するフラグ
            boolean hasError = false;

            // 1. jobTypeId (integer, NOT NULL)
            if (jobTypeId <= 0) {
                request.setAttribute("jobTypeError", "職種を選択してください。");
                hasError = true;
            }

            // 2. title (varchar(20), NOT NULL)
            if (title == null || title.trim().isEmpty()) {
                request.setAttribute("titleError", "求人タイトルを入力してください。");
                hasError = true;
            } else if (title.length() > 20) {
                request.setAttribute("titleError", "求人タイトルは20文字以内で入力してください。");
                hasError = true;
            }

            // 3. employmentTypeId (integer, NOT NULL)
            if (employmentTypeId <= 0) {
                request.setAttribute("employmentTypeError", "雇用形態を選択してください。");
                hasError = true;
            }

            // 4. description (text, NOT NULL)
            if (description == null || description.trim().isEmpty()) {
                request.setAttribute("descriptionError", "仕事内容を入力してください。");
                hasError = true;
            }

            // 5. requirement (varchar(500), NOT NULL)
            if (requirement == null || requirement.trim().isEmpty()) {
                request.setAttribute("requirementError", "応募資格を入力してください。");
                hasError = true;
            } else if (requirement.length() > 500) {
                request.setAttribute("requirementError", "応募資格は500文字以内で入力してください。");
                hasError = true;
            }

            // 6. headcount (varchar(10))
            if (headcount != null && headcount.length() > 10) {
                request.setAttribute("headcountError", "採用人数は10文字以内で入力してください。");
                hasError = true;
            }

            // 7. location (text, NOT NULL)
            if (location == null || location.trim().isEmpty()) {
                request.setAttribute("locationError", "勤務地を入力してください。");
                hasError = true;
            }

            // 8. access (varchar(50))
            if (access != null && access.length() > 50) {
                request.setAttribute("accessError", "アクセス情報は50文字以内で入力してください。");
                hasError = true;
            }

            // 9. salaryTypeId (integer, NOT NULL)
            if (salaryTypeId <= 0) {
                request.setAttribute("salaryTypeError", "給与形態を選択してください。");
                hasError = true;
            }

            // 10. salary (varchar(30), NOT NULL)
            if (salary == null || salary.trim().isEmpty()) {
                request.setAttribute("salaryError", "給与を入力してください。");
                hasError = true;
            } else if (salary.length() > 30) {
                request.setAttribute("salaryError", "給与は30文字以内で入力してください。");
                hasError = true;
            }

            // 11. allowance (varchar(200), NOT NULL)
            if (allowance == null || allowance.trim().isEmpty()) {
                request.setAttribute("allowanceError", "諸手当を入力してください（ない場合は「なし」等）。");
                hasError = true;
            } else if (allowance.length() > 200) {
                request.setAttribute("allowanceError", "諸手当は200文字以内で入力してください。");
                hasError = true;
            }

            // 12. transportationFee (boolean, NOT NULL)
            if (transportationFeeStr == null || transportationFeeStr.trim().isEmpty()) {
                request.setAttribute("transportationFeeError", "交通費支給の有無を選択してください。");
                hasError = true;
            } 
            // エラーがなければ boolean にパースする
            boolean transportationFee = Boolean.parseBoolean(transportationFeeStr);

            // 13. workingHours (varchar(50), NOT NULL)
            if (workingHours == null || workingHours.trim().isEmpty()) {
                request.setAttribute("workingHoursError", "勤務時間を入力してください。");
                hasError = true;
            } else if (workingHours.length() > 50) {
                request.setAttribute("workingHoursError", "勤務時間は50文字以内で入力してください。");
                hasError = true;
            }

            // 14. holiday (varchar(30), NOT NULL)
            if (holiday == null || holiday.trim().isEmpty()) {
                request.setAttribute("holidayError", "休日・休暇を入力してください。");
                hasError = true;
            } else if (holiday.length() > 30) {
                request.setAttribute("holidayError", "休日・休暇は30文字以内で入力してください。");
                hasError = true;
            }

            // 15. smokingPrevention (varchar(50), NOT NULL)
            if (smokingPrevention == null || smokingPrevention.trim().isEmpty()) {
                request.setAttribute("smokingPreventionError", "受動喫煙防止措置を入力してください。");
                hasError = true;
            } else if (smokingPrevention.length() > 50) {
                request.setAttribute("smokingPreventionError", "受動喫煙防止措置は50文字以内で入力してください。");
                hasError = true;
            }

            // 16. insurance (jsonb, NOT NULL) - チェックボックスで何も選択されていない場合をチェック
            if (insurance == null || insurance.length == 0) {
                request.setAttribute("insuranceError", "加入保険を少なくとも1つ選択してください。");
                hasError = true;
            }

            // 17. benefits (varchar(200)) ※NULL許容
            if (benefits != null && benefits.length() > 200) {
                request.setAttribute("benefitsError", "福利厚生は200文字以内で入力してください。");
                hasError = true;
            }

            // 18. imageUrl (text, NOT NULL)
            if (imageUrl == null || imageUrl.trim().isEmpty()) {
                request.setAttribute("imageUrlError", "画像URLを入力してください。");
                hasError = true;
            }

            // 19. workplacePr (varchar(200)) ※NULL許容
            if (workplacePr != null && workplacePr.length() > 200) {
                request.setAttribute("workplacePrError", "職場PRは200文字以内で入力してください。");
                hasError = true;
            }

            // 20. selectionProcess (varchar(150), NOT NULL)
            if (selectionProcess == null || selectionProcess.trim().isEmpty()) {
                request.setAttribute("selectionProcessError", "選考プロセスを入力してください。");
                hasError = true;
            } else if (selectionProcess.length() > 150) {
                request.setAttribute("selectionProcessError", "選考プロセスは150文字以内で入力してください。");
                hasError = true;
            }

            // 21. interviewLocation (text, NOT NULL)
            if (interviewLocation == null || interviewLocation.trim().isEmpty()) {
                request.setAttribute("interviewLocationError", "面接場所を入力してください。");
                hasError = true;
            }
            
            // 22. publicationStatus (boolean, NOT NULL) のチェック
            if (publicationStatusStr == null || publicationStatusStr.trim().isEmpty()) {
                request.setAttribute("publicationStatusError", "公開ステータスを選択してください。");
                hasError = true;
            }
            // エラーがなければ boolean にパースする
            boolean publicationStatus = Boolean.parseBoolean(publicationStatusStr);
            
            // エラーがあった場合
            if (hasError) {
                return ""; 
            }
            
            // 求人DAOのインスタンス生成
            JobDAO dao = new JobDAO();
            
            // 求人を登録する
            dao.insert(
            		userId, 
            	    jobTypeId, 
            	    title, 
            	    employmentTypeId, 
            	    description, 
            	    requirement, 
            	    headcount, 
            	    location, 
            	    access, 
            	    salaryTypeId, 
            	    salary, 
            	    allowance, 
            	    transportationFee, 
            	    workingHours, 
            	    holiday, 
            	    smokingPrevention, 
            	    insurance, 
            	    benefits, 
            	    imageUrl, 
            	    workplacePr, 
            	    selectionProcess, 
            	    interviewLocation, 
            	    publicationStatus
            	    );
            
            return ""; 
            
        } catch (Exception e) {
            //サーバー側のログにエラーの詳細を出力する
            e.printStackTrace();
            
            //ユーザー向けのエラーメッセージをリクエストにセットする
            request.setAttribute("errorMessage", "データの取得中にエラーが発生しました。しばらく経ってから再度お試しください。");
            
            //エラー用の専用画面、または元の安全な画面へフォワードする
            return "/error.jsp"; 
        }
    }

}
