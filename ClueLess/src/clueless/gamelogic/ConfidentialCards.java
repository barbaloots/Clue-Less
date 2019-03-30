package clueless.gamelogic;

import java.util.Random;

/**
 * Generate and store the true game solution.
 * 
 * @author matthewsobocinski
 */
public class ConfidentialCards {
	private RoomName room;
	private WeaponType weapon;
	private CharacterName character;

	public ConfidentialCards() {
		this.generateTrueGameSolution();
	}
	
	/**
	 * Randomly select a room, a weapon, and character to be used
	 * as the true game solution.
	 */
	public void generateTrueGameSolution() {
		Random random = new Random();
		
		// Randomly select a room
		RoomName[] roomName = RoomName.values();
		this.room = roomName[random.nextInt(roomName.length)];
		
		// Randomly select a weapon
		WeaponType[] weaponType = WeaponType.values();
		this.weapon = weaponType[random.nextInt(weaponType.length)];
		
		// Randomly select a character
		CharacterName[] characterName = CharacterName.values();
		this.character = characterName[random.nextInt(characterName.length)];
	}

	public RoomName getRoom() {
		return room;
	}

	public WeaponType getWeapon() {
		return weapon;
	}

	public CharacterName getCharacter() {
		return character;
	}

	@Override
	public String toString() {
		return "ConfidentialCards [room=" + room + ", weapon=" + weapon + ", character=" + character + "]";
	}
}
