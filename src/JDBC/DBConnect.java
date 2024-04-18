package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	// 1. 테이블 만드는 메서드
	public void createTable() {
		String sql = "create table if not exists play (\n"
				+ "	id INT(50) NOT NULL auto_increment,\n"
				+ "    name varchar(100) NOT NULL,\n"
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
		String sql = "insert into play (name, score) values(?,?)";
		
		try {
			//Statement 객체 생성
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, score);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL연동 실패");
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
	}
	
	// 3. 데이터 조회 메서드
	public ArrayList<PlayVO> playerList() {
		ArrayList<PlayVO> list = new ArrayList<>();
		String sql = "select * from play";
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int score = rs.getInt("score");
				
				PlayVO vo = new PlayVO(id, name, score, 0);
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL연동 실패");
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	// 4. 중복 확인 메서드
	
	public int nameConfirm(String name) {
		int result = 0;
		
		String sql = "select * from play where name = ?";
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = 1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		return result;
	}
	
	// 5. 랭킹 출력 메서드
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
	
	// 6. 정보 수정 메서드 
	public void updatePlay(int score, String name) {
		
		String sql = "update Play set score =? where name =?";
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, score);
			pstmt.setString(2, name);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL연동 실패");
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
	}
	

}
