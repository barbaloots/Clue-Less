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
	
	/**
	 * If a player's accusation is false, eliminate them from the game
	 * 
	 * @param player
	 * 		the player to be eliminated
	 */
	private void eliminate(Player player) {
		
	}
	
	/**
	 * If a player's accusation is true, end the game and declare them winner
	 * 
	 * @param player
	 * 		the winning player
	 */
	private void endGame(Player player) {
		
	}
}
