package model.pokemon;

/**
 * Growlithe is an instance of the Pokemon class.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Growlithe extends Pokemon {

	/**
	 * All Pokemon are constructed the same way.
	 */
	public Growlithe(String name, String path, int hitPoints, int catchProbability, int runProbability, int strength) {
		super(name, path, hitPoints, catchProbability, runProbability, strength);
	}

	@Override
	public void moveOne(Pokemon enemyPokemon) {
		scratch(enemyPokemon);

	}

	@Override
	public void moveTwo(Pokemon enemyPokemon) {
		quickAttack(enemyPokemon);

	}

	@Override
	public void moveThree(Pokemon enemyPokemon) {
		bulkUp();

	}

	@Override
	public void moveFour(Pokemon enemyPokemon) {
		growl(enemyPokemon);

	}

	@Override
	public String getMoveOne() {
		return "Scratch";
	}

	@Override
	public String getMoveTwo() {
		return "Quick Attack";
	}

	@Override
	public String getMoveThree() {
		return "Growth";
	}

	@Override
	public String getMoveFour() {
		return "Growl";
	}

}
