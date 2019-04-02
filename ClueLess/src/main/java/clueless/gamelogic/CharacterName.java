package clueless.gamelogic;

/**
 * Make iterating over characters easier.
 * 
 * @author matthewsobocinski
 */
public enum CharacterName {
	PLUM("PLUM"),
	PEACOCK("PEACOCK"),
	GREEN("GREEN"),
	WHITE("WHITE"),
	SCARLET("SCARLET"),
	MUSTARD("MUSTARD");
	
	private String characterName;

	/**
	 * Constructor that sets the characterName
	 * 
	 * @param characterName
	 * 		the name of the character
	 */
    private CharacterName(String characterName) {
        this.characterName = characterName;
    }

    /**
     * Get the characterName
     * 
     * @return the characterName
     */
    public String getCharacterName() {
        return characterName;
    }
}
