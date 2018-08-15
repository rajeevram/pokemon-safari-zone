package views.battle;

import java.util.LinkedList;
import java.util.Queue;

import controller.Controller;
import controller.SoundPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.pokemon.Pokemon;
import model.trainer.Trainer;

/**
 * This is an abstract class the has two children: SafariBattle and
 * TrainerBattle
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class SafariBattle extends BattleView {

	// Animation Instance Variables
	protected Timeline ballCatchingPokemon, ballRoll;

	protected Image ball = new Image("file:media/images/SafariBall.png");

	private Button throwRock = new Button("Throw Rock");
	private Button throwBait = new Button("Throw Bait");
	private Button throwBall = new Button("Throw Ball");
	private Queue<String> messages = new LinkedList<String>();
	private Label ballsLeft;
	private boolean battleEnds = false;
	protected Image trainerImage;

	private Timeline throwingRock, throwingBait;
	
	/**
	 * Constructor for abstract BattleView class
	 * 
	 * @param trainer
	 * @param currentPokemon
	 */
	public SafariBattle(Trainer trainer, Pokemon currentPokemon) {
		super(trainer, currentPokemon);

		trainerImage = trainer.getBattleImage();

		gc3.drawImage(trainerImage, 0, 0, 140, 160, 90, 220, 260, 350);
		gc3.drawImage(bar, -1, 550, 400, 150);

		pokemonImage = new Image("file:media/images/" + currentPokemon.getImage());

		setOnKeyPressed(new MessageUpdater());

		ballRoll = new Timeline(new KeyFrame(Duration.millis(300), new BallRollAnimation()));
		ballRoll.setCycleCount(3);

		trainerThrowing = new Timeline(new KeyFrame(Duration.millis(230), new TrainerThrowingRock()));
		trainerThrowing.setCycleCount(5);

		ballCatchingPokemon = new Timeline(new KeyFrame(Duration.millis(4), new BallCatchingPokemon()));
		ballCatchingPokemon.setCycleCount(464);

		highGridPaneMethods();

		pokemonGettingHit = new Timeline(new KeyFrame(Duration.millis(100), new PokemonGettingHit()));
		pokemonGettingHit.setCycleCount(15);
		
		throwingRock = new Timeline(new KeyFrame(Duration.millis(10), new ThrowingRockAnimation()));
		throwingRock.setCycleCount(100);
		
		throwingBait = new Timeline(new KeyFrame(Duration.millis(10), new ThrowingBaitAnimation()));
		throwingBait.setCycleCount(100);

	}

	protected void lowGridPaneMethods() {

		status.setMinWidth(200);
		status.setMinHeight(80);
		status.setWrapText(true);
		pane3.setPadding(new Insets(580, 300, 200, 50));
		status.setFont(font);
		pane3.add(status, 0, 0);
		pane1.setPadding(new Insets(550, 700, 200, 400));

		throwBall.setMinWidth(200);
		throwBall.setMinHeight(80);
		throwBall.setStyle("-fx-background-color:#33FFCC;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		throwRock.setMinWidth(200);
		throwRock.setMinHeight(80);
		throwRock.setStyle("-fx-background-color:#F08080;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		throwBait.setMinWidth(200);
		throwBait.setMinHeight(80);
		throwBait.setStyle("-fx-background-color:#FFD700;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		runAway.setMinWidth(200);
		runAway.setMinHeight(80);
		runAway.setStyle("-fx-background-color:#00BFFF;" + "-fx-border-color:black;" + "-fx-border-radius:5px");
		pane1.add(throwBall, 0, 0);
		pane1.add(throwRock, 0, 1);
		pane1.add(throwBait, 1, 0);
		pane1.add(runAway, 1, 1);

		throwBall.setOnAction(event -> {
			throwBall();

		});

		throwBait.setOnAction(event -> {
			throwBait();
		});

		throwRock.setOnAction(event -> {
			throwRock();

		});

		runAway.setOnAction(event -> {
			Controller.endWildBattle();
		});

	}

	public void decrementProgressBar() {
		progressBar.setProgress((double) currentPokemon.getCurrentHP() / (double) currentPokemon.getHitPoints());
	}

	public void throwRock() {
		disableButtons();
		this.requestFocus();
		trainerThrowing.play();
		currentPokemon.rockThrown();
		decrementProgressBar();
		trainerThrowing.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				throwingRock.play();
			}
		});
		throwingRock.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				SoundPlayer.playEffectOnce("MoveAttack");
				pokemonGettingHit.play();
			}	
		});
		status.setText("You threw a rock!");
		trainer.move();
		messages.add(currentPokemon.getName() + " is angry!");
		if (trainer.getMoveCount() == 10) {
			messages.add(currentPokemon.getName() + " ran away!");
			battleEnds = true;
			return;
		}
		if (currentPokemon.runsAway()) {
			messages.add(currentPokemon.getName() + " ran away!");
			battleEnds = true;
			return;
		}
		messages.add(currentPokemon.getName() + " is watching carefully.");
	}

	public void throwBait() {
		disableButtons();
		this.requestFocus();
		trainerThrowing.play();
		trainerThrowing.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				throwingBait.play();
			}
		});
		throwingBait.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				SoundPlayer.playEffectOnce("EatingBait");
			}	
		});
		status.setText("You threw bait!");
		trainer.move();
		messages.add(currentPokemon.getName() + " is eating the bait.");
		if (trainer.getMoveCount() == 10) {
			messages.add(currentPokemon.getName() + " ran away!");
			battleEnds = true;
			return;
		}
		if (currentPokemon.runsAway()) {
			messages.add(currentPokemon.getName() + " ran away!");
			battleEnds = true;
			return;
		}
		messages.add(currentPokemon.getName() + " is watching carefully.");
	}

	public void throwBall() {

		disableButtons();
		trainerThrowing.play();
		trainerThrowing.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {

				StandingPokemon.stop();
				gc3.clearRect(400, 0, 800, 300);
				ballCatchingPokemon.play();

				if (true) {

					status.setText("You threw a safari ball!");
					trainer.throwBall();
					// ballsLeft.setText(" Safari Balls Remaining: " + trainer.getBallCount());

					int i = 0;
					while (i < 3) {

						if (currentPokemon.rollsBall()) {
							i++;
							messages.add("Roll " + i);
							continue;
						} else {
							messages.add(currentPokemon.getName() + " escaped!");
							if (currentPokemon.runsAway()) {
								messages.add(currentPokemon.getName() + " ran away!");
								battleEnds = true;
							}
							return;
						}
					}
					if (i == 3) {
						trainer.addPokemon(currentPokemon);
						messages.add("Caught the " + currentPokemon.getName() + "!");
						battleEnds = true;
						return;
					}
				}

				if (trainer.getBallCount() < 1) {
					messages.add("Out of safari balls!");
				} else {
					messages.add(currentPokemon.getName() + " ran away!");
					battleEnds = true;

				}

			}
		});

		this.requestFocus();

	}

	protected class MessageUpdater implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				if (!messages.isEmpty()) {
					if (messages.peek().contains("Caught")) {
						gc3.drawImage(ball, 180, 0, 29, 50, 570, 194, 40, 60);
						SoundPlayer.stopSound();
						SoundPlayer.playEffectOnce("Capture");
						String str = messages.remove();
						status.setText(str);
						return;
					} else {
						String str = messages.remove();
						status.setText(str);

						if (str.contains("Roll")) {
							ballRoll.play();
						} else if (str.contains("escaped")) {
							StandingPokemon.play();
						}
					}
				} else if (!battleEnds) {
					enableButtons();
					status.setText("What will you do?");
				} else if (battleEnds && messages.isEmpty()) {
					if (SoundPlayer.isPlayingEffect()) {
						return;
					}
					if ( trainer.hasOneOfEach() || trainer.hasTwoOfFive() || trainer.hasFiveOfTwo()) {
						Controller.winToTown();
						return;
					}
					if (trainer.getBallCount() == 0) {
						Controller.loseToTown();
						return;
					}
					Controller.endWildBattle();
				}
			}
		}

	}

	private void disableButtons() {
		throwRock.setDisable(true);
		throwBall.setDisable(true);
		throwBait.setDisable(true);
		runAway.setDisable(true);
	}

	private void enableButtons() {
		throwRock.setDisable(false);
		throwBall.setDisable(false);
		throwBait.setDisable(false);
		runAway.setDisable(false);
	}

	protected class BallCatchingPokemon implements EventHandler<ActionEvent> {

		double sx = 570, sy = 80;
		double sw = 40;
		double sh = 60;
		int tic = 0;

		@Override
		public void handle(ActionEvent event) {

			tic++;

			if (tic <= 350) {
				gc3.drawImage(ball, 150, 0, 29, 50, 570, 80, 40, 60);
			} else if (tic % 2 == 0) {
				sy += 2;
				gc3.clearRect(400, 0, 800, 300);
				gc3.drawImage(ball, 0, 0, 29, 50, sx, sy, sw, sh);

			}
			if (tic == 464) {
				sy = 80;
				tic = 0;
			}
		}
	}

	protected class BallRollAnimation implements EventHandler<ActionEvent> {

		double sx = 570, sy = 194, dx = 0, dw = 30;
		double sw = 40;
		double sh = 60;
		int tic = 0;

		@Override
		public void handle(ActionEvent event) {

			gc3.clearRect(500, 100, 300, 300);
			gc3.drawImage(ball, dx, 0, dw, 50, sx, sy, sw, sh);

			dx += 120;
			if (dx > 120) {

				dx = 0;
			}

		}
	}

	protected class TrainerThrowingRock implements EventHandler<ActionEvent> {

		private int tic2 = 0;
		double sx = 0, sy = 0;
		double sw = 159;
		double sh = 160;

		@Override
		public void handle(ActionEvent event) {
			tic2++;

			sx += 160;
			gc3.clearRect(0, 0, 400, 800);
			gc3.drawImage(trainerImage, sx, sy, sw, sh, 90, 220, 260, 350);
			gc3.drawImage(bar, -1, 550, 400, 150);

			if (tic2 == 5) {
				gc3.clearRect(0, 0, 400, 800);
				gc3.drawImage(trainerImage, 0, 0, 140, 160, 90, 220, 260, 350);
				gc3.drawImage(bar, -1, 550, 400, 150);
				tic2 = 0;
				sx = 0;

			}

		}
	}
	
	protected class ThrowingRockAnimation implements EventHandler<ActionEvent> {
		  private int tic = 0, sx = 32;
		    double x = 350;
		    Image image = new Image("file:media/images/BaitRock.png");
		    
		    @Override 
		    public void handle(ActionEvent event) {
		        x++;
		        tic++;
		        gc3.clearRect(0, 0, 500, 250);
		        gc3.drawImage(image, sx, 0, 30, 30, x, 100, 20, 20);
		        if (tic == 100) {
	        			gc3.clearRect(0, 0, 500, 250);
		        }
		    }
	}
	
	protected class ThrowingBaitAnimation implements EventHandler<ActionEvent> {
	    private int tic = 0;
	    double x = 350;
	    Image image = new Image("file:media/images/BaitRock.png");
	    
	    @Override 
	    public void handle(ActionEvent event) {
	        x++;
	        tic++;
	        gc3.clearRect(0, 0, 500, 250);
	        gc3.drawImage(image, 0, 0, 30, 30, x, 100, 20, 20);
	        if (tic == 100) {
        			gc3.clearRect(0, 0, 500, 250);
	        }
	        
	    }
	}

}
