package model.pokemon;

import java.io.Serializable;
import java.util.Random;

/**
 * This class represents a Pokemon object. It is an abstract class because all
 * the instance of Pokemon have the same functionality. The child classes are:
 * Pikachu, Jigglypuff, Pidgeotto, Gloom. In the safari battle context, each of
 * the Pokemon reacts to the Trainers actions. In a trainer battle context, the
 * Pokemon has a single attack that it can use.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public abstract class Pokemon implements Serializable {

	// Instance Variables
	protected String name;
	protected String imageSource;
	protected int random;
	protected int catchRate;
	protected int runRate;
	protected int strength;
	protected int hitPoints;
	protected int currentHP;
	protected int breakOutProbability = 5;
	protected int defense = 1;
	protected int attack = 2;

	/**
	 * This is the non-default constructor
	 * 
	 * @param name
	 *            the type of this Pokemon
	 * @param path
	 *            the file for the image
	 * @param hitPoints
	 *            this Pokemon's health
	 * @param catchProbability
	 *            integer the represents a catch rate
	 * @param runProbability
	 *            integer that represents the running rate
	 * @param strength
	 *            integer that represents the attack level
	 */
	public Pokemon(String name, String path, int hitPoints, int catchProbability, int runProbability, int strength) {
		this.name = name;
		this.imageSource = "file:media/images/" + path;
		this.hitPoints = hitPoints;
		this.currentHP = hitPoints;
		this.catchRate = catchProbability;
		this.runRate = runProbability;
		this.strength = strength;
	}

	/**
	 * Retrieves the name of the Pokemon object
	 * 
	 * @return String value
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves the image source for the Pokemon
	 * 
	 * @return String value
	 */
	public String getImage() {
		return imageSource;
	}

	/**
	 * increases currentHP
	 */
	public void increaseCurrentHP() {
		currentHP += hitPoints / 10;
		if(currentHP > hitPoints) {
			currentHP = hitPoints;
		}
	}

	public void potionHPIncrease() {
		currentHP += hitPoints / 2;
		if(currentHP > hitPoints) {
			currentHP = hitPoints;
		}
	}
	
	public void resetCurrentHP() {
		currentHP = hitPoints;
	}
	
	public void resetStats() {
		attack = 2;
		defense = 1;
	}

	/**
	 * increases currentHP
	 */
	public void decreaseCurrentHP() {
		currentHP -= hitPoints / 10;
	}

	/**
	 * Retrieve the original health for this Pokemon
	 * 
	 * @return integer
	 */
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * Retrieves the current health for this Pokemon
	 * 
	 * @return integer
	 */
	public int getCurrentHP() {
		return currentHP;
	}

	/**
	 * Calculates a run away based on a random integer
	 * 
	 * @return true, if runs away; false, otherwise
	 */
	public boolean runsAway() {
		random = new Random().nextInt(10);
		if (random < runRate) {
			return true;
		}
		return false;
	}

	/**
	 * Calculates a ball roll based on a random integer
	 * 
	 * @return true, if does not escape; false, otherwise
	 */
	public boolean rollsBall() {
		random = new Random().nextInt(10);
		if (random < catchRate) {
			return true;
		}
		return false;
	}

	public void raiseAttack(int amount) {
		attack += amount;
	}

	public void lowerAttack(int amount) {
		attack -= amount;
	}

	public void raiseDefense(int amount) {
		defense += amount;
	}

	public void lowerDefense(int amount) {
		defense -= amount;
	}

	/**
	 * Bait reduces both the catch rate and run rate
	 */
	public void baitThrown() {
		catchRate--;
		runRate--;
	}

	public void raiseCurrentHP(int amount) {
		currentHP += amount;
	}

	public void lowerCurrentHP(int amount) {
		if (amount <= 1) {
			currentHP -= 1;
		} else {
			currentHP -= amount;
		}

	}

	/**
	 * Rocks increase the catch rate and run rate, but decrease the health
	 */
	public void rockThrown() {
		currentHP--;
		catchRate++;
		runRate++;
	}

	/**
	 * Each Pokemon has a unique catch rate
	 * @return
	 */
	public int getCatchRate() {
		return catchRate;
	}

	/**
	 * Each pokemon has a unique run rate
	 * @return
	 */
	public int getRunRate() {
		return runRate;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Pokemon)) {
			return false;
		}
		Pokemon otherPoke = (Pokemon)other;
		if (this.getName() == otherPoke.getName()) {
			return true;
		}
		return false;
	}

	/*----------Pokémon Move List----------*/
	protected void tackle(Pokemon foe) {
		foe.lowerCurrentHP(this.attack - foe.defense);
	}

	protected void quickAttack(Pokemon foe) {
		foe.lowerCurrentHP(this.attack - foe.defense);
	}

	protected void scratch(Pokemon foe) {
		foe.lowerCurrentHP(this.attack - foe.defense);
	}

	protected void waterGun(Pokemon foe) {
		foe.lowerCurrentHP(this.attack - foe.defense);
	}

	protected void harden() {
		this.raiseDefense(1);
	}

	protected void defenseCurl() {
		this.raiseDefense(1);
	}

	protected void bulkUp() {
		this.raiseAttack(1);
	}

	protected void growth() {
		this.raiseAttack(1);
	}

	protected void growl(Pokemon foe) {
		foe.lowerAttack(1);
	}

	protected void charm(Pokemon foe) {
		foe.lowerAttack(1);
	}

	protected void leer(Pokemon foe) {
		foe.lowerDefense(1);
	}

	protected void tailWhip(Pokemon foe) {
		foe.lowerDefense(1);
	}

	/**
	 * The Pokemon's first move
	 * @param enemyPokemon the one being attacked
	 */
	public abstract void moveOne(Pokemon enemyPokemon);

	/**
	 * The Pokemon's second move
	 * @param enemyPokemon the one being attacked
	 */
	public abstract void moveTwo(Pokemon enemyPokemon);

	/**
	 * The Pokemon's third move
	 * @param enemyPokemon the one being attacked
	 */
	public abstract void moveThree(Pokemon enemyPokemon);

	/**
	 * The Pokemon's fourth move
	 * @param enemyPokemon the one being attacked
	 */
	public abstract void moveFour(Pokemon enemyPokemon);

	/**
	 * Wrapper method for first move
	 */
	public abstract String getMoveOne();

	/**
	 * Wrapper method for second move
	 */
	public abstract String getMoveTwo();

	/**
	 * Wrapper method for third move
	 */
	public abstract String getMoveThree();

	/**
	 * Wrapper method for four move
	 */
	public abstract String getMoveFour();

}