package model;

import java.util.HashMap;

import javafx.scene.image.Image;

/**
 * This class employs the Flytweight design pattern to create Terrain objects
 * that will be used in the various non-battle view maps. Each Terrain object is
 * a Singleton with a specific sprite image.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class Terrain {

	protected Image image;
	private static WildGrass wildGrass;
	private static Water water;
	private static Tree tallTree;
	private static Flower flower;
	private static Dirt dirt;
	private static Floor floor;
	private static Image ground;
	private static Bookshelf bookshelf;
	private static Table table;
	private static Path path;
	private static Bridge bridge;
	private static Building building;
	private static Cave cave;
	private static HashMap<Character, Terrain> letterTable = new HashMap<>();

	/**
	 * Instantiates a Terrain object with a specific image
	 * 
	 * @param path
	 */
	public Terrain(String path) {
		this.image = new Image("file:media/images/" + path);
	}

	/**
	 * Instantiate all Terrain objects once
	 */
	public static void instantiateFlyweights() {
		wildGrass = new WildGrass("WildGrass.png");
		water = new Water("Water.png");
		tallTree = new Tree("TallTree.png");
		flower = new Flower("Flowers.png");
		dirt = new Dirt("Dirt.png");
		floor = new Floor("Floor.png");
		bookshelf = new Bookshelf("FurnitureOne.png");
		table = new Table("FurnitureTwo.png");
		ground = new Image("file:media/images/Ground.png");
		path = new Path("Path.png");
		bridge = new Bridge("Bridge.png");
		building = new Building("Building.png");
		cave = new Cave("Cave.png");
		intializeCharacterMap();
	}

	/**
	 * Assign characters to each Terrain object
	 */
	public static void intializeCharacterMap() {
		letterTable.put('G', wildGrass);
		letterTable.put('W', water);
		letterTable.put('T', tallTree);
		letterTable.put('F', flower);
		letterTable.put('D', dirt);
		letterTable.put('M', floor);
		letterTable.put('A', bookshelf);
		letterTable.put('a', table);
		letterTable.put('w', path);
		letterTable.put('|', bridge);
		letterTable.put('$', building);
		letterTable.put('R', cave);
	}

	/**
	 * A special type of Terrain object
	 * 
	 * @return
	 */
	public static Image getGround() {
		return ground;
	}

	/**
	 * Get the instance of the Terrain object via fly weight
	 * 
	 * @param key
	 * @return
	 */
	public static Terrain getInstance(char key) {
		if (letterTable.containsKey(key)) {
			return letterTable.get(key);
		}
		return new Terrain("stuff");
	}

	/**
	 * Returns the sprite for a particular Terrain object
	 * 
	 * @return image object
	 */
	public Image getImage() {
		return image;
	}

}

/**
 * Wild grass is where a trainer can encounter wild Pokémon
 */
class WildGrass extends Terrain {

	WildGrass(String path) {
		super(path);
	}

}

/**
 * Water is where a trainer can fish with their rod
 */
class Water extends Terrain {

	Water(String path) {
		super(path);
	}

}

/**
 * Just for decorating the border of the views
 */
class Tree extends Terrain {

	Tree(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Flower extends Terrain {

	Flower(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Dirt extends Terrain {

	Dirt(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Floor extends Terrain {

	Floor(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Bookshelf extends Terrain {

	Bookshelf(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Table extends Terrain {

	Table(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Path extends Terrain {

	Path(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Bridge extends Terrain {

	Bridge(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Building extends Terrain {

	Building(String path) {
		super(path);
	}

}

/**
 * Just for decorating the tiles within the views.
 */
class Cave extends Terrain {

	Cave(String path) {
		super(path);
	}

}