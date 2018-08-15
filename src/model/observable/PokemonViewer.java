package model.observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.pokemon.Pokemon;
import model.trainer.Trainer;

/**
 * This is the table view for the Pokemon owned by the trainer
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R. Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class PokemonViewer extends TableView<Pokemon> {

	private Trainer trainer;

	@SuppressWarnings("unchecked")
	public PokemonViewer(Trainer trainer) {
		this.trainer = trainer;
		// add columns to table

		TableColumn<Pokemon, String> nameColumn = new TableColumn<>("Pokémon");
		TableColumn<Pokemon, Integer> hpColumn = new TableColumn<>("Current HP");

		// set cell values for specified column
		nameColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, String>("name"));
		hpColumn.setCellValueFactory(new PropertyValueFactory<Pokemon, Integer>("currentHP"));
		// all cols to table view

		this.getColumns().addAll(nameColumn, hpColumn);

		nameColumn.setPrefWidth(80);
		hpColumn.setPrefWidth(100);

		this.setPrefSize(180, 300);
		this.setStyle( " -fx-border-color:black;"
                + " -fx-font-size: 14;"
                + " -fx-text-fill: white;"
                + " -fx-border-radius:5px");
		/*
		 * this.setMaxWidth(180); this.setMaxHeight(400); this.setPadding(new
		 * Insets(100,0,0,0));
		 */

		// Set up the the model for this TableView
		update();
	}

	/**
	 * Instantiate and update observable list of trainer's pokemon
	 */
	public void update() {
		ObservableList<Pokemon> Pokemon = FXCollections.observableArrayList();
		// Populate the observable list so all show in the TableView in the GUI
		for (int i = 0; i < trainer.getOwnedPokemon().size(); i++) {
			Pokemon currentPokemon = trainer.getOwnedPokemon().get(i);
			/*
			if (currentPokemon.getCurrentHP() <= 0) {
				trainer.removePokemon(currentPokemon);
			}*/
			Pokemon.add(currentPokemon);
		}
		this.setItems(Pokemon);
		this.getSelectionModel().select(0);
	}

}
