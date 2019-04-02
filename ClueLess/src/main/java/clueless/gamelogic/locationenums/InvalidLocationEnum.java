package clueless.gamelogic.locationenums;

import clueless.gamelogic.Location;

/**
 * Enum for invalid locations on the board
 * 
 * @author erinsmedley
 *
 */
public enum InvalidLocationEnum  {
	INVALID1(new Location(1,3)),
	INVALID2(new Location(3,3)),
	INVALID3(new Location(1,1)), 
	INVALID4(new Location(3,1));

	Location location;
	
	private InvalidLocationEnum(Location location) {
		this.location = location;
	}
}
