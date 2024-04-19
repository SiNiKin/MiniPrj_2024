package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.JdbcUtil;


public class DBConnect {

	private String url = "jdbc:mysql://localhost:3306/mpj?serverTimezone=Asia/Seoul";
	private String user = "root";
	private String password = "root1234";
	
	// 데이터 접속을 위한 객체  
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 생성자 - Connection 객체를 생성... 
	public DBConnect() {
		try {
			//드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Connection 객체 생성
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException ce) {
			System.out.println("드라이버 로드 실패");
			System.out.println(ce.getMessage());
		} catch (SQLException sqle) {
			System.out.println("SQL연동 실패");
			System.out.println(sqle.getMessage());
		}
	}
	
	//CRUD 메서드 
	
	// 1. 테이블 만드는 메서드
	public void createTable() {
		String sql = "create table if not exists play (\n"
				+ "	id INT(50) NOT NULL auto_increment,\n"
				+ "    name varchar(100) unique NOT NULL,\n"
				+ "    score int NOT NULL,\n"
				+ "    constraint play_pk primary key(id)\n"
				+ ")";
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL연동 실패");
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
	}
	
	// 2. 데이터 입력 메서드
	public void insert(String name, int score) {
		String sql = "insert into play (name, score) values(?,?) on duplicate key update score = ?";
		
		try {
			//Statement 객체 생성
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, score);
			pstmt.setInt(3, score);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL연동 실패");
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
	}
	
	// 3. 랭킹 출력 메서드
	public PlayVO allPlay() {
		PlayVO vo = null;
		String sql = "select id, name, score, rank() over (order by score asc) as ranking from play";
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int score = rs.getInt("score");
				int rank = rs.getInt("ranking");
				
				vo = new PlayVO(id, name, score, rank);
				
				System.out.println(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL연동 실패");
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		return vo;
	}
	
	

}