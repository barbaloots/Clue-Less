package clueless.gamelogic;

/**
 * This class represents the movement from players and weapons
 * each turn of the game. Every turn, there is player movement
 * caused by the turn player for their character as well as player
 * movement on the other characters caused by suggestions. Weapons
 * are also moved by means of suggestion, and this class enables
 * that they move properly.
 */
public class Movement {
	private int spacesToMove;
	private String direction;
    private Location currentLocation;
    private Location destinationLocation;
    
    public Movement(int spacesToMove, String direction, Location currentLocation, Location destinationLocation) {
    	this.spacesToMove = spacesToMove;
    	this.direction = direction;
    	this.currentLocation = currentLocation;
    	this.destinationLocation = destinationLocation;
    }

	public int getSpacesToMove() {
		return spacesToMove;
	}

	public String getDirection() {
		return direction;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public Location getDestinationLocation() {
		return destinationLocation;
	}

	public void setSpacesToMove(int spacesToMove) {
		this.spacesToMove = spacesToMove;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public void setDestinationLocation(Location destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
}
