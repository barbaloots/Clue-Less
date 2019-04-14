package clueless.gamelogic;

import java.util.ArrayList;

/**
 * The Player class handles all functionality related to each character in the game.
 */
public class Player {
	private boolean active;
	private int numMoves;
	private String abbreviation;
	private Location location;
	private Notebook notebook;
	private CharacterName characterName;
	private ArrayList<Card> currentHand;

	/**
	 * Constructor.
	 * 
	 * @param active whether this character is attached to a live player
	 * @param abbreviation the player's abbreviation
	 * @param characterName the name of the character this player is associated with
	 * @param location the current location on the board
	 */
	public Player(boolean active, String abbreviation, CharacterName characterName, Location location) {
		this.active = active;
		this.numMoves = 0;
		this.abbreviation = abbreviation;
		this.notebook = new Notebook();
		this.characterName = characterName;
		this.location = location;
		this.currentHand = new ArrayList<>();
	}

	/**
	 * Get whether this player is active.
	 * 
	 * @return whether the player is currently active
	 */
	public boolean isActive() {
		return active;
	}
	
	public int getNumMoves() {
		return numMoves;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public Location getLocation() {
		return location;
	}

	public CharacterName getCharacterName() {
		return characterName;
	}

	public Notebook getNotebook() {
		return notebook;
	}

	public ArrayList<Card> getCurrentHand() {
		return currentHand;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}
	
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	public void setCharacterName(CharacterName characterName) {
		this.characterName = characterName;
	}

	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}

	public void setCurrentHand(ArrayList<Card> currentHand) {
		this.currentHand = currentHand;
	}

	@Override
	public String toString() {
		return "Player [active=" + active + ", abbreviation=" + abbreviation + ", location=" + location.toString()
				+ ", characterName=" + characterName.getCharacterName() + "]";
	}
}
