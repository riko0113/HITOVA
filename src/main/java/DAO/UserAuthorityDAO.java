package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.UserAuthority;


public class UserAuthorityDAO extends DAO{
	//求職者の一覧を取得する
	public List<UserAuthority> findAllUsers() throws Exception{
		List<UserAuthority> userList = new ArrayList();
		
		String sql = 
				"SELECT j.candidate_id," +
				"p.last_name, p.first_name, " +
				"j.account_status_id, " +
				"j.authority_id, a.authority_name " +
				"FROM job_seeker_accounts j " +
				"LEFT JOIN profiles p " +
				"ON j.candidate_id = p.candidate_account_id " +
				
	}

}
