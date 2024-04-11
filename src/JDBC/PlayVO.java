package JDBC;

public class PlayVO {

	private int id;
	private String name;
	private int score;
	
	public PlayVO() {}

	public PlayVO(String name, int score) {
		this.name = name;
		this.score = score;
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
		return "[id : "+id+", name : "+name+", score : "+score + "]";
	}
	
}
