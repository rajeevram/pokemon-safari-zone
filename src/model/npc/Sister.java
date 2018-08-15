package model.npc;

import javafx.scene.image.Image;

public class Sister implements NPC {

	private final String messageOne = "Pokémon! Pokémon! Pokémon! What's your favorite Pokémon?";
	private final String messageTwo = "N/A";
	private String name = "Sister";
	private Image image = new Image("file:media/images/SisterNPC.png");

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
