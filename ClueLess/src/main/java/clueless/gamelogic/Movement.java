package clueless.gamelogic;

/**
 * Encapsulate the start and end locations that make up a Movement.
 * 
 * @author matthewsobocinski
 */
public class Movement {
    private Location startLoc;
    private Location endLoc;
    
    public Movement(Location startLoc, Location endLoc) {
    	this.startLoc = startLoc;
    	this.endLoc = endLoc;
    }

	public Location getStartLoc() {
		return startLoc;
	}

	public Location getEndLoc() {
		return endLoc;
	}

	public void setStartLoc(Location startLoc) {
		this.startLoc = startLoc;
	}

	public void setEndLoc(Location endLoc) {
		this.endLoc = endLoc;
	}
	
	@Override
	public String toString() {
		return "SL" + startLoc.getX() + startLoc.getY() + "EL" + endLoc.getX() + endLoc.getY();
	}
}
