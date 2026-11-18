package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.CompanyAccount;
import bean.CorporationInformation;
import bean.SoleProprietorInformation;

public class CompanyDAO extends DAO {

    // 一覧表示用のデータ入れ物
    public static class CompanyRow {
        private Long company_account_id;
        private String company_name;
        private String business_type;
        private String person_name;
        private String email;
        private String registered_at;
        private Boolean review_status;
        private int published_job_count;
        private String account_status_name;

        public Long getCompany_account_id() { return company_account_id; }
        public void setCompany_account_id(Long v) { this.company_account_id = v; }
        public String getCompany_name() { return company_name; }
        public void setCompany_name(String v) { this.company_name = v; }
        public String getBusiness_type() { return business_type; }
        public void setBusiness_type(String v) { this.business_type = v; }
        public String getPerson_name() { return person_name; }
        public void setPerson_name(String v) { this.person_name = v; }
        public String getEmail() { return email; }
        public void setEmail(String v) { this.email = v; }
        public String getRegistered_at() { return registered_at; }
        public void setRegistered_at(String v) { this.registered_at = v; }
        public Boolean getReview_status() { return review_status; }
        public void setReview_status(Boolean v) { this.review_status = v; }
        public int getPublished_job_count() { return published_job_count; }
        public void setPublished_job_count(int v) { this.published_job_count = v; }
        public String getAccount_status_name() { return account_status_name; }
        public void setAccount_status_name(String v) { this.account_status_name = v; }
    }

    // 1. 企業一覧取得（企業名検索対応）
    public List<CompanyRow> searchCompanies(String keyword) throws Exception {
        List<CompanyRow> list = new ArrayList<>();
        Connection con = getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("  ca.company_account_id, ");
        sql.append("  COALESCE(ci.official_company_name, spi.trade_name, '未設定') AS display_company_name, ");
        sql.append("  ca.business_type, ");
        sql.append("  ca.person_name, ");
        sql.append("  ca.email, ");
        sql.append("  ca.review_status, ");
        sql.append("  ast.status_name AS account_status_name, ");
        sql.append("  (SELECT COUNT(*) FROM jobs j WHERE j.company_account_id = ca.company_account_id AND j.publication_status = true) AS published_job_count ");
        sql.append("FROM company_accounts ca ");
        sql.append("LEFT JOIN corporation_information ci ON ca.company_account_id = ci.company_account_id ");
        sql.append("LEFT JOIN sole_proprietor_information spi ON ca.company_account_id = spi.company_account_id ");
        sql.append("LEFT JOIN accounts_status ast ON ca.account_status_id = ast.account_status_id ");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("WHERE COALESCE(ci.official_company_name, spi.trade_name, '') LIKE ? ");
        }
        sql.append("ORDER BY ca.company_account_id ASC");

        PreparedStatement st = con.prepareStatement(sql.toString());
        if (keyword != null && !keyword.trim().isEmpty()) {
            st.setString(1, "%" + keyword.trim() + "%");
        }

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            CompanyRow row = new CompanyRow();
            row.setCompany_account_id(rs.getLong("company_account_id"));
            row.setCompany_name(rs.getString("display_company_name"));
            row.setBusiness_type(rs.getString("business_type"));
            row.setPerson_name(rs.getString("person_name"));
            row.setEmail(rs.getString("email"));
            row.setRegistered_at("-");
            row.setReview_status(rs.getBoolean("review_status"));
            row.setPublished_job_count(rs.getInt("published_job_count"));
            row.setAccount_status_name(rs.getString("account_status_name"));
            list.add(row);
        }

        rs.close();
        st.close();
        con.close();
        return list;
    }

    // 2. 企業アカウント基本情報取得（詳細画面・マイページ用）
    public CompanyAccount getCompanyAccountById(Long id) throws Exception {
        CompanyAccount ca = null;
        Connection con = getConnection();

        String sql = "SELECT ca.*, i.industry_name, ast.status_name AS account_status_name " +
                     "FROM company_accounts ca " +
                     "LEFT JOIN industries i ON ca.industry_id = i.industry_id " +
                     "LEFT JOIN accounts_status ast ON ca.account_status_id = ast.account_status_id " +
                     "WHERE ca.company_account_id = ?";

        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, id);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            ca = new CompanyAccount();
            ca.setCompany_account_id(rs.getLong("company_account_id"));
            ca.setPerson_name(rs.getString("person_name"));
            ca.setPerson_name_kana(rs.getString("person_name_kana"));
            ca.setDepartment(rs.getString("department"));
            ca.setPosition(rs.getString("position"));
            ca.setEmail(rs.getString("email"));
            ca.setBusiness_type(rs.getString("business_type"));
            ca.setBusiness_description(rs.getString("business_description"));
            ca.setIndustry_id(rs.getLong("industry_id"));
            ca.setIndustry_name(rs.getString("industry_name"));
            ca.setNumber_of_employee(rs.getInt("number_of_employee"));
            ca.setEstablished_year(rs.getInt("established_year"));
            ca.setReview_status(rs.getBoolean("review_status"));
            ca.setAccount_status_id(rs.getLong("account_status_id"));
            ca.setAccount_status_name(rs.getString("account_status_name"));
        }

        rs.close();
        st.close();
        con.close();
        return ca;
    }

    // 3. 法人情報取得（詳細画面・マイページ用）
    public CorporationInformation getCorporationInfoById(Long id) throws Exception {
        CorporationInformation ci = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM corporation_information WHERE company_account_id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, id);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            ci = new CorporationInformation();
            ci.setCompany_account_id(rs.getLong("company_account_id"));
            ci.setCorporate_number(rs.getString("corporate_number"));
            ci.setOfficial_company_name(rs.getString("official_company_name"));
            ci.setMain_phone_number(rs.getString("main_phone_number"));
            ci.setCompany_website_url(rs.getString("company_website_url"));
        }

        rs.close();
        st.close();
        con.close();
        return ci;
    }

    // 4. 個人事業主情報取得（詳細画面・マイページ用）
    public SoleProprietorInformation getSoleProprietorInfoById(Long id) throws Exception {
        SoleProprietorInformation spi = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM sole_proprietor_information WHERE company_account_id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setLong(1, id);

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            spi = new SoleProprietorInformation();
            spi.setCompany_account_id(rs.getLong("company_account_id"));
            spi.setTrade_name(rs.getString("trade_name"));
            spi.setCompany_representative_name(rs.getString("company_representative_name"));
            spi.setBusiness_office_address(rs.getString("business_office_address"));
            spi.setBusiness_office_phone_number(rs.getString("business_office_phone_number"));
            spi.setStore_verification_url(rs.getString("store_verification_url"));
            spi.setBusiness_verification_url(rs.getString("business_verification_document_storage_url"));
        }

        rs.close();
        st.close();
        con.close();
        return spi;
    }
    
    // 指定した企業アカウントIDから、企業名・メールアドレス・会社概要・店舗URLを取得するメソッド
    public CompanyAccount getCompanyDetails(Long companyAccountId) throws Exception {
        CompanyAccount company = null;
        
        // SQL
        String sql = "SELECT " +
                     "ca.company_account_id, ca.email, ca.business_description, ca.business_type, " +
                     "corp.official_company_name, corp.company_website_url, " +
                     "sole.trade_name, sole.store_verification_url " +
                     "FROM company_accounts ca " +
                     "LEFT JOIN corporation_informations corp ON ca.company_account_id = corp.company_account_id " +
                     "LEFT JOIN sole_proprietor_informations sole ON ca.company_account_id = sole.company_account_id " +
                     "WHERE ca.company_account_id = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setLong(1, companyAccountId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // business_type の値を取得
                    String businessType = rs.getString("business_type");
                    
                    // 「法人」の場合の判定
                    if ("法人".equals(businessType)) {
                        CorporationInformation corp = new CorporationInformation();
                        
                        // 共通項目をセット
                        corp.setCompany_account_id(rs.getLong("company_account_id"));
                        corp.setEmail(rs.getString("email"));
                        corp.setBusiness_description(rs.getString("business_description"));
                        corp.setBusiness_type(businessType);
                        
                        // 法人固有項目をセット
                        corp.setOfficial_company_name(rs.getString("official_company_name"));
                        corp.setCompany_website_url(rs.getString("company_website_url"));
                        
                        company = corp;
                        
                    } else {
                        // 「個人事業主」の場合の判定
                        SoleProprietorInformation sole = new SoleProprietorInformation();
                        
                        // 共通項目をセット
                        sole.setCompany_account_id(rs.getLong("company_account_id"));
                        sole.setEmail(rs.getString("email"));
                        sole.setBusiness_description(rs.getString("business_description"));
                        sole.setBusiness_type(businessType);
                        
                        // 個人事業主固有項目をセット
                        sole.setTrade_name(rs.getString("trade_name"));
                        sole.setStore_verification_url(rs.getString("store_verification_url"));
                        
                        company = sole;
                    }
                }
            }
        }
        return company;
    }
}