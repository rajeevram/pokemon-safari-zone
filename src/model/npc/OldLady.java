package model.npc;

import javafx.scene.image.Image;

public class OldLady implements NPC {

	private final String messageOne = "There are many different ways to win the Safari Game. One way is to catch one of each type of Pokémon that can appear.";
	private final String messageTwo = "In the Safari Zone, a Pokémon runs away after 10 turns no matter what. That's not a lot of time to catch it...";
	private String name = "Old Lady";
	private Image image = new Image("file:media/images/OldLadyNPC.png");

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
