package model.items;

import java.io.Serializable;

import javafx.scene.image.Image;

/**
 * This class is abstract because it serves as a parent to many child Item
 * objects. Each concrete item has an associated image sprite.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public abstract class Item implements Comparable<Item>, Serializable {

	protected static transient Image image;
	protected String name;
	protected Integer count = 1;
	protected int price;

	/**
	 * The constructor requires a file path
	 * 
	 * @param path
	 *            the location of the image
	 */
	public Item(String path) {
		this.image = new Image("file:media/images/" + path);
	}

	/**
	 * This method retrieves the image for this Item
	 * 
	 * @return image object
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Increment the count of an item in inventory
	 */
	public void increaseCount() {
		count++;
	}

	/**
	 * Decrement the count of an item in inventory
	 */
	public void decreaseCount() {
		if (count >= 0) {
			count--;
		}
	}

	/**
	 * Get the count of items in the inventory
	 * @return
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * Set the sprite sheet for items; important for persistence
	 */
	public static void setImage() {
		image = new Image("file:media/images/ItemSprites.png");
	}

	/**
	 * Retrieve the sprite sheet for items
	 * @return
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Get the price of this item
	 * @return
	 */
	public int getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Item other) {
		return this.name.compareTo(other.name);
	}

}
