import java.util.Scanner;

import JDBC.DBConnect;

public class Menu {
   
   static Scanner scan = new Scanner(System.in);
   
   public static void main(String[] args) {
      
      while(true) {
         // 고객 메뉴 ui
         System.out.println("[[ PITCHER GAME ]]");
         System.out.println("게임을 시작하려면 S을, 재시작하려면 R, 끝내려면 E를 입력하세요.");
         System.out.print(">>");
         String menu = scan.next();
         menu =  menu.toLowerCase();
         
         EXIT: switch(menu.charAt(0)) {
         case 'ㄴ':
         case 's':
         case 'ㄱ':
         case 'r':
            System.out.println("게임을 시작합니다.");
            Event e = new Event();
            e.gameStart();
            System.out.print(">>");
            String reMenu = scan.next();
            switch(reMenu.charAt(0)) {
               case 'ㄷ': 
               case 'e':
                  break EXIT;
               default:
                  break;
            }  
         case 'ㄷ':
         case 'e':
            System.out.println("게임이 끝났습니다.");
            DBConnect dao = new DBConnect();
            dao.allPlay();
            
         }
         
      }
   }
}