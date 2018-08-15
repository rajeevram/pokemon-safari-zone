package model;

/**
 * This enumeration if for locations in the game.
 *
 * @author Alan D., Paria K., Samantha F., Rajeev R., Scrum Master: Niven
 *         Francis, CSC 335 Final Project
 */
public enum Location {
	BUILDING("BuildingMusic"), SAFARI("SafariZone"), TOWN("TownMusic"), CAVE("CaveMusic");

	private String musicPath;

	Location(String musicPath) {
		this.musicPath = musicPath;
	}

	public String getMusicPath() {
		return musicPath;
	}
}
