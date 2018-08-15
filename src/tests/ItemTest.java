package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import model.items.Bicycle;
import model.items.FishingRod;
import model.items.Item;
import model.items.Potion;
import model.items.SafariBall;
import model.items.XAttack;
import model.items.XDefense;

public class ItemTest {

	// We add this rule to be able to test Alerts within a JavaFX system. Please
	// read the JavaFXThreadingRule class for more information. Without this
	// annotation, we cannot properly JUnit Test our alerting system.
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Test
	public void testCreateItem() {
		Item item = new SafariBall("test");
		assertEquals(item.getClass().toString(), "class model.items.SafariBall");
		assertTrue(item.getImage() != null);
		assertEquals(item.toString(), "Safari Ball");
		item = new Potion("test");
		assertEquals(item.getClass().toString(), "class model.items.Potion");
		item = new Bicycle("test");
		assertEquals(item.getClass().toString(), "class model.items.Bicycle");
		item = new FishingRod("test");
		FishingRod.getFishingRodInstance();
		Bicycle.getBicycleInstance();
		assertEquals(item.getClass().toString(), "class model.items.FishingRod");
		item = new Potion("test");
		item.getName();
		item.decreaseCount();
		item = new XDefense("Test");
		item.getName();
		item.decreaseCount();
		item = new XAttack("Test");
		item.getName();
		item.decreaseCount();
		item.increaseCount();
		item.getCount();
		Item other = new Potion("potion");
		item.compareTo(other);
		Item.setImage();
		assertTrue(item.getPrice() == 100);

	}

}