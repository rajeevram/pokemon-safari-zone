package model.items;

/**
 * This class represents a Bicycle object and it a child of the Item class. The
 * Bicycle is used to move more quickly around the map.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Bicycle extends Item {

	private static Bicycle bicycleInstance = new Bicycle("Bicycle");

	/**
	 * The constructor requires a file path
	 * 
	 * @param path
	 *            the location of the image
	 */
	public Bicycle(String path) {
		super(path);
		this.name = "Bicycle";
	}

	/**
	 * Employs the Singleton design patterns to get an instance of Bicycle
	 * @return the singular instance of bicycle in this class
	 */
	public static Bicycle getBicycleInstance() {
		return bicycleInstance;
	}

}
