package clueless.gamelogic;

import java.util.ArrayList;

/**
 * Used to represent the rooms on the board.
 * 
 * @author matthewsobocinski
 */
public class Room extends BoardLocationEntity {
	private boolean hasSecretPassage;
	private ArrayList<Player> currentPlayers = null;
	
	public Room(String name, String abbreviation, Location location, boolean hasSecretPassage) {
		super(name, abbreviation, location);
		this.hasSecretPassage = hasSecretPassage;
		this.currentPlayers = new ArrayList<>();
	}
	
	public boolean hasSecretPassage() {
		return hasSecretPassage;
	}
	
	public ArrayList<Player> getCurrentPlayers() {
		return currentPlayers;
	}
}
