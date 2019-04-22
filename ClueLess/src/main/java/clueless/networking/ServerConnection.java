package main.java.clueless.networking;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import main.java.clueless.gamelogic.Game;
import main.java.clueless.gamelogic.TurnEnforcement;

/**
 * Start the server and accept connections from clients.
 * 
 * @author matthewsobocinski
 */
public class ServerConnection {
	private static final Logger logger = Logger.getLogger(ServerConnection.class);
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
		// Configure log4j
		DOMConfigurator.configure("log4jserver.xml");
		// Count players as they join the game
		int playerCount = 0;
		// Only need a single ServerSocket object, but a new ConnectionHandler is created for each player that joins
		ServerSocket serverSocket = null;
		// Store all ConnectionHandler objects for the purposes of broadcasting messages
		ArrayList<ConnectionHandler> connections = new ArrayList<ConnectionHandler>();
		// Set the global properties
		setProperties();
		
		try {
			// Bind to the port number
			serverSocket = new ServerSocket(PORT_NUMBER);
			logger.info("Server running...");
			logger.info("Waiting for " + NUM_PLAYERS + " players to connect...");
			
			// Create a new game for all players to join
			Game game = new Game(NUM_PLAYERS);
			
			// Accept connections until the specified number of players have joined
			while(playerCount < NUM_PLAYERS) {
				// Accept a client connection
				Socket clientSocket = serverSocket.accept();
				// Instantiate a connection handler to interact with this client using a separate thread
				Runnable connectionHandler = new ConnectionHandler(clientSocket, ++playerCount, game.getPlayers().get(playerCount-1), game);
				// Store the ConnectionHandler instance
				connections.add((ConnectionHandler) connectionHandler);
				new Thread(connectionHandler).start();
			}
			
			System.out.println(NUM_PLAYERS + " have joined the game. Initializing game.");
			// Initialize the TurnEnforcement module
			TurnEnforcement.initializePlayerArray(NUM_PLAYERS);
			// Make sure the Game instance has the ability to broadcast messages/send board updates
			game.setConnections(connections);
			// Send the initial prompts out to players (first player in ArrayList starts)
			game.sendPlayersPrompts(false, null, game.getPlayers().get(0));
		} catch (IOException e) {
			logger.error(e.getMessage());
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
