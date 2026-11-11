package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Job;

public class JobSearchDAO extends DAO { // DAOを継承

    public List<Job> searchJobs(String keyword) throws Exception {
        List<Job> list = new ArrayList<>();

        String sql = "SELECT * FROM jobs WHERE title LIKE ? OR location LIKE ?";

        // JNDIの接続プールを利用
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, "%" + keyword + "%");
            st.setString(2, "%" + keyword + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Job job = new Job();
                    
                    job.setJob_id(rs.getLong("job_id")); 
                    job.setTitle(rs.getString("title"));
                    job.setLocation(rs.getString("location"));
                    job.setSalary(rs.getString("salary"));
                    job.setaccess(rs.getString("access"));
                    
                    list.add(job);
                }
            }
        }
        return list;
    }
}