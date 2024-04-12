import java.util.Scanner;

import JDBC.DBConnect;
import JDBC.PlayVO;

public class Event {
	public static void main(String[] args) {
		
		DBConnect dao = new DBConnect();
		PlayVO vo = new PlayVO();
		
		Scanner scan = new Scanner(System.in);	
		
		System.out.print("이름을 입력해주세요: ");
		vo.setName(scan.next());
	
				
	}
	

}
