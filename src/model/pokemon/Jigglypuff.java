package model.pokemon;

/**
 * Jigglypuff is an instance of the Pokemon class.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Jigglypuff extends Pokemon {

	/**
	 * All Pokemon are constructed the same way.
	 */
	public Jigglypuff(String name, String path, int hitPoints, int catchProbability, int runProbability, int strength) {
		super(name, path, hitPoints, catchProbability, runProbability, strength);
	}

	@Override
	public void moveOne(Pokemon enemyPokemon) {
		tackle(enemyPokemon);

	}

	@Override
	public void moveTwo(Pokemon enemyPokemon) {
		quickAttack(enemyPokemon);

	}

	@Override
	public void moveThree(Pokemon enemyPokemon) {
		charm(enemyPokemon);

	}

	@Override
	public void moveFour(Pokemon enemyPokemon) {
		defenseCurl();

	}

	@Override
	public String getMoveOne() {
		return "Tackle";
	}

	@Override
	public String getMoveTwo() {
		return "Quick Attack";
	}

	@Override
	public String getMoveThree() {
		return "Charm";
	}

	@Override
	public String getMoveFour() {
		return "Defense Curl";
	}

}
