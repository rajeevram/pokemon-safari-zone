package model.items;

/**
 * This class represents an XDefense object and it a child of the Item class.
 * Used to increase the defense of the pokemon temporarily.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class XDefense extends Item {

	/**
	 * The constructor requires a file path
	 * 
	 * @param path
	 *            the location of the image
	 */
	public XDefense(String path) {
		super(path);
		this.name = "XDefense";
		this.price = 100;
	}

}