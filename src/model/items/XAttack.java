package model.items;

/**
 * This class represents an XAttack object and it a child of the Item class.
 * Used to increase the attack of the pokemon temporarily.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class XAttack extends Item {

	/**
	 * The constructor requires a file path
	 * 
	 * @param path
	 *            the location of the image
	 */
	public XAttack(String path) {
		super(path);
		this.name = "XAttack";
		this.price = 100;
	}

}
