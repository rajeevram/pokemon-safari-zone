package views.battle;

import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.pokemon.Pokemon;
import model.trainer.Trainer;

public abstract class BattleView extends BorderPane {

	// Graphical Instance Variables
	protected Canvas canvas1 = new Canvas(800, 800);
	protected Canvas canvas2 = new Canvas(800, 800);
	protected Canvas canvas3 = new Canvas(800, 800);
	protected GraphicsContext gc1 = canvas1.getGraphicsContext2D();
	protected GraphicsContext gc2 = canvas2.getGraphicsContext2D();
	protected GraphicsContext gc3 = canvas3.getGraphicsContext2D();
	protected GridPane pane1, pane2, pane3;
	protected Image background;
	protected Image boy = new Image("file:media/images/BoyThrowing.png");
	protected Image girl = new Image("file:media/images/GirlThrowing.png");
	protected Trainer trainer;
	protected Pokemon currentPokemon;
	protected Timeline pokemonGettingHit, GrassAnimation, StandingPokemon, trainerThrowing;
	protected Image bar = new Image("file:media/images/Message.png");
	protected Image pokemonImage;
	protected ProgressBar progressBar = new ProgressBar(2);
	protected Label status = new Label("What will you do?");
	protected Button runAway = new Button("Run Away");
	protected boolean isTrainerBattle;

	protected final Font font = new Font("Verdana", 14);

	public BattleView(Trainer trainer, Pokemon currentPokemon) {
		this.trainer = trainer;
		this.currentPokemon = currentPokemon;

		pane1 = new GridPane();
		pane2 = new GridPane();
		pane3 = new GridPane();

		StandingPokemon = new Timeline(new KeyFrame(Duration.millis(500), new StandingPokemonAnimation()));
		StandingPokemon.setCycleCount(400);
		StandingPokemon.play();

		runAway.setOnAction(event -> {
			Controller.endWildBattle();
		});

	}

	protected void highGridPaneMethods() {

		pane2.setHgap(15);
		pane2.setVgap(10);
		Label hpBar = new Label("Current Hit Points:");
		pane2.add(hpBar, 1, 1, 1, 1);
		pane2.add(progressBar, 1, 2);
		progressBar.setMinWidth(250);
		pane2.setPadding(new Insets(0, 0, 0, 15));

	}

	protected class PokemonGettingHit implements EventHandler<ActionEvent> {

		private int tic = 0;

		public void handle(ActionEvent event) {

			tic++;

			double sx = 80, sy = 0;
			double sw = 80;
			double sh = 80;

			StandingPokemon.stop();

			if (tic % 2 == 0) {
				gc3.clearRect(400, 0, 400, 400);
			} else {
				gc3.clearRect(400, 0, 400, 400);
				gc3.drawImage(pokemonImage, sx, sy, sw, sh, 480, 30, 220, 238);
			}
			if (tic == 15) {
				tic = 0;
				StandingPokemon.play();
			}

		}
	}

	protected class StandingPokemonAnimation implements EventHandler<ActionEvent> {

		double sx = 0, sy = 0;
		double sw = 80;
		double sh = 80;

		public void handle(ActionEvent event) {

			gc3.clearRect(400, 0, 800, 800);
			gc3.drawImage(pokemonImage, sx, sy, sw, sh, 480, 30, 220, 238);

			sx += 80;
			if (sx == 160) {
				sx = 0;
			}

		}
	}
	
	public void disableRunAway() {
		runAway.setDisable(true);
	}

	public boolean getTrainerBattle() {
		return isTrainerBattle;
	}
	
	public void setTrainerBattle(boolean trainer) {
		isTrainerBattle = trainer;
	}
	
	
	
	

}
