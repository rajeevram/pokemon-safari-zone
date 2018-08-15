package model.pokemon;

/**
 * Pidgeotto is an instance of the Pokemon class.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Pidgeotto extends Pokemon {

	/**
	 * All Pokemon are constructed the same way.
	 */
	public Pidgeotto(String name, String path, int hitPoints, int catchProbability, int runProbability, int strength) {
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
		quickAttack(enemyPokemon);

	}

	@Override
	public void moveFour(Pokemon enemyPokemon) {
		growth();

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
		return "Quick Attack";

	}

	@Override
	public String getMoveFour() {
		return "Growth";

	}
}