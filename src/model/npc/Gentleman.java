package model.npc;

import javafx.scene.image.Image;

public class Gentleman implements NPC {

	private final String messageOne = "There are a total of ten unique Pokémon in the Safari Zone. Did you encounter all of them yet?";
	private final String messageTwo = "Did you know you can throw a rock to make a wild Pokémon easier to catch? But, be careful, because this also makes the Pokémon more likely to run away!";
	private String name = "Gentleman";
	private Image image = new Image("file:media/images/GentlemanNPC.png");

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
