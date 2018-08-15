package views.general;

import java.awt.Point;
import java.util.Random;

import model.Direction;
import model.pokemon.Gloom;
import model.pokemon.Jigglypuff;
import model.pokemon.Pidgeotto;
import model.pokemon.Pikachu;
import model.pokemon.Pokemon;
import model.pokemon.Staryu;
import model.pokemon.Tentacool;
import model.trainer.Trainer;

/**
 * This is one concrete instance of the General View. It contains all the
 * information and back-end logic for the map the user plays on in the Safari
 * Zone quest.
 * 
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public class SafariView extends GeneralView {

	/*----------Instance Variables----------*/
	private Character[][] safariMap;
	private final int middle = 14;

	/*----------Constructor & Assignment----------*/

	public SafariView(Trainer trainer) {
		super(trainer);
	}

	/**
	 * Adds flower patches to the map at various places
	 * 
	 * @param i
	 * @param j
	 */
	private void addFlowers(int i, int j) {
		int iterateRow = i;
		int iterateCol;
		for (; iterateRow <= i + 3; iterateRow++) {
			for (iterateCol = j; iterateCol <= j + 3; iterateCol++) {
				if ((iterateRow >= 0 && iterateCol >= 0)
						&& (iterateRow < safariMap.length && iterateCol < safariMap.length)) {
					if (safariMap[iterateRow][iterateCol] != 'W') {
						safariMap[iterateRow][iterateCol] = 'F';
					}
				}
			}
		}
	}

	public void setupMap() {

		safariMap = new Character[totalSquares][totalSquares];
		initializeMap(safariMap);

		for (int i = 0; i < safariMap.length; i++) {
			for (int j = 0; j < safariMap.length; j++) {
				/*
				 * if ((i == offset-1) &&(j <= offset+18 && j >= offset+11)) { safariMap[i][j] =
				 * 'L'; }
				 */
				if ((i <= offset - 1) && (j <= offset + 18 && j >= offset + 11)) {
					safariMap[i][j] = 'R';
				} else if (i >= offset + 30 && (j == offset + 14 || j == offset + 15)) {
					safariMap[i][j] = '|';
				} else if (i >= safariMap.length - offset || j >= safariMap.length - offset || i <= offset - 1
						|| j <= offset - 1) {
					safariMap[i][j] = 'W';
				} else if (i >= safariMap.length - (offset + 2) || j >= safariMap.length - (offset + 2)
						|| i <= offset + 1 || j <= offset + 1 || i == offset + middle || i == offset + middle + 1
						|| j == offset + middle || j == offset + middle + 1) {
					safariMap[i][j] = 'D';
				}

				else if ((i == offset + 6 && j == offset + 6) || (i == offset + 20 && j == offset + 6)
						|| (i == offset + 6 && j == offset + 20) || (i == offset + 20 && j == offset + 20)) {
					addFlowers(i, j);
				}

				else {
					if (safariMap[i][j] == '\u0000') { // null character
						safariMap[i][j] = 'G';
					}
				}

			}
		}

		safariMap[offset - 1][offset + 14] = 'E';
		safariMap[offset - 1][offset + 15] = 'E';
		// grid = safariMap;
		currentPosition = new Point(24, 39);
		setCanvasOffset(currentPosition.x, currentPosition.y);
		grid = safariMap;
		drawView();
		safariMap[offset][offset] = 'Z';
		safariMap[offset + 9][offset + 20] = 'U';
		safariMap[offset + 20][offset + 9] = 'N';
		safariMap[offset + 29][offset + 29] = 'X';
		grid = safariMap;
		drawView();
	}

	public void updateMap() {
		safariMap = grid;
	}

	public void setMap() {
		grid = safariMap;
		reposition();
	}

	protected void reposition() {
		currentPosition.y += 1;
		if (trainer.direction != Direction.DOWN) {
			direction = Direction.UP;
		}
		setCanvasOffset(currentPosition.x, currentPosition.y);
		drawTrainer();
	}

	/**
	 * The strategy for picking the type of Pokémon that appears
	 * 
	 * @return one of four types
	 */
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
		} else {
			if (random < 3) {
				return new Staryu("Staryu", "StaryuFront.png", 15, 6, 3, 5);
			} else {
				return new Tentacool("Tentacool", "TentaCoolFront.png", 15, 5, 4, 5);
			}
		}

	}

	@Override
	public String npcMessage(char spot) {
		return getNPC(spot).getMessageTwo();
	}

}