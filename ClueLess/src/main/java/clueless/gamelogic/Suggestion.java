package clueless.gamelogic;

/**
 * The suggestion class handles all functionality related to making a suggestion.
 * 
 * TODO: Add other fields as necessary.
 * 
 * @author matthewsobocinski
 */
public class Suggestion extends CluelessTriple {
	
	/**
	 * Constructor
	 * 
	 * @param weaponType
	 * 		the weapon being suggested
	 * @param roomName
	 * 		the room being suggested
	 * @param characterName
	 * 		the character being suggested
	 */
	public Suggestion(WeaponType weaponType, RoomName roomName, CharacterName characterName) {
		super(weaponType, roomName, characterName);
	}
}
