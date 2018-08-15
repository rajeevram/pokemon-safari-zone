package model.npc;

import javafx.scene.image.Image;

public class Brother implements NPC {

	private final String messageOne = "Safari Zone! Safari Zone! Safari Zone! Do you like my song?";
	private final String messageTwo = "N/A";
	private String name = "Brother";
	private Image image = new Image("file:media/images/BrotherNPC.png");

	@Override
	public String getMessageOne() {
		return messageOne;
	}

	@Override
	public String getMessageTwo() {
		return messageTwo;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public String getName() {
		return name;
	}

}
