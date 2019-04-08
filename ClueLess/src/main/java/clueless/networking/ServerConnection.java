package clueless.networking;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import clueless.gamelogic.Game;
import clueless.gamelogic.TurnEnforcement;

/**
 * Start the server and accept connections from clients.
 * 
 * @author matthewsobocinski
 */
public class ServerConnection {
	// Port number at which communication will be made
	private static int PORT_NUMBER;
	// Note: this is just a placeholder for the skeletal increment
	private static int NUM_PLAYERS;
	// Properties object to hold system configuration
	private static Properties props = null;
	// Path to properties file
	private static final String propsPath = "resources/clueless.properties";
	// String constants for keys in properties file
	private static final String PORT_NUMBER_KEY = "portNumber";
	private static final String NUM_PLAYERS_KEY = "numPlayers";

	public static void main(String[] args) throws IOException {
		// Count players as they join the game
		int playerCount = 0;
		// Only need a single ServerSocket object, but a new ConnectionHandler is created for each player that joins
		ServerSocket serverSocket = null;
		
		// Set the global properties
		setProperties();
		
		try {
			// Bind to the port number
			serverSocket = new ServerSocket(PORT_NUMBER);
			System.out.println("Server running...");
			System.out.println("Waiting for " + NUM_PLAYERS + " players to connect...");
			
			// Create a new game for all players to join
			Game game = new Game();
			
			// Accept connections until the specified number of players have joined
			while(playerCount < NUM_PLAYERS) {
				// Accept a client connection
				Socket clientSocket = serverSocket.accept();
				// Instantiate a connection handler to interact with this client using a separate thread
				Runnable connectionHandler = new ConnectionHandler(clientSocket, ++playerCount, game);
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

	/**
	 * Helper method to assign variables based on values from properties file.
	 */
	private static void setProperties() {
		props = new Properties();          
		try {
			props.load(new FileInputStream(propsPath));
			PORT_NUMBER = Integer.parseInt(props.getProperty(PORT_NUMBER_KEY));
			NUM_PLAYERS = Integer.parseInt(props.getProperty(NUM_PLAYERS_KEY));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
