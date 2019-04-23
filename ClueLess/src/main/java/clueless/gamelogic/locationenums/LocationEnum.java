package clueless.gamelogic.locationenums;

import clueless.gamelogic.Location;

/**
 * Store the locations of each room and hallway in a 5x5 grid (includes 
 * the four invalid location). Reference the game description document 
 * for more details.
 * 
 * ==========================================================
 * 	(0,0)	|	(0,1)	|	(0,2)	|	(0,3)	|	(0,4)	|		
 * 	Study	|	SH_Hall |	Hall	|	HL_Hall	|	Lounge	|
 * ==========================================================
 * 	(1,0)	|	(1,1)	|	(1,2)	|	(1,3)	|	(1,4)	|	
 * 	SL_Hall	|	INVALID	|	HB_Hall	|	INVALID	|	LD_HALL	|
 * ==========================================================
 * 	(2,0)	|	(2,1)	|	(2,2)	|	(2,3)	|	(2,4)	|	
 * 	Libary	|	LB_Hall	|	Bilrd.  |	BD_HALL	|	Dining	|
 * ==========================================================
 * 	(3,0)	|	(3,1)	|	(3,2)	|	(3,3)	|	(3,4)	|		
 * 	LC_Hall	|	INVALID	|	BB_Hall	|	INVALID	|	DK_Hall	|
 * ==========================================================
 * 	(4,0)	|	(4,1)	|	(4,2)	|	(4,3)	|	(4,4)	|	
 * 	Consrv. |	CB_Hall	|	Ballrm. |	BK_Hall	|	Kitchen	|
 * ==========================================================
 *
 */
public enum LocationEnum {
	// First row
	STUDY("RS", new Location(0,0)),
	STUDY_TO_HALL_HALLWAY("RSRHH", new Location(0,1)),
	HALL("RH", new Location(0,2)),
	HALL_TO_LOUNGE_HALLWAY("RHRLH", new Location(0,3)),
	LOUNGE("RL", new Location(0,4)),
	
	// Second row
	STUDY_TO_LIBRARY_HALLWAY("RSRIH", new Location(1,0)),
	INVALID_LOCATION_ONE("IL1", new Location(1,1)),
	HALL_TO_LIBRARY_HALLWAY("RHRBH", new Location(1,2)),
	INVALID_LOCATION_TWO("IL2", new Location(1,3)),
	LOUNGE_TO_DINING_ROOM_HALLWAY("RLRDH", new Location(1,4)),
	
	// Third row
	LIBRARY("RI", new Location(2,0)),
	LIBRARY_TO_BILLIARD_ROOM_HALLWAY("RIRBH", new Location(2,1)),
	BILLIARDROOM("RB", new Location(2,2)),
	BILLIARD_ROOM_TO_DINING_ROOM_HALLWAY("RBRDH", new Location(2,3)),
	DININGROOM("RD", new Location(2,4)),
	
	// Fourth row
	LIBRARY_TO_CONSERVATORY_HALLWAY("RIRCH", new Location(3,0)),
	INVALID_LOCATION_THREE("IL3", new Location(3,1)),
	BILLIARD_ROOM_TO_BALLROOM_HALLWAY("RBRAH", new Location(3,2)),
	INVALID_LOCATION_FOUR("IL4", new Location(3,3)),
	DINING_ROOM_TO_KITCHEN_HALLWAY("RDRKH", new Location(3,4)),
	
	// Fifth row
	CONSERVATORY("RC", new Location(4,0)),
	CONSERVATORY_TO_BALLROOM_HALLWAY("RCRAH", new Location(4,1)),
	BALLROOM("RA", new Location(4,2)),
	BALLROOM_TO_KITCHEN_HALLWAY("RARKH", new Location(4,3)),
	KITCHEN("RK", new Location(4,4));
	
	String abbreviation;
	Location location;
	
	private LocationEnum(String abbreviation, Location location) {
		this.abbreviation = abbreviation;
		this.location = location;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
	public Location getLocation() {
		return location;
	}
}
