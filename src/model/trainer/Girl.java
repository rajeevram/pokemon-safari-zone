package model.trainer;

import javafx.scene.image.Image;

/**
 * This is one concrete instance of the Trainer class. The female has her own
 * sprite.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Girl extends Trainer {

	public Girl(String name) {
		super(name);
		walkImage = new Image("file:media/images/GirlWalking.png");
		fishImage = new Image("file:media/images/GirlFishing.png");
		bikeImage = new Image("file:media/images/GirlBiking.png");
		battleImage = new Image("file:media/images/GirlThrowing.png");
	}

	/**
	 * Important for resetting images persistence
	 */
	public void setImages() {
		walkImage = new Image("file:media/images/GirlWalking.png");
		fishImage = new Image("file:media/images/GirlFishing.png");
		bikeImage = new Image("file:media/images/GirlBiking.png");
		battleImage = new Image("file:media/images/GirlThrowing.png");
	}

}