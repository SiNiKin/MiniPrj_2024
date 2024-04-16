package JDBC;

public class PlayVO {

	private int id;
	private String name;
	private int score;
	private int ranking;
	
	public PlayVO() {}
	
	public PlayVO(int id, String name, int score, int ranking) {
		this.id = id;
		this.name = name;
		this.score = score;
		this.ranking = ranking;
	}

	public int getRanking() {
		return ranking;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "id : "+id+"\t| player : "+name+"\t| score : "+ score + "\t| rank : " + ranking;
	}
	
}
