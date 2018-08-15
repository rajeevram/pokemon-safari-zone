package model.trainer;

import javafx.scene.image.Image;
import model.npc.NPC;
import model.pokemon.Jigglypuff;
import model.pokemon.Pikachu;

/**
 * This is one concrete instance of the NPC class. However, it also extends
 * Trainer so gain important functionality.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class EnemyTrainer extends Trainer implements NPC {
	
	private String messageOne = "Do you want to battle with me? (Y/N)";
	private Image image;

	/**
	 * An enemy trainer has some Pokemon with which to fight and a name
	 * @param name
	 */
	public EnemyTrainer(String name) {
		super(name);
		addPokemon(new Pikachu("Pikachu", null, 30, 30, 30, 30));
		addPokemon(new Jigglypuff("Jigglypuff", null, 30, 30, 30, 30));		
	}
	
	/**
	 * This sets the Image for the enemy trainer
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String getMessageOne() {
		return messageOne;
	}

	@Override
	public String getMessageTwo() {
		return null;
	}
	
	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void setImages() {
		return;
	}

}
