package application;
	
import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.io.*;


public class Main extends Application {
	
	public Scene sceneEasy, sceneHard, highScoreEasy, highScoreHard, getAliasScene;
	public BorderPane rootEasy, rootHard, highScoreEasyPane, highScoreHardPane;
	public int flags = 0;
	public GameBoard gameBoard;
	public TableView<HighScore> scoreListEasy, scoreListHard;
	String name = "";
	
	
	@Override
	public void start(Stage primaryStage) {
				
		try {
			
			scoreListEasy = loadHighScores(Difficulty.Easy);
			scoreListHard = loadHighScores(Difficulty.Hard);
			
			gameBoard = new GameBoard();
			
			//Scene Easy game ↓--------------------------------------------------------------------------------------------------
			
			rootEasy = new BorderPane();
			rootEasy.setStyle("-fx-background-color: cornflowerblue ");
			
			sceneEasy = new Scene(rootEasy,400,400);
			
			Button easy1 = new Button("Easy");
			Button hard1 = new Button("Hard");
			Button highScore1 = new Button("Highs score");
			Label flagCount1 = new Label("Flags " + String.valueOf(gameBoard.numberOfFlags));
			Label time1 = new Label("Time: " + String.valueOf(gameBoard.timer));
			
			flagCount1.setStyle("-fx-font-size: 1.5em; -fx-text-fill: firebrick ");
			time1.setStyle("-fx-font-size: 1.5em");
			
			HBox hbEasy = new HBox(15);			
			hbEasy.getChildren().addAll(flagCount1, easy1, hard1, highScore1, time1);
			hbEasy.setPadding(new Insets(20, 20, 20, 20));
			hbEasy.setAlignment(Pos.CENTER);
			rootEasy.setTop(hbEasy);
			
			rootEasy.setCenter(gameBoard);
			
			easy1.setOnMouseClicked(e -> {
				gameBoard = new GameBoard(8, 8, 15, Difficulty.Easy);
				rootEasy.setCenter(gameBoard);
				primaryStage.setScene(sceneEasy);
				primaryStage.show();
			});
			
			hard1.setOnMouseClicked(e -> {
				gameBoard = new GameBoard(20,15, 40, Difficulty.Hard);
				rootHard.setCenter(gameBoard);
				primaryStage.setScene(sceneHard);
				primaryStage.show();
			});
			highScore1.setOnMouseClicked(e -> {
				primaryStage.setScene(highScoreEasy);
				primaryStage.show();
				
			});
			
			//Scene hard game ↓--------------------------------------------------------------------------------------------------------

			rootHard = new BorderPane();
			rootHard.setStyle("-fx-background-color: cornflowerblue");
			
			sceneHard = new Scene(rootHard, 800, 600);
			
			Button easy2 = new Button("Easy");
			Button hard2 = new Button("Hard");
			Button highScore2 = new Button("High score");
			Label flagCount2 = new Label("Flags " + String.valueOf(gameBoard.numberOfFlags));
			Label time2 = new Label("Time: " + String.valueOf(gameBoard.timer));
			
			flagCount2.setStyle("-fx-font-size: 1.5em; -fx-text-fill: firebrick ");
			time2.setStyle("-fx-font-size: 1.5em");
			
			HBox hbHard = new HBox(15);
			hbHard.getChildren().addAll(flagCount2, easy2, hard2, highScore2, time2);
			hbHard.setPadding(new Insets(20, 20, 20, 20));
			hbHard.setAlignment(Pos.CENTER);
			rootHard.setTop(hbHard);
			
			rootHard.setCenter(gameBoard);
			
			
			easy2.setOnMouseClicked(e -> {
				gameBoard = new GameBoard(8, 8, 15, Difficulty.Easy);
				rootEasy.setCenter(gameBoard);
				primaryStage.setScene(sceneEasy);
				primaryStage.show();
			});
			hard2.setOnMouseClicked(e -> {
				gameBoard = new GameBoard(20, 15, 40, Difficulty.Hard);
				rootHard.setCenter(gameBoard);
				primaryStage.setScene(sceneHard);
				primaryStage.show();
			});
			highScore2.setOnMouseClicked(e -> {
				primaryStage.setScene(highScoreHard);
				primaryStage.show();
			});
			
			//Scene highscore easy ↓--------------------------------------------------------------------------------------------------------
			
			highScoreEasyPane = new BorderPane();
			highScoreEasyPane.setStyle("-fx-background-color: cornflowerblue");
			
			Button backToNewGameEasy = new Button("New Game Easy");
			Button backToNewGameHard = new Button("New Game Hard");
			Button goToHighScoreHard = new Button("High score hard ->");
			Label highScoreEasyLabel = new Label("High score for easy");
			
			VBox vbEasy = new VBox(15);
			vbEasy.getChildren().addAll(highScoreEasyLabel, backToNewGameEasy, backToNewGameHard, goToHighScoreHard);
			vbEasy.setPadding(new Insets(20, 20, 20, 20));
			highScoreEasyPane.setRight(vbEasy);
			highScoreEasyPane.setCenter(scoreListEasy);
			
			highScoreEasy = new Scene(highScoreEasyPane, 400, 400);
			
			
			backToNewGameEasy.setOnMouseClicked(e -> {
				gameBoard = new GameBoard(8, 8, 15, Difficulty.Easy);
				rootEasy.setCenter(gameBoard);
				primaryStage.setScene(sceneEasy);
				primaryStage.show();
			});
			backToNewGameHard.setOnMouseClicked(e -> {
				gameBoard = new GameBoard(20, 15, 40, Difficulty.Hard);
				rootHard.setCenter(gameBoard);
				primaryStage.setScene(sceneHard);
				primaryStage.show();
			});
			goToHighScoreHard.setOnMouseClicked(e -> {
				primaryStage.setScene(highScoreHard);
				primaryStage.show();
			});
			
			//Scene highscore hard ↓---------------------------------------------------------------------------------------------------
			
			highScoreHardPane = new BorderPane();
			highScoreHardPane.setStyle("-fx-background-color: cornflowerblue");
			Button backToNewGameEasy2 = new Button("New Game Easy");
			Button backToNewGameHard2 = new Button("New Game Hard");
			Button goToHighScoreEasy = new Button("High score easy ->");
			Label highScoreHardLabel = new Label("High score for hard");
			
			highScoreHardPane.setCenter(scoreListHard);
			
			VBox vbHard = new VBox(15);
			vbHard.getChildren().addAll(highScoreHardLabel, backToNewGameEasy2, backToNewGameHard2, goToHighScoreEasy);
			vbHard.setPadding(new Insets(20, 20, 20, 20));
			highScoreHardPane.setRight(vbHard);
			
			highScoreHard = new Scene(highScoreHardPane, 400, 400);
			
			backToNewGameEasy2.setOnMouseClicked(e -> {
				gameBoard = new GameBoard(8, 8, 15, Difficulty.Easy);
				rootEasy.setCenter(gameBoard);
				primaryStage.setScene(sceneEasy);
				primaryStage.show();
			});
			backToNewGameHard2.setOnMouseClicked(e -> {
				gameBoard = new GameBoard(20, 15, 40, Difficulty.Hard);
				rootHard.setCenter(gameBoard);
				primaryStage.setScene(sceneHard);
				primaryStage.show();
			});
			goToHighScoreEasy.setOnMouseClicked(e -> {
				primaryStage.setScene(highScoreEasy);
				primaryStage.show();
			});
			
			//---------------------------------------------------------------------------------------------------					
			
			primaryStage.setTitle("Minesweeper");
			primaryStage.setScene(sceneEasy);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		 
		launch(args);
	}
	
	public TableView<HighScore> loadHighScores(Difficulty diff) {		
		
		TableView<HighScore> tv = new TableView<HighScore>();
		
		TableColumn<HighScore, Integer> score = new TableColumn<>("Score");
		score.setCellValueFactory(new PropertyValueFactory<>("time"));
		score.setSortType(TableColumn.SortType.DESCENDING);
		TableColumn<HighScore, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		tv.getColumns().add(score);
		tv.getColumns().add(name);
		
		String fileName = "";
		
		switch(diff) {
		case Easy:
			fileName = "C:\\Users\\Sebbe Java\\eclipse-workspace\\minesweeper3\\src\\application\\hsEasy.txt";
			break;
		case Hard:
			fileName = "C:\\Users\\Sebbe Java\\eclipse-workspace\\minesweeper3\\src\\application\\hsHard.txt";
			break;
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
			String line = "";
			while(br.readLine() != null) {
				line = br.readLine().trim();
				String[] lineSplit = line.split(" ");
				tv.getItems().add(new HighScore(Integer.parseInt(lineSplit[0]), lineSplit[1]));
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tv;
		
	}
	
	public void saveHighScore(Difficulty diff, int time, String name) {
		switch(diff) {
		case Easy:
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter("hsEasy.txt"))){
				bw.append(new HighScore(time, name).toString());
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
		case Hard:
			try(BufferedWriter bw = new BufferedWriter(new FileWriter("hsHard.txt"))){
				bw.append(new HighScore(time, name).toString());
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
		}
	}
	
	public String getAlias() {
		
		Pane pane = new Pane();
		Label enterName = new Label("Please enter your name!");
		TextField text = new TextField();
		text.setPromptText("Enter name here");
		Button enter = new Button("OK!");
		
		enter.setOnMouseClicked(e -> {
			name = text.getText().toString();
			text.clear();
		});
		
		VBox vbox = new VBox();
		HBox hbox = new HBox();
		
		hbox.getChildren().addAll(text, enter);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		vbox.getChildren().addAll(enterName, hbox);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		vbox.setAlignment(Pos.CENTER_LEFT);
		
		pane.getChildren().add(vbox);
		
		Stage stage = new Stage();
		getAliasScene= new Scene(pane, 200, 200);
		
		stage.setScene(getAliasScene);
		stage.show();
		
		return name;
	}
}
