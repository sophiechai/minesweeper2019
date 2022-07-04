public class User5 {
	private String name = "";
	private int score ;
	public User5() {
		name = "Anonymous";
		score = 999;
		//System.out.println("into Constructor. name score " + name + " " + score);
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
}