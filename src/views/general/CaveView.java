package views.general;

import java.awt.Point;
import java.util.Random;

import model.Direction;
import model.pokemon.Golbat;
import model.pokemon.Growlithe;
import model.pokemon.Pokemon;
import model.pokemon.Seaking;
import model.pokemon.Zubat;
import model.trainer.Trainer;

/**
 * This is one concrete instance of the General View. It will be implemented in
 * the next iteration.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class CaveView extends GeneralView {

	private Character[][] caveMap;

	public CaveView(Trainer trainer) {
		super(trainer);
	}

	@Override
	public void setupMap() {
		caveMap = new Character[totalSquares][totalSquares];
		initializeMap(caveMap);

		currentPosition = new Point(offset + 10, offset + 10);

		setCanvasOffset(currentPosition.x, currentPosition.y);
		for (int i = 0; i < totalSquares; i++) {
			for (int j = 0; j < totalSquares; j++) {
				if ((i >= offset + 2 && i <= offset + 7) && (j >= offset + 13 && j <= offset + 17)) {
					caveMap[i][j] = 'W';
				} else if ((i >= offset && i <= offset + 9) && (j >= offset && j <= offset + 19)) {
					caveMap[i][j] = 'D';
				} else if ((i == offset + 10) && (j == offset + 9 || j == offset + 10)) {
					caveMap[i][j] = 'E';
				} else {
					if (caveMap[i][j] == '\u0000') { // null character
						caveMap[i][j] = 'V';
					}
				}

			}
		}

		setCanvasOffset(currentPosition.x, currentPosition.y);
		grid = caveMap;
		drawView();
		caveMap[offset + 1][offset + 1] = 'N';
		// currentPosition = new Point(offset+, startPositionY);
		grid = caveMap;
		drawView();

	}

	public void setMap() {
		grid = caveMap;
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
		int random = new Random().nextInt(10);
		if (area.equals("Cave")) {
			if (random < 5) {
				return new Zubat("Zubat", "ZubatFront.png", 15, 6, 3, 5);
			} else if (random < 8) {
				return new Golbat("Golbat", "GolbatFront.png", 15, 5, 4, 5);
			} else {
				return new Growlithe("Growlithe", "GrowlitheFront.png", 15, 3, 6, 5);
			}
		} else {
			return new Seaking("Seaking", "SeakingFront.png", 15, 3, 6, 5);
		}
	}

	@Override
	public void updateMap() {
		caveMap = grid;
	}

	@Override
	public String npcMessage(char spot) {
		return getNPC(spot).getMessageOne();
	}

}
