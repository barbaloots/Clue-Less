package clueless;

/**
 * Store all weapon types in an enum (just makes it easier
 * to iterate over them, as opposed to listing them as static
 * variables).
 * 
 * @author matthewsobocinski
 */
public enum WeaponType {
	ROPE("ROPE"),
	LEAD_PIPE("LEAD_PIPE"),
	KNIFE("KNIFE"),
	WRENCH("WRENCH"),
	CANDLESTICK("CANDLESTICK"),
	REVOLVER("REVOLVER");
	
	private String weapon;

    private WeaponType(String weapon) {
        this.weapon = weapon;
    }

    public String getWeapon() {
        return weapon;
    }
}
