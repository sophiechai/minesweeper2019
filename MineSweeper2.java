import javafx.animation.KeyFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MineSweeper2 extends Application{
	static Timer timer;
	StackPane spane;
	private static GridPane gpane;
	private static Timeline animation;
	private static ImageView smile;
	private static NoMine noMine;
	static int click = 0;

	private static GridButton b;
	static int gridX = 8;
	static int gridY = 8;
	static int mine = 10;
	static GridButton[][] buttons;// =  new GridButton[gridX][gridY];
	Scene scene1, scene2;
	static Scene scene3, scene4, scene5;
	private static ArrayList<User5> list = new 	ArrayList<>();
	private static int diff = 0;
	private static int time;
	private static	File file = new File("res/mineScores.txt");	
	static int statusBomb=0;
	static RadioMenuItem m1 ;//= new RadioMenuItem("Beginner"); 
	static RadioMenuItem m2;//= new RadioMenuItem("Intermediate"); 
	static RadioMenuItem m3;//= new RadioMenuItem("Expert"); 
	static RadioMenuItem m4;//= new RadioMenuItem("Custom"); 


	public static void main(String[] args) {		
		launch(args);
	}

	public void start(Stage priStage) {
		buttons =  new GridButton[gridX][gridY];
		Menu m = new Menu("Game"); 
		Menu help = new Menu("Help");
		VBox vb = new VBox();

		MenuItem m0 = new MenuItem("New"); 
		 m1 = new RadioMenuItem("Beginner"); 
		 m2 = new RadioMenuItem("Intermediate"); 
		 m3 = new RadioMenuItem("Expert"); 
		m4 = new RadioMenuItem("Custom"); 
		ToggleGroup group1 = new ToggleGroup();
		m1.setToggleGroup(group1);
		m2.setToggleGroup(group1);
		m3.setToggleGroup(group1);
		m4.setToggleGroup(group1);

		MenuItem m5 = new MenuItem("Best Times..."); 
		MenuItem m6 = new MenuItem("Exit"); 

		m.getItems().addAll(m0,new SeparatorMenuItem(), m1,m2,m3,m4,new SeparatorMenuItem(),m5,new SeparatorMenuItem(),m6); 
		MenuBar mb = new MenuBar(); 
		mb.getMenus().addAll(m,help); 
		//setDifficulty(0);
		m0.setOnAction(e -> {
			animation.stop();
			start(priStage);
		});

		m1.setOnAction(e -> {
			setDifficulty(0);
			animation.stop();
			start(priStage);		
		});
		m2.setOnAction(e -> {

			setDifficulty(1);
			animation.stop();
			start(priStage);

		});
		m3.setOnAction(e -> {
			setDifficulty(2);
			animation.stop();
			start(priStage);
		});
		m4.setOnAction(e -> {	
			animation.stop();
			start4(new Stage(),priStage);
		});
		m5.setOnAction(e -> start3(new Stage()));
		m6.setOnAction(e -> priStage.close());

		statusBomb = 0;
		list.add(new User5());
		list.add(new User5());
		list.add(new User5());
		try {
			Scanner input = new Scanner(file);
			for(int i = 0; i < 3; i++) {
				if(input.hasNext()) {
					list.get(i).setScore(input.nextInt());
					list.get(i).setName(input.next());
				}
			}
			input.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		click = 0;
		//SECOND SCENE
		gpane = new GridPane();
		BorderPane bpane = new BorderPane();
		spane = new StackPane();
		timer = new Timer();
		noMine = new NoMine(mine);		
		//Design
		spane.setAlignment(Pos.CENTER);
		timer.setAlignment(Pos.CENTER_RIGHT);
		noMine.setAlignment(Pos.CENTER_LEFT);	
		timer.setStyle("-fx-border-color:  #787878 #fafafa #fafafa #787878; -fx-border-width: 2; -fx-border-radius: 0.001;");
		noMine.setStyle("-fx-border-color:  #787878 #fafafa #fafafa #787878; -fx-border-width: 2; -fx-border-radius: 0.001;");

		smile = new ImageView(new Image("File:res/face-smile.png"));
		smile.setPreserveRatio(true);
		smile.setFitHeight(40);		
		spane.setStyle("-fx-background-color: #bfbfbf; -fx-border-color: #787878 #fafafa #fafafa #787878; -fx-border-width: 3; -fx-border-radius: 0.001;");	
		gpane.setStyle("-fx-border-color:  #787878 #fafafa #fafafa #787878; -fx-border-width: 3; -fx-border-radius: 0.001;");

		smile.setOnMouseClicked(e -> {
			//priStage.close();
			animation.stop();
			start(priStage);
		});

		gpane.setAlignment(Pos.CENTER);
		//gpane.setPadding(new Insets(5));

		//Add panes
		bpane.setStyle("-fx-background-color: #bfbfbf;-fx-border-color: #fafafa #787878 #787878 #fafafa; -fx-border-width: 3; -fx-border-radius: 0.001;");
		bpane.setPadding(new Insets(10));

		bpane.setTop(spane);
		bpane.setCenter(gpane);
		//hpane.setSpacing(50);
		spane.getChildren().addAll(noMine, timer, smile);
		vb.getChildren().addAll(mb,bpane);
		animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {	
			timer.increment();
			timer.update();
		}));
		//Set mine position and buttons image
		for(int i = 0; i < buttons.length; i++)
			for(int j = 0; j < buttons[i].length; j++) {
				//System.out.println("placeImage is called!");
				b = new GridButton(i, j);
				b.clickMouse(animation, noMine,smile);
				buttons[i][j] = b;
			}
		//randomPlace();
		placeImage();		
		animation.setCycleCount(animation.INDEFINITE);
		scene2 = new Scene(vb);

		priStage.getIcons().add(new Image("File:res/icon.png"));
		priStage.setTitle("MineSweeper");
		priStage.setResizable(false);
		priStage.setScene(scene2);
		priStage.show();

	}
	public static void start2(Stage priStage2) {

		Label label1 = new Label("You have fastest time for beginner level. \n\t\t Please enter your name: ");
		label1.setStyle( "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #333333; -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
		TextField textField = new TextField ();
		textField.setMaxWidth(100);
		textField.setMaxHeight(50);
		VBox vb = new VBox();
		Button enter = new Button("OK");
		enter.setStyle("-fx-text-fill: white;-fx-font-weight: bold; -fx-background-color: linear-gradient(#61a2b1, #2A5058);-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		//Click Event
		enter.setOnMouseClicked(e -> {
			String name = textField.getText();
			list.get(diff).setName(name);
			start3(priStage2);
		}); 
		vb.getChildren().addAll(label1, textField, enter);
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(10));
		vb.setSpacing(20);
		scene3 = new Scene(vb, 350, 150);

		priStage2.setTitle("Congratulations!!");
		priStage2.setScene(scene3);
		priStage2.show();
	}

	public static  void start3(Stage priStage3) {
		HBox hb = new HBox();
		VBox vb = new VBox();
		///BorderPane bp = new BorderPane();
		try {
			PrintWriter write = new PrintWriter(file);
			for(int i = 0; i < 3; i++) {
				write.println(list.get(i).getScore() + " " + list.get(i).getName());						
			}
			write.close();			
		}

		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		Label labelA =  new Label(getHighScore(0));
		Label labelB = new Label(getHighScore(1));
		Label labelC = new Label(getHighScore(2));
		Button enter = new Button("OK");		

		labelA.setStyle("-fx-font-size: 12px;");
		enter.setStyle("-fx-text-fill: white;-fx-font-weight: bold; -fx-background-color: linear-gradient(#61a2b1, #2A5058);-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		Button reset = new Button("Reset");		
		reset.setStyle("-fx-text-fill: white;-fx-font-weight: bold; -fx-background-color: linear-gradient(#61a2b1, #2A5058);-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");

		//Click Event
		enter.setOnMouseClicked(e -> priStage3.close());
		reset.setOnMouseClicked(e -> {
			reset();
			labelA.setText("BEGINNER\tAnonymous\t999");
			labelB.setText("INTERMEDIATE\tAnonymous\t999");
			labelC.setText("EXPERT\tAnonymous\t999");
		}); 
		hb.getChildren().addAll(enter,reset);
		vb.getChildren().addAll(labelA,labelB,labelC,hb);
		vb.setSpacing(10);

		hb.setAlignment(Pos.CENTER);
		vb.setAlignment(Pos.CENTER);
		hb.setSpacing(10);

		scene4 = new Scene(vb, 350, 150);
		priStage3.setTitle("Fastest MineSweepers");
		priStage3.setScene(scene4);
		priStage3.show();
	}
	public  void start4(Stage priStage4, Stage previousStage) {
		VBox vb = new VBox();
		VBox vb2 = new VBox();
		BorderPane bp = new BorderPane();
		Label label1 = new Label("Height:");
		Label label2 = new Label("Width: ");
		Label label3 = new Label("Mines: ");
		TextField textField1 = new TextField ();
		TextField textField2 = new TextField ();
		TextField textField3 = new TextField ();
		textField1.setStyle("-fx-max-width: 50;-fx-max-height: 50;");
		textField2.setStyle("-fx-max-width: 50;-fx-max-height: 50;");
		textField3.setStyle("-fx-max-width: 50;-fx-max-height: 50;");

		HBox hb1 = new HBox();
		HBox hb2 = new HBox();
		HBox hb3 = new HBox();
		hb1.getChildren().addAll(label1, textField1);
		hb2.getChildren().addAll(label2, textField2);
		hb3.getChildren().addAll(label3, textField3);
		hb1.setSpacing(10);
		hb2.setSpacing(10);
		hb3.setSpacing(10);
		vb.getChildren().addAll(hb1,hb2,hb3);
		vb.setSpacing(10);
		vb.setAlignment(Pos.CENTER_LEFT);

		Button ok = new Button("OK");
		Button cancel = new Button("Cancel");
		ok.setStyle("-fx-text-fill: white;-fx-font-weight: bold; -fx-background-color: linear-gradient(#61a2b1, #2A5058);-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		cancel.setStyle("-fx-text-fill: white;-fx-font-weight: bold; -fx-background-color: linear-gradient(#61a2b1, #2A5058);-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
		vb2.getChildren().addAll(ok,cancel);
		vb2.setSpacing(20);
		vb2.setAlignment(Pos.CENTER);
		vb2.setPadding(new Insets(20));

		ok.setOnMouseClicked(e -> {
			gridY = Integer.parseInt(textField1.getText());		
			gridX = Integer.parseInt(textField2.getText());
			mine = Integer.parseInt(textField3.getText());
			if(gridY > 24) gridY = 24;
			if(gridX > 30) gridX = 30;
			int total = gridX * gridY;
			if(mine >= (total/2) + 5)
				mine = (total/2) + 5;
			start(new Stage());
			priStage4.close();
			previousStage.close();

		}); 
		cancel.setOnMouseClicked(e -> priStage4.close());

		bp.setLeft(vb);
		bp.setRight(vb2);
		bp.setPadding(new Insets(20));
		scene5 = new Scene(bp, 250, 150);
		priStage4.setTitle("Custom Field");
		priStage4.setScene(scene5);
		priStage4.show();

	}

	public static boolean highScore(int diff2, String name, int score) {	
		if(list.get(diff2).getScore() > score) {
			list.get(diff2).setScore(score);
			return true;
		}	
		else return false;

	}
	public static void reset() {
		try {
			PrintWriter write = new PrintWriter(file);			
			write.println("");							
			write.close();	
			for(int i = 0 ; i < 3; i++) {
				list.get(i).setScore(0);
				list.get(i).setName("Anonymous");
			}

		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getHighScore(int diff2) {
		String difficulty = switch (diff2) {
			case 0 -> "Beginner";
			case 1 -> "Intermediate";
			case 2 -> "Hard ";
			default -> "";
		};
		return difficulty + "\t" + list.get(diff2).getName() + "\t" + list.get(diff2).getScore();
	}

	public static void showSmile(int x) {
		if(x == (gridX * gridY - mine)){
			smile.setImage(new Image("File:res/face-win.png"));
			gpane.setMouseTransparent(true);
			animation.stop();
			time = timer.getTime();
			//System.out.println("TIME diff" + time + " " + diff);
			/*for(User5 s: list) {
				System.out.println("score: " + s.getScore());
				System.out.println("name: "+ s.getName());
			}*/

			if(highScore(diff, "Anonymous", time)) {
				//System.out.println("diff time: " + diff + " " + time);
				list.get(diff).setScore(time);
				start2(new Stage());
			}
		}
		if(x == -1) {
			smile.setImage(new Image("File:res/face-dead.png"));
			gpane.setMouseTransparent(true);
			animation.stop();
		}
		if(x == 1)
			smile.setImage(new Image("File:res/face-smile.png"));
	}

	public static  void randomPlace() {
		//System.out.println("into randomPlace");
		int row, col;
		/*for(int i = 0; i < buttons.length; i++)
			for(int j = 0; j < buttons[i].length; j++) {
				//System.out.println("placeImage is called!");
				b = new GridButton(i, j);
				b.clickMouse(animation, noMine,smile);
				buttons[i][j] = b;
			}*/
		for(int i = 0 ; i < mine ; i++) {
			row = new Random().nextInt(gridX);
			col = new Random().nextInt(gridY);
			//System.out.println(row + " " + col);

			if (buttons[row][col].getStatus2() == 1)
				i--;
			else {
				buttons[row][col].setButtonID(9);
				buttons[row][col].setStatus2(1);//status2 used to check if mine is placed at the position before	
			}
		}/*
		System.out.println("STATUS 2");
		for(int x = 0; x < gridX; x++) {	
			for(int y = 0; y < gridY; y++) {
				System.out.print(buttons[x][y].getStatus2() + " ");
			}
			System.out.println();
		}*/
	}
	public static void placeImage() {	
		//buttons = new GridButton[gridX][gridY];
		int count = 0;/*
		System.out.println("Button ID");
		for(int x = 0; x < gridX; x++) {	
			for(int y = 0; y < gridY; y++) {
				System.out.print(buttons[x][y].getButtonID() + " ");
			}
			System.out.println();
		}
		System.out.println("Status");
		for(int x = 0; x < gridX; x++) {	
			for(int y = 0; y < gridY; y++) {
				System.out.print(buttons[x][y].getStatus() + " ");
			}
			System.out.println();
		}*/

		gpane.getChildren().clear();
		//System.out.println("status bomb " + statusBomb);
		for(int x = 0; x < gridX; x++) {	
			for(int y = 0; y < gridY; y++) {
				int mineAround = 0;
				//System.out.println("status bomb " + statusBomb);

				if(buttons[x][y].getStatus() == 2 && statusBomb != 1) {
					//System.out.println("into flag status");
					buttons[x][y].setGraphic(new ImageView(new Image("File:res/flag.png")));
				}

				else if(statusBomb == 1) {
					if( buttons[x][y].getButtonID() == 9) {
						if (buttons[x][y].getStatus() == 0)
							buttons[x][y].setGraphic(new ImageView(new Image("File:res/mine-grey.png")));
						if (buttons[x][y].getStatus() == 2)
							buttons[x][y].setGraphic(new ImageView(new Image("File:res/flag.png")));						
						if (buttons[x][y].getStatus() == 1) {
							//user clicked 
							//System.out.println("Status2: " + buttons[x][y].getStatus2());
							buttons[x][y].setGraphic(new ImageView(new Image("File:res/mine-red.png")));
						}
					}
					else
						if(buttons[x][y].getStatus() == 2)
							buttons[x][y].setGraphic(new ImageView(new Image("File:res/mine-misflagged.png")));
				}
				else 
					if(buttons[x][y].getButtonID() != 9 || buttons[x][y].getStatus2() == 2) {	
						//System.out.println(buttons[x][y].getButtonID());
						if(x != 0) {
							if (buttons[x-1][y].getButtonID() == 9)
								mineAround++;
							if(y != gridY - 1)
								if (buttons[x-1][y+1].getButtonID() == 9) 
									mineAround++;
							if(y != 0)
								if (buttons[x-1][y-1].getButtonID() == 9)
									mineAround++;
						}
						if( x != gridX - 1) {					
							if(buttons[x+1][y].getButtonID() == 9) 
								mineAround++;
							if(y != 0)
								if (buttons[x+1][y-1].getButtonID() == 9) 
									mineAround++;
							if( y != gridY - 1)
								if (buttons[x+1][y+1].getButtonID() == 9)
									mineAround++;
						}
						if ( y != gridY - 1 )
							if (buttons[x][y+1].getButtonID() == 9) 
								mineAround++;
						if ( y != 0)
							if (buttons[x][y-1].getButtonID() == 9)
								mineAround++;
						buttons[x][y].setButtonID(mineAround);
					}
				//else System.out.println("invalid " + buttons[x][y].getX() + " " + buttons[x][y].getY());
				//System.out.println("2nd rounds");			
				//System.out.println("adding button");
				if((buttons[x][y].getStatus() == 1 && buttons[x][y].getButtonID() != 9)|| buttons[x][y].getStatus2() == 2) {
					buttons[x][y].setStatus(1);
					count++;
				}
				gpane.add(buttons[x][y], y, x);
				//System.out.println("button added");
			}
		}/*
		System.out.println("Button ID After");
		for(int x = 0; x < gridX; x++) {	
			for(int y = 0; y < gridY; y++) {
				System.out.print(buttons[x][y].getButtonID() + " ");
			}
			System.out.println();
		}
		System.out.println("Status After");
		for(int x = 0; x < gridX; x++) {	
			for(int y = 0; y < gridY; y++) {
				System.out.print(buttons[x][y].getStatus() + " ");
			}
			System.out.println();

		}

		System.out.println("count: " + count);*/
		if(count == (gridX * gridY - mine))
			showSmile(count);
	}
	public static void printStatus() {
		/*System.out.println("Status After2");
		for(int x = 0; x < gridX; x++) {	
			for(int y = 0; y < gridY; y++) {
				System.out.print(buttons[x][y].getStatus() + " ");
			}
			System.out.println();
		}*/
	}
	public void setDifficulty(int diff2) {
		diff = diff2;

		switch (diff2) {
			case 0 -> {
				gridX = 8;
				gridY = 8;
				mine = 10;
			}

			//placeImage();
			case 1 -> {
				gridX = 16;
				gridY = 16;
				mine = 40;
			}
			//randomPlace();
			//placeImage();
			case 2 -> {
				gridX = 32;
				gridY = 16;
				mine = 99;
			}
			//randomPlace();
			//placeImage();
		}
		buttons = new GridButton[gridX][gridY];
		for(int i = 0; i < buttons.length; i++)
			for(int j = 0; j < buttons[i].length; j++) {
				//System.out.println("placeImage is called!");
				b = new GridButton(i, j);
				b.clickMouse(animation, noMine,smile);
				buttons[i][j] = b;
			}
		randomPlace();

	}

}



