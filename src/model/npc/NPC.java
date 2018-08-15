package model.npc;

import javafx.scene.image.Image;

/**
 * This class represents an NPC object. Most NPCs are interaction-limited,
 * meaning they only hold brief conversations. A few NPCs can initiate a Trainer
 * battle.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public interface NPC {

	/**
	 * NPCs has different messages. This is the first one.
	 * @return String
	 */
	public String getMessageOne();

	/**
	 * NPCs has different messages. This is the second one.
	 * @return String
	 */
	public String getMessageTwo();

	/**
	 * NPCs have their own images from sprites
	 * @return String
	 */
	public Image getImage();

	/**
	 * NPCs are constructed with a name
	 * @return String
	 */
	public String getName();

}
