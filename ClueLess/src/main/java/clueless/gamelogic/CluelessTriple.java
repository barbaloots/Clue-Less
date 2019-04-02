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
	
	public CluelessTriple(WeaponType weaponType, RoomName roomName, CharacterName characterName) {
		this.weaponType = weaponType;
		this.roomName = roomName;
		this.characterName = characterName;
	}

	public RoomName getRoomName() {
		return roomName;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public CharacterName getCharacterName() {
		return characterName;
	}

	public void setRoomName(RoomName roomName) {
		this.roomName = roomName;
	}

	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	public void setCharacterName(CharacterName characterName) {
		this.characterName = characterName;
	}

	@Override
	public String toString() {
		return "CluelessTriple [roomName=" + roomName + ", weaponType=" + weaponType + ", characterName="
				+ characterName + "]";
	}

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
