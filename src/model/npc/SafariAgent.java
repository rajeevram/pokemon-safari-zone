package model.npc;

import javafx.scene.image.Image;

public class SafariAgent implements NPC {

	private String messageOne = "Welcome to the Safari Zone! Would you like to enter? The price is $500. (Y/N)";
	private String messageTwo;
	private String name = "Safari Agent";
	private Image image = new Image("file:media/images/SafariGuyNPC.png");

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
