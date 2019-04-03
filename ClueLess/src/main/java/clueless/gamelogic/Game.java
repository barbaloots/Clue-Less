package clueless.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Maintain informaton related to the current state of the game.
 * Check whether moves are valid, maintain a 2D-array for the board, etc.
 * 
 * @author matthewsobocinski
 */
public class Game {
	private boolean active;
	private String[][] board;
	private ArrayList<RoomCard> roomCards;
	private ArrayList<CharacterCard> characterCards;
	private ArrayList<WeaponCard> weaponCards;
	private HashMap<CharacterName, Player> players;

	/**
	 * Constructor. Create the fields necessary to be encapsulated by a Game object.
	 */
	public Game() {
		this.active = true;
		this.board = new String[3][3];
		this.roomCards = new ArrayList<>();
		this.characterCards = new ArrayList<>();
		this.weaponCards = new ArrayList<>();
		this.players = new HashMap<>();
		this.generateAllCards();
	}
	
	/**
	 * Generate and store all cards for the game.
	 */
	public void generateAllCards() {
		System.out.println("6 instances of the character card object:");
		for (CharacterName character : CharacterName.values()) {
			CharacterCard card = new CharacterCard(character.name());
			this.characterCards.add(card);
			System.out.println(card.toString());
		}

		System.out.println("\n9 instances of the room card object:");
		for (RoomName room : RoomName.values()) {
			RoomCard card = new RoomCard(room.name());
			this.roomCards.add(card);
			System.out.println(card.toString());
		}

		System.out.println("\n6 instances of the room card object:");
		for (WeaponType weapon2 : WeaponType.values()) {
			WeaponCard card = new WeaponCard(weapon2.name());
			this.weaponCards.add(card);
			System.out.println(card.toString());
		}
		System.out.println("\nIdentify location of item on game board using (x,y) coordinates:");
		Location loc = new Location(5, 8);
		System.out.println("\tItem located at (" + loc.getX() + "," + loc.getY() + ")");
	}
	

	/**
	 * Determine if the given CharacterName can make the given Movement.
	 * 
	 * @param character the name of the player attempting to move 
	 * @param move the Movement to validate
	 * @return whether the move is valid
	 */
	public boolean validateMove(CharacterName character, Movement move) {
		System.out.println("Game's validateMove() method has been called");
		return true;
	}

	public boolean isActive() {
		return active;
	}

	public String[][] getBoard() {
		return board;
	}

	public HashMap<CharacterName, Player> getPlayers() {
		return players;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setBoard(String[][] board) {
		this.board = board;
	}

	public void setPlayers(HashMap<CharacterName, Player> players) {
		this.players = players;
	}
}
