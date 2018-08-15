package model.npc;

import javafx.scene.image.Image;

public class Woman implements NPC {

	private final String messageOne = "I've heard rumors you can find a Bicycle and Fishing Rod in town...";
	private final String messageTwo = "Did you know you can throw bait to make a wild Pokémon more likely to stay in the battle? But, be careful, because this also makes the Pokémon harder to catch!";
	private String name = "Woman";
	private Image image = new Image("file:media/images/WomanNPC.png");

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
