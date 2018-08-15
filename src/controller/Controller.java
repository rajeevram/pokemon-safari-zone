package controller;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Location;
import model.pokemon.Pokemon;
import model.pokemon.Seaking;
import model.pokemon.Staryu;
import model.pokemon.Tentacool;
import model.trainer.EnemyTrainer;
import model.trainer.Trainer;
import views.battle.BattleView;
import views.battle.CaveBattle;
import views.battle.GrassBattle;
import views.battle.TownBattle;
import views.battle.WaterBattle;
import views.general.BuildingView;
import views.general.CaveView;
import views.general.ChooseTrainerView;
import views.general.GeneralView;
import views.general.ParentView;
import views.general.SafariView;
import views.general.TownView;

/**
 * This class is the coordinator for the entire Pokemon game, and it aptly named
 * the Controller. As you will see it is not an object, an indeed, almost
 * everything in this class is static. The Controller mediates between the
 * different views that the player will encounter. However, it is these views
 * that contain the event handlers rather than the Controller itself.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Controller extends Application {

	/*----------Static Variables----------*/
	private static ParentView currentView;
	private static BattleView currentBattle;
	private static Scene scene;
	private static BorderPane window;
	private static Pokemon currentPokemon;
	private static Trainer trainer;
	private static EnemyTrainer enemy;
	private static ArrayList<GeneralView> views = new ArrayList<>();
	private final static String saveStateFileName = "PokemonGameSaveFile";

	/*----------Main Method----------*/
	public static void main(String[] args) {
		launch(args);
	}

	/*----------Start Method----------*/
	/**
	 * This method starts the game with a general view. Right now, only the Safari
	 * View is written. Later, the player will always start in the Town View.
	 */
	public void start(Stage stage) {
		window = new BorderPane();
		scene = new Scene(window, 800, 700);
		if (!loadSaveState()) {
			startMenu();
		} else {
			saveStateSetup();
		}
		// startSetup();
		// startMenu();
		stage.setScene(scene);
		stage.show();
	}

	/*----------Change & Update View Methods----------*/

	/**
	 * Initializes the screen in which the player chooses the trainer.
	 */
	public static void startMenu() {
		currentView = new ChooseTrainerView(trainer);
		window.setCenter(currentView);
		controller.SoundPlayer.playSongRepeat("ChooseTrainer", 10000, 18100);
	}

	/**
	 * Called after the player chooses his or her trainer to set up the maps and
	 * everything else in the game
	 */
	public static void startSetup() {
		trainer = ((ChooseTrainerView) currentView).getTrainer();
		// trainer = new Girl("Alan");
		trainer.getStarterPokemon();
		views.add(new BuildingView(trainer));
		views.add(new TownView(trainer));
		views.add(new SafariView(trainer));
		views.add(new CaveView(trainer));
		GeneralView.setNPCS();
		currentView = views.get(0);
		for (GeneralView view : views) {
			view.setupMap();
		}
		startGame();
	}

	public static void saveStateSetup() {
		Location location = trainer.getLocation();
		if (location == Location.BUILDING) {
			currentView = views.get(0);
		} else if (location == Location.TOWN) {
			currentView = views.get(1);
		} else if (location == Location.SAFARI) {
			currentView = views.get(2);
		} else if (location == Location.CAVE) {
			currentView = views.get(3);
		}
		startGame();
	}

	/**
	 * This method resets to a non-battle view, and it always called after a battle
	 * finishes.
	 */
	private static void startGame() {
		SoundPlayer.stopSound();
		if (((GeneralView) currentView).getBiking()) {
			SoundPlayer.playSongRepeat("BicycleRide", 4150, 32000);
		} else {
			if (currentView == views.get(0)) {
				SoundPlayer.playSongRepeat("BuildingMusic", 7350, 59790);
			} else if (currentView == views.get(1)) {
				SoundPlayer.playSongRepeat("TownMusic", 4400, 52300);
			} else if (currentView == views.get(2)) {
				SoundPlayer.playSongRepeat("SafariZone", 1125, 45000);
			} else if (currentView == views.get(3)) {
				SoundPlayer.playSongRepeat("CaveMusic", 13200, 79000);
			}
		}
		((GeneralView) currentView).setRunning(false);
		((GeneralView) currentView).drawTrainer();
		trainer.setLocation(currentView);
		window.setCenter(currentView);

	}

	/**
	 * Changes the view to the safari zone
	 */
	public static void setSafari() {
		currentView = views.get(2);
		((GeneralView)currentView).updateMoney();
		((GeneralView)currentView).setEntrancePoint(new Point(24,38));
		((SafariView)currentView).setMap();
		startGame();
	}

	/**
	 * Changes the view to the town
	 */
	public static void setTown() {
		
		
		currentView = views.get(1);
		((GeneralView)currentView).displayStatistics();
		trainer.resetCaughtPokemon();
		startGame();
	}
	
	public static void loseToTown() {
		// reset after statistics
		currentView = views.get(1);
		((GeneralView)currentView).setMessage(trainer.getName() + " ran out of Safari Balls!");
		((GeneralView)currentView).displayStatistics();
		trainer.resetCaughtPokemon();
		startGame();
	}
	
	public static void winToTown() {
		// reset after statistics
		currentView = views.get(1);
		((GeneralView)currentView).setMessage(trainer.winCondition());
		((GeneralView)currentView).displayStatistics();
		trainer.resetCaughtPokemon();
		trainer.addMoney(3000);
		startGame();
	}

	/**
	 * This method determines to which view an entrance takes the character
	 */
	public static void entranceChangeView() {
		SoundPlayer.stopSound();
		if (currentView == views.get(2)) {
			currentView = views.get(3);
			trainer.setLocation(currentView);
		} else if (currentView == views.get(3)) {
			currentView = views.get(2);
			trainer.setLocation(currentView);
		} else if (currentView == views.get(1)) {
			currentView = views.get(0);
			trainer.setLocation(currentView);
		} else if (currentView == views.get(0)) {
			currentView = views.get(1);
			trainer.setLocation(currentView);
		}
		((GeneralView) currentView).setMap();
		startGame();
	}

	/**
	 * This methods creates a battle view, and it always called when the player is
	 * in a non-battle view.
	 * @param foundPokemon the Pokemon that was encountered in the wild
	 */
	public static void setWildBattle(Pokemon foundPokemon) {
		Controller.currentPokemon = foundPokemon;
		if (trainer.getLocation() == Location.SAFARI) {
			if (currentPokemon instanceof Staryu || currentPokemon instanceof Tentacool) {
				currentBattle = new WaterBattle(trainer, currentPokemon);
			} else {
				currentBattle = new GrassBattle(trainer, currentPokemon);
			}
		} else if (trainer.getLocation() == Location.CAVE) {
			if (currentPokemon instanceof Seaking) {
				currentBattle = new WaterBattle(trainer, currentPokemon);
			} else {
				currentBattle = new CaveBattle(trainer, currentPokemon);
			}	
		} else if (trainer.getLocation() == Location.TOWN) {
			currentBattle = new TownBattle(trainer, currentPokemon);
		}
		SoundPlayer.stopSound();
		startWildBattle(currentBattle);
	}
	
	public static void setTrainerBattle(EnemyTrainer enemy) {
		Controller.enemy = enemy;
		Controller.currentPokemon = enemy.getAlivePokemon();
		currentBattle = new TownBattle(trainer,currentPokemon);
		currentBattle.setTrainerBattle(true);
		SoundPlayer.stopSound();
		startTrainerBattle(currentBattle);
	}
	
	private static void startTrainerBattle(BattleView newView) {
		window.setCenter(newView);
		newView.disableRunAway();
		newView.setPadding(new Insets(0, 0, 0, 70));
		SoundPlayer.playSongRepeat("TrainerBattle", 16450, 53025);
	}


	/**
	 * This method starts the battle by setting the battle view in the GUI.
	 */
	private static void startWildBattle(BattleView newView) {
		window.setCenter(newView);
		newView.setPadding(new Insets(0, 0, 0, 70));
		SoundPlayer.playSongRepeat("WildBattle", 16450, 53025);
	}

	/**
	 * This end the battle and changes the battle view to a non-battle-view
	 */
	public static void endWildBattle() {
		trainer.resetMoveCount();
		SoundPlayer.stopSound();
		startGame();
		trainer.resetStatusEffects();
	}

	public static void endTrainerBattle() {
		SoundPlayer.stopSound();
		startGame();
		if(((TownBattle)currentBattle).getDidPlayerWin()) {
			int gotAmount = (int)(Math.random()*500);
			trainer.addMoney(gotAmount);
			((GeneralView)currentView).setMessage(trainer.getName() + " won " + gotAmount + ".");
			((GeneralView)currentView).updateMoney();
		}
		else {
			((GeneralView)currentView).setMessage(trainer.getName() + " lost.");
		}
		trainer.healPokemon();
		trainer.resetStatusEffects();
		enemy.healPokemon();
		enemy.resetStatusEffects();
	}
	
	@SuppressWarnings("unchecked")
	private static boolean loadSaveState() {

		Alert exitAlert = new Alert(AlertType.CONFIRMATION);
		exitAlert.setHeaderText("Do you want load your save file?");
		Optional<ButtonType> result = exitAlert.showAndWait();

		if (result.get() == ButtonType.OK) {
			try {
				FileInputStream fileInput = new FileInputStream(saveStateFileName);
				ObjectInputStream in = new ObjectInputStream(fileInput);

				// Read the trainer from the save file
				trainer = (Trainer) in.readObject();
				trainer.setImages();

				// Read the character grids for the view from the save file
				ArrayList<Character[][]> grids = (ArrayList<Character[][]>) in.readObject();
				ArrayList<Point> positions = (ArrayList<Point>) in.readObject();
				// Create four new view objects
				views.add(new BuildingView(trainer));
				views.add(new TownView(trainer));
				views.add(new SafariView(trainer));
				views.add(new CaveView(trainer));

				GeneralView.setNPCS();

				// Set the views with the saved grids
				int i = 0;
				for (GeneralView view : views) {
					view.setupMap();
					view.setEntrancePoint(positions.get(i));
					view.loadMap(grids.get(i));

					view.updateMap();
					view.drawView();
					i++;
				}

				in.close();
			} catch (IOException | ClassNotFoundException | ClassCastException e) {
				System.err.println("Error occured while loading save file.");
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;

	}
	
	public static SaveHandler generateSaveHandler() {
		return SaveHandler.generateSaveHandler();
	}

	/*----------Save & Exit Handlers----------*/

	private static class SaveHandler implements EventHandler<ActionEvent> {

		private static SaveHandler instance;

		private static SaveHandler generateSaveHandler() {
			if (instance == null) {
				instance = new SaveHandler();
			}
			return instance;
		}

		@Override
		public void handle(ActionEvent event) {

			Alert exitAlert = new Alert(AlertType.CONFIRMATION);
			exitAlert.setHeaderText("Do you want to save your game?");
			Optional<ButtonType> result = exitAlert.showAndWait();

			if (result.get() == ButtonType.OK) {

				try {
					FileOutputStream fileOutput = new FileOutputStream(saveStateFileName);
					ObjectOutputStream out = new ObjectOutputStream(fileOutput);

					// Write the trainer to the file
					((GeneralView) currentView).setTrainerPosition(((GeneralView) currentView).getTrainerPosition());
					out.writeObject(trainer);

					// Create a list to store the view character grids
					ArrayList<Character[][]> grids = new ArrayList<Character[][]>();

					// In a row, add the character grids to the list
					for (GeneralView view : views) {
						grids.add(view.getMap());
					}

					ArrayList<Point> positions = new ArrayList<Point>();

					for (GeneralView view : views) {
						positions.add(view.getTrainerPosition());
					}

					// Writes the views to the file
					out.writeObject(grids);

					out.writeObject(positions);

					out.close();
				} catch (IOException e) {
					System.err.println("Error occured while saving game to file.");
					e.printStackTrace();
				}

			}

		}

	}

}
