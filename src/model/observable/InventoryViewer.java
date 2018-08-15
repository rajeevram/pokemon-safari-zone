package model.observable;

import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.items.Item;
import model.trainer.Trainer;

/**
 * This is the table view for the Pokemon owned by the trainer
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R. Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */

public class InventoryViewer extends TableView<Item> {

	private Trainer trainer;
    private TableColumn<Item, Integer> countColumn;
    private TableColumn<Item, Integer> priceColumn;
    private TableColumn<Item, String> nameColumn;

	@SuppressWarnings("unchecked")
	public InventoryViewer(Trainer trainer) {
		this.trainer = trainer;
		// add columns to table

		nameColumn = new TableColumn<>("Name");
		countColumn = new TableColumn<>("Count");
		priceColumn = new TableColumn<>("Price");

		// set cell values for specified column
		nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		countColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("count"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("price"));

		this.setPrefSize(200, 300);
	    this.setStyle( " -fx-border-color:black;"
                + " -fx-font-size: 14;"
                + " -fx-text-fill: white;"
                + " -fx-border-radius:5px");

		// all cols to table view

		this.getColumns().addAll(nameColumn, countColumn, priceColumn);
		nameColumn.setPrefWidth(100);
		countColumn.setPrefWidth(100);
		priceColumn.setPrefWidth(100);
		priceColumn.setVisible(false);
		
		// Set up the the model for this TableView
		update();
	}

	
	/**
	 * Instantiate and update observable list of trainer's inventory items
	 */
	public void update() {
		ObservableList<Item> Item = FXCollections.observableArrayList();

		for (Entry<Item, Integer> entry : trainer.getItems().entrySet()) {
			Item item = entry.getKey();
			if (item.getCount() == 0) {
				trainer.removeItem(item);
			} else {
				Item.add(item);

			}

		}
		this.setItems(Item);
		this.getSelectionModel().select(0);
	}

	/**
	 * Modify the columns of the observable list of inventory items
	 */
	public void addPriceCol() {
		countColumn.setVisible(false);
		priceColumn.setVisible(true);
	}

	/**
	 * Reset the columns of the observable list of inventory items
	 */
	public void resetCols() {
		countColumn.setVisible(true);
		priceColumn.setVisible(false);

	}
}