package views.general;

import java.awt.Point;
import java.util.Random;

import model.Direction;
import model.pokemon.Gloom;
import model.pokemon.Jigglypuff;
import model.pokemon.Pidgeotto;
import model.pokemon.Pikachu;
import model.pokemon.Pokemon;
import model.trainer.Trainer;

/**
 * This is one concrete instance of the General View. It will be implemented in
 * the next iteration.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class TownView extends GeneralView {

	private Character[][] townMap;

	public TownView(Trainer trainer) {
		super(trainer);
	}

	public void addNPC() {
	}

	@Override
	public void setupMap() {

		townMap = new Character[totalSquares][totalSquares];
		initializeMap(townMap);

		for (int i = 0; i < totalSquares; i++) {
			for (int j = 0; j < totalSquares; j++) {
				if (i < offset && j >= offset + 14 && j <= offset + 15) {
					townMap[i][j] = '|';
				} else if (i < offset) {
					townMap[i][j] = 'W';
				} else if ((i >= offset && i <= offset + 8)
						&& ((j >= offset && j <= offset + 8) || (j >= offset + 21 && j <= offset + 29))) {
					townMap[i][j] = 'G';
				} else if (j < offset || j > offset + 29 || i > offset + 29
						|| ((i >= offset + 14 && i <= offset + 17) && (j >= offset && j <= offset + 12))
						|| ((i >= offset + 14 && i <= offset + 25) && (j >= offset + 23 && j <= offset + 26))) {
					townMap[i][j] = 'T';
				} else if ((i >= offset + 22 && i <= offset + 25) && (j >= offset + 11 && j <= offset + 18)) {
					townMap[i][j] = '$';
				} else {
					if (townMap[i][j] == '\u0000') { // null character
						townMap[i][j] = 'w';
					}
				}

			}
		}

		currentPosition = new Point(22, 35);
		setCanvasOffset(currentPosition.x, currentPosition.y);
		grid = townMap;
		drawView();

		townMap[offset][offset + 13] = 'I';
		townMap[offset + 3][offset + 16] = 'K';
		townMap[offset + 3][offset + 17] = 'S';
		townMap[offset + 9][offset + 29] = 'Z';
		townMap[offset + 26][offset + 11] = 'X';
		townMap[offset + 14][offset + 22] = 'U';
		townMap[offset + 13][offset] = 'J';
		townMap[offset + 18][offset] = 'Q';
		townMap[offset + 25][offset + 12] = 'E';
		townMap[offset + 25][offset + 13] = 'E';
		townMap[offset][offset] = 'b';
		townMap[offset][offset + 29] = 'f';

		grid = townMap;
		drawView();

	}

	public void setMap() {
		grid = townMap;
		reposition();
	}

	protected void reposition() {
		currentPosition.y += 1;
		direction = Direction.DOWN;
		setCanvasOffset(currentPosition.x, currentPosition.y);
		drawTrainer();
	}

	@Override
	protected Pokemon chooseWildPokemon(String area) {
		int random = new Random().nextInt(10);
		if (area.equals("Grass")) {
			if (random < 4) {
				return new Jigglypuff("Jigglypuff", "JigglypuffFront.png", 15, 6, 3, 5);
			} else if (random < 7) {
				return new Pidgeotto("Pidgeotto", "PidgeottoFront.png", 15, 5, 4, 5);
			} else if (random < 9) {
				return new Gloom("Gloom", "GloomFront.png", 15, 4, 5, 5);
			} else {
				return new Pikachu("Pikachu", "PikachuFront.png", 15, 3, 6, 5);
			}
		}
		return null;
	}

	@Override
	public void updateMap() {
		townMap = grid;
	}

	@Override
	public String npcMessage(char spot) {
		return getNPC(spot).getMessageOne();
	}

}