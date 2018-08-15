package model.pokemon;

/**
 * Golbat is an instance of the Pokemon class.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Golbat extends Pokemon {

	/**
	 * All Pokemon are constructed the same way.
	 */
	public Golbat(String name, String path, int hitPoints, int catchProbability, int runProbability, int strength) {
		super(name, path, hitPoints, catchProbability, runProbability, strength);
	}

	@Override
	public void moveOne(Pokemon enemyPokemon) {
		leer(enemyPokemon);

	}

	@Override
	public void moveTwo(Pokemon enemyPokemon) {
		quickAttack(enemyPokemon);

	}

	@Override
	public void moveThree(Pokemon enemyPokemon) {
		growl(enemyPokemon);

	}

	@Override
	public void moveFour(Pokemon enemyPokemon) {
		scratch(enemyPokemon);

	}

	@Override
	public String getMoveOne() {
		return "Leer";
	}

	@Override
	public String getMoveTwo() {
		return "Quick Attack";
	}

	@Override
	public String getMoveThree() {
		return "Growl";
	}

	@Override
	public String getMoveFour() {
		return "Scratch";
	}

}
