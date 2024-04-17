import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import JDBC.DBConnect;
import JDBC.PlayVO;

public class Event {
   Scanner scan;
   DBConnect dao ;
   PlayVO vo ;
   
   public Event(Scanner sc) {
      this.scan = sc;
      this.dao= new DBConnect();
      this.vo = new PlayVO();
      
      startGame();
   }
   
   public void startGame(){
      System.out.print("사용자 이름을 입력하세요: ");
      String playerName = scan.next();
      scan.nextLine();

      List<PlayVO> playerList = dao.playerList();
      boolean playerData = checkPlayer(playerList, playerName);
      List<Integer> comList = randomList();
      int save = playGame(comList);
      gameResult(playerData, playerName, save);

   }

   // 사용자 이름으로 데이터 조회
   public boolean checkPlayer(List<PlayVO> playerList, String playerName) {
      for (PlayVO player : playerList) {
         if (player.getName().equals(playerName)) {
            System.out.println("기존 플레이어입니다.");
            return true;
         }
      }
      return false;
   }

   // 랜덤한 3자리 수 생성
   public List<Integer> randomList() {
      List<Integer> comList = new ArrayList<>();
      while (comList.size() < 3) {
         int num = (int) (Math.random() * 9) + 1;
         if (!comList.contains(num)) {
            comList.add(num);
         }
      }
      return comList;
   }

   // 사용자한테 3자리수 입력값 받기
   public int playGame(List<Integer> comList) {
      int save = 0;
      
      while (true) {
         System.out.print("3자리 수를 입력해주세요 (예: 123): ");
         String userInput = scan.nextLine();
         if (userInput.length() != 3) {
            System.out.println("3자리 숫자를 입력하세요.");
            continue;
         }

         if (userInput.charAt(0) == userInput.charAt(1) || userInput.charAt(1) == userInput.charAt(2)
               || userInput.charAt(0) == userInput.charAt(2)) {
            System.out.println("중복된 숫자가 있습니다. 다시 입력해주세요.");
            continue;
         }

         int[] userNumber = new int[3];
         for (int i = 0; i < 3; i++) {
            // getNumericValue() 메서드 : 주어진 문자에 해당하는 정수 값을 반환
            userNumber[i] = Character.getNumericValue(userInput.charAt(i));
         }

         save++;

         // B,S,O 나누기
         int strikes = 0, balls = 0, outs = 0;
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               if (userNumber[i] == comList.get(j)) {
                  if (i == j) {
                     strikes++;
                  } else {
                     balls++;
                  }
               }
            }
         }
         outs = 3 - (strikes + balls);

         // 게임 결과 출력
         if (strikes == 3) {
            System.out.println("3Strike 입니다!! " + save + "번 만에 맞추셨습니다.");
            break;
         } else {
            System.out.println(strikes + "S " + balls + "B " + outs + "O");
         }
      }

      return save;
   }

   // 기존 플레이어일 경우 점수 업데이트
   public void gameResult(boolean playerData, String playerName, int save) {
      if (playerData) {
         dao.updatePlay(save, playerName);
      } else {
         dao.insert(playerName, save);
      }
   }
}