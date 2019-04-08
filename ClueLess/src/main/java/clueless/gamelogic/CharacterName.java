package clueless.gamelogic;

/**
 * Make iterating over characters easier.
 * 
 * @author matthewsobocinski
 */
public enum CharacterName {
	PLUM("Professor Plum"),
	PEACOCK("Mrs. Peacock"),
	GREEN("Mr. Green"),
	WHITE("Mrs. White"),
	SCARLET("Miss Scarlet"),
	MUSTARD("Colonel Mustard");
	
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
