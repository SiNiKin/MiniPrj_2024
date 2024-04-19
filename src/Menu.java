import java.util.Scanner;
import JDBC.DBConnect;

public class Menu {
   
   static Scanner sc = new Scanner(System.in);
   
   public static void main(String[] args) {
	DBConnect dao = new DBConnect();
	Event e = new Event(sc);
	
      while(true) {
         // 고객 메뉴 ui
         System.out.println("[[ PITCHER GAME ]]");
         System.out.println("-- 게임 시작 : S, 랭킹 : R, 게임 종료 : E --");
         System.out.print(">>");
         String menu = sc.next();
         menu =  menu.toLowerCase();
         
         switch(menu.charAt(0)) {
         	case 'ㄴ':
         	case 's':
         		System.out.println("게임을 시작합니다.");
			e.startGame();
         		break;
         	case 'ㄱ':
         	case 'r':
         		System.out.println("랭킹을 불러오겠습니다.");
         		dao.allPlay();
         		break;
         	case 'ㄷ':
         	case 'e':
         		System.out.println("게임이 끝났습니다.");
         		sc.close();
         		System.exit(0);
         	default:
         		System.out.println("잘못된 값을 입력하셨습니다. 다시 입력해주세요.");
         		break;
         } 
         
      }
   }
}
