package model.pokemon;

/**
 * Zubat is an instance of the Pokemon class.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Zubat extends Pokemon {

	/**
	 * All Pokemon are constructed the same way.
	 */
	public Zubat(String name, String path, int hitPoints, int catchProbability, int runProbability, int strength) {
		super(name, path, hitPoints, catchProbability, runProbability, strength);
	}

	@Override
	public void moveOne(Pokemon enemyPokemon) {
		tackle(enemyPokemon);

	}

	@Override
	public void moveTwo(Pokemon enemyPokemon) {
		tailWhip(enemyPokemon);

	}

	@Override
	public void moveThree(Pokemon enemyPokemon) {
		leer(enemyPokemon);

	}

	@Override
	public void moveFour(Pokemon enemyPokemon) {
		charm(enemyPokemon);

	}

	@Override
	public String getMoveOne() {
		return "Tackle";
	}

	@Override
	public String getMoveTwo() {
		return "Tail Whip";
	}

	@Override
	public String getMoveThree() {
		return "Leer";
	}

	@Override
	public String getMoveFour() {
		return "Charm";
	}

}
