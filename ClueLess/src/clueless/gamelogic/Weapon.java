package clueless.gamelogic;

/**
 * Encapsulate a weapon's type and location.
 *
 * @author drobi
 * @author matthewsobocinski
 */
public class Weapon {
	private WeaponType weaponType;
	private Location location;

	public Weapon(WeaponType weaponType, Location location) {
		this.weaponType = weaponType;
		this.location = location;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public Location getLocation() {
		return location;
	}

	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
