package ca.mcgill.ecse223.block.view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PlayPane extends BorderPane implements Block223PlayModeInterface {
	private static Pane playArea;
	private static PlayHeader playHeader;
	private static HallOfFamePane hofPane;
	private static HBox buttonsBox;
	private static Button startGame;

	private static Button quit;
	private static Button logout;
	private static Rectangle paddle;
	private static Circle ball;
	private static boolean started;

	private static TOCurrentlyPlayedGame pgame;
	private static TOConstant constants;
	
	private static MediaPlayer mediaPlayer;
	private static MediaView mediaView;
	private static ImageView imageView;
	private static String inputs = "";
	
	private static Stage primaryStage;
	
	//adding the music objects
	private static HBox musicContainer = new HBox(20);
	private static Button play;
	private static Button mute;
	private static Button nextSong;
	private static ImageView playimg;
	private static ImageView pauseimg;
	private static ImageView muteimg;
	private static ImageView unmuteimg;

	public PlayPane(Stage primaryStage) {
		PlayPane.primaryStage = primaryStage;
		try {
			pgame = Block223Controller.getCurrentPlayableGame();
			constants = Block223Controller.getConstants();
		}
		catch(InvalidInputException iie ) {
			//TODO
		}
		musicContainer.getChildren().clear();
		playArea = new Pane();
		playArea.setMaxSize(constants.getPlayAreaSide(), constants.getPlayAreaSide());
		playArea.setBorder(new Border(new BorderStroke(Color.WHITE, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Image background = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/alien.jpg"));
		playArea.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(constants.getPlayAreaSide(), constants.getPlayAreaSide(), false, false, false, false))));

		playHeader = new PlayHeader();
		playHeader.setBorder(new Border(new BorderStroke(Color.WHITE, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		hofPane = new HallOfFamePane("");

		buttonsBox = new HBox(20);
		buttonsBox.setAlignment(Pos.CENTER);
		
		prepareMusic();


		startGame = new Button("Start Game");
		startGame.setFocusTraversable(false);
		startGame.setOnAction(e -> {
			Block223Page.buttonPressSound();
			startGame.setDisable(true);
			quit.setDisable(true);
			logout.setDisable(true);
			started = true;
			mediaPlayer.stop();
			if(!MusicShuffler.gameMusicPlaying())
			MusicShuffler.playNextPlayedMusic();
			this.setCenter(playArea);
			// initiating a thread to start the game loop
			Runnable task = new Runnable() {
				public void run() {
					try {
						Block223Controller.startGame(PlayPane.this);
						startGame.setDisable(false);
						quit.setDisable(false);
						logout.setDisable(false);
						started = false;
						
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								playArea.setDisable(true);
								
							}
						});

					} catch (InvalidInputException e) {}
				}
			};
			Thread t2 = new Thread(task);
			t2.setDaemon(true);
			t2.start();
		});
		

		quit = new Button("Quit");
		quit.setFocusTraversable(false);
		quit.setOnAction(e -> {
			Block223Page.buttonPressSound();
			MusicShuffler.playSelectMusic();
			mediaPlayer.stop();
			Block223Page.changeToPlayableGameSelectionScene(primaryStage);
		});
		
		logout = new Button("Log out");
		logout.setFocusTraversable(false);
		logout.setOnAction(e -> {
			//MusicShuffler.playAdminMusic();
			MusicShuffler.playSelectMusic();
			MusicShuffler.pauseMusic();
			Block223Page.buttonPressSound();
			mediaPlayer.stop();
			Block223Controller.logout();
			primaryStage.setScene(Block223Page.getLoginScene());
			Block223Page.playCiaoGunty();
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					MusicShuffler.resumeMusic();
				}
			}, 2000);
		});
		buttonsBox.getChildren().addAll(startGame,quit, logout);
		buttonsBox.setPadding(new Insets(0, hofPane.getPrefWidth()*1.15, 0, 0));

		mediaPlayer = new MediaPlayer(new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/gameVideo.mp4")));
		mediaView = new MediaView(mediaPlayer);
		mediaView.setFitWidth(Block223Page.getScreenWidth()/3);
		mediaPlayer.play();
		
		this.setCenter(mediaView);
		this.setTop(playHeader);
		this.setBottom(buttonsBox);
		this.setRight(hofPane);
		this.setLeft(musicContainer);
		this.setPadding(new Insets(0,0,40,0));

		displayPlayArea();
		
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
	}

	private void prepareMusic() {
		playimg = new ImageView(new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/play.png")));
		playimg.setFitHeight(20);
		playimg.setFitWidth(20);
		pauseimg = new ImageView(new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/pause.png")));
		pauseimg.setFitHeight(20);
		pauseimg.setFitWidth(20);
		unmuteimg = new ImageView(new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/mutemusic.png")));
		unmuteimg.setFitHeight(20);
		unmuteimg.setFitWidth(20);
		muteimg = new ImageView(new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/music.png")));
		muteimg.setFitHeight(20);
		muteimg.setFitWidth(20);
		ImageView next = new ImageView(new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/nextsong.png")));
		next.setFitHeight(20);
		next.setFitWidth(20);
		nextSong = new Button("",next);
		play = new Button("",pauseimg);
		mute = new Button("",muteimg);
		musicContainer.getChildren().addAll(nextSong,mute,play);
		musicContainer.setPadding(new Insets(20,20,20,20));
		musicContainer.setAlignment(Pos.CENTER);
		nextSong.setFocusTraversable(false);
		play.setFocusTraversable(false);
		mute.setFocusTraversable(false);
		nextSong.setOnAction(e->{
			MusicShuffler.playNextPlayedMusic();
		});
		
		play.setOnAction(e->{
			if(MusicShuffler.isPlaying()) {
				MusicShuffler.pauseMusic();
				play.setGraphic(playimg);
			}
			else {
				MusicShuffler.resumeMusic();
				play.setGraphic(pauseimg);
				
			}
		});
		
		mute.setOnAction(e->{
			if(MusicShuffler.isMuted()) {
				MusicShuffler.unmuteMusic();
				mute.setGraphic(muteimg);
			}
			else {
				MusicShuffler.muteMusic();
				mute.setGraphic(unmuteimg);
			}
		});
	}

	public static void setInputs(String str) {
		inputs = inputs+str;
	}

	public static boolean isStarted() {
		return started;
	}

	public static void displayPlayArea() {
		
		paddle = new Rectangle();
		paddle.setWidth(pgame.getCurrentPaddleLength());
		paddle.setHeight(constants.getPaddleWidth());
		Image beam = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/beam.png"));
		paddle.setFill(new ImagePattern(beam));
		paddle.setTranslateX(pgame.getCurrentPaddleX());
		paddle.setTranslateY(constants.getPlayAreaSide()-constants.getVerticalDistance()-constants.getPaddleWidth());

		ball = new Circle();
		ball.setRadius(constants.getBallDiameter()/2);
		Image meteor = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/meteor.png"));
		ball.setFill(new ImagePattern(meteor));
		ball.setTranslateX(pgame.getCurrentBallX());
		ball.setTranslateY(pgame.getCurrentBallY());
		
		imageView = new ImageView();
		Image earth = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/earth.png"));
		imageView.setFitWidth(constants.getPlayAreaSide()/0.9);
		imageView.setFitHeight(constants.getPlayAreaSide()/2.9);
		imageView.setImage(earth);
		imageView.setTranslateX(constants.getPlayAreaSide()/2 - imageView.getFitWidth()/2);
		imageView.setTranslateY(constants.getPlayAreaSide() - imageView.getFitHeight()/3.5);
		
		playArea.getChildren().addAll(paddle, ball, imageView);

		for(TOCurrentBlock toBlock : pgame.getBlocks()) {
			Rectangle block = new Rectangle();
			block.setWidth(constants.getSize());
			block.setHeight(constants.getSize());
			Color blockColor = new Color((double)toBlock.getRed()/255, (double)toBlock.getGreen()/255, (double)toBlock.getBlue()/255, 1);
			block.setFill(blockColor);
			block.setTranslateX(toBlock.getX());
			block.setTranslateY(toBlock.getY());
			playArea.getChildren().add(block);
		}
	}

	@Override
	public String takeInputs() {
		String temp = ""+inputs;
		inputs = "";
		return temp;
	}

	@Override
	public void refresh() {	
		try {
			pgame = Block223Controller.getCurrentPlayableGame();
		} catch(InvalidInputException iie ) {
		}
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				playArea.getChildren().clear();
				playArea.getChildren().addAll(paddle, ball, imageView);
				
				for(TOCurrentBlock toBlock : pgame.getBlocks()) {
					Rectangle block = new Rectangle();
					block.setWidth(constants.getSize());
					block.setHeight(constants.getSize());
					Color blockColor = new Color((double)toBlock.getRed()/255, (double)toBlock.getGreen()/255, (double)toBlock.getBlue()/255, 1);
					block.setFill(blockColor);
					block.setTranslateX(toBlock.getX());
					block.setTranslateY(toBlock.getY());
					playArea.getChildren().add(block);
				}				
			}
		});
		
		ball.setTranslateX(pgame.getCurrentBallX());
		ball.setTranslateY(pgame.getCurrentBallY());
		paddle.setTranslateX(pgame.getCurrentPaddleX());
		paddle.setWidth(pgame.getCurrentPaddleLength());
		PlayHeader.refreshHeader(pgame.getCurrentLevel(), pgame.getLives(), pgame.getScore());
	}
	
	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				Block223Page.setGameOverScene(primaryStage, nrOfLives, hof);		
			}
		});	
	}
}

