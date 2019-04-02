package clueless.gamelogic;

import java.util.Random;

/**
 * Generate and store the true game solution.
 * 
 * @author matthewsobocinski
 */
public class GameSolution extends CluelessTriple {
	public GameSolution() {
		super(getRandomWeaponType(), getRandomRoomName(), getRandomCharacterName());
	}
	
	/**
	 * Useful for testing. In the real game, the no-argument constructor will be used
	 * to randomly generate these fields.
	 * 
	 * @param weaponType
	 * @param roomName
	 * @param characterName
	 */
	public GameSolution(WeaponType weaponType, RoomName roomName, CharacterName characterName) {
		super(weaponType, roomName, characterName);
	}

	/**
	 * 
	 * @return
	 */
	private static CharacterName getRandomCharacterName() {
		// Randomly select a WeaponType
		CharacterName[] characterNames = CharacterName.values();
		return characterNames[new Random().nextInt(characterNames.length)];
	}

	/**
	 * 
	 * @return
	 */
	private static WeaponType getRandomWeaponType() {
		// Randomly select a WeaponType
		WeaponType[] weaponTypes = WeaponType.values();
		return weaponTypes[new Random().nextInt(weaponTypes.length)];
	}

	/**
	 * 
	 * @return
	 */
	private static RoomName getRandomRoomName() {
		// Randomly select a RoomName
		RoomName[] roomNames = RoomName.values();
		return roomNames[new Random().nextInt(roomNames.length)];
	}

	/**
	 * Confirm or refute the accusation's validity. 
	 * 
	 * @param accusation <code>Accusation</code> made by a character
	 * @return whether the accusation matches the true game solution
	 */
	public boolean checkAccusation(Accusation accusation) {
		// Invoke the overriden equals() method to simplify comparison
		return this.equals(accusation);
	}
}
