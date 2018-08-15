package tests;

import org.junit.Rule;
import org.junit.Test;

import model.items.Potion;
import model.observable.InventoryViewer;
import model.observable.PokemonViewer;
import model.trainer.Boy;
import model.trainer.Trainer;

public class ViewerTest {

	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Test
	public void testEverything() {
		Trainer trainer = new Boy("name");
		trainer.getStarterPokemon();
		Potion potion = new Potion("name");
		trainer.addItem(potion);
		InventoryViewer thing = new InventoryViewer(trainer);
		thing.update();
		trainer.addItem(potion);
		thing.update();
		potion.decreaseCount();
		potion.decreaseCount();
		thing.update();
		Trainer trainer2 = new Boy("Name");
		trainer2.getStarterPokemon();
		PokemonViewer viewer = new PokemonViewer(trainer2);
		viewer.update();
		thing.addPriceCol();
		thing.resetCols();
		
	}
}
