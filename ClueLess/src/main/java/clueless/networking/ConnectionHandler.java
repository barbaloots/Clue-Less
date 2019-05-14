package clueless.networking;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import clueless.gamelogic.BoardLocationEntity;
import clueless.gamelogic.Card;
import clueless.gamelogic.Game;
import clueless.gamelogic.Hallway;
import clueless.gamelogic.Move;
import clueless.gamelogic.Player;
import clueless.gamelogic.Room;
import clueless.gamelogic.TurnEnforcement;
import clueless.ui.ProveSuggestionWindow;

/**
 * A SERVER-SIDE class that handles each connection made to the server with a different thread.
 * 
 * @author matthewsobocinski
 */
public class ConnectionHandler implements Runnable {
	private static final Logger logger = Logger.getLogger(ConnectionHandler.class);
	private int playerNumber;
	private Player player;
	private Game game;
	private ProveSuggestionWindow disproveWindow;
	private PrintWriter clientOut;
	private BufferedReader clientIn = null;
	private PrintWriter serverOut = null;
	// Prefixes/constants for info that will be sent by the server to players (through broadcast or a singular message)
	private static final String SERVER_MSG_SEP = "_";
	private static final String CHARACTER_NAME = "CN";
	private static final String CARD = "CARD";
	private static final String NEW_LOCATION = "NL";
	private static final String SUGGESTION = "SUGG";

	/**
	 * Instantiate a ConnectionHandler for maintaining the individual connections made by each client.
	 * 
	 * @param clientSocket the socket by which communication is performed between client and server
	 * @param playerCount the number of the player to join the game
	 * @param player the Player object representing this user in the game
	 * @param game the Game with which this ConnectionHandler is associated
	 * @throws IOException
	 */
	public ConnectionHandler(Socket clientSocket, int playerCount, Player player, Game game) throws IOException {
		// Instantiate a writer for writing back to the client
		this.serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
		// Instantiate a reader for reading messages from the client
		this.clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		// Save the player number
		this.playerNumber = playerCount;
		// Save the Player objects
		this.player = player;
		// Store the game 
		this.game = game;
		// Initialize the logger
		this.initLogger();
		// Send each of the clients their respective cards
		this.sendClientCards();
		logger.info("Creating ClientConnectionHandler for Player " + playerCount + "...");
	}

	/**
	 * Configure log4j.
	 */
	private void initLogger() {
		DOMConfigurator.configure("log4jserver.xml");
	}

	/**
	 * Send a message to the client.
	 * 
	 * @param message the message to send
	 */
	public void sendMessage(String message) {
		serverOut.println(message);
	}

	/**
	 * Send a client the cards they've been dealt.
	 */
	private void sendClientCards() {
		for(Card card : player.getCurrentHand()) {
			serverOut.println(CARD + SERVER_MSG_SEP + card.getCardType() + SERVER_MSG_SEP + card.getCardName());
		}
	}

	@Override
	public void run() {
		logger.info("Starting new thread for Player " + playerNumber + "...");
		serverOut.println("Welcome, Player " + playerNumber + "! Your character is " + player.getCharacterName().getCharacterName() + ".");
		// Send the player their character name as an abbreviation
		serverOut.println(CHARACTER_NAME + SERVER_MSG_SEP + player.getAbbreviation());
		// Send the client the locations of objects on the board so they can populate their local board
		BoardLocationEntity[][] gameBoard = this.game.getBoard();
		for(int x = 0; x < gameBoard.length; x++) {
			for(int y = 0; y < gameBoard[0].length; y++) {
				BoardLocationEntity boardLocEntity = gameBoard[x][y];
				// If it's a hallway and it's occupied, send the info
				if(boardLocEntity instanceof Hallway) {
					Hallway hallway = (Hallway) boardLocEntity;
					if(hallway.isOccupied()) {
						String playerAbbrev = hallway.getPlayer().getAbbreviation();
						// Send a message that basically says "New location of this player is this" 
						serverOut.println(NEW_LOCATION + SERVER_MSG_SEP + playerAbbrev + SERVER_MSG_SEP + x + y);
					}
				}
				// If it's a room and there are occupants, send the info
				if(boardLocEntity instanceof Room) {
					Room room = (Room) boardLocEntity;
					if(!room.getCurrentPlayers().isEmpty()) {
						ArrayList<Player> currentPlayers = room.getCurrentPlayers();
						for(Player player : currentPlayers) {
							String playerAbbrev = player.getAbbreviation();
							serverOut.println(NEW_LOCATION + SERVER_MSG_SEP + playerAbbrev + x + y);
						}
					}
				}
			}
		}

		String clientInput = null;

		// Listen indefinitely for input from clients
		while(true) {
			try {
				// Parse input from the client
				clientInput = clientIn.readLine();

				// If null input is received, assume that the player has left the game
				if(clientInput == null) {
					// Exit the infinite loop
					break;
				}

				// Make sure the game has started before accepting input
				if(!TurnEnforcement.gameHasStarted()) {
					serverOut.println("The game has not started yet. Please wait.");
					continue;
				}
				
				// If a player is expected to disprove a suggestion, make sure this is the proper player
				if(TurnEnforcement.isInDisproveSuggestionMode()) {
					String characterCurrentlyDisproving = TurnEnforcement.getPlayerFromWhomDisproveSuggestionIsExpected();
					// If the game isn't currently excepting an attempt to disprove a move from this player
					if(!characterCurrentlyDisproving.equals(getPlayer().getAbbreviation())) {
						// Let them know it's not their turn
						serverOut.println("A suggestion has been made, but " + characterCurrentlyDisproving + " is currently"
								+ " attempting to disprove a suggestion.");
						continue;
					}
					
					// If the player is attempting to disprove a suggestion, check their card against what was suggested
					if(clientInput.startsWith(Move.DISPROVE_SUGGEST)) {
						String cardResponse = clientInput.split(Move.MOVE_SEP)[1];
						if(cardResponse.equals(game.getRecentSuggestionChar())) {
							game.broadcastMsg(getPlayer().getCharacterName().getCharacterName() + " disproved a suggestion");
							TurnEnforcement.turnOffDisproveSuggestionMode();
						} else if(cardResponse.equals(game.getRecentSuggestionRoom())) {
							game.broadcastMsg(getPlayer().getCharacterName().getCharacterName() + " disproved a suggestion");
							TurnEnforcement.turnOffDisproveSuggestionMode();
						} else if(cardResponse.equals(game.getRecentSuggestionWeap())) {
							game.broadcastMsg(getPlayer().getCharacterName().getCharacterName() + " disproved a suggestion");
							TurnEnforcement.turnOffDisproveSuggestionMode();
						} else {
							game.broadcastMsg(getPlayer().getCharacterName().getCharacterName() + " was unable to disprove a suggestion");
							TurnEnforcement.disproveSuggestionFailed();
						}
						continue;
					}
				}

				// Get the number of the player whose turn it currently is
				int currentPlayer = TurnEnforcement.getCurrentPlayer();
				// If it isn't this player's turn, send a message telling them to wait their turn
				if(currentPlayer != playerNumber) {
					serverOut.println("It's currently Player " + currentPlayer + "'s turn. Please wait.");
					continue;
				}

				/*
				 * Need this special case for handling accusations that might be false.
				 * If validateMove() returns false for an accusation, the player is eliminated.
				 * If validateMove() returns true for an accusation, the player wins.
				 */
				if(clientInput.startsWith(Move.ACCUS_STR)) {
					if(game.validateMove(player, clientInput)) {
						logger.info("Player " + player.getCharacterName().getCharacterName() + " has made a correct accusation and wins the game.");
						// Send a game over message to all players
						this.game.broadcastMove(player, "Correction Accusation: " + game.getGameSolution().toString());
						// Kill the server (i.e., game over)
						System.exit(0);
					} else {
						logger.info("Player " + player.getCharacterName().getCharacterName() + " has made a false accusation and loses.");
						// Eliminate the player (i.e., skip all of their future turns)
						TurnEnforcement.eliminatePlayer(playerNumber);
						// Mark that a turn has been made
						TurnEnforcement.turnMade();
					}
				}

				// If we get here, we can process their turn, check it for validity, etc.
				// For now, assume all moves given are valid.
				if(!game.validateMove(player, clientInput)) {
					logger.info("Received invalid move " + clientInput);
					serverOut.println("Sorry, that move is invalid. Please try again.");
					continue;
				}

				// Once a suggestion is made or the client decides to end their turn, the turn is over
				if(clientInput.equals("Done")) {
					// Inform the TurnEnforcement module that a turn has been taken
					TurnEnforcement.turnMade();
				}
				
				if(!clientInput.startsWith(Move.SUGGEST_STR)) {
					game.sendPlayersPrompts(false, null, player);
				}

				logger.info("Received a move from Player " + playerNumber + "!");
				// serverOut.println("Player " + playerNumber + ", your move has been received.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TurnEnforcement.eliminatePlayer(playerNumber);
		System.out.println("Player " + playerNumber + " has disconnected from the game.");
	}

	/**
	 * Helpful getter for determining what prompts to send players.
	 * 
	 * @return the <code>Player</code> object associated with this <code>ConnectionHandler</code>.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Another helpful getter for determining what prompts to send players.
	 * 
	 * @return the player number associated with this <code>ConnectionHandler</code>.
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}
}
