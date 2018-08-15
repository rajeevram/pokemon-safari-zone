package model.npc;

import javafx.scene.image.Image;

public class RegularMan implements NPC {

	private final String messageOne = "If you step into the wild grass, a Pokémon can just appear. Are you prepared?!";
	private final String messageTwo = "If a Pokémon runs out of hit points, then the battle ends. Make sure to capture it before that happens!";
	private String name = "Man";
	private Image image = new Image("file:media/images/RegularManNPC.png");

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
