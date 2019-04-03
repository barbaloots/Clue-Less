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
