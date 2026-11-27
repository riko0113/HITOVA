package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompanyApprovalDAO extends DAO {

    public static class ApprovalCompanyItem {
        private long companyAccountId;
        private String companyName;
        private String personName;
        private String department;
        private String position;
        private String email;
        private String phone;
        private String address;
        private String websiteUrl;
        private String corporateNumber;
        private String businessType;
        private String businessDescription;
        private Integer establishedYear;
        private Integer numberOfEmployee;
        private String entityType; // "法人" or "個人事業主"
        private String status;     // "PENDING"(審査中), "APPROVED"(承認済み), "REJECTED"(拒否)

        public long getCompanyAccountId() { return companyAccountId; }
        public void setCompanyAccountId(long companyAccountId) { this.companyAccountId = companyAccountId; }
        public String getCompanyName() { return companyName; }
        public void setCompanyName(String companyName) { this.companyName = companyName; }
        public String getPersonName() { return personName; }
        public void setPersonName(String personName) { this.personName = personName; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getWebsiteUrl() { return websiteUrl; }
        public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
        public String getCorporateNumber() { return corporateNumber; }
        public void setCorporateNumber(String corporateNumber) { this.corporateNumber = corporateNumber; }
        public String getBusinessType() { return businessType; }
        public void setBusinessType(String businessType) { this.businessType = businessType; }
        public String getBusinessDescription() { return businessDescription; }
        public void setBusinessDescription(String businessDescription) { this.businessDescription = businessDescription; }
        public Integer getEstablishedYear() { return establishedYear; }
        public void setEstablishedYear(Integer establishedYear) { this.establishedYear = establishedYear; }
        public Integer getNumberOfEmployee() { return numberOfEmployee; }
        public void setNumberOfEmployee(Integer numberOfEmployee) { this.numberOfEmployee = numberOfEmployee; }
        public String getEntityType() { return entityType; }
        public void setEntityType(String entityType) { this.entityType = entityType; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    // 申請企業一覧の総件数を取得
    public int countApplications(String statusFilter) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM company_accounts ca ");
        if ("APPROVED".equals(statusFilter)) {
            sql.append("WHERE ca.review_status = TRUE ");
        } else if ("PENDING".equals(statusFilter)) {
            sql.append("WHERE (ca.review_status IS NULL OR ca.review_status = FALSE) AND COALESCE(ca.account_status_id, 1) <> 3 ");
        } else if ("REJECTED".equals(statusFilter)) {
            sql.append("WHERE ca.account_status_id = 3 ");
        }

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // ページネーション付きで申請企業一覧を取得
    public List<ApprovalCompanyItem> findApplications(String statusFilter, int limit, int offset) throws Exception {
        List<ApprovalCompanyItem> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT ca.company_account_id, ca.person_name, ca.email, ca.business_type, "
          + "       ca.review_status, ca.account_status_id, "
          + "       ci.official_company_name AS corp_name, "
          + "       spi.trade_name AS sole_name "
          + "FROM company_accounts ca "
          + "LEFT JOIN corporation_information ci ON ca.company_account_id = ci.company_account_id "
          + "LEFT JOIN sole_proprietor_information spi ON ca.company_account_id = spi.company_account_id "
        );

        if ("APPROVED".equals(statusFilter)) {
            sql.append("WHERE ca.review_status = TRUE ");
        } else if ("PENDING".equals(statusFilter)) {
            sql.append("WHERE (ca.review_status IS NULL OR ca.review_status = FALSE) AND COALESCE(ca.account_status_id, 1) <> 3 ");
        } else if ("REJECTED".equals(statusFilter)) {
            sql.append("WHERE ca.account_status_id = 3 ");
        }

        sql.append("ORDER BY ca.company_account_id DESC LIMIT ? OFFSET ?");

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ApprovalCompanyItem item = new ApprovalCompanyItem();
                    item.setCompanyAccountId(rs.getLong("company_account_id"));
                    item.setPersonName(rs.getString("person_name"));
                    item.setEmail(rs.getString("email"));
                    item.setBusinessType(rs.getString("business_type"));

                    boolean reviewStatus = rs.getBoolean("review_status");
                    int accountStatusId = rs.getInt("account_status_id");
                    if (accountStatusId == 3) {
                        item.setStatus("REJECTED");
                    } else if (reviewStatus) {
                        item.setStatus("APPROVED");
                    } else {
                        item.setStatus("PENDING");
                    }

                    String corpName = rs.getString("corp_name");
                    String soleName = rs.getString("sole_name");
                    if (corpName != null && !corpName.isEmpty()) {
                        item.setCompanyName(corpName);
                        item.setEntityType("法人");
                    } else if (soleName != null && !soleName.isEmpty()) {
                        item.setCompanyName(soleName);
                        item.setEntityType("個人事業主");
                    } else {
                        item.setCompanyName(rs.getString("person_name") + "（未設定）");
                        item.setEntityType(rs.getString("business_type") != null ? rs.getString("business_type") : "-");
                    }
                    list.add(item);
                }
            }
        }
        return list;
    }

    // 1件の企業詳細を取得
    public ApprovalCompanyItem findById(long companyAccountId) throws Exception {
        String sql =
            "SELECT ca.company_account_id, ca.person_name, ca.department, ca.position, ca.email, "
          + "       ca.business_type, ca.business_description, ca.established_year, ca.number_of_employee, "
          + "       ca.review_status, ca.account_status_id, "
          + "       ci.official_company_name AS corp_name, ci.corporate_number, ci.main_phone_number, ci.company_website_url, "
          + "       spi.trade_name AS sole_name, spi.business_office_address, spi.business_office_phone_number, spi.store_verification_url "
          + "FROM company_accounts ca "
          + "LEFT JOIN corporation_information ci ON ca.company_account_id = ci.company_account_id "
          + "LEFT JOIN sole_proprietor_information spi ON ca.company_account_id = spi.company_account_id "
          + "WHERE ca.company_account_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, companyAccountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ApprovalCompanyItem item = new ApprovalCompanyItem();
                    item.setCompanyAccountId(rs.getLong("company_account_id"));
                    item.setPersonName(rs.getString("person_name"));
                    item.setDepartment(rs.getString("department"));
                    item.setPosition(rs.getString("position"));
                    item.setEmail(rs.getString("email"));
                    item.setBusinessType(rs.getString("business_type"));
                    item.setBusinessDescription(rs.getString("business_description"));

                    int estYear = rs.getInt("established_year");
                    if (!rs.wasNull()) item.setEstablishedYear(estYear);
                    int empNum = rs.getInt("number_of_employee");
                    if (!rs.wasNull()) item.setNumberOfEmployee(empNum);

                    boolean reviewStatus = rs.getBoolean("review_status");
                    int accountStatusId = rs.getInt("account_status_id");
                    if (accountStatusId == 3) {
                        item.setStatus("REJECTED");
                    } else if (reviewStatus) {
                        item.setStatus("APPROVED");
                    } else {
                        item.setStatus("PENDING");
                    }

                    String corpName = rs.getString("corp_name");
                    String soleName = rs.getString("sole_name");
                    if (corpName != null && !corpName.isEmpty()) {
                        item.setCompanyName(corpName);
                        item.setCorporateNumber(rs.getString("corporate_number"));
                        item.setPhone(rs.getString("main_phone_number"));
                        item.setWebsiteUrl(rs.getString("company_website_url"));
                        item.setAddress("-");
                        item.setEntityType("法人");
                    } else if (soleName != null && !soleName.isEmpty()) {
                        item.setCompanyName(soleName);
                        item.setCorporateNumber("-");
                        item.setPhone(rs.getString("business_office_phone_number"));
                        item.setAddress(rs.getString("business_office_address"));
                        item.setWebsiteUrl(rs.getString("store_verification_url"));
                        item.setEntityType("個人事業主");
                    } else {
                        item.setCompanyName(rs.getString("person_name"));
                        item.setCorporateNumber("-");
                        item.setPhone("-");
                        item.setAddress("-");
                        item.setWebsiteUrl("-");
                        item.setEntityType(rs.getString("business_type") != null ? rs.getString("business_type") : "-");
                    }
                    return item;
                }
            }
        }
        return null;
    }

    // 承認・拒否ステータスを更新（review_status と account_status_id を更新）
    public int updateApprovalStatus(long companyAccountId, String newStatus) throws Exception {
        String sql;
        if ("APPROVED".equals(newStatus)) {
            sql = "UPDATE company_accounts SET review_status = TRUE, account_status_id = 1 WHERE company_account_id = ?";
        } else {
            sql = "UPDATE company_accounts SET review_status = FALSE, account_status_id = 3 WHERE company_account_id = ?";
        }
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, companyAccountId);
            return ps.executeUpdate();
        }
    }
}