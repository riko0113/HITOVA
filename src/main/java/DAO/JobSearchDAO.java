package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobSearchDAO {
    // 先ほど直した小文字の「hitova」を指定します
    private final String URL = "jdbc:postgresql://localhost:5432/hitova";
    private final String USER = "postgres";
    private final String PASS = "post"; // ご自身のパスワードに変更

    // Beanがないため、Mapを使ってデータをまとめます
    public List<Map<String, String>> search(String keyword) throws Exception {
    	List<Map<String, String>> jobList = new ArrayList<>();
        
        // title（求人タイトル）にキーワードを含むものを検索
        String sql = "SELECT job_id, title, location, salary FROM jobs WHERE title LIKE ?";

        Class.forName("org.postgresql.Driver");

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // 前方一致・後方一致検索（%キーワード%）
            st.setString(1, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Map<String, String> job = new HashMap<>();
                job.put("id", String.valueOf(rs.getLong("job_id")));
                job.put("title", rs.getString("title"));
                job.put("location", rs.getString("location"));
                job.put("salary", rs.getString("salary"));
                jobList.add(job);
            }
        }
        return jobList;
    }
}