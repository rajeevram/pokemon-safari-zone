package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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

public class PokemonTest {

	@Test
	public void testJigglyPuffAccessors() {
		Pokemon jigglypuff = new Jigglypuff("Jigglypuff", "jigglypuff.png", 15, 6, 3, 5);

		assertTrue(jigglypuff.getName().equals("Jigglypuff"));
		assertTrue(jigglypuff.getImage().equals("file:media/images/jigglypuff.png"));
		assertTrue(jigglypuff.getCurrentHP() == 15);
		assertTrue(jigglypuff.getHitPoints() == 15);
		jigglypuff.potionHPIncrease();
		jigglypuff.decreaseCurrentHP();
		jigglypuff.increaseCurrentHP();
		jigglypuff.decreaseCurrentHP();
		jigglypuff.resetCurrentHP();
		jigglypuff.resetStats();
		Pokemon jigglypuff2 = new Jigglypuff("Jigglypuff", "jigglypuff.png", 20, 6, 3, 5);
		assertTrue(jigglypuff.equals(jigglypuff2));
		assertFalse(jigglypuff.equals(new Boy("Alan")));
		assertFalse(jigglypuff.equals(new Gloom("Gloom",null,50,6,1,1)));
		

	}

	@Test
	public void testRockThrown() {
		Pokemon pidgeotto = new Pidgeotto("Pidgeotto", "pidgeotto.png", 15, 5, 4, 5);
		pidgeotto.rockThrown();
		assertTrue(pidgeotto.getCurrentHP() == 14);
		assertTrue(pidgeotto.getCatchRate() == 6);
		assertTrue(pidgeotto.getRunRate() == 5);

		// jigglypuff.baitThrown();

	}

	@Test
	public void testPokemonConstructor() {
		Pokemon pokemon = new Staryu("name", "image", 15, 5, 4, 5);
		pokemon = new Tentacool("name", "image", 15, 5, 4, 5);
		pokemon = new Seaking("name", "image", 15, 5, 4, 5);
		pokemon = new Zubat("name", "image", 15, 5, 4, 5);
		pokemon = new Golbat("name", "image", 15, 5, 4, 5);
		pokemon = new Growlithe("name", "image", 15, 5, 4, 5);
		assertTrue(pokemon.equals(pokemon));
	}

	@Test
	public void testBallThrown() {
		Pokemon pikachu = new Pikachu("Pikachu", "pikachu.png", 15, 3, 6, 5);
		Boolean doesRun;

		while (doesRun = pikachu.rollsBall()) {
			assertTrue(doesRun);
		}
		while (doesRun = !pikachu.rollsBall()) {
			assertFalse(!doesRun);
		}
	}

	@Test
	public void testRunsAway() {
		Pokemon gloom = new Gloom("Gloom", "gloom.png", 15, 4, 5, 5);
		Boolean doesRun;

		while (doesRun = gloom.runsAway()) {
			assertTrue(doesRun);
		}
		while (doesRun = !gloom.runsAway()) {
			assertFalse(!doesRun);
		}
	}

	@Test
	public void baitThrown() {
		Pokemon gloom = new Gloom("Gloom", "gloom.png", 15, 4, 5, 5);

		gloom.baitThrown();
		assertTrue(gloom.getRunRate() == 4);
		assertTrue(gloom.getCatchRate() == 3);
	}

	@Test
	public void testOtherThings() {
		Pokemon pokemon = new Gloom("test", "test", 5, 5, 5, 5);
		pokemon.raiseCurrentHP(1);
		pokemon.increaseCurrentHP();
		pokemon.decreaseCurrentHP();
	}

	@Test
	public void testEverythingElse() {
		Pokemon pokemon = new Gloom("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Pidgeotto("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Pikachu("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Jigglypuff("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Tentacool("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Staryu("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Seaking("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Golbat("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Growlithe("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Zubat("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
		pokemon = new Persian("test", "test", 5, 5, 5, 5);
		pokemon.moveOne(pokemon);
		pokemon.moveTwo(pokemon);
		pokemon.moveThree(pokemon);
		pokemon.moveFour(pokemon);
		pokemon.getMoveOne();
		pokemon.getMoveTwo();
		pokemon.getMoveThree();
		pokemon.getMoveFour();
	}

}
