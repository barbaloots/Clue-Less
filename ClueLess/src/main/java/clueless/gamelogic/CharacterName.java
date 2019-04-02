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

    private CharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }
}
