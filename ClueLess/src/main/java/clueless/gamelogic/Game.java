package clueless.gamelogic;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import clueless.gamelogic.locationenums.LocationEnum;
import clueless.networking.ConnectionHandler;

/**
 * Maintain informaton related to the current state of the game.
 * Check whether moves are valid, maintain a 2D-array for the board, maintain
 * lists for game entities, etc.
 * 
 * Note from game description doc: Each time the game state changes (a person is moved,
 * a suggestion is made, a player disproves a suggestion, or a player is unable to 
 * disprove a suggestion) all players should be notified.
 * 
 * @author matthewsobocinski
 */
public class Game {
	private static final Logger logger = Logger.getLogger(Game.class);
	private boolean active;
	private int numPlayers;
	private GameSolution gameSolution;
	private BoardLocationEntity[][] board;
	private ArrayList<RoomCard> roomCards;
	private ArrayList<CharacterCard> characterCards;
	private ArrayList<WeaponCard> weaponCards;
	private ArrayList<ConnectionHandler> connections;
	private ArrayList<Player> players;
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
	public Game(int numPlayers) {
		this.active = true;
		this.numPlayers = numPlayers;
		this.board = new BoardLocationEntity[5][5];
		this.roomCards = new ArrayList<>();
		this.characterCards = new ArrayList<>();
		this.weaponCards = new ArrayList<>();
		this.connections = new ArrayList<>();
		this.players = new ArrayList<>();
		this.gameSolution = new GameSolution();
		this.initLogger();
		this.generateCards();
		this.createPlayers();
		this.dealCards();
		this.populateBoard();
		this.printBoard();
	}

	/**
	 * Configure log4j.
	 */
	private void initLogger() {
		DOMConfigurator.configure("log4jserver.xml");
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
	 * Shuffle and deal out the remaining cards (cards that are not part of the true game solution).
	 */
	private void dealCards() {
		logger.debug("The true game solution is: " + gameSolution.toString());
		ArrayList<Card> remainingCards = new ArrayList<>();

		// Collect cards that aren't part of the game's solution
		for(RoomCard roomCard : roomCards) {
			// If this card isn't part of the game's solution, add it to the pool of remaining cards
			if(!roomCard.getCardName().equals(gameSolution.getRoomName().toString())) {
				remainingCards.add(roomCard);
			}
		}

		for(WeaponCard weaponCard : weaponCards) {
			// If this card isn't part of the game's solution, add it to the pool of remaining cards
			if(!weaponCard.getCardName().equals(gameSolution.getWeaponType().toString())) {
				remainingCards.add(weaponCard);
			}
		}

		for(CharacterCard characterCard : characterCards) {
			// If this card isn't part of the game's solution, add it to the pool of remaining cards
			if(!characterCard.getCardName().equals(gameSolution.getCharacterName().toString())) {
				remainingCards.add(characterCard);
			}
		}

		// Shuffle the remaining cards
		Collections.shuffle(remainingCards);

		// Deal the cards to the players
		int numPlayers = players.size();
		for(int i = 0; i < remainingCards.size(); i++) {
			players.get(i % numPlayers).getCurrentHand().add(remainingCards.get(i));
		}

		// Make sure each player has an even number of cards (as even as possible)
		for(Player player : players) {
			logger.info(player.getCharacterName() + " has " + player.getCurrentHand().size() + " cards.");
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
		boolean playerIsActive = false;
		CharacterEnum[] characters = CharacterEnum.values();
		for(int i = 0; i < characters.length; i++) {
			// Only create NUM_PLAYERS active characters/players (the rest will be created, but aren't attached to an actual live player)
			if(i < numPlayers) {
				playerIsActive = true;
			}
			
			CharacterEnum character = characters[i];
			// Extract the fields from the CharacterEnum instance
			CharacterName name = character.getName();
			String abbreviation = character.getAbbreviation();
			Location startingLocation = character.getStartLocation();
			// Create a new Player object
			Player player = new Player(playerIsActive, abbreviation, name, startingLocation);
			// Place the player at their starting location on the board
			BoardLocationEntity boardLocationEntity = board[startingLocation.getX()][startingLocation.getY()];
			// Not the cleanest solution using instanceof, but workable for our purposes
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
			this.players.add(player);
			// Reset the playerIsActive boolean
			playerIsActive = false;
			logger.info("Created player: " + player.toString());
		}
	}

	/**
	 * Pretty prints the board, which is a 5x5 2D array.
	 * Although this method looks somewhat complex, the logic is necessary for
	 * ensuring the board is legible when printed to the console for early debugging.
	 */
	private void printBoard() {
		System.out.println("\nCURRENT CLUELESS BOARD:");
		System.out.println("Rooms are two letters in length and start with 'R'");
		System.out.println("Hallways are of the format Room 1, Room 2, 'H'");
		System.out.println("---------------------------------------------------");
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			for(int col = 0; col < BOARD_WIDTH; col++) {
				BoardLocationEntity entity = board[row][col];
				if(col == 0) {
					System.out.print("| ");
				}
				// Print the abbreviation for the Room, Hallway, or InvalidLocation
				// If it's an InvalidLocation, don't print a label for it
				if(entity.getAbbreviation().startsWith("IL")) {
					System.out.print(String.format("%10s", " | "));
				} else {
					System.out.print(String.format("%10s", entity.getAbbreviation() + " | "));
				}
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
					// Print an empty section
					System.out.print(String.format("%10s", " | "));
				}
			}
			System.out.println("\n---------------------------------------------------");
		}
	}

	/**
	 * Determine if the given CharacterName can make the given move.
	 * 
	 * The String move should be in one of the following formats:
	 * <ol>
	 * <li>MV_STARTLOC_ENDLOC (make a move from one location to another)</li>
	 * <li>AA_CHARACTER_ROOM_WEAPON (make an accusation)</li>
	 * <li>AS_CHARACTER_ROOM_WEAPON (make a suggestion)</li>
	 * <li>DS_CARD (disprove a suggestion with a card string)</li>
	 * </ol>
	 * 
	 * @param character the name of the player attempting to move 
	 * @param move the move to validate (move, accusation, suggestion, etc.)
	 * @return whether the move is valid
	 */
	public boolean validateMove(Player player, String move) {
		logger.info(player.getCharacterName() + " attempted move " + move.toString());
		
		// Broadcast the move to all players
		broadcastMove(player, move);
		
		// Options for moving:
		//	1. Move through one of the doors to the hallway (if it is not blocked). 
		//	2. Take a secret passage to a diagonally opposite room (if there is one) and make a
		//	suggestion.
		// 	3. If you are in a hallway, you must move to one of the two rooms accessible from that hallway and make a suggestion.
		//	4. If you were moved to the room by another player making a suggestion, you may, if
		//	you wish, stay in that room and make a suggestion. Otherwise you may move
		//	through a doorway or take a secret passage as described above.
		// Other rules:
		// 	1. If all of the exits are blocked (i.e., there are people in all of the hallways) and you are not in
		//	one of the corner rooms (with a secret passage), and you weren’t moved to the room by
		//	another player making a suggestion, you lose your turn (except for maybe making an
		//	accusation).
		// 	2. Whenever a suggestion is made, the room must be the room the one making the suggestion
		//	is currently in. The suspect in the suggestion is moved to the room in the suggestion. 
		
		if(move.startsWith(Move.MOVE_STR)) {
			// Check if the move is valid
			// If this is the player's first move
			if(player.getNumMoves() == 0) {
				// Ensure that their first move is to the room adjacent to their home square
			}
			
			// Split the move string by the separator and extract the values
			String[] values = move.split(Move.MOVE_SEP);
			String startLoc = values[1];
			String endLoc = values[2];
			
			logger.info("Received movement: " + move + " from player " + player.getCharacterName());
			logger.info("startLoc: " + startLoc);
			logger.info("endLoc: " + endLoc);
			
			// TODO: Logic
			
		} else if(move.startsWith(Move.ACCUS_STR)) {
			// Split the move string by the separator and extract the values
			String[] values = move.split(Move.MOVE_SEP);
			String character = values[1];
			String room = values[2];
			String weapon = values[3];
			
			logger.info("Received accusation: " + move + " from player " + player.getCharacterName());
			logger.info("character: " + character);
			logger.info("room: " + room);
			logger.info("weapon: " + weapon);
			
			// TODO: Logic
			
		} else if(move.startsWith(Move.SUGGEST_STR)) {
			// Split the move string by the separator and extract the values
			String[] values = move.split(Move.MOVE_SEP);
			String character = values[1];
			String room = values[2];
			String weapon = values[3];
			
			logger.info("Received suggestion: " + move + " from player " + player.getCharacterName());
			logger.info("character: " + character);
			logger.info("room: " + room);
			logger.info("weapon: " + weapon);
			
			// TODO: Logic
			
		} else if(move.startsWith(Move.DISPROVE_SUGGEST)) {
			// Split the move string by the separator and extract the values
			String[] values = move.split(Move.MOVE_SEP);
			String card = values[1];
			
			logger.info("Received a disprove suggestion attempt " + move + " from player " + player.getCharacterName());
			logger.info("card used to disprove: " + card);
			
		} else {
			logger.error("Didn't recognize the move " + move + " from " + player.getCharacterName());
		}

		// For now, always return true (TODO: Start returning false for invalid moves)
		return true;
	}

	/**
	 * Broadcast a move (i.e., send it to all players). This should only happen
	 * if the move was successful.
	 * 
	 * @param message the message summarizing the move that was made
	 */
	public void broadcastMove(Player player, String move) {
		// Broadcast the move to each Player by interating over each ConnectionHandler we have
		for(ConnectionHandler connection : connections) {
			connection.sendMessage(player.getCharacterName().toString() + " made move " + move);
		}
	}

	public boolean isActive() {
		return active;
	}

	public BoardLocationEntity[][] getBoard() {
		return board;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setBoard(BoardLocationEntity[][] board) {
		this.board = board;
	}

	public void setConnections(ArrayList<ConnectionHandler> connections) {
		this.connections = connections;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
}
