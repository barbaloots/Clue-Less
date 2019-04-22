package main.java.clueless.gamelogic;

/**
 * Define the properties of each character, including where 
 * each player/character starts each game, their abbreviation, and
 * their full name (type CharacterName).
 * 
 * @author erinsmedley
 */
public enum CharacterEnum {
	PLUM("PP", CharacterName.PLUM, new Location(1,0)),
	PEACOCK("MP", CharacterName.PEACOCK, new Location(3,0)),
	GREEN("MG", CharacterName.GREEN, new Location(4,1)),
	WHITE("MW", CharacterName.WHITE, new Location(4,3)),
	MUSTARD("CM", CharacterName.MUSTARD, new Location(1,4)),
	SCARLET("MS", CharacterName.SCARLET, new Location(0,3));
	
	String abbreviation;
	CharacterName charName;
	Location startLocation;
	
	/**
	 * Enum constructor that sets the character's abbreviation, name, and starting location.
	 * 
	 * @param abbreviation
	 * 		the character's abbreviation
	 * @param startLocation
	 * 		the character start location
	 * @return a new CharacterEnum instance
	 */
	CharacterEnum(String abbreviation, CharacterName charName, Location startLocation) {
		this.abbreviation = abbreviation;
		this.charName = charName;
		this.startLocation = startLocation;
	}
	
	/**
	 * Get the character's abbreviation.
	 * 
	 * @return the abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}
	
	/**
	 * Get the character's name.
	 * 
	 * @return
	 */
	public CharacterName getName() {
		return charName;
	}
	
	/**
	 * Get the character's start location.
	 * 
	 * @return the location
	 */
	public Location getStartLocation() {
		return startLocation;
	}
}
