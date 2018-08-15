package controller;

import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Direction;
import model.Location;
import model.npc.Merchant;
import model.npc.NPC;
import model.pokemon.Pokemon;
import model.trainer.EnemyTrainer;
import model.trainer.Trainer;
import views.general.CaveView;
import views.general.GeneralView;

/**
 * This class handles animation of the player, and movement of the background
 * canvas around the player. Sub-animation of fishing and biking is also handled
 * here.
 */
public class MoveHandler {

	private GeneralView currentView;
	private char spot;
	private int row, col;
	private double animationTime;
	private Timeline trainerTimeline, timeline;
	private Trainer trainer;
	private Pokemon foundPokemon;
	private Canvas canvas1, canvas2, canvas3;
	private GraphicsContext gcLayer2, gcLayer3;
	private Direction direction;
	private int oddOrEven = 0;
	private Queue<String> messages = new LinkedList<String>();

	private Character[][] grid;
	private boolean finished = true;
	private final int heightChange = 5;
	//private EnemyTrainer enemyTrainer;

	/**
	 * The constructor for the moving machinery
	 * @param generalView the view with moving
	 * @param inTrainer the player in the game
	 * @param canvas1 first canvas
	 * @param canvas2 second canvas
	 * @param canvas3 third canvas
	 */
	public MoveHandler(GeneralView generalView, Trainer inTrainer, Canvas canvas1, Canvas canvas2, Canvas canvas3) {
		this.canvas1 = canvas1;
		this.canvas2 = canvas2;
		this.canvas3 = canvas3;
		gcLayer2 = canvas2.getGraphicsContext2D();
		gcLayer3 = canvas3.getGraphicsContext2D();
		timeline = new Timeline(60);
		trainerTimeline = new Timeline(60);
		trainerTimeline.setCycleCount(2);
		currentView = generalView;
		trainer = inTrainer;
		animationTime = currentView.getAnimationTime();
	}

	/**
	 * Acts like an eventHandler for the class, called when input is received in
	 * GeneralView by the private class StartMoveHandler. Starts the moving
	 * animation if the previous animation has been finished.
	 * 
	 * @param event
	 */
	public void handle(KeyEvent event) {
		grid = currentView.getMap();
		canvas2.requestFocus();
		currentView.setMessage("");

		if (finished) {
			move(event);
		}
	}

	/**
	 * void move(event) -- Called by the handle method, decodes the direction and
	 * calls the specified direction method to move the character. Also handles
	 * startup of Biking and Fishing, and handles enter for interaction with the
	 * object in front of the user.
	 * 
	 * @param event
	 */
	private void move(KeyEvent event) {
		oddOrEven++;
		if (event.getCode() == KeyCode.DOWN) {
			moveDown();
		}

		// If trying to move north
		else if (event.getCode() == KeyCode.UP) {
			moveUp();
		}

		// If trying to move west
		else if (event.getCode() == KeyCode.LEFT) {
			moveLeft();
		}

		// If trying to move east
		else if (event.getCode() == KeyCode.RIGHT) {
			moveRight();
		}

		// Trying to pickup an item
		else if (event.getCode() == KeyCode.ENTER) {
			pressEnter();
		} else if (event.getCode() == KeyCode.F) {
			tryFish();
		} else if (event.getCode() == KeyCode.B) {
			tryBike();
		}
	}

	/**
	 * Handles attempting to fish (on 'F' keypress). Checks if they can through a
	 * Trainer method, and starts the animation and, on completion, calls the logic
	 * for chance of battle if they can fish.
	 */
	private void tryFish() {
		if (trainer.canFish()) {
			findSpotInFront();
			spot = grid[row][col];
			if (spot == 'W') {
				trainer.step(); // Fishing counts as a step
				// animate throwing fishing rod
				finished = false;
				trainerTimeline.getKeyFrames()
						.add(new KeyFrame(Duration.millis(animationTime / 2), new FishAnimation()));
				trainerTimeline.setCycleCount(20);
				trainerTimeline.play();

				trainerTimeline.setOnFinished(action -> {
					if (currentView.getBiking()) {
						currentView.setBiking(false);
						bikeAdjustments();
					}
					trainerTimeline.getKeyFrames().remove(0);
					trainerTimeline.setCycleCount(2);
					finished = true;
					encounterPokemon(spot);
					currentView.drawTrainer();
					canvas2.requestFocus();
					if (trainer.getStepCount() == 0) {
						canvas2.setOnKeyPressed(new MessageUpdater());
						messages.add(trainer.getName() + " ran out of steps!");
						currentView.setMessage(messages.remove());
					}
				});
			}
		}
	}

	/**
	 * Handles attempting to bike (on 'B' keypress). Checks if they can through
	 * Trainer method. Calls bikeAdjustments to change animation times and draw the
	 * trainer on bike and start background music.
	 */
	private void tryBike() {
		if (trainer.canBike()) {
			currentView.setBiking(!currentView.getBiking());
			bikeAdjustments();
		}
		currentView.drawTrainer();
		canvas2.requestFocus();
	}

	/**
	 * Handles change in animation times and player looks when the user gets on or
	 * off the bike. Also calls for change in background music. Called when the user
	 * tries to ride the bike, and when fishing is started.
	 */
	private void bikeAdjustments() {
		if (currentView.getBiking()) {
			animationTime /= 2;
			trainerTimeline.setCycleCount(3);
			SoundPlayer.stopSound();
			SoundPlayer.playSongRepeat("BicycleRide", 4150, 32000);
		} else {
			SoundPlayer.stopSound();
			animationTime *= 2;
			trainerTimeline.setCycleCount(2);
			SoundPlayer.stopSound();
			Location newer = trainer.getLocation();
			if (newer == Location.CAVE) {
				SoundPlayer.playSongRepeat("CaveMusic", 13200, 79000);
				return;
			}
			if (newer == Location.SAFARI) {
				SoundPlayer.playSongRepeat("SafariZone", 1125, 45000);
				return;
			}
		}
	}

	/**
	 * Handles Moving down, corresponding to an increase in the y-value of the
	 * trainer position Calls canMoveTo(spot) to check for ability to move, handles
	 * movement if it can, otherwise plays hitting wall sound.
	 */
	private void moveDown() {
		currentView.setDirection(trainer.direction = direction = Direction.DOWN);
		currentView.drawTrainer();
		currentView.setRunning(true);
		spot = grid[currentView.getTrainerPosition().y + 1][currentView.getTrainerPosition().x];
		if (canMoveTo(spot)) {
			trainer.step();
			currentView.getTrainerPosition().y++;
			finished = false;

			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime),
					new KeyValue(canvas1.translateYProperty(), canvas1.getTranslateY() - currentView.getGridSize())));
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime),
					new KeyValue(canvas3.translateYProperty(), canvas3.getTranslateY() - currentView.getGridSize())));
			timeline.play();

			trainerWalkTimeLine();

			timeline.setOnFinished(action -> {
				endTimeline();
			});

		} else {
			finished = false;
			trainerWalkTimeLine();
			SoundPlayer.playEffectOnce("HitBarrier");
			return;
		}
	}

	/**
	 * Handles Moving up, corresponding to an decrease in the y-value of the trainer
	 * position Calls canMoveTo(spot) to check for ability to move, handles movement
	 * if it can, otherwise plays hitting wall sound.
	 */
	private void moveUp() {
		currentView.setDirection(trainer.direction = direction = Direction.UP);
		currentView.drawTrainer();
		currentView.setRunning(true);
		spot = grid[currentView.getTrainerPosition().y - 1][currentView.getTrainerPosition().x];

		if (canMoveTo(spot)) {
			trainer.step();
			currentView.getTrainerPosition().y--;
			finished = false;
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime),
					new KeyValue(canvas1.translateYProperty(), canvas1.getTranslateY() + currentView.getGridSize())));
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime),
					new KeyValue(canvas3.translateYProperty(), canvas3.getTranslateY() + currentView.getGridSize())));
			timeline.play();

			trainerWalkTimeLine();

			timeline.setOnFinished(action -> {
				endTimeline();
			});

		} else {
			finished = false;
			trainerWalkTimeLine();
			SoundPlayer.playEffectOnce("HitBarrier");
			return;
		}
	}

	/**
	 * Handles Moving left, corresponding to an decrease in the x-value of the
	 * trainer position Calls canMoveTo(spot) to check for ability to move, handles
	 * movement if it can, otherwise plays hitting wall sound.
	 */
	private void moveLeft() {
		currentView.setDirection(trainer.direction = direction = Direction.LEFT);
		currentView.drawTrainer();
		currentView.setRunning(true);
		spot = grid[currentView.getTrainerPosition().y][currentView.getTrainerPosition().x - 1];
		if (canMoveTo(spot)) {
			trainer.step();

			currentView.getTrainerPosition().x--;
			finished = false;
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime),
					new KeyValue(canvas1.translateXProperty(), canvas1.getTranslateX() + currentView.getGridSize())));
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime),
					new KeyValue(canvas3.translateXProperty(), canvas3.getTranslateX() + currentView.getGridSize())));
			timeline.play();

			trainerWalkTimeLine();

			timeline.setOnFinished(action -> {
				endTimeline();
			});
		} else {
			finished = false;
			trainerWalkTimeLine();
			SoundPlayer.playEffectOnce("HitBarrier");
			return;
		}
	}

	/**
	 * Handles Moving right, corresponding to an increase in the x-value of the
	 * trainer position Calls canMoveTo(spot) to check for ability to move, handles
	 * movement if it can, otherwise plays hitting wall sound.
	 */
	private void moveRight() {
		currentView.setDirection(trainer.direction = direction = Direction.RIGHT);
		currentView.drawTrainer();
		currentView.setRunning(true);
		spot = grid[currentView.getTrainerPosition().y][currentView.getTrainerPosition().x + 1];
		if (canMoveTo(spot)) {
			trainer.step();
			currentView.getTrainerPosition().x++;
			finished = false;
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime),
					new KeyValue(canvas1.translateXProperty(), canvas1.getTranslateX() - currentView.getGridSize())));
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime),
					new KeyValue(canvas3.translateXProperty(), canvas3.getTranslateX() - currentView.getGridSize())));
			timeline.play();

			trainerWalkTimeLine();

			timeline.setOnFinished(action -> {
				endTimeline();
			});
		} else {
			finished = false;
			trainerWalkTimeLine();
			SoundPlayer.playEffectOnce("HitBarrier");
			return;
		}

	}

	/**
	 * Calls helper function to find the spot in front of the player(in the
	 * direction they are facing), and interacts appropriately. Picks it up if it is
	 * an item, shows NPC message and interaction if it is an NPC.
	 */
	private void pressEnter() {

		findSpotInFront();
		spot = grid[row][col];
		if (spot == 'b') {
			trainer.addItem(currentView.getItems().get(0));
			SoundPlayer.playEffectOnce("PickupItem");
			while (SoundPlayer.isPlayingEffect()) {
				continue;
			}
			currentView.setInventoryText();
			grid[row][col] = 'G';
			currentView.updateMap();
			currentView.drawView();
		} else if (spot == 'f') {
			trainer.addItem(currentView.getItems().get(1));
			SoundPlayer.playEffectOnce("PickupItem");
			while (SoundPlayer.isPlayingEffect()) {
				continue;
			}
			currentView.setInventoryText();
			grid[row][col] = 'G';
			currentView.updateMap();
			currentView.drawView();
		}
		if (spot == 'J' || spot == 'U' || spot == 'I' || spot == 'O' || spot == 'S' || spot == 'K' || spot == 'N'
				|| spot == 'X' || spot == 'Z' || spot == 'Q') {
			int sx, sy, gridSize = currentView.getGridSize();
			if (direction == Direction.UP) { // D L U R
				sx = gridSize * 3;
				sy = 0;
			} else if (direction == Direction.RIGHT) {
				sx = gridSize * 2;
				sy = gridSize;
			} else if (direction == Direction.DOWN) {
				sx = 0;
				sy = gridSize * 2;
			} else {
				sx = gridSize;
				sy = gridSize * 3;
			}
			gcLayer3.clearRect(col * gridSize, row * gridSize, gridSize, gridSize);
			NPC npc = currentView.getNPC(spot);
			if (npc instanceof Merchant) {
				gcLayer3.drawImage(npc.getImage(), sx, 0, gridSize, gridSize, col * gridSize, row * gridSize, gridSize,
						gridSize);
			} else if (npc instanceof Trainer) {
				gcLayer3.drawImage(npc.getImage(), gridSize, sy, gridSize, gridSize, col * gridSize, row * gridSize,
						gridSize, gridSize);
			} else {
				gcLayer3.drawImage(npc.getImage(), sx, 0, gridSize, gridSize, col * gridSize, row * gridSize, gridSize,
						gridSize);
			}
			canvas2.setOnKeyPressed(new MessageUpdater());
			messages.add(currentView.npcMessage(spot));
			currentView.setMessage(messages.remove());
		}
		if (spot == 'I') {
			canvas2.setOnKeyPressed(new MessageUpdater());
			messages.add(currentView.npcMessage(spot));
			currentView.setMessage(messages.remove());
		}
		else if (spot == 'J') {
			if (currentView.getOnDisplay() == false) {
				NPC npc = currentView.getNPC(spot);
				currentView.displayShop((Merchant)npc);
			}
		}
		/*
		else if (spot == 'Q') {
			if (currentView.getOnDisplay() == false) {
				enemyTrainer = (EnemyTrainer)currentView.getNPC(spot);
				
			}
		}
		else if (spot == 'O') {
			
		}*/

	}

	/**
	 * Helper function to get the grid character one in front of the player, based
	 * on their direction. Called by canFish() and pressEnter().
	 */
	private void findSpotInFront() {

		if (direction == Direction.UP) {
			row = currentView.getTrainerPosition().y - 1;
			col = currentView.getTrainerPosition().x;
		} else if (direction == Direction.RIGHT) {
			row = currentView.getTrainerPosition().y;
			col = currentView.getTrainerPosition().x + 1;
		} else if (direction == Direction.DOWN) {
			row = currentView.getTrainerPosition().y + 1;
			col = currentView.getTrainerPosition().x;
		} else {
			row = currentView.getTrainerPosition().y;
			col = currentView.getTrainerPosition().x - 1;

		}
	}

	/* ----------------Methods used by the move(direction) methods-------------- */

	/**
	 * Helper method to determine if the character can move to the spot passed in.
	 * Cannot walk into walls, trees, NPCs, or water.
	 * 
	 * @param spot
	 */
	private boolean canMoveTo(char spot) {

		if (spot == 'W' || spot == 'T' || spot == 'X' || spot == 'P' || spot == 'R' || spot == 'L' || spot == 'V'
				|| spot == 'B' || spot == 'A' || spot == 'a' || spot == '$' || spot == 'Q' || spot == 'J' || spot == 'U'
				|| spot == 'I' || spot == 'O' || spot == 'S' || spot == 'K' || spot == 'N' || spot == 'X' || spot == 'Z'
				|| spot == 'b' || spot == 'f' || spot == 'p' || spot == '|') {
			return false;
		}
		return true;
	}

	/**
	 * Called on the end of the timeline of the movement of the background map. If
	 * the spot the user moved to was an entrance/exit, call Controller method to
	 * change the view to the appropriate view. Otherwise calls encounterPokemon on
	 * the spot to see if a battle should occur.
	 */
	private void endTimeline() {
		finished = true;
		// currentView.drawTrainer();
		timeline.getKeyFrames().removeAll(timeline.getKeyFrames());
		canvas2.requestFocus();
		if (spot == 'E') {
			if (currentView.getBiking()) {
				currentView.setBiking(false);
				bikeAdjustments();
			}
			Controller.entranceChangeView();
		} else {
			encounterPokemon(spot);
		}
	}

	/**
	 * Called on movement by each of the directions to handle move animations for
	 * the trainer.
	 */
	private void trainerWalkTimeLine() {
		if (canMoveTo(spot)) {

			trainerTimeline.getKeyFrames().add(new KeyFrame(
					Duration.millis(animationTime / trainerTimeline.getCycleCount()), new WalkAnimation()));
			trainerTimeline.play();
			trainerTimeline.setOnFinished(action -> {
				trainerTimeline.getKeyFrames().removeAll(trainerTimeline.getKeyFrames());
				canvas2.requestFocus();
				currentView.drawTrainer();
				finished = true;
				if (trainer.getStepCount() == 0) {
					canvas2.setOnKeyPressed(new MessageUpdater());
					messages.add(trainer.getName() + " ran out of steps!");
					currentView.setMessage(messages.remove());
				}
			});
		} else {
			trainerTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(animationTime / 2), (KeyValue) null));
			trainerTimeline.play();
			trainerTimeline.setOnFinished(action -> {
				finished = true;
				canvas2.requestFocus();
			});
		}
	}

	/**
	 * Calls the view's overridden encounterWildPokemon method to get the correct
	 * return pokemon based on the view the player is in. Calls it with respect to
	 * the spot they walked on. Calls checkForBattle on the pokemon returned by the
	 * encounterWildPokemon method
	 * 
	 * @param gridSpot
	 */
	private void encounterPokemon(char gridSpot) {
		Pokemon wild = null;
		if (gridSpot == 'G') {
			wild = currentView.encounterWildPokemon("Grass");
		} else if (gridSpot == 'W') {
			wild = currentView.encounterWildPokemon("Water");
		} else if (gridSpot == 'D') {
			if (currentView instanceof CaveView)
				wild = currentView.encounterWildPokemon("Cave");
		}
		checkForBattle(wild);
	}

	/**
	 * If the pokemon found by encounterWildPokemon is not null, start a battle on
	 * that pokemon using Controller.setBattle Will start the battle based on the
	 * view.
	 * 
	 * @param wild
	 */
	private void checkForBattle(Pokemon wild) {
		if (wild != null) {
			// Animate battle start
			foundPokemon = wild;
			Controller.setWildBattle(foundPokemon);
		}
	}

	/**
	 * Private inner class to handle walk and biking animations for the trainer.
	 * Called on move and on tryFish(). Checks if stepcount is 0, and if it is
	 * returns the player to the town through Controller.setTown()
	 * 
	 * @author Alan
	 *
	 */
	private class WalkAnimation implements EventHandler<ActionEvent> {

		private int sx, sy, sw, sh, dx, dy, dw, dh, shift;

		/**
		 * Constructor, assigns appropriate values to draw the correct message.
		 */
		public WalkAnimation() {
			// Match the sx/sy of the source image separation
			if (!currentView.getBiking()) {
				if (oddOrEven % 2 == 0) {
					sx = 2 * currentView.getGridSize();
				} else {
					sx = 0;
				}
				shift = currentView.getGridSize();
			} else {
				if (oddOrEven % 2 == 0) {
					sx = 3 * currentView.getGridSize();
				} else {
					sx = currentView.getGridSize();
				}
				shift = currentView.getGridSize();
			}
			sy = 0;
			sw = currentView.getGridSize();
			sh = currentView.getGridSize();
			dw = dh = currentView.getGridSize();
			dx = dy = (currentView.getMidPoint()) * currentView.getGridSize();

		}

		/**
		 * Handler, called when the trainerTimeline starts on move. Adjusts frames of
		 * drawing based on walking/biking and left/right foot. (oddOrEven).
		 */
		@Override
		public void handle(ActionEvent arg0) {
			gcLayer2.clearRect(0, 0, 500, 500);
			// down, left, up, right in spritesheet.
			if (GeneralView.getDirection() == Direction.DOWN) {
				sy = 0;
			} else if (GeneralView.getDirection() == Direction.LEFT) {
				sy = currentView.getGridSize();
			} else if (GeneralView.getDirection() == Direction.UP) {
				sy = 2 * currentView.getGridSize();
			} else {
				sy = 3 * currentView.getGridSize();
			}
			if (!currentView.getBiking()) {
				gcLayer2.drawImage(trainer.getWalkImage(), sx, sy, sw, sh, dx, dy, dw, dh);
			} else {
				gcLayer2.drawImage(trainer.getBikeImage(), sx, sy, sw, sh, dx, dy, dw, dh);
			}
			// System.out.println("SX: " + sx + " Cycles: " +
			// trainerTimeline.getCycleCount());
			if (oddOrEven % 2 == 0) {
				sx -= shift;
			} else {
				sx += shift;
			}

		}

	}

	/**
	 * Called when the trainer calls tryFish and canFish. Handles animation of the
	 * fishing. Will return trainer to town if stepcount == 0.
	 * 
	 * @author Alan
	 *
	 */
	private class FishAnimation implements EventHandler<ActionEvent> {
		private int sx, sy, sw, sh, dx, dy, dw, dh, tic;

		public FishAnimation() {
			// Match the sx/sy of the source image separation
			sx = 0;
			sy = 13;
			if (GeneralView.getDirection() == Direction.LEFT) {
				sy += currentView.getGridSize();
			} else if (GeneralView.getDirection() == Direction.DOWN) {
				sy = 2 * (currentView.getGridSize() + heightChange);
			} else if (GeneralView.getDirection() == Direction.RIGHT) {
				sy += 3 * currentView.getGridSize() + heightChange;
			}
			sw = currentView.getGridSize();
			sh = currentView.getGridSize();
			dw = dh = currentView.getGridSize();
			dx = dy = (currentView.getMidPoint()) * currentView.getGridSize();
			tic = 0;
		}

		/**
		 * Handles the directional adjustment on the trainer fishing image to draw the
		 * animation.
		 */
		@Override
		public void handle(ActionEvent arg0) {
			if (tic < 3) {
				gcLayer2.clearRect(0, 0, 500, 500);
				// down, left, up, right in spritesheet.
				gcLayer2.drawImage(trainer.getFishImage(), sx, sy, sw, sh, dx, dy, dw, dh);
				if (GeneralView.getDirection() == Direction.UP || GeneralView.getDirection() == Direction.DOWN) {

					if (tic == 1 || tic == 2) {
						sh += heightChange;
						dh += heightChange;
						if (GeneralView.getDirection() == Direction.UP) {
							sy -= heightChange;
							dy -= heightChange;
						} else {
							dy += heightChange / 2;
						}
					}
					sx += currentView.getGridSize();

				} else if (GeneralView.getDirection() == Direction.LEFT
						|| GeneralView.getDirection() == Direction.RIGHT) {

					if (tic == 1) {
						sw += heightChange;
						sx += currentView.getGridSize() + heightChange * 2;
						dw += heightChange;
						if (GeneralView.getDirection() == Direction.LEFT) {
							dx -= heightChange / 2;
						} else {
							dx += heightChange / 2;
						}
					} else {
						sx += currentView.getGridSize();
					}
				}

				tic++;
			}
			if (tic % 3 == 0) {
				gcLayer2.clearRect(0, 0, 500, 500);
				gcLayer2.drawImage(trainer.getFishImage(), sx, sy, sw, sh, dx, dy, dw, dh);
			}
		}
	}

	private class MessageUpdater implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				if (!messages.isEmpty()) {
					currentView.setMessage(messages.remove());
				} else if (messages.isEmpty()) {

					if (currentView.getMessage().contains("Y/N")) {
						return;
					} 
					else if (currentView.getMessage().contains("ran out")) {
						Controller.setTown();
					}
					currentView.setMessage("");
					canvas2.setOnKeyPressed(currentView.getMoveHandler());

				}
			} else if (event.getCode() == KeyCode.Y) {
				if (currentView.getMessage().contains("Safari")) {
					currentView.setMessage("");
					canvas2.setOnKeyPressed(currentView.getMoveHandler());
					if (trainer.canSpendMoney(1000)) {
						trainer.pay(500);
						currentView.updateMoney();
						Controller.setSafari();
					}
					else {
						currentView.setMessage(trainer.getName() + " cannot afford a trip to the Safari Zone.");
					}
					return;
				}
				if (currentView.getMessage().contains("battle")) {
					Controller.setTrainerBattle((EnemyTrainer)currentView.getNPC('Q'));
					currentView.setMessage("");
					canvas2.setOnKeyPressed(currentView.getMoveHandler());
					return;
				}
				if (currentView.getMessage().contains("tutorial")) {
					currentView.displayInstructions();
//					currentView.setMessage("");
//					canvas2.setOnKeyPressed(currentView.getMoveHandler());
				}
			} else if (event.getCode() == KeyCode.N) {
				if (currentView.getMessage().contains("Y/N")) {
					currentView.setMessage("");
					canvas2.setOnKeyPressed(currentView.getMoveHandler());
				}
			}
		}
	}
}
