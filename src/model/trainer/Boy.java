package model.trainer;

import javafx.scene.image.Image;

/**
 * This is one concrete instance of the Trainer class. The male has his own
 * sprite.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Boy extends Trainer {

	public Boy(String name) {
		super(name);
		walkImage = new Image("file:media/images/BoyWalking.png");
		fishImage = new Image("file:media/images/BoyFishing.png");
		bikeImage = new Image("file:media/images/BoyBiking.png");
		battleImage = new Image("file:media/images/BoyThrowing.png");
	}

	/**
	 * Important for resetting images persistence
	 */
	public void setImages() {
		walkImage = new Image("file:media/images/BoyWalking.png");
		fishImage = new Image("file:media/images/BoyFishing.png");
		bikeImage = new Image("file:media/images/BoyBiking.png");
		battleImage = new Image("file:media/images/BoyThrowing.png");
	}

}
