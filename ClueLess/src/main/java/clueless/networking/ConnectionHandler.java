package clueless.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import clueless.gamelogic.Card;
import clueless.gamelogic.Game;
import clueless.gamelogic.Player;
import clueless.gamelogic.TurnEnforcement;

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
	private BufferedReader clientIn = null;
	private PrintWriter serverOut = null;

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
		this.initLogger();
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
			serverOut.println("Your hand contains card: " + card.getCardName());
		}
	}

	@Override
	public void run() {
		logger.info("Starting new thread for Player " + playerNumber + "...");
		serverOut.println("Welcome, Player " + playerNumber + "! Your character is " + player.getCharacterName().getCharacterName() + ".");
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

				// Get the number of the player whose turn it currently is
				int currentPlayer = TurnEnforcement.getCurrentPlayer();
				// If it isn't this player's turn, send a message telling them to wait their turn
				if(currentPlayer != playerNumber) {
					serverOut.println("It's currently Player " + currentPlayer + "'s turn. Please wait.");
					continue;
				}
				
				// If we get here, we can process their turn, check it for validity, etc.
				// For now, assume all moves given are valid.
				game.validateMove(player, "AS_PP_LB_LP"); // Accuse Professor Plum (library, lead pipe)
				// Test broadcasting the move to all players
				game.broadcastMove(player, "AS_PP_LB_LP");

				// Inform the TurnEnforcement module that a turn has been taken
				TurnEnforcement.turnMade();

				logger.info("Received a move from Player " + playerNumber + "!");
				serverOut.println("Player " + playerNumber + ", your move has been received.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		TurnEnforcement.eliminatePlayer(playerNumber);
		System.out.println("Player " + playerNumber + " has disconnected from the game.");
	}
}
