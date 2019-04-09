package clueless.gamelogic;

/**
 * Three fields that will be reused several times.
 * 
 * @author matthewsobocinski
 */
public abstract class CluelessTriple {
	private WeaponType weaponType;
	private RoomName roomName;
	private CharacterName characterName;
	
	/**
	 * Constructor that sets the reused attributes
	 * 
	 * @param weaponType
	 * 		the type of weapon
	 * @param roomName
	 * 		the room in the mansion
	 * @param characterName
	 * 		the name of the character
	 */
	public CluelessTriple(WeaponType weaponType, RoomName roomName, CharacterName characterName) {
		this.weaponType = weaponType;
		this.roomName = roomName;
		this.characterName = characterName;
	}

	/**
	 * Get the roomName
	 * 
	 * @return the roomName
	 */
	public RoomName getRoomName() {
		return roomName;
	}

	/**
	 * Get the weaponType
	 * 
	 * @return the weaponType
	 */
	public WeaponType getWeaponType() {
		return weaponType;
	}

	/**
	 * Get the characterName
	 * 
	 * @return the characterName
	 */
	public CharacterName getCharacterName() {
		return characterName;
	}

	/**
	 * Set the roomName
	 * 
	 * @param roomName
	 * 		the roomName to set
	 */
	public void setRoomName(RoomName roomName) {
		this.roomName = roomName;
	}

	/**
	 * Set the weaponType
	 * 
	 * @param weaponType
	 * 		the weaponType to set
	 */
	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	/**
	 * Set the characterName
	 * 
	 * @param characterName
	 * 		the characterName
	 */
	public void setCharacterName(CharacterName characterName) {
		this.characterName = characterName;
	}

	/**
	 * Convert the related attributes to a String for output
	 * 
	 * @return the String representation of the attributes
	 */
	@Override
	public String toString() {
		return "roomName=" + roomName + ",weaponType=" + weaponType + ",characterName="
				+ characterName;
	}

	/**
	 * Convert result into a hash code
	 * 
	 * @return the result
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((characterName == null) ? 0 : characterName.hashCode());
		result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
		result = prime * result + ((weaponType == null) ? 0 : weaponType.hashCode());
		return result;
	}

	@Override
	/**
	 * Note that this is a modified version of the Eclipse-generated equals(Object obj)
	 * method. Modifications include using .equals() to compare values from enums and
	 * removing the class name comparison.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		CluelessTriple other = (CluelessTriple) obj;
		if (!characterName.equals(other.characterName))
			return false;
		if (!roomName.equals(other.roomName))
			return false;
		if (!weaponType.equals(other.weaponType))
			return false;
		return true;
	}
}
