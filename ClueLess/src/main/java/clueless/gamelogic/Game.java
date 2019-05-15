package clueless.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import clueless.gamelogic.locationenums.LocationEnum;
import clueless.networking.ConnectionHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private String recentSuggestionChar = null;
	private String recentSuggestionRoom = null;
	private String recentSuggestionWeap = null;
	// Board height
	private static final int BOARD_HEIGHT = 5;
	// Board width
	private static final int BOARD_WIDTH = 5;
	// Used to indicate that a given BoardLocationEntity should be of type InvalidLocation
	private static final String INVALID_STR = "INVALID";
	// Used to indicate that a given BoardLocationEntity should be of type Hallway
	private static final String HALLWAY_STR = "HALLWAY";
	// Used by a player to indicate that they wish to end their turn
	private static final String DONE_STR = "Done";
	// Used by the game to indicate to players that another player has ended their turn
	private static final String END_TURN_STR = "End turn.";

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
		this.createPlayers();
		this.generateCards();
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
		logger.info("The true game solution is: " + gameSolution.toString());
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
		/*
		 * Place the locations on the board. This includes hallways, rooms, and invalid locations
		 * (i.e., empty squares no one can go to).
		 */
		for(LocationEnum boardLoc : LocationEnum.values()) {
			Location location = boardLoc.getLocation();
			String name = boardLoc.name();
			String abbreviation = boardLoc.getAbbreviation();
			boolean hasSecretPassage = boardLoc.hasSecretPassage();

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
				Room room = new Room(name, abbreviation, location, hasSecretPassage);
				board[location.getX()][location.getY()] = room;
			}
		}

		/*
		 * Place the characters on the board. It's important to do this now because the server will soon
		 * send out the locations of all players/board entities to all clients.
		 */
		for(Player player : players) {
			Location startingLocation = player.getLocation();
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
			} else {
				logger.error("boardLocationEntity is null");
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
		System.out.println("---------------------------------------------------------------------------------" 
				+ "---------------------------------------------");
		for(int row = 0; row < BOARD_HEIGHT; row++) {
			for(int col = 0; col < BOARD_WIDTH; col++) {
				BoardLocationEntity entity = board[row][col];
				if(col == 0) {
					System.out.print("| ");
				}
				// Print the abbreviation for the Room, Hallway, or InvalidLocation
				// If it's an InvalidLocation, don't print a label for it
				if(entity.getAbbreviation().startsWith("IL")) {
					System.out.print(String.format("%25s", " | "));
				} else if(entity instanceof Hallway){
					System.out.print(String.format("%25s", "Hallway" + " (" + row + "," + col + ") | "));
				} else {
					System.out.print(String.format("%25s", entity.getName() + " (" + row + "," + col + ") | "));
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
					System.out.print(String.format("%25s", allOccupants + " | "));
				} else if(entity instanceof Hallway) {
					// If this location is a Hallway and it's occupied, print the occupant's abbreviation
					if(((Hallway) entity).isOccupied()) {
						System.out.print(String.format("%25s", ((Hallway) entity).getPlayer().getAbbreviation() + " | "));
					} else {
						System.out.print(String.format("%25s", " | "));
					}
				} else {
					// Print an empty section
					System.out.print(String.format("%25s", " | "));
				}
			}
			System.out.println("\n---------------------------------------------------------------------------------" 
					+ "---------------------------------------------");
		}
	}

	/**
	 * Determine if the given CharacterName can make the given move.
	 * 
	 * 
	 * The String move should be in one of the following formats:
	 * <ol>
	 * <li>MV_ENDLOC (make a move from one location to another)</li>
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
		// Verify that the input is of the expected format
		/*if(!validateInput(move)) {
			logger.info("Move " + move + " has an invalid format");
			// If it fails, don't need to broadcast as it was just a user typo
			return false;
		}*/

		// If a player enters 'Done', they've decided to end their turn
		if(move.trim().equalsIgnoreCase(DONE_STR)) {
			// Increment the move count for this player 
			player.setNumMoves(player.getNumMoves() + 1);
			// Broadcast this move
			broadcastMove(player, END_TURN_STR);
			// Always a valid move
			return true;
		}

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
			// Split the move string by the separator and extract the values
			String[] values = move.split(Move.MOVE_SEP);
			String endLoc = values[1];
			int endLocX = Integer.parseInt(endLoc.split(",")[0]);
			int endLocY = Integer.parseInt(endLoc.split(",")[1]);
			logger.info("Move: " + move);
			logger.info("endLocX: " + endLocX);
			logger.info("endLocY: " + endLocY);
			
			if(endLocX > 4 || endLocX < 0 || endLocY > 4 || endLocY < 0) {
				logger.info("Player " + player.getCharacterName().getCharacterName() + "'s move attempt went out of bounds. Invalid move.");
				return false;
			}
			
			
			BoardLocationEntity destLocEntity = board[endLocX][endLocY];
			logger.info("destLocEntity: " + destLocEntity.getName());

			logger.info("Received movement: " + move + " from player " + player.getCharacterName());
			/*
			 * First, get the current location of this player, which will be the most important info
			 * when determining if the given move is valid.
			 */
			Location currentLoc = player.getLocation();
			int currentX = currentLoc.getX();
			int currentY = currentLoc.getY();
			BoardLocationEntity currentLocEntity = board[currentX][currentY];

			/*
			 * NOTE: If the currentLocEntity object is an instance of a Room, we can now check whether
			 * it contains a secret passageway.
			 * This code here is for example purposes only - will need to check whether a room contains 
			 * a secret passageway if a player attempts to make a diagonal move from one of the corner rooms.
			 */
			if(currentLocEntity instanceof Room) {
				Room room = (Room) currentLocEntity;
				boolean hasSecretPassage = room.hasSecretPassage();
				if(hasSecretPassage) {
					logger.info(room.getName() + " has a secret passage");
				} else {
					logger.info(room.getName() + " doesn't have a secret passage");
				}
			} else {
				logger.info(currentLocEntity + " is not an instance of Room");
			}

			/*
			 * If this is the player's first move, it must be to the hallway adjacent to their home square
			 */
			if(player.getNumMoves() == 0) {
				// If the end location isn't a hallway and they're not currently in a hallway, it's an invalid move
				if(!(destLocEntity instanceof Hallway) && !(currentLocEntity instanceof Hallway)) {
					logger.info("Player " + player.getCharacterName().getCharacterName() + "'s first move attempt was not to a hallway. Invalid move.");
					return false;
				}
			}

			/*
			 * Test to see if a move goes out of bounds
			 */


			/*
			 * Test to see if the move goes into an invalid location 
			 */
			if(destLocEntity instanceof InvalidLocation) {
				logger.info("Player " + player.getCharacterName().getCharacterName() + "'s move attempt went to an invalid location. Invalid move.");
				return false;
			}

			/*
			 * If the attempted move is to a hallway, the hallway cannot be occupied. General hallway check logic:
			 */
			if(destLocEntity instanceof Hallway) {
				Hallway hallway = (Hallway) destLocEntity;
				// If the hallway is occupied, the player can't move there
				if(hallway.isOccupied()) {
					logger.info("Player " + player.getCharacterName().getCharacterName() + " attempted to move to a non-empty hallway.");
					return false;
				}

				// If the hallway isn't occupied, it must be adjacent to the player's current location
				boolean isAdjacent = checkHallwayAdjacency(currentLoc, new Location(endLocX, endLocY));
				if(!isAdjacent) {
					logger.info("Player " + player.getCharacterName().getCharacterName() + " attempted to move to a non-adjacent hallway.");
					return false;
				}
			}

			// Increment the number of moves this player has made. This is necessary to ensure certain rules are enforced
			player.setNumMoves(player.getNumMoves() + 1);

			// Broadcast the move so each client can update their boards
			System.out.println("BEFORE");
			broadcastNewLocation(player, destLocEntity);
			System.out.println("AFTER");

			// Update the Player's location
			player.setLocation(new Location(endLocX, endLocY));

			// The move was valid
			return true;
		} else if(move.startsWith(Move.ACCUS_STR)) {
			// Split the move string by the separator and extract the values
			String[] values = move.split(Move.MOVE_SEP);
			String accusationCharacter = values[1];
			String accusationRoom = values[2];
			String accusationWeapon = values[3];

			logger.info("Received accusation: " + move + " from player " + player.getCharacterName());

			// Check the accuracy of the accusation
			String gameSolutionChar = gameSolution.getCharacterName().toString();
			String gameSolutionRoom = gameSolution.getRoomName().getRoomName();
			String gameSolutionWeap = gameSolution.getWeaponType().getWeapon();
			if(gameSolutionChar.equals(accusationCharacter) && gameSolutionRoom.equals(accusationRoom) && 
					gameSolutionWeap.equals(accusationWeapon)) {
				return true;
			} else {
				return false;
			}
		} else if(move.startsWith(Move.SUGGEST_STR)) {
			// Split the move string by the separator and extract the values
			String[] values = move.split(Move.MOVE_SEP);
			String character = values[1];
			String room = values[2];
			String weapon = values[3];

			// Save the suggestion parameters
			recentSuggestionChar = character;
			recentSuggestionRoom = room;
			recentSuggestionWeap = weapon;

			logger.info("Received suggestion: " + move + " from player " + player.getCharacterName());
			logger.info("Character: " + character);
			logger.info("Room: " + room);
			logger.info("Weapon: " + weapon);

			// The character who was suggested must be moved to the room that was suggested
			// Find the BoardLocationEntity on the board that corresponds to this room
			BoardLocationEntity suggestedRoomEntity = null;
			for(int row = 0; row < BOARD_HEIGHT; row++) {
				for(int col = 0; col < BOARD_WIDTH; col++) {
					BoardLocationEntity entity = board[row][col];
					if(entity.getName().equals(room)) {
						suggestedRoomEntity = entity;
						break;
					}
				}
			}

			// If we didn't find the room, there was a typo in the input
			if(suggestedRoomEntity == null) {
				return false;
			}

			// Broadcast the new location of the character that was suggested so clients can update their boards
			broadcastNewLocation(character, suggestedRoomEntity);

			// Turn on "disprove suggestion" mode, passing references to this game and the player who made the suggestion
			TurnEnforcement.turnOnDisproveSuggestionMode(this, player);

			// Send the static TurnEnforcement class a list of players that might need to disprove the suggestion
			ArrayList<String> playersToDisprove = new ArrayList<>();
			for(Player p : players) {
				// Everyone other than the player who made the suggestion may be asked to disprove it
				if(!p.getAbbreviation().equals(player.getAbbreviation())) {
					if(p.isActive()) {
						playersToDisprove.add(p.getAbbreviation());
					}
				}
			}
			TurnEnforcement.setPlayersToDisprove(playersToDisprove);

			// Broadcast the first player who should make a suggestion
			broadcastDisproveSuggestion(playersToDisprove.get(0), move);

			return true;
		} else if(move.startsWith(Move.DISPROVE_SUGGEST)) {
			// Split the move string by the separator and extract the values
			String[] values = move.split(Move.MOVE_SEP);
			String card = values[1];

			logger.info("Received a disprove suggestion attempt " + move + " from player " + player.getCharacterName());
			logger.info("Card used to disprove: " + card);
		} else {
			logger.error("Didn't recognize the move " + move + " from " + player.getCharacterName());
			return false;
		}

		return true;
	}

	/**
	 * Check if two <code>Location</code> objects are adjacent to one another.
	 * NOTE: This should ONLY be used to check if a square is adjacent to a 
	 * Hallway's location because hallways are never diagonal. Therefore,
	 * if a hallway's distance from a square is anything other than 1, it's
	 * an invalid move.
	 * @param locOne the first <code>Location</code>
	 * @param locTwo the second <code>Location</code>
	 * @return whether the two locations are adjacent to one another
	 */
	private boolean checkHallwayAdjacency(Location locOne, Location locTwo) {
		int xDiff = locTwo.getX() - locOne.getX();
		int yDiff = locTwo.getY() - locTwo.getY();
		int distance = (int) Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		logger.info("Calculated a distance of " + distance + " between " + locOne.toString() + " and " + locTwo.toString());
		return distance == 1;
	}

	/**
	 * Send appropriate prompts to each player based on whose turn it currently is
	 * and what move was just made.
	 * 
	 * @param suggestionMade whether the move just made was a suggestion
	 * @param suggestedValues the values that were suggested (i.e., room, character, and weapon)
	 * @param player the <code>Player</code> object of the player who just made a move
	 */
	public void sendPlayersPrompts(boolean suggestionMade, String suggestedValues, Player player) {
		int currentPlayer = TurnEnforcement.getCurrentPlayer();
		String justMovedCharacter = player.getCharacterName().getCharacterName();

		/*
		 * Add a special case for handling suggestions because that will require input from each
		 * active user.
		 */
		if(suggestionMade) {
			for(ConnectionHandler connection : connections) {
				String characterName = connection.getPlayer().getCharacterName().getCharacterName();
				if(!characterName.equals(justMovedCharacter)) {
					connection.sendMessage("Please enter a card to refute the suggestion: " + suggestedValues);
				}
			}
		} else {
			for(ConnectionHandler connection : connections) {
				int playerNumber = connection.getPlayerNumber();
				String characterName = connection.getPlayer().getCharacterName().getCharacterName();
				if(currentPlayer == playerNumber) {
					connection.sendMessage(characterName + ", it's your turn.");
					connection.sendMessage("To move to a square, use the syntax MV_XY, where XY is a set of (X,Y) coordinates.");
					connection.sendMessage("To make a suggestion, use the syntax AS_<Character>_<Room>_<Weapon>.");
					connection.sendMessage("To make an accusation, use the syntax AA_<Character>_<Room>_<Weapon>.");
					connection.sendMessage("To end your turn, enter 'Done'.");
					connection.sendMessage(">");
				} else {
					connection.sendMessage("Player " + currentPlayer + " is currently making a turn.");
				}
			}
		}
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

	/**
	 * Broadcast any message.
	 */
	public void broadcastMsg(String message) {
		// Broadcast the given string to all clients
		for(ConnectionHandler connection : connections) {
			connection.sendMessage(message);
		}
	}

	/**
	 * Broadcast a new location so each client can update their board. Update the master board (located in the 
	 * server via the Game's board[][] variable) with the new information.
	 * 
	 * @param player
	 * @param newLocation
	 */
	public void broadcastNewLocation(Player player, BoardLocationEntity newLocation) {
		Location newLoc = newLocation.getLocation();
		int newX = newLoc.getX();
		int newY = newLoc.getY();
		String playerAbbrev = player.getAbbreviation();
		// Broadcast the move to each Player by interating over each ConnectionHandler we have
		for(ConnectionHandler connection : connections) {
			connection.sendMessage("NL" + Move.MOVE_SEP + player.getAbbreviation() + Move.MOVE_SEP + newX + newY);
		}

		// Update the master board (i.e., the Game class' board[][])
		// Search the board for this abbreviation and remove it from the now obsolete location
		logger.info("Updating the master board with the new location");
		for(int x = 0; x < BOARD_WIDTH; x++) {
			for(int y = 0; y < BOARD_HEIGHT; y++) {
				BoardLocationEntity boardLocEntity = board[x][y];
				if(boardLocEntity instanceof Hallway) {
					Hallway hallway = (Hallway) boardLocEntity;
					if(hallway.isOccupied()) {
						// If we found their old location to be a hallway, remove them from that hallway
						if(hallway.getPlayer().getAbbreviation().equals(playerAbbrev)) {
							// Mark the hallway as not occupied
							hallway.setOccupied(false);
							// Mark the current player in the hallway as null
							hallway.setPlayer(null);
						}
					}
				} else if(boardLocEntity instanceof Room) {
					Room room = (Room) boardLocEntity;
					ArrayList<Player> currentPlayers = room.getCurrentPlayers();
					// Create an iterator for the ArrayList to avoid ConcurrentModificationExceptions in 
					// the event that we need to remove an element
					Iterator<Player> playerIterator = currentPlayers.iterator();
					while(playerIterator.hasNext()) {
						// Get the next Player from the iterator
						Player nextPlayer = playerIterator.next();
						// If the abbreviation matches that of the player who's getting a new location
						if(nextPlayer.getAbbreviation().equals(playerAbbrev)) {
							// Remove the player from this location
							playerIterator.remove();
						}
					}
				}
			}
		}

		// Put the player at the new location on the local board (i.e., the board this client can actually view)
		BoardLocationEntity boardLocEntity = board[newX][newY];
		if(boardLocEntity instanceof Hallway) {
			Hallway hallway = (Hallway) boardLocEntity;
			// Mark the hallway as occupied
			hallway.setOccupied(true);
			// Set the player (this is a dummy player just to populate/display/maintain the board properly)
			hallway.setPlayer(new Player(playerAbbrev));
		} else if(boardLocEntity instanceof Room) {
			Room room = (Room) boardLocEntity;
			// Add the player to the room's list of occupants
			room.getCurrentPlayers().add(new Player(playerAbbrev));
		}

		printBoard();
	}

	/**
	 * Broadcast the player who should make a suggestion.
	 */
	public void broadcastDisproveSuggestion(String playerToDisprove, String suggestion) {
		// Broadcast the move to each Player by interating over each ConnectionHandler we have
		for(ConnectionHandler connection : connections) {
			connection.sendMessage("Waiting for " + playerToDisprove + " to disprove the suggestion " + suggestion);
		}
	}

	/**
	 * Broadcast a new location so each client can update their board.
	 * 
	 * @param characterAbbrev
	 * @param newLocation
	 */
	public void broadcastNewLocation(String characterAbbrev, BoardLocationEntity newLocation) {
		Location newLoc = newLocation.getLocation();
		int newX = newLoc.getX();
		int newY = newLoc.getY();
		// Broadcast the move to each Player by interating over each ConnectionHandler we have
		for(ConnectionHandler connection : connections) {
			connection.sendMessage("NL" + Move.MOVE_SEP + characterAbbrev + Move.MOVE_SEP + newX + newY);
		}

		// Update the master board (i.e., the Game class' board[][])
		// Search the board for this abbreviation and remove it from the now obsolete location
		logger.info("Updating the master board with the new location");
		for(int x = 0; x < BOARD_WIDTH; x++) {
			for(int y = 0; y < BOARD_HEIGHT; y++) {
				BoardLocationEntity boardLocEntity = board[x][y];
				if(boardLocEntity instanceof Hallway) {
					Hallway hallway = (Hallway) boardLocEntity;
					if(hallway.isOccupied()) {
						// If we found their old location to be a hallway, remove them from that hallway
						if(hallway.getPlayer().getAbbreviation().equals(characterAbbrev)) {
							// Mark the hallway as not occupied
							hallway.setOccupied(false);
							// Mark the current player in the hallway as null
							hallway.setPlayer(null);
						}
					}
				} else if(boardLocEntity instanceof Room) {
					Room room = (Room) boardLocEntity;
					ArrayList<Player> currentPlayers = room.getCurrentPlayers();
					// Create an iterator for the ArrayList to avoid ConcurrentModificationExceptions in 
					// the event that we need to remove an element
					Iterator<Player> playerIterator = currentPlayers.iterator();
					while(playerIterator.hasNext()) {
						// Get the next Player from the iterator
						Player nextPlayer = playerIterator.next();
						// If the abbreviation matches that of the player who's getting a new location
						if(nextPlayer.getAbbreviation().equals(characterAbbrev)) {
							// Remove the player from this location
							playerIterator.remove();
						}
					}
				}
			}
		}

		// Put the player at the new location on the local board (i.e., the board this client can actually view)
		BoardLocationEntity boardLocEntity = board[newX][newY];
		if(boardLocEntity instanceof Hallway) {
			Hallway hallway = (Hallway) boardLocEntity;
			// Mark the hallway as occupied
			hallway.setOccupied(true);
			// Set the player (this is a dummy player just to populate/display/maintain the board properly)
			hallway.setPlayer(new Player(characterAbbrev));
		} else if(boardLocEntity instanceof Room) {
			Room room = (Room) boardLocEntity;
			// Add the player to the room's list of occupants
			room.getCurrentPlayers().add(new Player(characterAbbrev));
		}

		printBoard();
	}
	
	/**
	 * Send a message to only this player.
	 * 
	 * @param player the player to whom the message will be sent
	 * @param message the message to send
	 */
	public void sendSpecificPlayerMsg(Player player, String message) {
		for(ConnectionHandler connection : connections) {
			String characterName = connection.getPlayer().getCharacterName().getCharacterName();
			if(characterName.equals(player.getCharacterName().getCharacterName())) {
				connection.sendMessage(message);
			}
		}
	}
	
	/**
	 * Tell a player its their turn to attempt to disprove the suggestion.
	 * 
	 * @param abbreviation the abbreviation for the character who should disprove next
	 * @param message the message to send to this player
	 */
	public void tellPlayerToDisprove(String abbreviation, String message) {
		for(ConnectionHandler connection : connections) {
			String characterAbbrev = connection.getPlayer().getAbbreviation();
			if(characterAbbrev.equals(abbreviation)) {
				connection.sendMessage(message);
			}
		}
	}

	/**
	 * Validate the user input to ensure it matches the expected format.
	 * 
	 * @param move the move entered by the player
	 * @return whether the move matches the regex
	 */
	public boolean validateInput(String move) {
		//Valid input to ensure string is valid using regular expression
		String regExpPattern = "^[A-Za-z]{2}_[0-9]{2}$|^[A-Za-z]{2}(_[A-Za-z]{2,10}){1,3}|(?i)DONE(?-i)";;
		Pattern a = Pattern.compile(regExpPattern);
		Matcher matcher = a.matcher(move);
		if (matcher.find()) {
			return true;
		} else {
			logger.info("Move " + move + " failed regexp match " + a);
			return false;
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

	public GameSolution getGameSolution() {
		return gameSolution;
	}

	public String getRecentSuggestionChar() {
		return recentSuggestionChar;
	}

	public String getRecentSuggestionRoom() {
		return recentSuggestionRoom;
	}

	public String getRecentSuggestionWeap() {
		return recentSuggestionWeap;
	}

	public void setRecentSuggestionChar(String recentSuggestionChar) {
		this.recentSuggestionChar = recentSuggestionChar;
	}

	public void setRecentSuggestionRoom(String recentSuggestionRoom) {
		this.recentSuggestionRoom = recentSuggestionRoom;
	}

	public void setRecentSuggestionWeap(String recentSuggestionWeap) {
		this.recentSuggestionWeap = recentSuggestionWeap;
	}
}
