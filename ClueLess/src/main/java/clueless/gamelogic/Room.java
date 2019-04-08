package clueless.gamelogic;

import java.util.ArrayList;

/**
 * Used to represent the rooms on the board.
 * 
 * @author matthewsobocinski
 */
public class Room extends BoardLocationEntity {
	private ArrayList<Player> currentPlayers = null;
	
	public Room(String name, String abbreviation, Location location) {
		super(name, abbreviation, location);
		this.currentPlayers = new ArrayList<>();
	}
	
	public ArrayList<Player> getCurrentPlayers() {
		return currentPlayers;
	}
}
