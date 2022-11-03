package application;

public class HighScore {
	int time;
	String name;
	
	public HighScore() {
		this.time = 0;
		this.name = "";
	}
	
	public HighScore(int fastTime, String name) {
		this.time = fastTime;
		this.name = name;
	}

	public int getHighScore() {
		return time;
	}

	public void setHighScore(int highScore) {
		this.time = highScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return time + " " + name + "\n";
		
	}

	
	

}
