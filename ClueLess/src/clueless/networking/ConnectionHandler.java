package clueless.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import clueless.gamelogic.TurnEnforcement;

/**
 * Handle each connection made to the server with a different thread.
 * 
 * @author matthewsobocinski
 */
public class ConnectionHandler implements Runnable {
	private int playerNumber;
	private BufferedReader clientIn = null;
	private PrintWriter serverOut = null;
	
	/**
	 * Instantiate a ConnectionHandler for maintaining the individual connections made by each client.
	 * 
	 * @param clientSocket the socket by which communication is performed between client and server
	 * @param playerCount the number of the player to join the game
	 * @throws IOException
	 */
	public ConnectionHandler(Socket clientSocket, int playerCount) throws IOException {
		// Instantiate a writer for writing back to the client
		this.serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
		// Instantiate a reader for reading messages from the client
		this.clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		// Save the player number
		this.playerNumber = playerCount;
		System.out.println("Creating ClientConnectionHandler for Player " + playerCount + "...");
	}

	@Override
	public void run() {
		System.out.println("Starting new thread for Player " + playerNumber + "...");
		serverOut.println("Welcome, Player " + playerNumber + "!");
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
				
				// Inform the TurnEnforcement module that a turn has been taken
				TurnEnforcement.turnMade();
				
				System.out.println("Received a move from Player " + playerNumber + "!");
				serverOut.println("Player " + playerNumber + ", your move has been received.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Player " + playerNumber + " has disconnected from the game.");
	}
}
