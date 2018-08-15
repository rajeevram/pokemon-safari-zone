package views.battle;

import javafx.scene.image.Image;
import model.pokemon.Pokemon;
import model.trainer.Trainer;

public class WaterBattle extends SafariBattle {

	Image backGround = new Image("file:media/images/waterBackGround.jpg");

	public WaterBattle(Trainer trainer, Pokemon currentPokemon) {
		super(trainer, currentPokemon);
		pokemonImage = new Image(currentPokemon.getImage());
		background = new Image("file:media/images/BackgroundWithWater.png");
		gc1.drawImage(backGround, 0, -200, 800, 750);
		lowGridPaneMethods();
		this.getChildren().addAll(canvas1, canvas2, canvas3, pane1, pane2, pane3);
	}

}
