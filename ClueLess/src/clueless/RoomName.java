package clueless;

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
	BILLIARD_ROOM("BILLIARD_ROOM"),
	DINING_ROOM("DINING_ROOM"),
	CONSERVATORY("CONSERVATORY"),
	BALLROOM("BALLROOM"),
	KITCHEN("KITCHEN");
	
	String roomName;

    private RoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }
}
