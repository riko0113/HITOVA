package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SkillDAO {
    // ※ご自身のPostgreSQLの環境に合わせてURL、ユーザー名、パスワードを変更してください
    private final String URL = "jdbc:postgresql://localhost:5432/hitova"; // データベース名に注意
    private final String USER = "postgres";
    private final String PASS = "post";

    // Beanを使わず、引数で直接データを受け取る
    public int insert(long candidateId, String skillName, String skillContent) throws Exception {
        int count = 0;
        // skill_idは自動採番（generated always as identity）なので指定しません
        String sql = "INSERT INTO skills (candidate_id, skill_name, skill_content) VALUES (?, ?, ?)";

        Class.forName("org.postgresql.Driver");

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, candidateId);
            st.setString(2, skillName);
            st.setString(3, skillContent);

            count = st.executeUpdate(); // 成功すれば1が返る
        }
        return count;
    }
}