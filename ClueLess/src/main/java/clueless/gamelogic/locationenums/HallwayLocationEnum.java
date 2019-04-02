package clueless.gamelogic.locationenums;

import clueless.gamelogic.Location;

public enum HallwayLocationEnum {
	STUDY_HALL(new Location(1, 4)),
	HALL_LOUNGE(new Location(3, 4)),
	STUDY_LIBRARY(new Location(0, 3)),
	HALL_BILLIARD(new Location(2, 3)),
	LOUNGE_DINING(new Location(4, 3)),
	LIBRARY_BILLIARD(new Location(1, 2)),
	BILLIARD_DINING(new Location(3, 2)),
	LIBRARY_CONSERVATORY(new Location(0, 1)),
	BILLIARD_BALLROOM(new Location(2, 1)),
	DINING_KITCHEN(new Location(4, 1)),
	CONSERVATORY_BALLROOM(new Location(1, 0)),
	BALLROOM_KITCHEN(new Location(3, 0));

	Location location;

	private HallwayLocationEnum(Location location) {
		this.location = location;
	}
}
