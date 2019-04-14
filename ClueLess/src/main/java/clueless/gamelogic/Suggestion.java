package clueless.gamelogic;

/**
 * Encapsulate the field of a suggestion.
 * 
 * @author matthewsobocinski
 */
public class Suggestion extends Move {
	private WeaponType weaponType;
	private RoomName roomName;
	private CharacterName characterName;
	private static final MoveType moveType = Move.MoveType.SUGGESTION;
	
	/**
	 * Constructor.
	 * 
	 * @param weaponType
	 * 		type of weapon being accused
	 * @param roomName
	 * 		room in the mansion being accused
	 * @param characterName
	 * 		character being accused
	 */
	public Suggestion(WeaponType weaponType, RoomName roomName, CharacterName characterName) {
		super(moveType);
		this.weaponType = weaponType;
		this.roomName = roomName;
		this.characterName = characterName;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public RoomName getRoomName() {
		return roomName;
	}

	public CharacterName getCharacterName() {
		return characterName;
	}

	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	public void setRoomName(RoomName roomName) {
		this.roomName = roomName;
	}

	public void setCharacterName(CharacterName characterName) {
		this.characterName = characterName;
	}

	/**
	 * Create the <code>String</code> representation of a <code>Suggestion</code>.
	 * <p>AS_CHARACTER_ROOM_WEAPON</p>
	 */
	@Override
	public String toString() {
		return Move.SUGGEST_STR + Move.MOVE_SEP + characterName + Move.MOVE_SEP + 
				roomName + Move.MOVE_SEP + weaponType;
	}
}
