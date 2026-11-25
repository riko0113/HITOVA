package report;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

import DAO.ReportDAO;
import DAO.ReportDAO.ReportTarget;
import bean.Report;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import login.AuthWeb;
import tool.Action;

public class ReportSendAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Object candidateObj = (session != null) ? session.getAttribute(AuthWeb.candidate_key) : null;
        if (!(candidateObj instanceof Long)) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return null;
        }

        long candidateId = (Long) candidateObj;
        long jobId = Long.parseLong(request.getParameter("job_id"));
        long companyAccountId = Long.parseLong(request.getParameter("company_account_id"));
        String reportType = request.getParameter("report_type");
        String detail = request.getParameter("content");

        if (detail == null || detail.trim().isEmpty()) {
            ReportDAO dao = new ReportDAO();
            ReportTarget target = dao.findTargetByJobId(jobId);
            request.setAttribute("target", target);
            request.setAttribute("error", "通報内容を入力してください。");
            return "/report/reportForm.jsp";
        }

        String fullContent = (reportType != null && !reportType.isEmpty())
                ? "【" + reportType + "】" + detail.trim()
                : detail.trim();
        if (fullContent.length() > 500) {
            fullContent = fullContent.substring(0, 500);
        }

        // 添付ファイル
        String evidencePath = "";
        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/form-data")) {
            try {
                Part filePart = request.getPart("evidence_file");
                if (filePart != null && filePart.getSize() > 0) {
                    String submitted = filePart.getSubmittedFileName();
                    String ext = "";
                    if (submitted != null && submitted.contains(".")) {
                        ext = submitted.substring(submitted.lastIndexOf("."));
                    }
                    String savedName = UUID.randomUUID().toString() + ext;
                    String uploadDirReal = request.getServletContext().getRealPath("/uploads/reports");
                    if (uploadDirReal != null) {
                        File dir = new File(uploadDirReal);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        try (InputStream in = filePart.getInputStream()) {
                            Files.copy(in, Path.of(uploadDirReal, savedName), StandardCopyOption.REPLACE_EXISTING);
                        }
                        evidencePath = "/uploads/reports/" + savedName;
                    }
                }
            } catch (Exception ignored) {
            }
        }

        Report report = new Report();
        report.setReporter_id(candidateId);
        report.setReported_user_id(companyAccountId);
        report.setContent(fullContent);
        report.setReported_at(LocalDateTime.now());
        report.setReporter_type("false");         // 求職者
        report.setReported_person_type("true");   // 企業
        report.setFor_storing_evidence(evidencePath);

        ReportDAO dao = new ReportDAO();
        dao.insert(report);

        response.sendRedirect(request.getContextPath() + "/report/reportComplete.jsp");
        return null;
    }
}