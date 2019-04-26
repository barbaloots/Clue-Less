package clueless.gamelogic;

/**
 * Store all weapon types in an enum (just makes it easier
 * to iterate over them, as opposed to listing them as static
 * variables).
 * 
 * @author matthewsobocinski
 */
public enum WeaponType {
	ROPE("ROPE"),
	LEADPIPE("LEADPIPE"),
	KNIFE("KNIFE"),
	WRENCH("WRENCH"),
	CANDLESTICK("CANDLESTICK"),
	REVOLVER("REVOLVER");
	
	private String weapon;

	/**
	 * Constructor
	 * 
	 * @param weapon
	 * 		the weapon to set
	 */
    private WeaponType(String weapon) {
        this.weapon = weapon;
    }

    /**
     * Get the weapon
     * 
     * @return the weapon
     */
    public String getWeapon() {
        return weapon;
    }
}
