package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import model.items.Item;
import model.items.Potion;
import model.items.SafariBall;
import model.pokemon.Gloom;
import model.pokemon.Golbat;
import model.pokemon.Growlithe;
import model.pokemon.Jigglypuff;
import model.pokemon.Persian;
import model.pokemon.Pidgeotto;
import model.pokemon.Pikachu;
import model.pokemon.Pokemon;
import model.pokemon.Seaking;
import model.pokemon.Staryu;
import model.pokemon.Tentacool;
import model.pokemon.Zubat;
import model.trainer.Boy;
import model.trainer.EnemyTrainer;
import model.trainer.Girl;
import model.trainer.Trainer;
import views.general.BuildingView;
import views.general.CaveView;
import views.general.SafariView;
import views.general.TownView;

public class TrainerTest {

	// We add this rule to be able to test Alerts within a JavaFX system. Please
	// read the JavaFXThreadingRule class for more information. Without this
	// annotation, we cannot properly JUnit Test our alerting system.
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Test
	public void testCanFishOrBike() {
		Trainer trainer = new Girl("girl");
		trainer.setLocation(new BuildingView(trainer));
		assertFalse(trainer.canFish());
		assertFalse(trainer.canBike());
	}

	@Test
	public void testAddingItem() {
		Trainer trainer = new Girl("girl");
		Item item = new Potion("Potion");
		trainer.addItem(item);
	}

	@Test
	public void testAddingPokemon() {
		Trainer trainer = new Girl("girl");
		Pokemon poke = new Pikachu("Pika", "path", 0, 0, 0, 0);
		trainer.addPokemon(poke);
	}

	@Test
	public void testMoveCount() {
		Trainer trainer = new Girl("girl");
		assertEquals(0, trainer.getMoveCount());
		trainer.move();
		trainer.move();
		trainer.move();
		trainer.move();
		trainer.move();
		assertEquals(5, trainer.getMoveCount());
		trainer.resetMoveCount();
		assertEquals(0, trainer.getMoveCount());
	}

	@Test
	public void testBallCount() {

		Trainer trainer = new Boy("girl");
		Item ball = new SafariBall("SafariBall.png");

		trainer.removeItem(ball);
		trainer.addItem(ball);
		trainer.removeItem(ball);

		assertEquals(0, trainer.getMoveCount());
		assertEquals(30, trainer.getBallCount());

		trainer.throwBall();
		trainer.throwBall();
		trainer.throwBall();
		trainer.throwBall();
		trainer.throwBall();

		assertEquals(25, trainer.getBallCount());
		assertEquals(5, trainer.getMoveCount());

		trainer.resetBallCount();
		assertEquals(30, trainer.getBallCount());

	}

	@Test
	public void testStepCountBoy() {
		Trainer trainer = new Boy("girl");
		assertEquals(500, trainer.getStepCount());
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		assertEquals(500, trainer.getStepCount());
		trainer.resetStepCount();
		assertEquals(500, trainer.getStepCount());

	}

	@Test
	public void testStepCountGirl() {
		Trainer trainer = new Girl("girl");
		assertEquals(500, trainer.getStepCount());
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		trainer.step();
		assertEquals(500, trainer.getStepCount());
		trainer.resetStepCount();
		assertEquals(500, trainer.getStepCount());
	}

	@Test
	public void testGetTheItems() {
		Trainer trainer = new Girl("girl");
		Map<Item, Integer> map = trainer.getItems();
		assertTrue(map.isEmpty());
		trainer.addItem(new SafariBall("SafariBall.png"));
		map = trainer.getItems();
		assertFalse(map.isEmpty());
		trainer.addItem(new SafariBall("SafariBall.png"));
		map = trainer.getItems();
		assertEquals(map.size(), 1);
	}

	@Test

	public void testRemovePokemon() {
		Pokemon poke = new Pikachu("Pika", "path", 0, 0, 0, 0);
		Trainer trainer = new Girl("girl");
		trainer.getStarterPokemon();
		trainer.addPokemon(poke);
		assertTrue(trainer.getOwnedPokemon().size() == 2);
		trainer.removePokemon(poke);
		assertTrue(trainer.getOwnedPokemon().size() == 1);
	}

	@Test
	public void testLocationStepCount() {
		Trainer trainer = new Girl("girl");
		trainer.setLocation(new SafariView(trainer));
		trainer.step();
		assertEquals(499, trainer.getStepCount());
	}

	@Test
	public void testEverythingElse() {
		Trainer trainer = new Boy("name");
		trainer.getName();
		trainer.getInventorySize();
		trainer.getLocation();
		trainer.setLocation(new SafariView(trainer));
		trainer.setLocation(new TownView(trainer));
		trainer.setLocation(new BuildingView(trainer));
		trainer.setLocation(new CaveView(trainer));
		trainer.removePokemon(null);
		trainer.getBikeImage();
		trainer.getWalkImage();
		trainer.getFishImage();
		trainer.getOwnedPokemon();
		trainer.canBike();
		trainer.canFish();
		trainer.getMoney();
		assertTrue(trainer.canSpendMoney(2));
		trainer.pay(2);
		assertFalse(trainer.canSpendMoney(50000));
		trainer.purchase(new Potion("pot"),50);
		trainer.addMoney(200);
		trainer.getBattleImage();
		trainer.setPosition(1,1);
		trainer.healPokemon();
		trainer.resetStatusEffects();
		trainer.getAlivePokemon();
		trainer.numAlivePokemon();
		trainer.resetCaughtPokemon();
		assertFalse(trainer.hasFiveOfTwo());
		assertFalse(trainer.hasTwoOfFive());
		assertFalse(trainer.hasOneOfEach());
		trainer.winCondition();
		trainer.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer.addPokemon(new Gloom("Gloom",null, 30, 25, 20, 10));
		trainer.addPokemon(new Golbat("Golbat",null, 28, 25, 20, 10));
		trainer.addPokemon(new Growlithe("Growlithe",null, 26, 25, 20, 10));
		trainer.addPokemon(new Jigglypuff("Jigglypuff",null, 24, 25, 20, 10));
		trainer.addPokemon(new Persian("Persian",null, 22, 25, 20, 10));
		trainer.addPokemon(new Pidgeotto("Pidgeotto",null, 20, 25, 20, 10));
		trainer.addPokemon(new Pikachu("Pikachu",null, 18, 25, 20, 10));
		trainer.addPokemon(new Seaking("Seaking",null, 16, 25, 20, 10));
		trainer.addPokemon(new Staryu("Staryu",null, 14, 25, 20, 10));
		trainer.addPokemon(new Tentacool("Tentacool",null, 12, 25, 20, 10));
		trainer.addPokemon(new Zubat("Zubat",null, 10, 25, 20, 10));
		assertTrue(trainer.hasOneOfEach());
		trainer.winCondition();
		
		Trainer trainer2 = new Boy("name");
		trainer2.addPokemon(new Gloom("Gloom",null, 30, 25, 20, 10));
		trainer2.addPokemon(new Gloom("Gloom",null, 30, 25, 20, 10));
		trainer2.addPokemon(new Gloom("Gloom",null, 30, 25, 20, 10));
		trainer2.addPokemon(new Gloom("Gloom",null, 30, 25, 20, 10));
		trainer2.addPokemon(new Gloom("Gloom",null, 30, 25, 20, 10));
		trainer2.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer2.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer2.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer2.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		trainer2.addPokemon(new Pikachu("Pikachu",null,20,20,20,20));
		assertTrue(trainer2.hasFiveOfTwo());
		trainer2.winCondition();
		
		Trainer trainer3 = new Boy("name");
		trainer3.caughtAnotherPokemon();
		trainer3.addPokemon(new Zubat("Zubat",null, 10, 25, 20, 10));
		assertTrue(trainer3.caughtAnotherPokemon());
		assertFalse(trainer3.caughtAnotherPokemon());
		trainer3.addPokemon(new Zubat("Zubat",null, 10, 25, 20, 10));
		trainer3.addPokemon(new Tentacool("Tentacool",null, 12, 25, 20, 10));
		trainer3.addPokemon(new Tentacool("Tentacool",null, 12, 25, 20, 10));
		trainer3.addPokemon(new Staryu("Staryu",null, 14, 25, 20, 10));
		trainer3.addPokemon(new Staryu("Staryu",null, 14, 25, 20, 10));
		trainer3.addPokemon(new Seaking("Seaking",null, 16, 25, 20, 10));
		trainer3.addPokemon(new Seaking("Seaking",null, 16, 25, 20, 10));
		trainer3.addPokemon(new Pikachu("Pikachu",null, 18, 25, 20, 10));
		trainer3.addPokemon(new Pikachu("Pikachu",null, 18, 25, 20, 10));
		assertTrue(trainer3.hasTwoOfFive());
		trainer3.winCondition();
		trainer3.getNumCaught();
	}
	
	@Test
	public void testEnemyTrainer() {
		EnemyTrainer et = new EnemyTrainer("Al");
		Trainer boy = new Boy("Man");
		boy.setImages();
		Trainer girl = new Girl("Girl");
		girl.setImages();
		et.setImage(boy.getWalkImage());

		et.getMessageOne();

		et.getMessageTwo();
		et.getImage();
		et.setImages();
		et.healPokemon();
		et.resetStatusEffects();
		et.getAlivePokemon();
		et.numAlivePokemon();
	}

}
