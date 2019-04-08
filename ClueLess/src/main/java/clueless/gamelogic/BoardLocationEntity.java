package clueless.gamelogic;

/**
 * An abstract class that allows us to store objects in the 2D-array.
 * The advantage here is that we can work with Hallway, Room, and 
 * InvalidLocation objects, but maintain all of them in the same 2D-array.
 * 
 * @author matthewsobocinski
 */
public abstract class BoardLocationEntity {
	private String name;
	private String abbreviation;
	private Location location;
	
	/**
	 * 
	 * @param name the long-form name of this board location
	 * @param abbreviation the abbreviated version of this location's name
	 * @param location the <code>Location</code> object containing the (x,y) coordinates of the location
	 */
	public BoardLocationEntity(String name, String abbreviation, Location location) {
		this.name = name;
		this.abbreviation = abbreviation;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public Location getLocation() {
		return location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "name=" + name + ",location=" + location.toString();
	}
}	
