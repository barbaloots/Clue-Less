package clueless.gamelogic.locationenums;

import clueless.gamelogic.Location;

/**
 * Where each player-character starts each game on the board
 * 
 * @author erinsmedley
 *
 */
public enum CharacterStartLocationEnum {
	PLUM(new Location(0, 3)),
	PEACOCK(new Location(0, 1)),
	GREEN(new Location(1, 0)),
	WHITE(new Location(3, 0)),
	MUSTARD(new Location(4, 3)),
	SCARLET(new Location(3, 4));
	
	Location location;
	
	/**
	 * Enum constructor that sets the character start location
	 * 
	 * @param location
	 * 		the character start location
	 */
	private CharacterStartLocationEnum(Location location) {
		this.location = location;
	}
	
	/**
	 * Get the character's start location
	 * 
	 * @return the location
	 */
	public Location getCharacterStartLocation() {
		return location;
	}
}
