package application;

import javafx.scene.control.Button;

public class Tile extends Button{
	public int x;
	public int y;
	public boolean isFlag;
	public boolean isOpen;
	public boolean isMine;
	public int mineNeighbors;
	
	public Tile() {
		
	}
	
	public Tile(int x, int y, boolean isMine) {
		this.x = x;
		this.y = y;
		this.isMine = isMine;
	}

}
