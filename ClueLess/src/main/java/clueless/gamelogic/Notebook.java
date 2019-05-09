package clueless.gamelogic;

import java.util.HashMap;

/**
 * Create a notebook for each player, allowing them to keep track of
 * their knowledge throughout the game.
 * 
 * @author drobi
 * @author matthewsobocinski
 */
public class Notebook {
	private HashMap<CharacterName, Boolean> characterSection;
	private HashMap<RoomName, Boolean> roomSection;
	private HashMap<WeaponType, Boolean> weaponSection;
	
	/**
	 * Create a new notebook, maintaining a HashMap for each section
	 * of the typical Clue notebook.
	 */
	public Notebook() {
		this.characterSection = new HashMap<>();
		this.roomSection = new HashMap<>();
		this.weaponSection = new HashMap<>();
		populateNotebookSections();
	}

	/**
	 * Set every slot initially to false (i.e. the notebook will be empty).
	 */
	private void populateNotebookSections() {
		characterSection.put(CharacterName.GREEN, false);
		characterSection.put(CharacterName.MUSTARD, false);
		characterSection.put(CharacterName.PEACOCK, false);
		characterSection.put(CharacterName.PLUM, false);
		characterSection.put(CharacterName.SCARLET, false);
		characterSection.put(CharacterName.WHITE, false);
		
		roomSection.put(RoomName.BALLROOM, false);
		roomSection.put(RoomName.BILLIARDROOM, false);
		roomSection.put(RoomName.CONSERVATORY, false);
		roomSection.put(RoomName.DININGROOM, false);
		roomSection.put(RoomName.HALL, false);
		roomSection.put(RoomName.KITCHEN, false);
		roomSection.put(RoomName.LIBRARY, false);
		roomSection.put(RoomName.LOUNGE, false);
		roomSection.put(RoomName.STUDY, false);
		
		weaponSection.put(WeaponType.CANDLESTICK, false);
		weaponSection.put(WeaponType.KNIFE, false);
		weaponSection.put(WeaponType.LEADPIPE, false);
		weaponSection.put(WeaponType.REVOLVER, false);
		weaponSection.put(WeaponType.ROPE, false);
		weaponSection.put(WeaponType.WRENCH, false);
	}

	public HashMap<CharacterName, Boolean> getCharacterSection() {
		return characterSection;
	}

	public HashMap<RoomName, Boolean> getRoomSection() {
		return roomSection;
	}

	public HashMap<WeaponType, Boolean> getWeaponSection() {
		return weaponSection;
	}

	public void setCharacterSection(HashMap<CharacterName, Boolean> characterSection) {
		this.characterSection = characterSection;
	}

	public void setRoomSection(HashMap<RoomName, Boolean> roomSection) {
		this.roomSection = roomSection;
	}

	public void setWeaponSection(HashMap<WeaponType, Boolean> weaponSection) {
		this.weaponSection = weaponSection;
	}
}
