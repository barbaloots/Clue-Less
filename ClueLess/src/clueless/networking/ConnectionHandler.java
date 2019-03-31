package clueless.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
		System.out.println("Creating ClientConnectionHandler for " + playerCount);
	}

	@Override
	public void run() {
		System.out.println("Starting thread for a new player!");
		String clientInput = null;
		
		// Listen indefinitely for input from clients
		while(true) {
			try {
				System.out.println("Listening for input from client " + playerNumber + "...");
				// Parse input from the client
				clientInput = clientIn.readLine();
				
				// If null input is received, assume that the player has left the game
				if(clientInput == null) {
					// Exit the infinite loop
					break;
				}
				
				System.out.println(playerNumber + " says " + clientInput);
				serverOut.println("Got your message!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Player " + playerNumber + " has disconnected from the game.");
	}
}
