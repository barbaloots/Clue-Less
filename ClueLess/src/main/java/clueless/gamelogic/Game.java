package clueless.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;

import clueless.gamelogic.locationenums.LocationEnum;

/**
 * Maintain informaton related to the current state of the game.
 * Check whether moves are valid, maintain a 2D-array for the board, maintain
 * lists for game entities, etc.
 * 
 * @author matthewsobocinski
 */
public class Game {
	private boolean active;
	private BoardLocationEntity[][] board;
	private ArrayList<RoomCard> roomCards;
	private ArrayList<CharacterCard> characterCards;
	private ArrayList<WeaponCard> weaponCards;
	private HashMap<CharacterName, Player> players;
	// Board height
	private static final int BOARD_HEIGHT = 5;
	// Board width
	private static final int BOARD_WIDTH = 5;
	// Used to indicate that a given BoardLocationEntity should be of type InvalidLocation
	private static final String INVALID_STR = "INVALID";
	// Used to indicate that a given BoardLocationEntity should be of type Hallway
	private static final String HALLWAY_STR = "HALLWAY";

	/**
	 * Constructor. Create the fields necessary to be encapsulated by a Game object.
	 */
	public Game() {
		this.active = true;
		this.board = new BoardLocationEntity[5][5];
		this.roomCards = new ArrayList<>();
		this.characterCards = new ArrayList<>();
		this.weaponCards = new ArrayList<>();
		this.players = new HashMap<>();
		this.generateCards();
		this.populateBoard();
		this.createPlayers();
		this.printBoard();
	}

	/**
	 * Initialize a number of game objects, including cards, hallways, etc.
	 */
	public void generateCards() {
		// Generate character cards
		for (CharacterName character : CharacterName.values()) {
			CharacterCard card = new CharacterCard(character.name());
			this.characterCards.add(card);
		}

		// Generate room cards
		for (RoomName room : RoomName.values()) {
			RoomCard card = new RoomCard(room.name());
			this.roomCards.add(card);
		}

		// Generate weapon cards
		for (WeaponType weapon : WeaponType.values()) {
			WeaponCard card = new WeaponCard(weapon.name());
			this.weaponCards.add(card);
		}
	}

	/**
	 * Populate the 2D BoardEntityLocation array by iterating over the LocationEnum's values.
	 */
	private void populateBoard() {
		for(LocationEnum boardLoc : LocationEnum.values()) {
			Location location = boardLoc.getLocation();
			String name = boardLoc.name();
			String abbreviation = boardLoc.getAbbreviation();

			if(name.startsWith(INVALID_STR)) {
				// Create a new InvalidLocation object and place it on the board
				InvalidLocation invalidLocation = new InvalidLocation(name, abbreviation, location);
				board[location.getX()][location.getY()] = invalidLocation;
			} else if(name.endsWith(HALLWAY_STR)) {
				// Create a new Hallway object and place it on the board (last field, player, is null to start)
				Hallway hallway = new Hallway(false, name, abbreviation, location, null);
				board[location.getX()][location.getY()] = hallway;
			} else {
				// Create a new Room object and place it on the board
				Room room = new Room(name, abbreviation, location);
				board[location.getX()][location.getY()] = room;
			}
		}
	}
	
	/**
	 * Create the Players, place them at their starting locations, and add
	 * them to the HashMap of Players.
	 */
	private void createPlayers() {
		for(CharacterEnum character : CharacterEnum.values()) {
			// Extract the fields from the CharacterEnum instance
			CharacterName name = character.getName();
			String abbreviation = character.getAbbreviation();
			Location startingLocation = character.getStartLocation();
			// Create a new Player object
			Player player = new Player(abbreviation, name, startingLocation);
			// Place the player at their starting location on the board
			BoardLocationEntity boardLocationEntity = board[startingLocation.getX()][startingLocation.getY()];
			// Not the cleanest solution, but workable for our purposes
			if(boardLocationEntity instanceof Room) {
				// Add this Player to the Room's list of current Players
				((Room) boardLocationEntity).getCurrentPlayers().add(player);
			} else if(boardLocationEntity instanceof Hallway) {
				// Set this Player as the Player occupying the Hallway
				((Hallway) boardLocationEntity).setPlayer(player);
				// Mark the Hallway as occupied
				((Hallway) boardLocationEntity).setOccupied(true);
			}
			// Add the Player object to the HashMap of Players, using their CharacterName as a key
			this.players.put(name, player);
			System.out.println("Created player: " + player.toString());
		}
	}

	/**
	 * Print the board, as stored in the 5x5 2D array.
	 * Although this method looks somewhat complex, the logic is necessary for
	 * ensuring the board is legible when printed to the console for debugging.
	 */
	private void printBoard() {
		System.out.println("\nCURRENT CLUELESS BOARD:");
		System.out.println("Rooms are two letters in length and start with 'R'");
		System.out.println("Hallways are of the format Room 1, Room 2, 'H'");
		System.out.println("Invalid location start with 'IL'");
		System.out.println("---------------------------------------------------");
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			for(int col = 0; col < BOARD_WIDTH; col++) {
				BoardLocationEntity entity = board[row][col];
				if(col == 0) {
					System.out.print("| ");
				}
				// Print the abbreviation for the Room, Hallway, or InvalidLocation
				System.out.print(String.format("%10s", entity.getAbbreviation() + " | "));
			}
			// Same "row", new line (so we can print the occupants of a cell)
			System.out.println();
			// Print the occupants of a given cell, if there are any
			for(int col = 0; col < BOARD_WIDTH; col++) {
				BoardLocationEntity entity = board[row][col];
				if(col == 0) {
					System.out.print("| ");
				}
				// If this location is a Room and it has occupants, print all occupants
				if(entity instanceof Room && !((Room) entity).getCurrentPlayers().isEmpty()) {
					String allOccupants = "";
					ArrayList<Player> players = ((Room) entity).getCurrentPlayers();
					// Form a string containing all the comma-separated abbreviations of the Players in this room
					for(int i = 0; i < players.size(); i++) {
						if(i != players.size()-1) {
							allOccupants += players.get(i).getAbbreviation() + ",";
						} else {
							allOccupants += players.get(i).getAbbreviation();
						}
					}
					// Print the Room's occupants
					System.out.print(String.format("%10s", allOccupants + " | "));
				} else if(entity instanceof Hallway) {
					// If this location is a Hallway and it's occupied, print the occupant's abbreviation
					if(((Hallway) entity).isOccupied()) {
						System.out.print(String.format("%10s", ((Hallway) entity).getPlayer().getAbbreviation() + " | "));
					} else {
						System.out.print(String.format("%10s", " | "));
					}
				} else {
					System.out.print(String.format("%10s", " | "));
				}
			}
			System.out.println("\n---------------------------------------------------");
		}
	}

	/**
	 * Determine if the given CharacterName can make the given Movement.
	 * 
	 * @param character the name of the player attempting to move 
	 * @param move the Movement to validate
	 * @return whether the move is valid
	 */
	public boolean validateMove(CharacterName character, Movement move) {
		return true;
	}

	public boolean isActive() {
		return active;
	}

	public BoardLocationEntity[][] getBoard() {
		return board;
	}

	public HashMap<CharacterName, Player> getPlayers() {
		return players;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setBoard(BoardLocationEntity[][] board) {
		this.board = board;
	}

	public void setPlayers(HashMap<CharacterName, Player> players) {
		this.players = players;
	}
}
