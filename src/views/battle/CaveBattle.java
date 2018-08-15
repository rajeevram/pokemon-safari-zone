package views.battle;

import javafx.scene.image.Image;
import model.pokemon.Pokemon;
import model.trainer.Trainer;

/**
 * This is one concrete realization of the Battle View abstract class.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class CaveBattle extends SafariBattle {

	public CaveBattle(Trainer trainer, Pokemon pokemon) {
		super(trainer, pokemon);
		pokemonImage = new Image(pokemon.getImage());
		initializePane();
	}
	
	private void initializePane() {
		
		background = new Image("file:media/images/CaveBackground.png");
		gc1.drawImage(background, 0, -50, 800, 610);
		lowGridPaneMethods();
		this.getChildren().addAll(canvas1, canvas3, canvas2, pane2, pane1, pane3);

	}

}
