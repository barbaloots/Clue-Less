package clueless.gamelogic;

/**
 * Encapsulate the fields of an accusation. 
 *
 * @author matthewsobocinski
 */
public class Accusation extends CluelessTriple {

	/**
	 * Constructor
	 * 
	 * @param weaponType
	 * 		type of weapon being accused
	 * @param roomName
	 * 		room in the mansion being accused
	 * @param characterName
	 * 		character being accused
	 */
	public Accusation(WeaponType weaponType, RoomName roomName, CharacterName characterName) {
		super(weaponType, roomName, characterName);
	}
}
