package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.Profile;

// プロフィール登録DAO
public class ProfileRegisterDAO extends DAO {

    // プロフィール情報を登録する
    public int insert(Profile profile) throws Exception {

        String sql =
            "INSERT INTO profiles (" +
            "last_name, " +
            "first_name, " +
            "last_name_kana, " +
            "first_name_kana, " +
            "birth_day, " +
            "gender, " +
            "current_postal_code, " +
            "current_address, " +
            "desired_employment_type_id, " +
            "desired_work_location, " +
            "desired_job_type_id, " +
            "highest_education, " +
            "work_history, " +
            "candidate_account_id" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ) {

            // 氏名
            st.setString(1, profile.getLast_name());
            st.setString(2, profile.getFirst_name());

            // 氏名カナ
            st.setString(3, profile.getLast_name_kana());
            st.setString(4, profile.getFirst_name_kana());

            // 生年月日
            st.setDate(
                5,
                java.sql.Date.valueOf(
                    profile.getBirth_date().toLocalDate()
                )
            );

            // 性別
            st.setBoolean(6, profile.getgender());

            // 住所
            st.setString(7, profile.getCurrent_postal_code());
            st.setString(8, profile.getCurrent_address());

            // 希望条件
            st.setInt(9, profile.getDesired_employment_type_id());
            st.setString(10, profile.getDesired_work_location());
            st.setInt(11, profile.getDesired_job_type_id());

            // 学歴・職歴
            st.setString(12, profile.getHighest_education());
            st.setString(13, profile.getWork_history());

            // 求職者アカウントID
            st.setLong(14, profile.getCandidate_account_id());

            // 登録実行
            return st.executeUpdate();
        }
    }
}