package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompanyAccountEditDAO extends DAO {

    public static class IndustryItem {
        private int industry_id;
        private String industry_name;

        public int getIndustry_id() { return industry_id; }
        public void setIndustry_id(int industry_id) { this.industry_id = industry_id; }
        public String getIndustry_name() { return industry_name; }
        public void setIndustry_name(String industry_name) { this.industry_name = industry_name; }
    }

    public static class CompanyEditData {
        private long company_account_id;
        private String person_name;
        private String person_name_kana;
        private String department;
        private String position;
        private String email;
        private String business_type;
        private String business_description;
        private int industry_id;
        private Integer number_of_employee;
        private Integer established_year;

        // 法人情報
        private boolean isCorporation;
        private String corporate_number;
        private String official_company_name;
        private String main_phone_number;
        private String company_website_url;

        // 個人事業主情報
        private boolean isSoleProprietor;
        private String trade_name;
        private String company_representative_name;
        private String business_office_address;
        private String business_office_phone_number;
        private String store_verification_url;

        public long getCompany_account_id() { return company_account_id; }
        public void setCompany_account_id(long company_account_id) { this.company_account_id = company_account_id; }
        public String getPerson_name() { return person_name; }
        public void setPerson_name(String person_name) { this.person_name = person_name; }
        public String getPerson_name_kana() { return person_name_kana; }
        public void setPerson_name_kana(String person_name_kana) { this.person_name_kana = person_name_kana; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getBusiness_type() { return business_type; }
        public void setBusiness_type(String business_type) { this.business_type = business_type; }
        public String getBusiness_description() { return business_description; }
        public void setBusiness_description(String business_description) { this.business_description = business_description; }
        public int getIndustry_id() { return industry_id; }
        public void setIndustry_id(int industry_id) { this.industry_id = industry_id; }
        public Integer getNumber_of_employee() { return number_of_employee; }
        public void setNumber_of_employee(Integer number_of_employee) { this.number_of_employee = number_of_employee; }
        public Integer getEstablished_year() { return established_year; }
        public void setEstablished_year(Integer established_year) { this.established_year = established_year; }

        public boolean isIsCorporation() { return isCorporation; }
        public void setIsCorporation(boolean isCorporation) { this.isCorporation = isCorporation; }
        public String getCorporate_number() { return corporate_number; }
        public void setCorporate_number(String corporate_number) { this.corporate_number = corporate_number; }
        public String getOfficial_company_name() { return official_company_name; }
        public void setOfficial_company_name(String official_company_name) { this.official_company_name = official_company_name; }
        public String getMain_phone_number() { return main_phone_number; }
        public void setMain_phone_number(String main_phone_number) { this.main_phone_number = main_phone_number; }
        public String getCompany_website_url() { return company_website_url; }
        public void setCompany_website_url(String company_website_url) { this.company_website_url = company_website_url; }

        public boolean isIsSoleProprietor() { return isSoleProprietor; }
        public void setIsSoleProprietor(boolean isSoleProprietor) { this.isSoleProprietor = isSoleProprietor; }
        public String getTrade_name() { return trade_name; }
        public void setTrade_name(String trade_name) { this.trade_name = trade_name; }
        public String getCompany_representative_name() { return company_representative_name; }
        public void setCompany_representative_name(String company_representative_name) { this.company_representative_name = company_representative_name; }
        public String getBusiness_office_address() { return business_office_address; }
        public void setBusiness_office_address(String business_office_address) { this.business_office_address = business_office_address; }
        public String getBusiness_office_phone_number() { return business_office_phone_number; }
        public void setBusiness_office_phone_number(String business_office_phone_number) { this.business_office_phone_number = business_office_phone_number; }
        public String getStore_verification_url() { return store_verification_url; }
        public void setStore_verification_url(String store_verification_url) { this.store_verification_url = store_verification_url; }
    }

    // 業種一覧を取得する
    public List<IndustryItem> findAllIndustries() throws Exception {
        List<IndustryItem> list = new ArrayList<>();
        String sql = "SELECT industry_id, industry_name FROM industries ORDER BY industry_id";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                IndustryItem item = new IndustryItem();
                item.setIndustry_id(rs.getInt("industry_id"));
                item.setIndustry_name(rs.getString("industry_name"));
                list.add(item);
            }
        }
        return list;
    }

    // 企業情報を全項目取得する
    public CompanyEditData findById(long companyId) throws Exception {
        String sql = "SELECT ca.*, "
                   + "ci.corporate_number, ci.official_company_name, ci.main_phone_number, ci.company_website_url, "
                   + "spi.trade_name, spi.company_representative_name, spi.business_office_address, "
                   + "spi.business_office_phone_number, spi.store_verification_url "
                   + "FROM company_accounts ca "
                   + "LEFT JOIN corporation_information ci ON ca.company_account_id = ci.company_account_id "
                   + "LEFT JOIN sole_proprietor_information spi ON ca.company_account_id = spi.company_account_id "
                   + "WHERE ca.company_account_id = ?";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, companyId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    CompanyEditData data = new CompanyEditData();
                    data.setCompany_account_id(rs.getLong("company_account_id"));
                    data.setPerson_name(rs.getString("person_name"));
                    data.setPerson_name_kana(rs.getString("person_name_kana"));
                    data.setDepartment(rs.getString("department"));
                    data.setPosition(rs.getString("position"));
                    data.setEmail(rs.getString("email"));
                    data.setBusiness_type(rs.getString("business_type"));
                    data.setBusiness_description(rs.getString("business_description"));
                    data.setIndustry_id(rs.getInt("industry_id"));

                    int emp = rs.getInt("number_of_employee");
                    data.setNumber_of_employee(rs.wasNull() ? null : emp);

                    int year = rs.getInt("established_year");
                    data.setEstablished_year(rs.wasNull() ? null : year);

                    if (rs.getString("corporate_number") != null) {
                        data.setIsCorporation(true);
                        data.setCorporate_number(rs.getString("corporate_number"));
                        data.setOfficial_company_name(rs.getString("official_company_name"));
                        data.setMain_phone_number(rs.getString("main_phone_number"));
                        data.setCompany_website_url(rs.getString("company_website_url"));
                    }
                    if (rs.getString("trade_name") != null) {
                        data.setIsSoleProprietor(true);
                        data.setTrade_name(rs.getString("trade_name"));
                        data.setCompany_representative_name(rs.getString("company_representative_name"));
                        data.setBusiness_office_address(rs.getString("business_office_address"));
                        data.setBusiness_office_phone_number(rs.getString("business_office_phone_number"));
                        data.setStore_verification_url(rs.getString("store_verification_url"));
                    }
                    return data;
                }
            }
        }
        return null;
    }

    // 企業情報をトランザクションで一括更新する
    public void update(CompanyEditData data) throws Exception {
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            try {
                String sqlBase = "UPDATE company_accounts SET "
                               + "person_name = ?, person_name_kana = ?, department = ?, position = ?, "
                               + "business_description = ?, industry_id = ?, number_of_employee = ?, established_year = ? "
                               + "WHERE company_account_id = ?";
                try (PreparedStatement st = con.prepareStatement(sqlBase)) {
                    st.setString(1, data.getPerson_name());
                    st.setString(2, data.getPerson_name_kana());
                    st.setString(3, data.getDepartment());
                    st.setString(4, data.getPosition());
                    st.setString(5, data.getBusiness_description());
                    st.setInt(6, data.getIndustry_id());

                    if (data.getNumber_of_employee() != null) {
                        st.setInt(7, data.getNumber_of_employee());
                    } else {
                        st.setNull(7, java.sql.Types.INTEGER);
                    }
                    if (data.getEstablished_year() != null) {
                        st.setShort(8, data.getEstablished_year().shortValue());
                    } else {
                        st.setNull(8, java.sql.Types.SMALLINT);
                    }
                    st.setLong(9, data.getCompany_account_id());
                    st.executeUpdate();
                }

                if (data.isIsCorporation()) {
                    String sqlCorp = "UPDATE corporation_information SET "
                                   + "official_company_name = ?, main_phone_number = ?, company_website_url = ? "
                                   + "WHERE company_account_id = ?";
                    try (PreparedStatement st = con.prepareStatement(sqlCorp)) {
                        st.setString(1, data.getOfficial_company_name());
                        st.setString(2, data.getMain_phone_number());
                        st.setString(3, data.getCompany_website_url());
                        st.setLong(4, data.getCompany_account_id());
                        st.executeUpdate();
                    }
                } else if (data.isIsSoleProprietor()) {
                    String sqlSole = "UPDATE sole_proprietor_information SET "
                                   + "trade_name = ?, company_representative_name = ?, "
                                   + "business_office_address = ?, business_office_phone_number = ?, store_verification_url = ? "
                                   + "WHERE company_account_id = ?";
                    try (PreparedStatement st = con.prepareStatement(sqlSole)) {
                        st.setString(1, data.getTrade_name());
                        st.setString(2, data.getCompany_representative_name());
                        st.setString(3, data.getBusiness_office_address());
                        st.setString(4, data.getBusiness_office_phone_number());
                        st.setString(5, data.getStore_verification_url());
                        st.setLong(6, data.getCompany_account_id());
                        st.executeUpdate();
                    }
                }

                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}