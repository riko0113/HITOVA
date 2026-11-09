
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.PortfolioAdd;

public class portfolioDAO {

    // DBに接続する処理
    private Connection getConnection()
            throws NamingException, SQLException {

        InitialContext context = new InitialContext();

        DataSource ds = (DataSource) context.lookup(
                "java:comp/env/jdbc/hitova");

        return ds.getConnection();
    }

    // 作品を登録する処理
    public int insert(PortfolioAdd portfolio)
            throws NamingException, SQLException {

        String sql = "INSERT INTO portfolios "
                + "(work_name, description, file_url, candidate_account_id) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, portfolio.getWorkName());
            ps.setString(2, portfolio.getDescription());
            ps.setString(3, portfolio.getFileUrl());
            ps.setLong(4, portfolio.getCandidateAccountId());

            return ps.executeUpdate();
        }
    }
}
