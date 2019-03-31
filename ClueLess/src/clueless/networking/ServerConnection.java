package clueless.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import clueless.gamelogic.TurnEnforcement;

/**
 * Start the server and accept connections from clients.
 * 
 * @author matthewsobocinski
 */
public class ServerConnection {
	// Port number at which communication will be made
	private static final int PORT_NUMBER = 8888;
	// Note: this is just a placeholder for the skeletal increment
	private static final int NUM_PLAYERS = 3;

	public static void main(String[] args) throws IOException {
		int playerCount = 0;
		ServerSocket serverSocket = null;
		
		try {
			// Bind to the port number
			serverSocket = new ServerSocket(PORT_NUMBER);
			System.out.println("Server running...");
			System.out.println("Waiting for " + NUM_PLAYERS + " players to connect...");
			
			// Accept connections until the specified number of players have joined
			while(playerCount < NUM_PLAYERS) {
				// Accept a client connection
				Socket clientSocket = serverSocket.accept();
				// Instantiate a connection handler to interact with this client using a separate thread
				Runnable connectionHandler = new ConnectionHandler(clientSocket, ++playerCount);
				new Thread(connectionHandler).start();
			}
			
			System.out.println(NUM_PLAYERS + " have joined the game. Initializing game.");
			TurnEnforcement.initializePlayerArray(NUM_PLAYERS);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			serverSocket.close();
		}
	}
}
