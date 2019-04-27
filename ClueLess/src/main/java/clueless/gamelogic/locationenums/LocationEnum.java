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
	STUDY("RS", new Location(0,0), true),
	STUDYTOHALLHALLWAY("RSRHH", new Location(0,1), false),
	HALL("RH", new Location(0,2), false),
	HALLTOLOUNGEHALLWAY("RHRLH", new Location(0,3), false),
	LOUNGE("RL", new Location(0,4), true),
	
	// Second row
	STUDYTOLIBRARYHALLWAY("RSRIH", new Location(1,0), false),
	INVALIDLOCATIONONE("IL1", new Location(1,1), false),
	HALLTOLIBRARYHALLWAY("RHRBH", new Location(1,2), false),
	INVALIDLOCATIONTWO("IL2", new Location(1,3), false),
	LOUNGETODININGROOMHALLWAY("RLRDH", new Location(1,4), false),
	
	// Third row
	LIBRARY("RI", new Location(2,0), false),
	LIBRARYTOBILLIARDROOMHALLWAY("RIRBH", new Location(2,1), false),
	BILLIARDROOM("RB", new Location(2,2), false),
	BILLIARDROOMTODININGROOMHALLWAY("RBRDH", new Location(2,3), false),
	DININGROOM("RD", new Location(2,4), false),
	
	// Fourth row
	LIBRARYTOCONSERVATORYHALLWAY("RIRCH", new Location(3,0), false),
	INVALIDLOCATIONTHREE("IL3", new Location(3,1), false),
	BILLIARDROOMTOBALLROOMHALLWAY("RBRAH", new Location(3,2), false),
	INVALIDLOCATIONFOUR("IL4", new Location(3,3), false),
	DININGROOMTOKITCHENHALLWAY("RDRKH", new Location(3,4), false),
	
	// Fifth row
	CONSERVATORY("RC", new Location(4,0), true),
	CONSERVATORYTOBALLROOMHALLWAY("RCRAH", new Location(4,1), false),
	BALLROOM("RA", new Location(4,2), false),
	BALLROOMTOKITCHENHALLWAY("RARKH", new Location(4,3), false),
	KITCHEN("RK", new Location(4,4), true);
	
	boolean hasSecretPassage;
	String abbreviation;
	Location location;
	
	private LocationEnum(String abbreviation, Location location, boolean hasSecretPassage) {
		this.abbreviation = abbreviation;
		this.location = location;
		this.hasSecretPassage = hasSecretPassage;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public boolean hasSecretPassage() {
		return hasSecretPassage;
	}
}
