package application;

import java.io.*;
import java.util.*;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

public class GameBoard extends Pane implements Runnable{
	
	public Tile[][] tile;
	public boolean isStopped;
	public List<Tile> minesList = new ArrayList<>();
	public int numberOfMines = 0;
	public int numberOfFlags = 0;
	public boolean isStarted;
	public int timer = 0;
	public Thread thread;
	public Difficulty difficulty;
	public Main main;
	public GridPane pane = new GridPane();

	
	public GameBoard() {
		this(8, 8, 15, Difficulty.Easy);
	}
	
	public GameBoard(int x, int y, int mines, Difficulty difficulty) {
		isStopped = true;
		this.difficulty = difficulty;
		drawBoard(x, y, mines);
		
	}
	
	public void drawBoard(int width, int height, int mines) {
		
		tile = new Tile[width][height];
		
		Set<Integer> mineSet = getMines(height * width, mines);
		List<Integer> tempListMine = new ArrayList<>();
		tempListMine.addAll(mineSet);
		Collections.sort(tempListMine);
		
		int count = 1;
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j< height; j++) {
				
				Tile t = new Tile();
				t.x = i; 
				t.y = j;
				tile[i][j] = t;
				
				
				if(tempListMine.contains(count)) {
					t.isMine = true;
					minesList.add(t);
				}
				count++;
				
				t.setStyle("-fx-background-color: gainsboro; -fx-border-color: darkgray");
				t.setMinWidth(30);
				t.setMaxWidth(30);
				t.setPrefWidth(30);
				
				t.setMinHeight(30);
				t.setMaxHeight(30);
				t.setPrefHeight(30);

				t.setMinSize(30, 30);
				t.setMaxSize(30, 30);
				t.setPrefSize(30, 30);
				
				t.setOnMouseClicked(e -> {
					if(e.getButton() == MouseButton.SECONDARY) {
						if(isStopped && !isStarted) {
							isStarted = true;
							isStopped = false;
							thread.run();
						}
							
						if(isStopped || t.isOpen) return;
						
						if(t.isFlag){
							t.isFlag = false;
							numberOfFlags--;
						 	t.setStyle("-fx-background-color: gainsboro; -fx-border-color: darkgray");
						 	
							return;
						}
						if(!t.isFlag) {
							t.isFlag = true;
							t.setStyle("-fx-background-color: palegreen ;  -fx-border-color: darkgray");
							numberOfFlags++;
						 	
							if(numberOfFlags == numberOfMines) {
								if(checkIfWin()) {
									win();
								}
							}
						 	return;
						}
						
						
					}
					if(e.getButton() == MouseButton.PRIMARY) {
						if(isStopped && !isStarted) {
							isStarted = true;
							isStopped = false;
							thread.run();
						}
						if(isStopped || t.isOpen || t.isFlag ) return;
						
						if(t.isMine) {
							t.setStyle("-fx-background-color: black");
							gameOver();
							return;
						}
						
						t.mineNeighbors = countNeighborTilesWithMines(t);
						
						if(t.mineNeighbors > 0) {
							t.isOpen = true;
							String numberOfNeighbors = String.valueOf(t.mineNeighbors);
							t.setText(String.valueOf(t.mineNeighbors));
							t.setStyle("-fx-background-color: red;  -fx-border-color: darkgray");
						}
						else {
						checkForNearestCellWithValue(t);
						return;
						}	
					}
				});
				pane.getChildren().addAll(t);
			}
			
		}
	}
	

	public void checkForNearestCellWithValue(Tile t) {
		
		for(int i = t.x; i < tile.length; i++) {
			for(int j = t.y; j < tile[i].length; j++) {
				
				if(t.isMine || t.isOpen) break;
				
				if (!tile[i][j].isMine && tile[i][j].mineNeighbors == 0 && !t.isOpen) {
					t.isOpen = true;
					t.setStyle("-fx-background-color: red;  -fx-border-color: darkgray");
				}
				if(!tile[i][j].isMine && tile[i][j].mineNeighbors > 0 && !t.isOpen) {
					t.isOpen = true;
					t.setText(String.valueOf(t.mineNeighbors));
					t.setStyle("-fx-background-color: red;  -fx-border-color: darkgray");
					break;
				}
			}
		}
		
		for(int i = t.x; i >= 0; i--) {
			for(int j = t.y; j >= 0; j--) {
				
				if(t.isMine || t.isOpen) break;
				
				if (!tile[i][j].isMine && tile[i][j].mineNeighbors == 0 && !t.isOpen) {
					t.isOpen = true;
					t.setStyle("-fx-background-color: red;  -fx-border-color: darkgray");
				}
				if(!tile[i][j].isMine && tile[i][j].mineNeighbors > 0 && !t.isOpen) {
					t.isOpen = true;
					t.setText(String.valueOf(t.mineNeighbors));
					t.setStyle("-fx-background-color: red;  -fx-border-color: darkgray");
					break;
				}
			}
		}
			
	}
	
	public int countNeighborTilesWithMines(Tile t) {
		int mineCount = 0;
		
		for(int i = t.x - 1; i <= t.x + 1; i++) {
			for (int j = t.y - 1; j <= t.y + 1; j++) {
				if( j < 0 || j >= tile.length || i < 0 || i >= tile[j].length){
					continue;
					}
				if(tile[i][j] == t) {
					continue;
				}
				if(tile[i][j].isMine) {
					mineCount++;
				}
			}
		}
		return mineCount;
	}
	
	public Set<Integer> getMines(int totalTiles, int mines) {
		
		Set<Integer> mineSet = new HashSet<>();
		
		Random rand = new Random();
		while(mineSet.size() < mines) {
			mineSet.add(rand.nextInt(totalTiles));
		}
		return mineSet;
	}
	
	public void showAllMines() {
		
		for(Tile t : minesList) {
			if(t.isMine && t.isFlag) {
				t.setStyle("-fx-background-color: black; -fx-border-color: gold");
				continue;
			}
			if(t.isMine) {
				t.setStyle("-fx-background-color: black");
			}
		}
		
	}
	
	public void gameOver() {
		isStopped = true;
		showAllMines();		
	}
	
	public void win() {
		isStopped = true;
		thread.interrupt();
		
		main.saveHighScore(difficulty, timer, main.getAlias());
	}
	
	public boolean checkIfWin() {
			
			for(Tile t : minesList) {
							
				if(!t.isFlag) {
				return false;
				}
			
		}
			for(int i = 0; i < tile.length; i++) {
				for (int j = 0; j < tile[i].length; j++) {
					if(!tile[i][j].isOpen && !tile[i][j].isMine) {
						return false;
					}
				}
			}
		return true;
	}
	
	@Override
	public void run() {
		try {		
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			}
		timer++;
	}

}
