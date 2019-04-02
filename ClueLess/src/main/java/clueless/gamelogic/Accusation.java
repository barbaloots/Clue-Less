package clueless.gamelogic;

/**
 * Encapsulate the fields of an accusation. 
 *
 * @author matthewsobocinski
 */
public class Accusation extends CluelessTriple {
	public Accusation(WeaponType weaponType, RoomName roomName, CharacterName characterName) {
		super(weaponType, roomName, characterName);
	}
}
