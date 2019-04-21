package clueless.gamelogic;

/**
 * Make iterating over rooms easier.
 * 
 * @author matthewsobocinski
 */
public enum RoomName {
	STUDY("STUDY"),
	HALL("HALL"),
	LOUNGE("LOUNGE"),
	LIBRARY("LIBRARY"),
	BILLIARDROOM("BILLIARDROOM"),
	DININGROOM("DININGROOM"),
	CONSERVATORY("CONSERVATORY"),
	BALLROOM("BALLROOM"),
	KITCHEN("KITCHEN");
	
	String roomName;
	Location location;
	
	/**
	 * Constructor
	 * 
	 * @param roomName
	 * 		the roomName to set
	 */
    private RoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Get the roomName
     * 
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }
}
