import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import JDBC.DBConnect;
import JDBC.PlayVO;

public class Event {
	public static void main(String[] args) {
		DBConnect dao = new DBConnect();
		PlayVO vo = new PlayVO();

		Scanner scan = new Scanner(System.in);
		System.out.print("사용자 이름을 입력하세요: ");
		String playerName = scan.next();
		scan.nextLine();

		// 사용자 이름으로 데이터 조회
		List<PlayVO> playerList = dao.allPlay();
		boolean playerData = false;
		for (PlayVO player : playerList) {
			if (player.getName().equals(playerName)) {
				playerData = true;
				System.out.println("기존 회원입니다.");
				break;
			}
		}

		// 랜덤한 3자리 수 생성
		List<Integer> comList = new ArrayList<>();
		while (comList.size() < 3) {
			int num = (int) (Math.random() * 9) + 1;
			if (!comList.contains(num)) {
				comList.add(num);
			}
		}

		int save = 0;
		while (true) {
			System.out.print("3자리 수를 입력해주세요 (예: 123): ");
			String userInput = scan.nextLine();
			if (userInput.length() != 3) {
				System.out.println("3자리 숫자를 입력하세요.");
				continue;
			}

			if (userInput.charAt(0) == userInput.charAt(1) || userInput.charAt(1) == userInput.charAt(2) || userInput.charAt(0) == userInput.charAt(2)) {
				System.out.println("중복된 숫자가 있습니다. 다시 입력해주세요.");
				continue;
			}

			int[] userNumber = new int[3];
			for (int i = 0; i < 3; i++) {
				//getNumericValue() 메서드 : 주어진 문자에 해당하는 정수 값을 반환
			    userNumber[i] = Character.getNumericValue(userInput.charAt(i));
			}

			save++;
			
			// B,S,O 나누기
			int strikes = 0, balls = 0;
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

			// 게임 결과 출력
			if (strikes == 3) {
				System.out.println("3Strike 입니다!! " + save + "번 만에 맞추셨습니다.");
				if (playerData) {
					// 기존 점수 업데이트
					dao.updatePlay(new PlayVO(playerName, save));
				} else {
					dao.insert(new PlayVO(playerName, save));
				}
				break;
			} else {
				System.out.println(balls + "B " + strikes + "S ");
			}
		}

		scan.close();
	}
}
