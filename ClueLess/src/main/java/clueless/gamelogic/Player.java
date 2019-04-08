package clueless.gamelogic;

import java.util.ArrayList;

/**
 * The Player class handles all functionality related to each character in the game.
 */
public class Player {
	private boolean active;
	private String abbreviation;
	private Location location;
	private Notebook notebook;
	private CharacterName characterName;
	private ArrayList<Card> currentHand;

	/**
	 * Constructor.
	 * 
	 * @param character the name of the new Player to create.
	 */
	public Player(String abbreviation, CharacterName characterName, Location location) {
		this.active = false;
		this.abbreviation = abbreviation;
		this.notebook = new Notebook();
		this.characterName = characterName;
		this.location = location;
		this.currentHand = new ArrayList<>();
	}

	/**
	 * Accept a move, which will then be validated with the game logic subsystem.
	 * If the move was not valid, return false. Otherwise, return true.
	 * 
	 * @param move
	 * @return whether the move was successful
	 */
	private boolean move(Movement move) {
		return false;
	}

	/**
	 * If a player's accusation is false, eliminate them from the game.
	 * 
	 */
	private void eliminate() {
		this.active = false;
	}

	/**
	 * Make an accusation.
	 * 
	 * @param accusation
	 */
	private void makeAccusation(Accusation accusation) {
		// Communicate to the game logic subsystem that this player wishes to make an accusation
	}

	/**
	 * Make a suggestion.
	 * 
	 * @param suggestion
	 */
	private void makeSuggestion(Suggestion suggestion) {
		// Communicate to the game logic subsystem that this player wishes to make a suggestion
	}

	/**
	 * Get whether this player is active.
	 * 
	 * @return whether the player is currently active
	 */
	public boolean isActive() {
		return active;
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
