package main.java.clueless.gamelogic;

/**
 * Encapsulate a weapon's type and location.
 *
 * @author drobi
 * @author matthewsobocinski
 */
public class Weapon {
	private WeaponType weaponType;
	private Location location;

	/**
	 * Constructor
	 * 
	 * @param weaponType
	 * 		the weapon being set
	 * @param location
	 * 		the location of the weapon being set
	 */
	public Weapon(WeaponType weaponType, Location location) {
		this.weaponType = weaponType;
		this.location = location;
	}

	/**
	 * Get the weaponType
	 * 
	 * @return the weaponType
	 */
	public WeaponType getWeaponType() {
		return weaponType;
	}

	/**
	 * Get the location
	 * 
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Set the weaponType
	 * 
	 * @param weaponType
	 * 		the weaponType to set
	 */
	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	/**
	 * Set the location
	 * 
	 * @param location
	 * 		the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
}
