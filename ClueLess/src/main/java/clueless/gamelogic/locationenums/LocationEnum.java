package clueless.gamelogic.locationenums;

import clueless.gamelogic.Location;

public enum LocationEnum {
	STUDY(new Location(0, 4)),
	HALL(new Location(2, 4)),
	LOUNGE(new Location(4, 4)),
	LIBRARY(new Location(0, 2)),
	BILLIARD_ROOM(new Location(2, 2)),
	DINING_ROOM(new Location(4, 2)),
	CONSERVATORY(new Location(0, 0)),
	BALLROOM(new Location(2, 0)),
	KITCHEN(new Location(4, 0));
	
	Location location;
	
	private LocationEnum(Location location) {
		this.location = location;
	}
}
