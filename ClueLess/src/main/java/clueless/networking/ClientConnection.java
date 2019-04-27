package clueless.networking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import clueless.gamelogic.AbbreviationMap;
import clueless.gamelogic.BoardLocationEntity;
import clueless.gamelogic.Card;
import clueless.gamelogic.CharacterCard;
import clueless.gamelogic.CharacterEnum;
import clueless.gamelogic.Hallway;
import clueless.gamelogic.InvalidLocation;
import clueless.gamelogic.Location;
import clueless.gamelogic.Notebook;
import clueless.gamelogic.Player;
import clueless.gamelogic.Room;
import clueless.gamelogic.RoomCard;
import clueless.gamelogic.WeaponCard;
import clueless.gamelogic.WeaponType;
import clueless.gamelogic.locationenums.LocationEnum;

/**
 * Run by the client to establish a connection to the server at the specified
 * host and port number.
 * 
 * @author matthewsobocinski
 */
public class ClientConnection {
	private final static Logger logger = Logger.getLogger(ClientConnection.class);
	// Port number of Clueless' server process
	private static int PORT_NUMBER;
	// IP address of Clueless' server process
	private static String IP_ADDRESS;
	// Properties object to hold system configuration
	private static Properties props = null;
	// Path to properties file
	private static final String propsPath = "resources/clueless.properties";
	// String constants for keys in properties file
	private static final String PORT_NUMBER_KEY = "portNumber";
	private static final String IP_ADDRESS_KEY = "ipAddress";
	// Game-related constants
	private static final String GAMEOVER = "GAMEOVER";

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// Configure log4j
		DOMConfigurator.configure("log4jclient.xml");
		Socket socket = null;
		PrintWriter clientOut = null;
		BufferedReader serverIn = null;
		// Set the global properties
		setProperties();

		try {
			// Bind to the host and port
			socket = new Socket(IP_ADDRESS, PORT_NUMBER);
			// Instantiate a writer for writing back to the server
			clientOut = new PrintWriter(socket.getOutputStream(), true);
			// Instantate a reader for reading messages from the server
			serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			Runnable serverInput = new ServerInput(serverIn);
			new Thread(serverInput).start();

			// Print the acknowledgement from the server
			//logger.info(serverIn.readLine());

			Scanner input = new Scanner(System.in);
			// Continually accept input
			while (input.hasNext()) {
				// Send the client input to the server
				clientOut.println(input.nextLine());
			}
		} catch (ConnectException e) {
			logger.error("Either the server is not running or the game is already full and cannot be joined.");
		}
	}

	/**
	 * Process INPUT FROM THE SERVER in a separate thread other than that which
	 * is used for sending this client's input to the server. This makes the 
	 * system full-duplex and ensures broadcast messages can be received right
	 * away.
	 *
	 * @author matthewsobocinski
	 */
	static class ServerInput implements Runnable {
		private static final Logger logger = Logger.getLogger(ServerInput.class);
		private static final int BOARD_HEIGHT = 5;
		private static final int BOARD_WIDTH = 5;
		private static final String SERVER_MSG_SEP = "_";
		// Used to indicate that a given BoardLocationEntity should be of type InvalidLocation
		private static final String INVALID_STR = "INVALID";
		// Used to indicate that a given BoardLocationEntity should be of type Hallway
		private static final String HALLWAY_STR = "HALLWAY";
		private BoardLocationEntity[][] board;
		// This player's current location on the board
		private Location currentLocation = null;
		private Notebook notebook = null;
		private String characterName = null;
		private ArrayList<Card> currentHand = null;
		private BufferedReader serverIn;
		// Prefixes for info that will be sent by the server (through broadcast or a singular message)
		private static final String CHARACTER_NAME = "CN";
		private static final String CARD = "CARD";
		private static final String NEW_LOCATION = "NL";
		private static final String SUGGESTION = "SUGG";

		/**
		 * Constructor.
		 * @param serverIn the reader object for obtaining input from the server
		 */
		public ServerInput(BufferedReader serverIn) {
			// Configure log4j
			DOMConfigurator.configure("log4jclient.xml");
			this.serverIn = serverIn;
			this.notebook = new Notebook();
			this.currentHand = new ArrayList<>();
			this.board = new BoardLocationEntity[BOARD_WIDTH][BOARD_HEIGHT];
			this.populateBoard();
			this.printBoard();
		}

		/**
		 * Populate the 2D BoardEntityLocation array by iterating over the LocationEnum's values.
		 */
		private void populateBoard() {
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
		}

		@Override
		public void run() {
			logger.debug("Started new ServerInput thread");
			try {
				while(true) {
					// Accept and print a message from the server
					String serverInput = serverIn.readLine();
					if(serverInput == null) {
						System.out.println("The server has either ended the game or crashed. Now exiting - thanks for playing.");
						System.exit(0);
					}

					/*
					 * All logic for handling input from the server needs to go here.
					 * This includes, but is not limited to, updating each client's local
					 * instance of the board.
					 * CN_<character abbreviation> = the name of the character this player has been assigned
					 * CARD_<card> = a card to place in this player's hand
					 * NL_<character>_<newloc>
					 * SUGG_<suggestion char>_<suggestion room>_<suggestion weapon>
					 * *GAMEOVER* - a correct accusation was made and the game is over
					 */
					if(serverInput.contains(GAMEOVER)) {
						System.out.println("A correct accusation was made and the game is over.");
						System.exit(0);
					}

					// Don't use a new line if it's just the prompt character
					if(serverInput.equals(">")) {
						System.out.print(serverInput + " ");
					} else {
						System.out.println(serverInput);
					}

					// Strip the prefix, which tells us what kind of info we have just received from the server
					String msgPrefix = serverInput.split(SERVER_MSG_SEP)[0];

					switch(msgPrefix) {
						case CHARACTER_NAME: {
							handleCharacterName(serverInput);
							break;
						}
						case CARD: {
							handleCard(serverInput);
							break;
						}
						case NEW_LOCATION: {
							handleNewLocation(serverInput);
							break;
						}
						case SUGGESTION: {
							handleSuggestion(serverInput);
							break;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Handle a character name sent from the server.
		 * @param serverInput the input string from the server
		 */
		private void handleCharacterName(String serverInput) {
			String charName = serverInput.split(SERVER_MSG_SEP)[1];
			this.characterName = charName;
		}

		/**
		 * Handle a card sent from the server to be placed in this player's hand.
		 * @param serverInput the input string from the server
		 */
		private void handleCard(String serverInput) {
			String[] values = serverInput.split(SERVER_MSG_SEP);
			// Get the type of the card (weapon, room, or character)
			String cardType = values[1];
			// Get the value of the card (e.g., PLUM)
			String cardValue = values[2];

			logger.info("You've received a card of type " + cardType + " and value " + cardValue);

			if(cardType.equals("ROOM")) {
				RoomCard card = new RoomCard(cardValue);
				this.currentHand.add(card);
				logger.info("Added card " + card.toString() + " to the current hand.");
			} else if(cardType.equals("CHARACTER")) {
				CharacterCard card = new CharacterCard(cardValue);
				this.currentHand.add(card);
				logger.info("Added card " + card.toString() + " to the current hand.");
			} else if(cardType.equals("WEAPON")) {
				WeaponCard card = new WeaponCard(cardValue);
				this.currentHand.add(card);
				logger.info("Added card " + card.toString() + " to the current hand.");
			}
		}

		/**
		 * Handle a message from the server indicating that a player has switched
		 * locations on the board. For example, if a character is move from location
		 * (x_1, y_1) to (x_2, y_2), we need to update that on our board. This involves 
		 * removing that particular player/entity from where they were previously and
		 * placing them where they are now.
		 * @param serverInput the input string from the server
		 */
		private void handleNewLocation(String serverInput) {
			String[] values = serverInput.split(SERVER_MSG_SEP);
			String entityAbbrev = values[1];
			int newX = Integer.parseInt(values[2].substring(0,1));
			int newY = Integer.parseInt(values[2].substring(1,2));
			logger.info("Received new location of (" + newX + "," + newY + ") for entity " + entityAbbrev);

			// Search the board for this abbreviation and remove it from the now obsolete location
			for(int x = 0; x < BOARD_WIDTH; x++) {
				for(int y = 0; y < BOARD_HEIGHT; y++) {
					BoardLocationEntity boardLocEntity = board[x][y];
					if(boardLocEntity instanceof Hallway) {
						Hallway hallway = (Hallway) boardLocEntity;
						if(hallway.isOccupied()) {
							// If we found their old location to be a hallway, remove them from that hallway
							if(hallway.getPlayer().getAbbreviation().equals(entityAbbrev)) {
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
							Player player = playerIterator.next();
							// If the abbreviation matches that of the player who's getting a new location
							if(player.getAbbreviation().equals(entityAbbrev)) {
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
				hallway.setPlayer(new Player(entityAbbrev));
			} else if(boardLocEntity instanceof Room) {
				Room room = (Room) boardLocEntity;
				// Add the player to the room's list of occupants
				room.getCurrentPlayers().add(new Player(entityAbbrev));
			} else {
				logger.error("Received a bad new location message from server: " + serverInput);
			}

			printBoard();
		}

		/**
		 * Handle a message from the server indicating that a suggestion was made.
		 * @param serverInput the input string from the server
		 */
		private void handleSuggestion(String serverInput) {
			// TODO
		}

		/**
		 * Pretty prints the board, which is a 5x5 2D array.
		 * Although this method looks somewhat complex, the logic is necessary for
		 * ensuring the board is legible when printed to the console for early debugging.
		 */
		private void printBoard() {
			if(characterName == null) {
				return;
			}
			System.out.println("\n\n\nYour Clueless character is " + AbbreviationMap.getCharacterFullname(characterName));
			System.out.println("Your current hand contains these cards:");
			// Display the player's hand to them
			for(int i = 0; i < currentHand.size(); i++) {
				Card card = currentHand.get(i);
				System.out.print("Card " + (i+1) + ": " + card.getCardName() + " (" + card.getCardType() + ")");
				if(i != (currentHand.size() - 1)) {
					System.out.print(", ");
				}
			}
			// Print the weapons used in the game
			System.out.println("\n\nAll Clueless Weapons:");
			WeaponType[] weapons = WeaponType.values();
			for(int i = 0; i < weapons.length; i++) {
				System.out.print(weapons[i].getWeapon());
				if(i != (weapons.length - 1)) {
					System.out.print(", ");
				}
			}
			// Print the characters in the game
			System.out.print("\nAll Clueless Characters: ");
			CharacterEnum[] characters = CharacterEnum.values();
			for(int i = 0; i < characters.length; i++) {
				System.out.print(characters[i].getName());
				if(i != (characters.length - 1)) {
					System.out.print(", ");
				}
			}
			System.out.println("\nCurrent Clueless board:");
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
	}

	/**
	 * Helper method to assign variables based on values from properties file.
	 */
	private static void setProperties() {
		props = new Properties();          
		try {
			props.load(new FileInputStream(propsPath));
			PORT_NUMBER = Integer.parseInt(props.getProperty(PORT_NUMBER_KEY));
			IP_ADDRESS = props.getProperty(IP_ADDRESS_KEY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
