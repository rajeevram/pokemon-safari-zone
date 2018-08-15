package views.general;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.observable.InventoryViewer;
import model.observable.PokemonViewer;
import model.trainer.Trainer;

public class StatSheet extends ParentView{
	
	private Trainer trainer;
	private VBox trainerDisplay; 
	private InventoryViewer inventoryViewer;
	private PokemonViewer pokemonViewer; 
	private Label header; 
	private Label name;
	private Label moves;
	private Label money;
	private Label caughtPoke; 
	private Label balls; 
	private Button exit = new Button("Exit");
	private final Font font = new Font("Verdana", 18);
	private final Font headerFont = new Font("Verdana", 20);
	
	public StatSheet(Trainer trainer) {
		this.trainer = trainer; 
		
		
		initalizePane(); 
		
	}
	
	public void initalizePane() {
		
		this.setPadding(new Insets(20, 20, 20, 20)); 
		//this.setPadding(header, new Insets(10,10,10,10));
		
		trainerDisplay = new VBox(); 
		name = new Label("Name: " + trainer.getName());
		moves = new Label("Steps Left: " + trainer.getStepCount());
		money = new Label("Money: $" + trainer.getMoney());
		balls = new Label("Balls Left: " + trainer.getBallCount());
		header = new Label("Trainer Statistics");
		caughtPoke = new Label("Pokemon Caught: "+ trainer.getNumCaught()); 
		trainerDisplay.getChildren().addAll(name, money, moves, balls, caughtPoke);
		inventoryViewer = new InventoryViewer(trainer);
		pokemonViewer = new PokemonViewer(trainer);
		
		BorderPane.setAlignment(header, Pos.TOP_CENTER);
		
		inventoryViewer.setMaxSize(204,300);
		pokemonViewer.setMaxSize(204,300);
		
		trainerDisplay.setPadding(new Insets(10, 50, 50, 50));
		trainerDisplay.setSpacing(15);
		
		header.setFont(headerFont);
		header.setStyle("-fx-background-color:#F08080;" +  "-fx-border-color:black;" + " -fx-border-radius:5px");

		balls.setFont(font);
		name.setFont(font);
		moves.setFont(font);
		money.setFont(font);
		caughtPoke.setFont(font);
		
		this.setTop(header);
		this.setLeft(inventoryViewer);
		this.setCenter(trainerDisplay);
		this.setRight(pokemonViewer);
		
		exit.setFont(font);
		
		exit.setMinWidth(35);
		exit.setMinHeight(35);
		exit.setFont(font);
		exit.setStyle("-fx-background-color:#F08080;" + " -fx-border-color:black;" + " -fx-border-radius:5px");

		
	}
}
