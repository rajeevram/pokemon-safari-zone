package model.trainer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javafx.scene.image.Image;
import model.Direction;
import model.Location;
import model.items.Bicycle;
import model.items.FishingRod;
import model.items.Item;
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
import views.general.BuildingView;
import views.general.CaveView;
import views.general.ParentView;
import views.general.SafariView;
import views.general.TownView;

/**
 * This class represents a Trainer object. The trainer can do many things in the
 * overworld including talking to NPCs, initiating a Trainer battle, walking
 * around, and picking up items. Within a safari battle context, the trainer can
 * throw rocks, bait, and safari balls; or run away. Within a trainer battle
 * context, the trainer can attack or use items, but cannot run away.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public abstract class Trainer implements Serializable {

	private int stepCount = 500;
	private int moveCount = 0;
	private int ballCount = 30;
	private int money = 3000;
	private int pokeCount = 0;
	private Location location;
	private String name;
	protected HashMap<String, Integer> emptyList = makeEmpty();
	protected HashMap<String, Integer> caughtPokemon = new HashMap<String, Integer>(emptyList);

	protected ArrayList<Pokemon> ownedPokemon = new ArrayList<Pokemon>();
	protected TreeMap<Item, Integer> items = new TreeMap<>();
	protected transient Image walkImage, fishImage, bikeImage, battleImage;

	public int myX, myY;
	public Direction direction = Direction.DOWN;

	public Trainer(String name) {
		this.name = name;
	}

	/**
	 * Return the name the player chose at the start
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Tracks how many turns a Trainer has left in a Safari Battle.
	 * 
	 * @return integer
	 */
	public int getMoveCount() {
		return moveCount;

	}

	/**
	 * The Trainer always starts with a Persian
	 */
	public void getStarterPokemon() {
		addPokemon(new Persian("Persian", null, 30, 30, 30, 30));
		resetCaughtPokemon();
	}

	/**
	 * Retrieve the Trainer's items
	 * 
	 * @return TreeMap
	 */
	public TreeMap<Item, Integer> getItems() {
		return items;
	}

	/**
	 * Check the size of the Trainer's inventory
	 * 
	 * @return integer
	 */
	public int getInventorySize() {
		return items.size();
	}

	/**
	 * Check and save the location of a Trainer
	 * 
	 * @return enumeration
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Set the location of the Trainer
	 * 
	 * @param view
	 *            the actual view
	 */
	public void setLocation(ParentView view) {
		if (view instanceof BuildingView) {
			location = Location.BUILDING;
		} else if (view instanceof TownView) {
			location = Location.TOWN;
		} else if (view instanceof SafariView) {
			location = Location.SAFARI;
		} else if (view instanceof CaveView) {
			location = Location.CAVE;
		}
	}

	/**
	 * Checks if the Trainer caught a duplicate
	 * 
	 * @return boolean
	 */
	public boolean caughtAnotherPokemon() {

		int count = 0;
		for (int poke : caughtPokemon.values()) {
			count += poke;
		}
		if (pokeCount < count) {
			pokeCount = count;
			return true;
		}
		return false;
	}

	/**
	 * First win condition
	 * 
	 * @return boolean
	 */
	public boolean hasOneOfEach() {
		for (int count : caughtPokemon.values()) {
			if (count < 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Second win condition
	 * 
	 * @return boolean
	 */
	public boolean hasFiveOfTwo() {
		int pokeCount = 0;
		for (int count : caughtPokemon.values()) {
			if (count >= 5) {
				pokeCount++;
			}
		}
		return pokeCount >= 2;
	}

	/**
	 * Third win condition
	 * 
	 * @return boolean
	 */
	public boolean hasTwoOfFive() {
		int pokeCount = 0;
		for (int count : caughtPokemon.values()) {
			if (count >= 2) {
				pokeCount++;
			}
		}
		return pokeCount >= 5;
	}

	/**
	 * When using a Poition
	 */
	public void healPokemon() {
		for (Pokemon p : ownedPokemon) {
			p.resetCurrentHP();
		}
	}

	/**
	 * Reset a Pokemon's status changes
	 */
	public void resetStatusEffects() {
		for (Pokemon p : ownedPokemon) {
			p.resetStats();
		}
	}

	/**
	 * What does this method do?
	 * 
	 * @return a HashMap apparently
	 */
	public HashMap<String, Integer> makeEmpty() {
		HashMap<String, Integer> newMap = new HashMap<>();
		newMap.put(new Gloom("Gloom", null, 30, 25, 20, 10).getName(), 0);
		newMap.put(new Golbat("Golbat", null, 28, 25, 20, 10).getName(), 0);
		newMap.put(new Growlithe("Growlithe", null, 26, 25, 20, 10).getName(), 0);
		newMap.put(new Jigglypuff("Jigglypuff", null, 24, 25, 20, 10).getName(), 0);
		newMap.put(new Persian("Persian", null, 22, 25, 20, 10).getName(), 0);
		newMap.put(new Pidgeotto("Pidgeotto", null, 20, 25, 20, 10).getName(), 0);
		newMap.put(new Pikachu("Pikachu", null, 18, 25, 20, 10).getName(), 0);
		newMap.put(new Seaking("Seaking", null, 16, 25, 20, 10).getName(), 0);
		newMap.put(new Staryu("Staryu", null, 14, 25, 20, 10).getName(), 0);
		newMap.put(new Tentacool("Tentacool", null, 12, 25, 20, 10).getName(), 0);
		newMap.put(new Zubat("Zubat", null, 10, 25, 20, 10).getName(), 0);
		return newMap;
	}

	/**
	 * Reset the caught Pokemon at the beginning of each quest
	 */
	public void resetCaughtPokemon() {
		caughtPokemon = new HashMap<String, Integer>(emptyList);
	}

	/**
	 * Return any alive Pokemon that the Trainer has
	 * 
	 * @return a pokemon
	 */
	public Pokemon getAlivePokemon() {
		for (Pokemon p : ownedPokemon) {
			if (p.getCurrentHP() > 0) {
				return p;
			}
		}
		return null;
	}

	/**
	 * The win condition Strings for the Safari Quest
	 * 
	 * @return String
	 */
	public String winCondition() {
		if (hasOneOfEach()) {
			return "Congratulations! You've won the Safari Game! You caught one of each type of Pokémon!";
		}
		if (hasFiveOfTwo()) {
			return "Congratulations! You've won the Safari Game! You caught two each of five types of Pokémon!";
		}
		if (hasTwoOfFive()) {
			return "Congratulations! You've won the Safari Game! You caught five each of two types Pokémon!";
		}
		return "You did not win yet! Keep trying!";
	}

	/**
	 * Check the number of alive Pokemon
	 * 
	 * @return and integer
	 */
	public int numAlivePokemon() {
		int count = 0;
		for (Pokemon p : ownedPokemon) {
			if (p.getCurrentHP() > 0) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Resets the trainer's move count to zero after every Safari Battle.
	 */
	public void resetMoveCount() {
		moveCount = 0;
	}

	/**
	 * Increments the trainer's move count during a Safari Battle.
	 */
	public void move() {
		moveCount++;
	}

	/**
	 * Tracks how many steps a Trainer has left in the Safari Zone.
	 * 
	 * @return integer
	 */
	public int getStepCount() {
		return stepCount;
	}

	/**
	 * Resets the trainer's step count after each Safari Zone quest.
	 */
	public void resetStepCount() {
		stepCount = 500;
	}

	/**
	 * Increments the trainer's step count within the Safari Zone.
	 */
	public void step() {
		if (location == Location.SAFARI) {
			stepCount--;
		}
	}

	/**
	 * This method allows a trainer to add an item to his inventory
	 * 
	 * @param item
	 *            the item to be added
	 */
	public void addItem(Item item) {
		if (items.containsKey(item)) {
			item.increaseCount();
			items.put(item, item.getCount());
		} else {
			items.put(item, item.getCount());
		}
	}

	/**
	 * This method allows a trainer to remove an item to his inventory
	 * 
	 * @param item
	 *            the item to be removed
	 */
	public void removeItem(Item item) {
		if (items.containsKey(item)) {
			items.remove(item);
		}
	}

	/**
	 * This method allows a trainer to remove a Pokemon to their ownedPokemon
	 * 
	 * @param pokemon
	 *            the pokemon to be removed
	 */
	public void removePokemon(Pokemon pokemon) {
		if (ownedPokemon.contains(pokemon)) {
			ownedPokemon.remove(pokemon);
		}
	}

	/**
	 * Throws a Safari Ball at the Pokemon to try to catch it. Costs one move and
	 * one ball regardless of outcome.
	 */
	public void throwBall() {
		this.move();
		ballCount--;

	}

	/**
	 * Retrieves the number of Safari Balls this Trainer has left in the quest.
	 * 
	 * @return integer
	 */
	public int getBallCount() {
		return ballCount;
	}

	/**
	 * Player is awarded balls to catch Pokemon at the begninning of the quest
	 */
	public void resetBallCount() {
		ballCount = 30;
	}

	/**
	 * Adds a Pokemon that a trainer has successfully caught to his or her
	 * collection.
	 * 
	 * @param pokemon
	 *            the Pokemon that was caught
	 */
	public void addPokemon(Pokemon pokemon) {
		if (caughtPokemon.containsKey(pokemon.getName())) {
			caughtPokemon.put(pokemon.getName(), caughtPokemon.get(pokemon.getName()) + 1);
		} else {
			caughtPokemon.put(pokemon.getName(), 1);
		}

		if (!(ownedPokemon.contains(pokemon))) {

			ownedPokemon.add(pokemon);
		}
	}

	public abstract void setImages();

	/**
	 * Save the position of the Trainer on the map
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		myX = x;
		myY = y;
	}

	/**
	 * Sprite sheet for walking animations
	 * 
	 * @return walk image
	 */
	public Image getWalkImage() {
		return walkImage;
	}

	/**
	 * Sprite sheet for fishing animations
	 * 
	 * @return fish image
	 */
	public Image getFishImage() {
		return fishImage;
	}

	/**
	 * Sprite sheet for biking animations
	 * 
	 * @return bike image
	 */
	public Image getBikeImage() {
		return bikeImage;
	}

	/**
	 * Sprite sheet for battling in the Safari Zone
	 * 
	 * @return battle image
	 */
	public Image getBattleImage() {
		return battleImage;
	}

	/**
	 * Check the Pokemon the Trainer owns
	 * 
	 * @return the owned pokemon
	 */
	public ArrayList<Pokemon> getOwnedPokemon() {
		return ownedPokemon;
	}

	/**
	 * Check if the Trainer has the fishing rod yet
	 * 
	 * @return boolean
	 */
	public boolean canFish() {
		if (location == Location.SAFARI || location == Location.CAVE) {
			return items.containsKey(FishingRod.getFishingRodInstance());
		} else {
			return false;
		}
	}

	/**
	 * Check if the Trainer has the bicycle yet
	 * 
	 * @return boolean
	 */
	public boolean canBike() {
		if (location == Location.SAFARI || location == Location.TOWN || location == Location.CAVE) {
			return items.containsKey(Bicycle.getBicycleInstance());
		} else {
			return false;
		}
	}

	/**
	 * Check the Traner's money
	 * 
	 * @return integer
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Check if the Trainer can spend money
	 * 
	 * @param cost
	 *            the cost of what is being purchased
	 * @return boolean
	 */
	public boolean canSpendMoney(int cost) {
		if (money - cost >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * When the player buys an item
	 * 
	 * @param item
	 * @param cost
	 */
	public void purchase(Item item, int cost) {
		money -= cost;
		addItem(item);
	}

	/**
	 * When the player spends money
	 * 
	 * @param cost
	 */
	public void pay(int cost) {
		money -= cost;
	}

	/**
	 * When the trainer earns money
	 * 
	 * @param income
	 *            the amount he or she earns
	 */
	public void addMoney(int income) {
		money += income;
	}

	/**
	 * Performs a count on the number of Pokemon the Trainer caught
	 * @return integer
	 */
	public int getNumCaught() {
		int count = 0;
		for (int poke : caughtPokemon.values()) {
			count += poke;
		}
		return count;
	}

}
