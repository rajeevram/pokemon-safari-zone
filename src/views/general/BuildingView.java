package views.general;

import java.awt.Point;

import model.Direction;
import model.pokemon.Pokemon;
import model.trainer.Trainer;

/**
 * This is one concrete instance of the General View. It will be implemented in
 * the next iteration.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class BuildingView extends GeneralView {

	private Character[][] buildingMap;

	public BuildingView(Trainer trainer) {
		super(trainer);
	}

	@Override
	public void setupMap() {
		buildingMap = new Character[totalSquares][totalSquares];

		for (int i = 0; i < totalSquares; i++) {
			for (int j = 0; j < totalSquares; j++) {
				buildingMap[i][j] = 'M';
			}
		}

		grid = buildingMap;
		drawView();

		for (int i = 0; i < totalSquares; i++) {
			for (int j = 0; j < totalSquares; j++) {
				if (i < offset || j < offset || i > offset + 9 || j > offset + 9) {
					buildingMap[i][j] = 'B';
				} else if (i >= offset + 7 && j >= offset + 6) {
					buildingMap[i][j] = 'a';
				} else {
					if (buildingMap[i][j] == '\u0000') { // null character
						buildingMap[i][j] = 'M';
					}
				}

			}
		}

		buildingMap[offset][offset + 4] = 'A';
		buildingMap[offset][offset + 5] = 'A';
		buildingMap[offset + 1][offset + 4] = 'A';
		buildingMap[offset + 1][offset + 5] = 'A';
		buildingMap[offset + 2][offset + 4] = 'A';
		buildingMap[offset + 2][offset + 5] = 'A';

		currentPosition = new Point(11, 19);
		setCanvasOffset(currentPosition.x, currentPosition.y);

		buildingMap[offset + 1][offset + 1] = 'O';

		buildingMap[offset + 10][offset + 1] = 'E';
		buildingMap[offset + 10][offset + 2] = 'E';
		// currentPosition = new Point(offset+, startPositionY);

		grid = buildingMap;
		drawView();

	}

	public void setMap() {
		grid = buildingMap;
		reposition();
	}

	protected void reposition() {
		currentPosition.y -= 1;
		direction = Direction.UP;
		setCanvasOffset(currentPosition.x, currentPosition.y);
		drawTrainer();
	}

	@Override
	protected Pokemon chooseWildPokemon(String area) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMap() {
		buildingMap = grid;
	}

	@Override
	public String npcMessage(char spot) {
		return getNPC(spot).getMessageOne();
	}

}
