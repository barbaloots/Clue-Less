package clueless.gamelogic;

/**
 * Store the information related to each of the hallways.
 * 
 * @author matthewsobocinski
 */
public class Hallway extends BoardLocationEntity {
	private boolean occupied;
	private Player player = null;
	
	public Hallway(boolean occupied, String name, String abbreviation, Location location, Player player) {
		super(name, abbreviation, location);
		this.occupied = occupied;
		this.player = player;
	}
	
	public boolean isOccupied() {
		return occupied;
	}

	public Player getPlayer() {
		return player;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
