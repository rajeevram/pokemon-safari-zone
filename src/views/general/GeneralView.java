package views.general;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import controller.Controller;
import controller.MoveHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Direction;
import model.Terrain;
import model.items.Bicycle;
import model.items.FishingRod;
import model.items.Item;
import model.items.Potion;
import model.items.XAttack;
import model.items.XDefense;
import model.npc.Brother;
import model.npc.Gentleman;
import model.npc.Merchant;
import model.npc.NPC;
import model.npc.OldLady;
import model.npc.ProfessorOak;
import model.npc.RegularMan;
import model.npc.SafariAgent;
import model.npc.Sister;
import model.npc.Woman;
import model.observable.InventoryViewer;
import model.observable.PokemonViewer;
import model.pokemon.Pokemon;
import model.trainer.Boy;
import model.trainer.EnemyTrainer;
import model.trainer.Girl;
import model.trainer.Trainer;

/**
 * This is the parent for three different views: SafariView, TownView,
 * BuildingView Handles setup and drawing of the maps and the still trainer.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R. Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public abstract class GeneralView extends ParentView implements Serializable {

	/*----------Construction & Assignment----------*/

	// Internal Logic Instance Variables
	protected Terrain[][] map = new Terrain[20][20];
	protected HashMap<String, NPC> NPCCollection;
	protected List<Pokemon> PokemonCollection = new ArrayList<Pokemon>();
	protected Trainer trainer;

	private static boolean running = false;
	private boolean biking;

	// External UI Instance Variable
	protected final int numSquares = 13; // Odd Number Centers Properly
	protected final int midPoint = (numSquares) / 2 + 4, gridSize = 30;// gridSize = 30;
	protected final int totalSquares = 50; // Leaves 30x30 walkable area
	protected final int startPositionX = 20, startPositionY = 20;
	protected final int offset = 10;
	protected Canvas canvas1, canvas2, canvas3;
	protected double animationTime = 100;
	protected Point currentPosition;
	protected Character[][] grid;
	protected static Direction direction = Direction.DOWN;
	protected static int newKey = 0;

	private TextArea textArea;
	private StatSheet statistics;
	private Canvas messageCanvas;
	private GraphicsContext messageGC;

	// Private Instance Variables
	private GridPane gridPane;
	private GraphicsContext gcLayer1, gcLayer2, gcLayer3;
	private HBox scroll;
	private VBox options, shop,instructions;
	private BorderPane pane;
	private Label item, money;
	private Button exit, save, menu, back, inventory, leave, closeInstructions;
	private Button close, down, up, pokemonList, use, select, buy, closeShop;
	
	private List<Item> items = new ArrayList<>();
	private static List<NPC> npcs = new ArrayList<>();
	private int inventoryIndex = 0, inventorySize = 0; 
	private Label npcClue = new Label();
	private PokemonViewer pokemonViewer;
	private Item selectedItem;
	private boolean onDisplay;

	private StartMoveHandler moveHandler;
	private static Font font = new Font("Verdana", 14);

	/**
	 * All views are constructed upon a Trainer object Creates separate layouts and
	 * canvases and items for each view created. Puts handlers for starting and
	 * stopping movement with key pressed/released, and handlers for opening up the
	 * instructions by holding CTRL Calls all the helper methods to initialize the
	 * gridPane, menuButtons, and vBox.
	 * 
	 * @param trainer the player in the game
	 */
	public GeneralView(Trainer trainer) {

		this.trainer = trainer;
		pokemonViewer = new PokemonViewer(trainer);
		textAreaMethods();

		canvas1 = new Canvas(gridSize * totalSquares, gridSize * totalSquares);
		canvas2 = new Canvas(numSquares * gridSize, numSquares * gridSize);
		canvas3 = new Canvas(gridSize * totalSquares, gridSize * totalSquares);
		canvasMethods();

		pane = new BorderPane();
		pane.getChildren().addAll(canvas1, canvas2, canvas3);
		shop = new VBox();
		instructions = new VBox();
		
		setCenter(pane);
		canvas2.setFocusTraversable(true);
		canvas2.requestFocus();
		moveHandler = new StartMoveHandler(this, trainer, canvas1, canvas2, canvas3);
		canvas2.setOnKeyPressed(moveHandler);
		canvas2.setOnKeyReleased(new EndMoveHandler());

		gridPaneMethods();
		setBottom(gridPane);
		menuButtonMethods();
		vBoxMethods();
		setLeft(options);

		items.add(new Bicycle("ItemSprites.png"));
		items.add(new FishingRod("ItemSprites.png"));
		items.add(new Potion("ItemSprites.png"));

		setOnKeyPressed(new InstructionStarter(this));
		setOnKeyReleased(new InstructionExiter());

	}

	/*----------Styling Methods*----------*/

	/**
	 * One of many styling methods
	 */
	private void textAreaMethods() {
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setMaxHeight(450);
		textArea.setMaxWidth(450);
		textArea.setMinSize(450,450);
		textArea.setFont(new Font("Verdana", 13));
		textArea.setWrapText(true);
		textArea.setText("The Map:\n" + "\tMove around the map using the arrow keys. "
				+ "Wild Pokémon can be encountered in the grass, caves, or in water. "
				+ "Avoid battles by walking on the paths in the flower patches. "
				+ "To pickup an item, walk up to it and press enter." + "\n\n" + "Battle:\n"
				+ "\tThrow a ball to catch a Pokémon. Throw rocks or bait to impact the probability you can catch it. "
				+ "Press enter to scroll through the messages after each turn in battle. "
				+ "At any time, you can choose to run from a wild battle." + "\n\n" + "Options:\n"
				+ "\tCheck the items in your inventory by using the buttons provided in the menu. "
				+ "When you quit the game, you have the option of saving your progress. ");		
	}

	private void canvasMethods() {
		gcLayer1 = canvas1.getGraphicsContext2D();
		gcLayer2 = canvas2.getGraphicsContext2D();
		gcLayer3 = canvas3.getGraphicsContext2D();
		setCanvasOffset(startPositionX, startPositionY);
		// moves it up and to the left
		Terrain.instantiateFlyweights();
	}

	protected void setCanvasOffset(int startPositionX, int startPositionY) {
		canvas1.setTranslateX(0);
		canvas1.setTranslateY(0);
		canvas3.setTranslateX(0);
		canvas3.setTranslateY(0);
		canvas1.setLayoutX(-(startPositionX - offset) * gridSize);
		canvas1.setLayoutY(-(startPositionY - offset) * gridSize);
		canvas3.setLayoutX(-(startPositionX - offset) * gridSize);
		canvas3.setLayoutY(-(startPositionY - offset) * gridSize);
	}

	/**
	 * One of many styling methods
	 */
	private void gridPaneMethods() {
		gridPane = new GridPane();
		// Label moveInstruction = new Label("Press & Hold Control For Instructions");
		// moveInstruction.setMinHeight(50);
		// moveInstruction.setPadding(new Insets(-20, 0, 0, 260));
		// moveInstruction.setStyle("-fx-font-family: Verdana; -fx-font-size: 12;
		// -fx-font-weight: bold");
		// gridPane.add(moveInstruction, 0, 0);

		npcClue.setWrapText(true);
		npcClue.setFont(font);
		npcClue.setPadding(new Insets(0, 0, 0, 20));
		npcClue.setMaxWidth(590);

		messageCanvas = new Canvas(620, 130);
		messageGC = messageCanvas.getGraphicsContext2D();
		messageGC.drawImage(new Image("file:media/images/Message.png"), 0, 0, 620, 130);

		gridPane.setPadding(new Insets(0, 0, 0, 180));
		gridPane.add(messageCanvas, 0, 0);
		gridPane.add(npcClue, 0, 0);
		gridPane.setMinHeight(130); // needs %gridsize = 10
		gridPane.setMaxHeight(130);
		gridPane.setStyle("-fx-background-color: #FFFFFF;");
	}

	/**
	 * One of many styling methods
	 */
	private void vBoxMethods() {
		options = new VBox();
		options.getChildren().add(menu);
		options.setAlignment(Pos.TOP_CENTER);
		options.setMinWidth(180);
		options.setMaxWidth(180);
		options.setPadding(new Insets(20, 0, 0, 0));
		scroll = new HBox();
		scroll.setAlignment(Pos.TOP_CENTER);
		scroll.getChildren().addAll(up, down);
		options.setStyle("-fx-background-color: #FFFFFF;");
	}

	/**
	 * One of many styling methods
	 */
	private void menuButtonMethods() {

		item = new Label();
		money = new Label("Current Funds\n\t$" + trainer.getMoney());
		menu = new Button("Trainer Menu");
		save = new Button("Save Game");
		exit = new Button("Quit Game");
		close = new Button("Close Menu");
		back = new Button("Back");
		inventory = new Button("Inventory");
		up = new Button("\u2b06");
		down = new Button("\u2b07");
		use = new Button("Use Item");
		leave = new Button("Leave Zone");
		closeInstructions = new Button("Close");
		select = new Button("Select");
		select.setOnAction(event -> {
			Pokemon selectedPokemon = pokemonViewer.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				trainer.getItems().put(selectedItem, trainer.getItems().get(selectedItem) - 1);
				if (trainer.getItems().get(selectedItem) == 0) {
					trainer.removeItem(selectedItem);
				}
				inventoryItemEffect(selectedItem, selectedPokemon);
				pokemonViewer.update();
				pokemonViewer.refresh();
			}
		});
		if (!(this instanceof SafariView || this instanceof CaveView)) {
			leave.setVisible(false);
			leave.setDisable(true);
		}
		
		pokemonList = new Button("Pokemon");
		
		buy = new Button("Buy");
		closeShop = new Button("Close");
		buy.setMinSize(200,50);
		buy.setMaxSize(200,50);
		buy.setText("Buy");
		closeShop.setText("Close");
		closeShop.setMinSize(200,50);
		closeShop.setMaxSize(200,50);
		
		shop.getChildren().addAll(buy,closeShop);
		
		instructions.getChildren().addAll(textArea,closeInstructions);


		menu.setFocusTraversable(false);
		inventory.setMinWidth(100);
		exit.setMinWidth(100);
		save.setMinWidth(100);
		close.setMinWidth(100);
		use.setMinWidth(100);
		pokemonList.setMinWidth(100);
		leave.setMinWidth(100);
		money.setMinWidth(100);
		closeInstructions.setMinWidth(100);

		back.setMinWidth(75);

		save.setFont(font);
		menu.setFont(font);
		exit.setFont(font);
		close.setFont(font);
		back.setFont(font);
		inventory.setFont(font);
		up.setFont(font);
		down.setFont(font);
		use.setFont(font);
		pokemonList.setFont(font);
		buy.setFont(font);
		closeShop.setFont(font);
		leave.setFont(font);
		money.setFont(font);
		closeInstructions.setFont(font);

		MenuHandler menuButtonHandler = new MenuHandler();

		menu.setOnAction(menuButtonHandler);
		exit.setOnAction(menuButtonHandler);
		close.setOnAction(menuButtonHandler);
		up.setOnAction(menuButtonHandler);
		down.setOnAction(menuButtonHandler);
		back.setOnAction(menuButtonHandler);
		inventory.setOnAction(menuButtonHandler);
		use.setOnAction(menuButtonHandler);
		pokemonList.setOnAction(menuButtonHandler);
		leave.setOnAction(menuButtonHandler);
		save.setOnAction(Controller.generateSaveHandler());
	}

	/**
	 * Sets the NPC list for all the views, called on setup in Controller.
	 */
	public static void setNPCS() {
		npcs.add(new Merchant("Guy"));
		npcs.add(new OldLady());
		npcs.add(new SafariAgent());
		npcs.add(new ProfessorOak());
		npcs.add(new Sister());
		npcs.add(new Brother());
		npcs.add(new Gentleman());
		npcs.add(new Woman());
		npcs.add(new RegularMan());
		npcs.add(new EnemyTrainer("Enemy"));
	}

	/* ---------- Getters and setters ---------------- */
	public double getAnimationTime() {
		return animationTime;
	}

	public int getGridSize() {
		return gridSize;
	}

	public int getMidPoint() {
		return midPoint;
	}

	public List<Item> getItems() {
		return items;
	}

	public static Direction getDirection() {
		return direction;
	}

	public static int getNewKey() {
		return newKey;
	}

	public void setDirection(Direction dir) {
		direction = dir;
	}

	public static boolean getRunning() {
		return running;
	}

	public void setRunning(boolean run) {
		running = run;
	}

	public boolean getBiking() {
		return biking;
	}

	public void setBiking(boolean bike) {
		biking = bike;
	}

	public String getMessage() {
		return npcClue.getText();
	}

	public void setMessage(String message) {
		npcClue.setText(message);
	}

	public Character[][] getMap() {
		return grid;
	}

	public Point getTrainerPosition() {
		return currentPosition;
	}

	public void setEntrancePoint(Point point) {
		currentPosition = point;
	}

	public void setTrainerPosition(Point position) {
		trainer.setPosition(position.x, position.y);
	}

	public StartMoveHandler getMoveHandler() {
		return moveHandler;
	}
	
	public boolean getOnDisplay() {
		return onDisplay;
	}

	/*----------View Maintenance Methods----------*/

	public abstract void setupMap();

	public abstract void setMap();

	public abstract void updateMap();

	public abstract String npcMessage(char spot);

	public void loadMap(Character[][] oldMap) {
		grid = oldMap;
		// currentPosition = new Point(trainer.myX,trainer.myY);
		setCanvasOffset(currentPosition.x, currentPosition.y);
		setDirection(trainer.direction);
	}

	public void initializeMap(Character[][] theMap) {
		for (int i = 0; i < totalSquares; i++) {
			for (int j = 0; j < totalSquares; j++) {
				theMap[i][j] = '\0';
			}
		}
	}

	/**
	 * Handles drawing for the view, iterates through the entire grid and draws the
	 * images representing the square that the character is representing. Calls
	 * getNPC to draw the image associated with the correct NPC for that spot. After
	 * drawing the background, calls drawTrainer() to draw the trainer.
	 */
	public void drawView() {
		int i, j = 0;
		boolean buildingDrawn = false, bridgeDrawn = false, caveDrawn = false;
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid.length; j++) {
				char gridSpot = grid[i][j];
				switch (gridSpot) {
				case 'W':
					gcLayer1.drawImage(Terrain.getInstance('W').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					break;
				case 'D':
					gcLayer1.drawImage(Terrain.getInstance('D').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					break;
				case 'G':
					gcLayer1.drawImage(Terrain.getInstance('G').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					break;
				case 'F':
					gcLayer1.drawImage(Terrain.getInstance('G').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					gcLayer1.drawImage(Terrain.getInstance('F').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					break;
				case 'R': // rock
					if (!caveDrawn) {
						caveDrawn = true;
						gcLayer1.drawImage(Terrain.getInstance('R').getImage(), 0, 0, 8 * gridSize, 10 * gridSize,
								j * gridSize, i * gridSize, 8 * gridSize, 10 * gridSize);
					}
					break;
				case 'L': // ledge
					gcLayer1.drawImage(Terrain.getInstance('L').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					gcLayer1.setFill(Color.DARKSLATEGRAY);
					gcLayer1.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
					break;
				case 'E': // entrance/exit
					if (this instanceof CaveView || this instanceof BuildingView) {
						gcLayer1.drawImage(Terrain.getInstance('E').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
								i * gridSize, gridSize, gridSize);
						gcLayer1.setFill(Color.WHITE);
						gcLayer1.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
					} else {
						gcLayer1.drawImage(Terrain.getInstance('E').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
								i * gridSize, gridSize, gridSize);
						gcLayer1.setFill(Color.BLACK);
						gcLayer1.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
					}
					break;
				case 'V': // void
					gcLayer1.drawImage(Terrain.getInstance('V').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					gcLayer1.setFill(Color.BLACK);
					gcLayer1.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
					break;
				case 'B': // brick
					// gcLayer1.drawImage(Terrain.getInstance('B').getImage(),0,0,
					// gridSize,gridSize,j*gridSize, i*gridSize,gridSize,gridSize);
					gcLayer1.setFill(Color.BLACK);
					gcLayer1.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
					break;
				case 'M': // mat (building floor)
					gcLayer1.drawImage(Terrain.getInstance('M').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					break;
				case 'A': // appointments (bookshelf)
					gcLayer1.drawImage(Terrain.getInstance('A').getImage(), 0, 0, gridSize * 2, gridSize * 3,
							(offset + 4) * gridSize, (offset) * gridSize, gridSize * 2, gridSize * 3);
					break;
				case 'a': // appointments (table)
					gcLayer1.drawImage(Terrain.getInstance('a').getImage(), 0, 0, gridSize * 4, gridSize * 3,
							(offset + 6) * gridSize, (offset + 7) * gridSize, gridSize * 4, gridSize * 3);
					break;
				case 'w': // walkway/Path
					gcLayer1.drawImage(Terrain.getInstance('w').getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					// gcLayer1.drawImage(Terrain.getInstance('H').getImage(),0,0,
					// gridSize,gridSize,j*gridSize, i*gridSize,gridSize,gridSize);
					break;
				case 'T': // trees
					gcLayer1.drawImage(Terrain.getGround(), 0, 0, gridSize, gridSize, j * gridSize, i * gridSize,
							gridSize, gridSize);
					gcLayer1.drawImage(Terrain.getInstance('T').getImage(), 0, 0, gridSize, gridSize + 5, j * gridSize,
							i * gridSize - 5, gridSize, gridSize + 5);
					break;
				case '$': // building
					if (!buildingDrawn) {
						buildingDrawn = true;
						gcLayer1.drawImage(Terrain.getInstance('$').getImage(), 0, 0, 8 * gridSize, 4 * gridSize,
								j * gridSize, i * gridSize, 8 * gridSize, 4 * gridSize);
					}
					break;
				case '|': // bridge
					if (!bridgeDrawn) {
						bridgeDrawn = true;
						gcLayer1.drawImage(Terrain.getInstance('|').getImage(), 0, 0, 2 * gridSize, 10 * gridSize,
								j * gridSize, i * gridSize, 2 * gridSize, 10 * gridSize);
					}
					break;
				case 'Q':
					EnemyTrainer enemyTrainer = (EnemyTrainer)getNPC(gridSpot);
					if (trainer instanceof Boy) {
						enemyTrainer.setImage(new Girl("Diana").getWalkImage());
						gcLayer3.drawImage(enemyTrainer.getImage(), gridSize, 0, gridSize, gridSize,
								j * gridSize, i * gridSize, gridSize, gridSize);
					} else {
						enemyTrainer.setImage(new Boy("Denny").getWalkImage());
						gcLayer3.drawImage(enemyTrainer.getImage(), gridSize, 0, gridSize, gridSize,
								j * gridSize, i * gridSize, gridSize, gridSize);
					}
					break;
				case 'J':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// junk seller (merchant)
					break;
				case 'U':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// Old lady
					break;
				case 'I':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// SafariAgent
					break;
				case 'O':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// Professor Oak
					break;
				case 'S':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// Sister
					break;
				case 'K':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// Brother
					break;
				case 'N':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// Gentleman
					break;
				case 'X':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// woman
					break;
				case 'Z':
					gcLayer3.drawImage(getNPC(gridSpot).getImage(), 0, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);// regular man
					break;
				case 'b':
					gcLayer1.drawImage(items.get(0).getImage(), gridSize, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					// gcLayer1.setFill(Color.RED);
					// gcLayer1.fillRect(j*gridSize,i*gridSize,gridSize,gridSize);
					break;
				case 'f':
					gcLayer1.drawImage(items.get(1).getImage(), 2 * gridSize, 0, gridSize, gridSize, j * gridSize,
							i * gridSize, gridSize, gridSize);
					// gcLayer1.setFill(Color.RED);
					// gcLayer1.fillRect(j*gridSize,i*gridSize,gridSize,gridSize);
					break;
				case 'p':
					gcLayer1.drawImage(items.get(2).getImage(), 0, 0, gridSize, gridSize, j * gridSize, i * gridSize,
							gridSize, gridSize);
					break;
				default:
					gcLayer1.setFill(Color.AQUAMARINE);
					gcLayer1.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
				}
			}
		}
		drawTrainer();
	}

	/**
	 * Returns the NPC object representing the spot marked on the grid.
	 * 
	 * @param npcChar the corresponding character
	 * @return the NPC
	 */
	public NPC getNPC(char npcChar) {

		switch (npcChar) {
		case 'J':
			return npcs.get(0);

		case 'U':
			return npcs.get(1);

		case 'I':
			return npcs.get(2);

		case 'O':
			return npcs.get(3);

		case 'S':
			return npcs.get(4);

		case 'K':
			return npcs.get(5);

		case 'N':
			return npcs.get(6);

		case 'X':
			return npcs.get(7);

		case 'Z':
			return npcs.get(8);

		case 'Q':
			return npcs.get(9);
		}
		return null;
	}

	/**
	 * Draws the trainer based on the direction they are facing.
	 */
	public void drawTrainer() {
		int sy = 0;
		if (direction == Direction.DOWN) {
			sy = 0;
		} else if (direction == Direction.LEFT) {
			sy = gridSize;
		} else if (direction == Direction.UP) {
			sy = 2 * gridSize;
		} else {
			sy = 3 * gridSize;
		}

		if (!getRunning()) {
			gcLayer2.clearRect(0, 0, 450, 450);
			if (getBiking()) {
				gcLayer2.drawImage(trainer.getBikeImage(), 0, sy, gridSize, gridSize, (midPoint) * gridSize,
						(midPoint) * gridSize, gridSize, gridSize);
			} else {
				gcLayer2.drawImage(trainer.getWalkImage(), gridSize, sy, gridSize, gridSize, (midPoint) * gridSize,
						(midPoint) * gridSize, gridSize, gridSize);
			}
		}
	}
	
	public void displayShop(Merchant merchant) {
		onDisplay=true;
		InventoryViewer inventory = merchant.getInventory();
		inventory.addPriceCol();
		shop.getChildren().add(0,inventory);
		inventory.setMinSize(204,300);
		pane.getChildren().add(shop);
		buy.setOnAction(event -> {
			Item selectedItem = inventory.getSelectionModel().getSelectedItem();
		      if (selectedItem != null) {
		    	  if(trainer.canSpendMoney(selectedItem.getPrice())) {
		    		  trainer.purchase(selectedItem, selectedItem.getPrice());
		    		  setMessage(trainer.getName() + " has purchased a(n) " + selectedItem.toString());
		    		  setInventoryText();
		    	  }
		    	  else {
		    		  setMessage(trainer.getName() + " could not afford a(n) " + selectedItem.toString());
		    	  }
		      }
		      canvas2.requestFocus();
		});
		closeShop.setOnAction(event -> {
			inventory.resetCols();
			shop.getChildren().remove(inventory);
			pane.getChildren().remove(shop);
			onDisplay=false;
		    canvas2.requestFocus();
		    setMessage("");
			canvas2.setOnKeyPressed(getMoveHandler());
		});
		
	}
	
	public void displayInstructions() {
		
		//pane.getChildren().add(instructions);
		instructions.setMinSize(450, 500);
		textArea.setMinSize(450,450);
		setCenter(textArea);
		setRight(closeInstructions);	
		BorderPane.setMargin(closeInstructions, new Insets(200,200,0,0));
		
		closeInstructions.setOnAction(event -> {
			setCenter(null);
			setBottom(null);
			setLeft(null);
			setRight(null);
			setCenter(pane);
			setBottom(gridPane);
			setLeft(options);
			//pane.getChildren().remove(instructions);
		    canvas2.requestFocus();
		    setMessage("");
			canvas2.setOnKeyPressed(getMoveHandler());
		});
	}
	
	public void displayStatistics() {
		
		statistics = new StatSheet(trainer);
		setLeft(null);
		setCenter(statistics);
		setBottom(closeInstructions);
		setRight(null);
		
		closeInstructions.setOnAction(event -> {
			setCenter(null);
			setBottom(null);
			setLeft(null);
			setRight(null);
			setCenter(pane);
			setBottom(gridPane);
			setLeft(options);
			//pane.getChildren().remove(instructions);
		    canvas2.requestFocus();
		    setMessage("");
			canvas2.setOnKeyPressed(getMoveHandler());
		});
	}
	
	public void updateMoney() {
		money.setText("Current Funds\n\t$" + trainer.getMoney());
	}

	/*----------Strategy Patterns----------*/

	/**
	 * The strategy for encountering a wild Pokémon
	 * 
	 * @param area the location where the battle is going to start
	 * @return the Pokémon that was encountered
	 */
	public Pokemon encounterWildPokemon(String area) {
		int random = new Random().nextInt(19);
		if (area.equals("Water")) {
			if (random < 18) {
				return chooseWildPokemon(area);
			}
		} else if (random < 5) {
			return chooseWildPokemon(area);
		}
		return null;
	}

	/**
	 * The strategy for picking the type of Pokémon that appears
	 * 
	 * @return one of four types
	 */
	protected abstract Pokemon chooseWildPokemon(String area);

	/*----------Private Event Handlers----------*/

	private class StartMoveHandler implements EventHandler<KeyEvent> {
		private MoveHandler move;

		public StartMoveHandler(GeneralView generalView, Trainer trainer, Canvas canvas1, Canvas canvas2,
				Canvas canvas3) {
			move = new MoveHandler(generalView, trainer, canvas1, canvas2, canvas3);
		}

		@Override
		public void handle(KeyEvent arg0) {
			move.handle(arg0);

		}

	}

	private class EndMoveHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent arg0) {
			KeyCode key = arg0.getCode();
			if ((key == KeyCode.UP && direction == Direction.UP)
					|| (key == KeyCode.RIGHT && direction == Direction.RIGHT)
					|| (key == KeyCode.DOWN && direction == Direction.DOWN)
					|| (key == KeyCode.LEFT && direction == Direction.LEFT)) {
				running = false;
				newKey = 0;
				// drawTrainer();
			}
		}
	}

	/**
	 * This inner class handles all the menu options for the user
	 */
	private class MenuHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			inventorySize = trainer.getInventorySize();
			String text = ((Button) event.getSource()).getText();
			options.getChildren().clear();

			if (text.equals(menu.getText())) {
				options.getChildren().addAll(inventory, pokemonList, save, exit, close, leave);
			} else if (text.equals(inventory.getText())) {
				setInventoryText();
				options.getChildren().addAll(back, scroll, item, use, money);
			} else if (text.equals(exit.getText())) {
				Platform.exit();
			} else if (text.equals(back.getText())) {
				options.getChildren().addAll(inventory, pokemonList, save, exit, close, leave);
			} else if (text.equals(close.getText())) {
				options.getChildren().addAll(menu);
			} else if (text.equals(use.getText())) {
				selectedItem = getInventoryItem();
				if (selectedItem == null || selectedItem instanceof Bicycle || selectedItem instanceof FishingRod) {
					options.getChildren().addAll(back, scroll, item, use, money);
				} else {
					options.getChildren().addAll(menu, pokemonViewer, select);
				}
			} 
			else if (text.equals(leave.getText())) {
				Controller.setTown();
				options.getChildren().add(menu);
			} else if (text.equals(pokemonList.getText())) {
				pokemonViewer.update();
				pokemonViewer.refresh();
				options.getChildren().addAll(menu, pokemonViewer);
			} else if (text.equals("\u2b06")) {
				if (inventorySize != 0) {
					inventoryIndex--;
					if (inventoryIndex < 0) {
						inventoryIndex = inventorySize + inventoryIndex;
					}
				}
				setInventoryText();
				options.getChildren().addAll(back, scroll, item, use, money);
			}

			else if (text.equals("\u2b07")) {
				if (inventorySize != 0) {
					inventoryIndex++;
					inventoryIndex = inventoryIndex % inventorySize;
				}
				setInventoryText();
				options.getChildren().addAll(back, scroll, item, use, money);
			}
			canvas2.requestFocus();

		}

	}

	/**
	 * This inner class handles displaying instructions when CTRL is pressed
	 */
	private class InstructionStarter implements EventHandler<KeyEvent> {

		private GeneralView currentView;

		InstructionStarter(GeneralView generalView) {
			this.currentView = generalView;
		}

		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.CONTROL) {
				currentView.setCenter(textArea);
			}
		}
	}

	/**
	 * This inner class handles removing instructions when CTRL is released
	 */
	private class InstructionExiter implements EventHandler<KeyEvent> {

//		private GeneralView currentView;
//
//		InstructionExiter(GeneralView generalView) {
//			this.currentView = generalView;
//		}

		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.CONTROL) {
				setCenter(null);
				setBottom(null);
				setLeft(null);
				setCenter(pane);
				setBottom(gridPane);
				setLeft(options);
			}
		}

	}

	/* ----- helper methods ----- */
	/**
	 * Allows the user to see his or her list of inventory items in real time Called
	 * by MoveHandler, in pressEnter() when a user picks up an item. Called by
	 * MenuHandler when the user opens the inventory or clicks the arrows to
	 * navigate their inventory.
	 */
	public void setInventoryText() {
		int i = 0;
		for (Item key : trainer.getItems().keySet()) {
			if (i == inventoryIndex) {
				item.setText(key + ": " + trainer.getItems().get(key));
				item.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
				updateMoney();
				return;
			}
			i++;
		}
		item.setText("");
	}

	/**
	 * Returns the item at the current index (the item currently being displayed in
	 * inventory). Called when the user clicks "Use Item", will be used on the
	 * pokemon selected after.
	 * 
	 * @return
	 */
	private Item getInventoryItem() {
		int i = 0;
		for (Item key : trainer.getItems().keySet()) {
			if (i == inventoryIndex) {
				return key;
			}
			i++;
		}
		item.setText("");
		return null;
	}

	/**
	 * Use the item effect on the currently selected pokemon passed in.
	 * 
	 * @param selectedItem
	 * @param selectedPokemon
	 */
	private void inventoryItemEffect(Item selectedItem, Pokemon selectedPokemon) {
		if (selectedItem instanceof Potion) {
			selectedPokemon.increaseCurrentHP();
		} else if (selectedItem instanceof XAttack) {
			selectedPokemon.raiseAttack(1); // adds to pokemon's attack
		} else if (selectedItem instanceof XDefense) {
			selectedPokemon.raiseDefense(1); // adds to pokemon's defense
		}

	}

}