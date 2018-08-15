package model.pokemon;

/**
 * Persian is an instance of the Pokemon class.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Persian extends Pokemon {

	/**
	 * All Pokemon are constructed the same way.
	 */
	public Persian(String name, String path, int hitPoints, int catchProbability, int runProbability, int strength) {
		super(name, path, hitPoints, catchProbability, runProbability, strength);
	}

	@Override
	public void moveOne(Pokemon enemyPokemon) {
		quickAttack(enemyPokemon);

	}

	@Override
	public void moveTwo(Pokemon enemyPokemon) {
		scratch(enemyPokemon);

	}

	@Override
	public void moveThree(Pokemon enemyPokemon) {
		leer(enemyPokemon);

	}

	@Override
	public void moveFour(Pokemon enemyPokemon) {
		growth();

	}

	@Override
	public String getMoveOne() {
		return "Quick Attack";
	}

	@Override
	public String getMoveTwo() {
		return "Scratch";
	}

	@Override
	public String getMoveThree() {
		return "Leer";
	}

	@Override
	public String getMoveFour() {
		return "Growth";
	}

}