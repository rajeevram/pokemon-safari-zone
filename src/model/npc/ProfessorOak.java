package model.npc;

import javafx.scene.image.Image;

public class ProfessorOak implements NPC {

	private String messageOne = "Would you like a tutorial on how to play this game? (Y/N)";
	private String messageTwo = "N/A";
	private String name = "Professor Oak";
	private Image image = new Image("file:media/images/OakNPC.png");

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
