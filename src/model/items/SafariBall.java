package model.items;

/**
 * This class represents a Safari Ball object and it a child of the Item class.
 * The Safari Ball is used to catch Pokemon in the Safari Zone.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class SafariBall extends Item {

	/**
	 * The constructor requires a file path
	 * 
	 * @param path
	 *            the location of the image
	 */
	public SafariBall(String path) {
		super(path);
		this.name = "Safari Ball";
	}

}
