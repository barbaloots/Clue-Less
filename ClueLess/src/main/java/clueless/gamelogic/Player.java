package clueless.gamelogic;

/**
 * The player class handles all functionality related to a player.
 * 
 */
public class Player {
	
	private CharacterName character;
	
	/**
	 * Constructor - adds new player to the game
	 */
	public Player(CharacterName character) {
		Notebook notebook = new Notebook();
		this.character = character;
	}
	
	/**
	 * Get the character
	 * 
	 * @return the character
	 */
	public CharacterName getCharacter() {
		return character;
	}
}
