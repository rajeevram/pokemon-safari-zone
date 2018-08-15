package views.battle;

import java.util.Random;

import controller.Controller;
import controller.SoundPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.items.Bicycle;
import model.items.FishingRod;
import model.items.Item;
import model.items.Potion;
import model.items.XAttack;
import model.items.XDefense;
import model.observable.InventoryViewer;
import model.observable.PokemonViewer;
import model.pokemon.Pokemon;
import model.trainer.Trainer;

/**
 * This is one concrete realization of the Battle View abstract class.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class TownBattle extends BattleView {

	private Pokemon enemyPokemon;
	private Pokemon currentBattlePokemon;

	private PokemonViewer pokemonViewer;
	private InventoryViewer inventoryViewer;
	private boolean playerWon;

	private Image myPokemonImage;

	private Timeline StandingPokemonBack, trainerPokemonGettingHit;

	private GridPane tablePane = new GridPane();

	private ProgressBar enemyProgressBar = new ProgressBar(1);
	private ProgressBar currentPokemonProgressBar = new ProgressBar(1);

	private Button fight = new Button("Fight");
	private Button switchPoke = new Button("Switch Pokemon");
	private Button items = new Button("Items");
	private Button use = new Button("Use");
	private Button change = new Button("Change");
	private Button select = new Button("Select");

	private Button move1 = new Button();
	private Button move2 = new Button();
	private Button move3 = new Button();
	private Button move4 = new Button();

	private Item selectedItem;

	private Label myHpBar;
	private Label enemyHpBar;

	private Timeline stall = new Timeline(new KeyFrame(Duration.millis(10), new Stalling()));

	/**
	 * This is a hefty constructor
	 * 
	 * @param trainer
	 *            the user itself
	 * @param currentPokemon
	 *            the Pokemon to battle
	 */
	public TownBattle(Trainer trainer, Pokemon currentPokemon) {

		super(trainer, currentPokemon);

		pokemonImage = new Image(currentPokemon.getImage());
		this.enemyPokemon = currentPokemon;

		pokemonImage = new Image("file:media/images/" + enemyPokemon.getName() + "Front.png");
		if (trainer.numAlivePokemon() == 0) {
			endBattle();
			return;
		}

		currentBattlePokemon = trainer.getOwnedPokemon().get(0);
		myPokemonImage = new Image("file:media/images/" + currentBattlePokemon.getName() + "Back.png");

		stall.setCycleCount(300);

		initializePane();

	}

	/**
	 * Initializes pane
	 */
	private void initializePane() {
		myHpBar = new Label("My " + currentBattlePokemon.getName() + "'s Hit Points:");
		enemyHpBar = new Label("Enemy " + enemyPokemon.getName() + "'s Hit Points:");

		tableViewMethods();

		background = new Image("file:media/images/GrassBackground.png");
		gc1.drawImage(background, 0, 0, 800, 580);

		// GrassAnimation = new Timeline(new KeyFrame(Duration.millis(5), new
		// GrassAnimation()));
		// GrassAnimation.setCycleCount(600);
		// GrassAnimation.play();

		StandingPokemonBack = new Timeline(new KeyFrame(Duration.millis(500), new StandingPokemonBack()));
		StandingPokemonBack.setCycleCount(400);
		StandingPokemonBack.play();

		pokemonGettingHit = new Timeline(new KeyFrame(Duration.millis(100), new PokemonGettingHit()));
		pokemonGettingHit.setCycleCount(15);
		
		decrementProgressBar();
		trainerPokemonGettingHit = new Timeline(new KeyFrame(Duration.millis(100), new PokemonGettingHit2()));
        	trainerPokemonGettingHit.setCycleCount(15);
        
		// GrassAnimation.setOnFinished(new EventHandler<ActionEvent>() {
		//
		// public void handle(ActionEvent arg0) {
		// lowGridPaneMethods();
		//
		//
		// }
		// });

		lowGridPaneMethods();
		highGridPaneMethods();

		this.getChildren().addAll(canvas1, canvas3, canvas2, pane1, pane2, pane3);

	}

	/*----------Pane Layout Methods----------*/

	/**
	 * This method sets the layout for the high grid pane
	 */
	protected void highGridPaneMethods() {

		pane2.setHgap(15);
		pane2.setVgap(10);

		myHpBar.setFont(font);
		enemyHpBar.setFont(font);

		pane2.add(enemyHpBar, 1, 1, 1, 1);
		pane2.add(myHpBar, 2, 1, 1, 1);

		pane2.add(enemyProgressBar, 1, 2);
		pane2.add(currentPokemonProgressBar, 2, 2, 1, 1);
		enemyProgressBar.setMinWidth(250);
		currentPokemonProgressBar.setMinWidth(250);
		pane2.setPadding(new Insets(0, 0, 0, 15));

	}

	/**
	 * This method adds and styles panes, buttons, and labels to the lower grid and
	 * applies event handlers to the item, select, fight, switch pokemon, and run
	 * away buttons
	 * 
	 */
	private void lowGridPaneMethods() {

		status.setMinWidth(300);
		status.setMinHeight(80);
		pane3.setPadding(new Insets(580, 300, 200, 50));
		status.setFont(font);
		pane3.add(status, 0, 0);
		pane1.setPadding(new Insets(550, 700, 200, 400));

		items.setMinWidth(200);
		items.setMinHeight(80);
		items.setStyle("-fx-background-color:#33FFCC;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		switchPoke.setMinWidth(200);
		switchPoke.setMinHeight(80);
		switchPoke.setStyle("-fx-background-color:#F08080;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		fight.setMinWidth(200);
		fight.setMinHeight(80);
		fight.setStyle("-fx-background-color:#FFD700;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		runAway.setMinWidth(200);
		runAway.setMinHeight(80);
		runAway.setStyle("-fx-background-color:#00BFFF;" + "-fx-border-color:black;" + "-fx-border-radius:5px");
		pane1.add(items, 0, 0);
		pane1.add(switchPoke, 0, 1);
		pane1.add(fight, 1, 0);
		pane1.add(runAway, 1, 1);

		/**
		 * This event handler calls the fight method when clicked
		 */

		fight.setOnAction(event -> {
			status.setText("");
			tablePane.getChildren().clear();
			setLeft(null);
			fight();
		});

	}

	/*
	 * This method organizes the layout for all methods pertaining to the table
	 * views
	 */
	private synchronized void tableViewMethods() {
		pokemonViewer = new PokemonViewer(trainer);
		inventoryViewer = new InventoryViewer(trainer);

		// this.setMargin(tablePane, new Insets(150,0,0,30));
		//
		// Insets buttonInsets = new Insets(5,0,0,85);
		// tablePane.setMargin(use, buttonInsets);
		// tablePane.setMargin(change, buttonInsets);
		//
		// tablePane.setMargin(select, new Insets(5,0,0,80));

		use.setMinWidth(35);
		use.setMinHeight(35);
		use.setFont(font);
		use.setStyle("-fx-background-color:#F08080;" + " -fx-border-color:black;" + " -fx-border-radius:5px");

		select.setMinWidth(35);
		select.setMinHeight(35);
		select.setFont(font);
		select.setStyle("-fx-background-color:#F08080;" + " -fx-border-color:black;" + " -fx-border-radius:5px");

		change.setMinWidth(35);
		change.setMinHeight(35);
		change.setFont(font);
		change.setStyle("-fx-background-color:#F08080;" + " -fx-border-color:black;" + " -fx-border-radius:5px");

		/**
		 * This event handler displays the table view of the trainer's inventory.
		 */

		items.setOnAction(event -> {
			clearTableViewItems();
			inventoryViewer.refresh();
			inventoryViewer.update();
			if (trainer.getInventorySize() == 0) {
				tablePane.getChildren().clear();
				status.setText("You have no items!");
			} else {
				status.setText("Select an item to use");
				tablePane.add(inventoryViewer, 0, 0);
				tablePane.add(use, 0, 1);
				setLeft(tablePane);
				use.setOnAction(new InventoryButtonListener());
			}
		});

		/**
		 * This handler gets the selected pokemon from trainer's inventory in the
		 * tableview and calls the inventoryItemEffect method that applies the selected
		 * inventory item to the selected pokemon
		 */

		select.setOnAction(event -> {
			clearTableViewItems();

			// pokemonViewer.refresh();
			// pokemonViewer.update();
			status.setText("");
			Pokemon selectedPokemon = pokemonViewer.getSelectionModel().getSelectedItem();
			inventoryItemEffect(selectedItem, selectedPokemon);
			enemyTurn();

		});

		/**
		 * This event handler makes pokemon table view visible and allows user to select
		 * a pokemon to use for next turn
		 */
		switchPoke.setOnAction(event -> {
			clearTableViewItems();
			status.setText("Select a Pokemon");
			if (trainer.getOwnedPokemon().size() == 0) {
				status.setText("You have no Pokemon!");
			} else {
				tablePane.add(pokemonViewer, 0, 0);
				tablePane.add(change, 0, 1);
				setLeft(tablePane);
				change.setOnAction(new PokemonButtonListener());
			}
		});

	}

	/*----------Strategy Methods----------*/
	/**
	 * Change the hit points bar as necessary
	 */
	public void decrementProgressBar() {
		double prevEnemyHP = enemyProgressBar.getProgress();
		enemyProgressBar.setProgress((double) enemyPokemon.getCurrentHP() / (double) enemyPokemon.getHitPoints());
		double currentEnemyHP = enemyProgressBar.getProgress();
		if (prevEnemyHP > currentEnemyHP+.01 ) {
			pokemonGettingHit.play();
			SoundPlayer.playEffectOnce("MoveAttack");
		}
		double prevHP = currentPokemonProgressBar.getProgress();
		currentPokemonProgressBar.setProgress(
				(double) currentBattlePokemon.getCurrentHP() / (double) currentBattlePokemon.getHitPoints());
		double currentHP = currentPokemonProgressBar.getProgress();
		if (prevHP > currentHP+.01 ) {
			if (trainerPokemonGettingHit != null) {
				trainerPokemonGettingHit.play();
				SoundPlayer.playEffectOnce("MoveAttack");
			}
		}
		pokemonViewer.refresh();
		pokemonViewer.update();
	}

	/**
	 * This method displays the different fight moves availble
	 */
	private void fight() {
		pane1.getChildren().removeAll(pane1.getChildren());
		move1.setMinWidth(200);
		move1.setMinHeight(80);
		move1.setStyle("-fx-background-color:#33FFCC;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		move1.setText(currentBattlePokemon.getMoveOne());
		move2.setMinWidth(200);
		move2.setMinHeight(80);
		move2.setStyle("-fx-background-color:#F08080;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		move2.setText(currentBattlePokemon.getMoveTwo());
		move3.setMinWidth(200);
		move3.setMinHeight(80);
		move3.setStyle("-fx-background-color:#FFD700;" + " -fx-border-color:black;" + " -fx-border-radius:5px");
		move3.setText(currentBattlePokemon.getMoveThree());
		move4.setMinWidth(200);
		move4.setMinHeight(80);
		move4.setStyle("-fx-background-color:#00BFFF;" + "-fx-border-color:black;" + "-fx-border-radius:5px");
		move4.setText(currentBattlePokemon.getMoveFour());
		pane1.add(move1, 0, 0);
		pane1.add(move2, 0, 1);
		pane1.add(move3, 1, 0);
		pane1.add(move4, 1, 1);

		move1.setOnAction(event -> {
            currentBattlePokemon.moveOne(enemyPokemon);
            status.setText(currentBattlePokemon.getName() + " used " + currentBattlePokemon.getMoveOne() + " !!");
            endTurn();
        });

        move2.setOnAction(event -> {
            currentBattlePokemon.moveTwo(enemyPokemon);
            status.setText(currentBattlePokemon.getName() + " used " + currentBattlePokemon.getMoveTwo() + " !!");
            endTurn();
        });

        move3.setOnAction(event -> {
            currentBattlePokemon.moveThree(enemyPokemon);
            status.setText(currentBattlePokemon.getName() + " used " + currentBattlePokemon.getMoveThree() + " !!");
            endTurn();
        });
        
        
        move4.setOnAction(event -> {
            currentBattlePokemon.moveFour(enemyPokemon);
            status.setText(currentBattlePokemon.getName() + " used " + currentBattlePokemon.getMoveFour() + " !!");
            endTurn();
        });

	}

	public void endBattle() {
		if(isTrainerBattle) {
			Controller.endTrainerBattle();
		}
		else {
			Controller.endWildBattle();
		}
	}

	/*----------Animation Methods*----------*/

	private class StandingPokemonBack implements EventHandler<ActionEvent> {

		double sx = 0, sy = 0;
		double sw = 80;
		double sh = 80;

		@Override
		public void handle(ActionEvent event) {

			gc3.clearRect(0, 0, 800, 800);
			gc3.drawImage(pokemonImage, sx, sy, sw, sh, 480, 30, 220, 250);
			gc3.drawImage(myPokemonImage, sx, sy, sw, sh, 80, 340, 220, 250);
			gc3.drawImage(bar, -1, 550, 400, 150);
			sx += 80;
			if (sx == 160) {
				sx = 0;
			}

		}
	}

	/*----------Event Handlers----------*/

	private class Stalling implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
		}

	}

	/*
	 * This method displays the trainer's inventory, gets selected item and calls
	 * method that applies it to the selected pokemon
	 */
	private class InventoryButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {

			// Determine which row is selected
			selectedItem = inventoryViewer.getSelectionModel().getSelectedItem();
			if (selectedItem == null) {
				status.setText("Select an item");
				return;
			}
			else if (selectedItem instanceof FishingRod || selectedItem instanceof Bicycle) {
				status.setText("Item cannot be used");
				return;
			}
			clearTableViewItems();
			selectedItem.decreaseCount();
			if (selectedItem.getCount() <= 0) {
				trainer.removeItem(selectedItem);
			}
			status.setText("Use this item on...?");
			tablePane.add(pokemonViewer, 0, 0);
			tablePane.add(select, 0, 1);
			setLeft(tablePane);

			inventoryViewer.refresh();
			inventoryViewer.update();
		}
	}

	/*
	 * This method displays the trainer's owned pokemon and gets the selected
	 * pokemon to use as the current battle pokemon
	 */
	private class PokemonButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// Determine which row is selected
			while (pokemonViewer.getSelectionModel().getSelectedItem().getCurrentHP() <= 0) {
				status.setText(currentBattlePokemon.getName() + " has 0 HP and can't battle!");
				pokemonViewer.getSelectionModel().clearSelection();
			}
			if(currentBattlePokemon != pokemonViewer.getSelectionModel().getSelectedItem()) {
				currentBattlePokemon = pokemonViewer.getSelectionModel().getSelectedItem();
			}
			else {
				status.setText("That pokemon is already active!");
				return;
			}
			
			decrementProgressBar();
			clearTableViewItems();
			enemyTurn();
		}
	}

	// private class MessageUpdater implements EventHandler<KeyEvent> {
	//
	// @Override
	// public void handle(KeyEvent event) {
	// if (event.getCode() == KeyCode.ENTER) {
	// if (!messages.isEmpty()) {
	// if (messages.peek().contains("Caught")) {
	// SoundPlayer.stopSound();
	// SoundPlayer.playEffectOnce("Capture");
	// }
	// status.setText(messages.remove());
	// }
	// else if (!battleEnds) {
	// status.setText("What will you do?");
	// }
	// else if (battleEnds && messages.isEmpty()) {
	// if ( !SoundPlayer.isPlayingEffect() ) {
	// endBattle();
	// }
	// }
	// }
	// }
	//
	// }

	/**
	 * This method determines who the selected item will effect the selected pokemon
	 * 
	 * @param selectedItem
	 * @param selectedPokemon
	 */
	private void inventoryItemEffect(Item selectedItem, Pokemon selectedPokemon) {
		if (selectedItem instanceof Potion) {
			selectedPokemon.potionHPIncrease();
			status.setText("You increased " + selectedPokemon.getName() + "'s HP");
		} else if (selectedItem instanceof XAttack) {
			selectedPokemon.raiseAttack(1); // adds to pokemon's attack
			status.setText("Increased " + selectedPokemon.getName() + "'s attack!");
		} else if (selectedItem instanceof XDefense) {
			selectedPokemon.raiseDefense(1); // adds to pokemon's defense
			status.setText("Raised " + selectedPokemon.getName() + "'s defense!");
		}

		tablePane.getChildren().clear();
		decrementProgressBar();

		stall.play();
		stall.setOnFinished(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				status.setText("What will you do?");
			}
		});

	}

	/*
	 * This method clears, and resets the text
	 */
	private void clearTableViewItems() {
		//status.setText("What will you do?");
		tablePane.getChildren().clear();
		setLeft(null);
	}

	/*
	 * This method resets the battle view after the turn ends
	 */
	private void endTurn() {
		clearTableViewItems();
		pane1.getChildren().removeAll(pane1.getChildren());
		pane1.add(items, 0, 0);
		pane1.add(switchPoke, 0, 1);
		pane1.add(fight, 1, 0);
		pane1.add(runAway, 1, 1);
		decrementProgressBar();
		enemyTurn();
	}

	/*
	 * This method uses a random value to determine which fight move that the enemy
	 * pokemon will use
	 */

	private void enemyTurn() {
		if (enemyPokemon.getCurrentHP() <= 0) {
			playerWon=true;
			endBattle();
		}
		Random gen = new Random();
		int rand = gen.nextInt(4);
		String move = "";
        if(rand < 1) {
            enemyPokemon.moveOne(currentBattlePokemon);
            move = enemyPokemon.getMoveOne();
        }
        else if (rand < 2) {
            enemyPokemon.moveTwo(currentBattlePokemon);
            move = enemyPokemon.getMoveTwo();
        }
        else if (rand < 3) {
            enemyPokemon.moveThree(currentBattlePokemon);
            move = enemyPokemon.getMoveThree();
        }
        else {
            enemyPokemon.moveFour(currentBattlePokemon);
            move = enemyPokemon.getMoveFour();
        }

        status.setText( status.getText() + "\n" + enemyPokemon.getName() + " used " + move + " !!");
		decrementProgressBar();
		if (currentBattlePokemon.getCurrentHP() <= 0) {
			pokemonViewer.update();
			pokemonViewer.refresh();
			status.setText(currentBattlePokemon.getName() + " has fainted!");

			stall.play();
			stall.setOnFinished(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent arg0) {
					status.setText("What will you do?");
				}
			});

			if (trainer.getAlivePokemon() == null) {
				playerWon=false;
				endBattle();
			} else if (trainer.numAlivePokemon() == 1) {
				currentBattlePokemon = trainer.getAlivePokemon();
			} else {
				tablePane.add(pokemonViewer, 0, 0);
				tablePane.add(use, 1, 1);
				use.setOnAction(new PokemonButtonListener());
			}
		}
	}
	

	
	protected class PokemonGettingHit2 implements EventHandler<ActionEvent> {

        private int tic = 0;

        public void handle(ActionEvent event) {

            tic++;

            StandingPokemonBack.stop();
            disableButtons();

            if (tic % 2 == 0) {
                gc3.clearRect(0, 200, 400, 353);
            } else {
                gc3.clearRect(0, 200, 400, 353);
                gc3.drawImage(myPokemonImage, 0, 0, 80, 80, 80, 340, 220, 238);
            }
            if (tic == 15) {
                tic = 0;
                StandingPokemonBack.play();
                enableButtons();
            }

        }
        
        
    }
	
	protected class PokemonGettingHit implements EventHandler<ActionEvent> {

		private int tic = 0;

		public void handle(ActionEvent event) {

			tic++;

			double sx = 80, sy = 0;
			double sw = 80;
			double sh = 80;

			StandingPokemon.stop();
			disableButtons();

			if (tic % 2 == 0) {
				gc3.clearRect(400, 0, 400, 400);
			} else {
				gc3.clearRect(400, 0, 400, 400);
				gc3.drawImage(pokemonImage, sx, sy, sw, sh, 480, 30, 220, 238);
			}
			if (tic == 15) {
				tic = 0;
				StandingPokemon.play();
				enableButtons();
			}

		}
	}
	
	private void disableButtons() {
        items.setDisable(true);
        fight.setDisable(true);
        switchPoke.setDisable(true);
        runAway.setDisable(true);
    }
    
    private void enableButtons() {
        items.setDisable(false);
        fight.setDisable(false);
        switchPoke.setDisable(false);
        if (!isTrainerBattle) {
        	runAway.setDisable(false);
        }   
    }
    
	public boolean getDidPlayerWin() {
		return playerWon;
	}

}
