package model.items;

/**
 * This class represents a Fishing Rod object and it a child of the Item class.
 * The Fishing Rod is used to fish for special Pokemon in the water.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class FishingRod extends Item {

	private static FishingRod fishingRodInstance = new FishingRod("FishingRod");

	/**
	 * The constructor requires a file path
	 * 
	 * @param path
	 *            the location of the image
	 */
	public FishingRod(String path) {
		super(path);
		this.name = "Fishing Rod";
	}

	/**
	 * Employs the Singleton design patterns to get an instance of FishingRod
	 * @return the singular instance of fishing rod in this class
	 */
	public static FishingRod getFishingRodInstance() {
		return fishingRodInstance;
	}

}
