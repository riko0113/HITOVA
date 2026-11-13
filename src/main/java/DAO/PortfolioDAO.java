
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Portfolio;

public class PortfolioDAO extends DAO{

    // 作品を登録する処理
	public int insert(Long userId, String workName, String description, String fileUrl) throws Exception {
        
        // SQL文の定義（プレースホルダーを使用し、SQLインジェクションを防止）
        String sql = "INSERT INTO portfolios (work_name, description, file_url, candidate_account_id) VALUES (?, ?, ?, ?)";
        
        int rowCount = 0;

        // try-with-resources構文を用いて、ConnectionやPreparedStatementを確実にクローズする
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // プレースホルダーに値をセット
            st.setString(1, workName);
            st.setString(2, description);
            st.setString(3, fileUrl);
            st.setLong(4, userId);
            
            // INSERT文を実行し、影響を受けた行数を取得
            rowCount = st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 呼び出し元（Actionクラス）のtry-catchで捕捉できるように再スロー
        }
        
        return rowCount;
    }
    
	// 作品を更新する処理
    public int update(Long portfolioId, Long userId, String workName, String description, String fileUrl) throws Exception {
        
        // SQL文の定義（WHERE句でportfolio_idとcandidate_account_idを指定し、他人のデータを書き換えられないようにする）
        String sql = "UPDATE portfolios SET work_name = ?, description = ?, file_url = ? WHERE portfolio_id = ? AND candidate_account_id = ?";
        
        int rowCount = 0;

        // try-with-resources構文を用いて、ConnectionやPreparedStatementを確実にクローズする
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // プレースホルダーに値をセット
            st.setString(1, workName);
            st.setString(2, description);
            st.setString(3, fileUrl);
            st.setLong(4, portfolioId);
            st.setLong(5, userId);
            
            // UPDATE文を実行し、影響を受けた行数を取得
            rowCount = st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 呼び出し元（Actionクラス）のtry-catchで捕捉できるように再スロー
        }
        
        return rowCount;
    }
	
    //一覧を取得する処理
    public List<Portfolio> searchList(Long userId) throws Exception {
        List<Portfolio> list = new ArrayList<>();
        
        // 取得するカラムをすべて明記したSQL
        String sql = "SELECT " +
                "  portfolio_id, work_name, description, file_url, candidate_account_id " +
                "FROM portfolios " +
                "WHERE candidate_account_id = ?";

        // try-with-resources構文でコネクションを自動クローズ
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // 求職者アカウントIDをセット
            st.setLong(1, userId);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Portfolio portfolio = new Portfolio();
                    
                    // Portfolio Beanにセット
                    portfolio.setPortfolio_id(rs.getLong("portfolio_id"));
                    portfolio.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    portfolio.setWork_name(rs.getString("work_name"));
                    portfolio.setDescription(rs.getString("description"));
                    portfolio.setFile_url(rs.getString("file_url"));
                    
                    list.add(portfolio);
                }
            }
        }
        return list;
    }
    
    //作品詳細を取得する処理
    public Portfolio searchDetail(Long portfolioId) throws Exception {
        Portfolio portfolio = null;
        
        // 取得するカラムをすべて明記したSQL
        String sql = "SELECT " +
                "  portfolio_id, work_name, description, file_url, candidate_account_id " +
                "FROM portfolios " +
                "WHERE portfolio_id = ?";

        // try-with-resources構文でコネクションを自動クローズ
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            
            // ポートフォリオIDをセット
            st.setLong(1, portfolioId);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) { 
                    portfolio = new Portfolio();
                    
                    // Portfolio Beanにセット
                    portfolio.setPortfolio_id(rs.getLong("portfolio_id"));
                    portfolio.setCandidate_account_id(rs.getLong("candidate_account_id"));
                    portfolio.setWork_name(rs.getString("work_name"));
                    portfolio.setDescription(rs.getString("description"));
                    portfolio.setFile_url(rs.getString("file_url"));
                }
            }
        }
        return portfolio;
    }
    
    // 作品を削除する処理
    public int delete(Long portfolioId, Long userId) throws Exception {
        String sql = "DELETE FROM portfolios WHERE portfolio_id = ? AND candidate_account_id = ?";
        int rowCount = 0;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
        	
        	// プレースホルダーに値をセット
            st.setLong(1, portfolioId);
            st.setLong(2, userId);
            
            // DELETE文を実行し、影響を受けた行数を取得
            rowCount = st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return rowCount;
    }
}
