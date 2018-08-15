package model.npc;

import javafx.scene.image.Image;
import model.items.Potion;
import model.items.XAttack;
import model.items.XDefense;
import model.observable.InventoryViewer;
import model.trainer.Trainer;

public class Merchant extends Trainer implements NPC {

	private final String messageOne = "Welcome to the Pokémart. What would you like to buy?";
	private final String messageTwo = "N/A";
	private String name = "Merchant";
	private Image image = new Image("file:media/images/MerchantNPC.png");
	private InventoryViewer inventory;
	
	public Merchant(String name) {
		super(name);
		items.put(new Potion("Itemsprites.png"), 1);
		items.put(new XAttack("Itemsprites.png"), 1);
		items.put(new XDefense("Itemsprites.png"), 1);
		inventory = new InventoryViewer(this);
		inventory.addPriceCol();
	}
	
	/**
	 * Retrieves the inventory viewer for the merchant
	 * @return InventoryViewer
	 */
	public InventoryViewer getInventory() {
		return inventory;
	}

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

	@Override
	public void setImages() {
		return;
	}

}
