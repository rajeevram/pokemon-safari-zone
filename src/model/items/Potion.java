package model.items;

/**
 * This class represents a Potion object and it a child of the Item class. A
 * Potion is used to heal a Pokemon's HP in and out of battle.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Potion extends Item {

	/**
	 * The constructor requires a file path
	 * 
	 * @param path
	 *            the location of the image
	 */
	public Potion(String path) {
		super(path);
		this.name = "Potion";
		this.price = 100;
	}

}
