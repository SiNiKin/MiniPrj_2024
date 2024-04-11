package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DBConnect {

	private String url = "jdbc:mysql://localhost:3306/testdb?serverTimezone=Asia/Seoul";
	private String user = "root";
	private String password = "root1234";
	
	// 데이터 접속을 위한 객체 
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	// 생성자 - Connection 객체를 생성... 
	public DBConnect() {
		try {
			//드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Connection 객체 생성
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connection 객체 생성!!");
		} catch (ClassNotFoundException ce) {
			System.out.println("드라이버 로드 실패");
			System.out.println(ce.getMessage());
		} catch (SQLException sqle) {
			System.out.println("SQL연동 실패");
			System.out.println(sqle.getMessage());
		}
	}
	
	//CRUD 메서드 
	// 1. 데이터 입력 메서드
	public int insert(PlayVO vo) {
		int result = 0;
		
		String sql = "insert into Play (name, score) "
				+ "values('"+vo.getName()+"' ,"
				+vo.getScore()+"')";
		
		try {
			//Statement 객체 생성
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("SQL 연동 에러");
			System.out.println(e.getMessage());
		} finally {
			try {
				if(stmt != null) stmt.close();
			} catch (Exception e2) {}
		}
		
		return result;
	}
	
	// 2. 정보 출력 메서드
	// 전체 테이블 정보 출력
	public List<PlayVO> allPlay() {
		List<PlayVO> list = new ArrayList();
		
		String sql = "select * from Play";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String Name = rs.getString("name");
				int score = rs.getInt("score");
				
				PlayVO vo = new PlayVO(Name, score);
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("SQL연동 실패");
			System.out.println(e.getMessage());
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(rs != null) rs.close();
			} catch (Exception e2) {}
		}
		
		
		return list;
	}
	
	// 3. 정보 수정 메서드 
	public int updatePlay(PlayVO vo) {
		int result = 0;
		
		String sql = "update Play set score = '"+vo.getScore()
						+"' where name = " + vo.getId();
		
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("SQL연동 실패");
			System.out.println(e.getMessage());
		} finally {
			try {
				if(stmt != null) stmt.close();
			} catch (Exception e2) {}
		}
		
		return result;
	}
	

}
