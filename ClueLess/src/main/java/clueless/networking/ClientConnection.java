package clueless.networking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

/**
 * Run by the client to establish a connection to the server at the specified
 * host and port number.
 * 
 * @author matthewsobocinski
 */
public class ClientConnection {
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

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
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

			// Print the acknowledgement from the server
			System.out.println(serverIn.readLine());

			Scanner input = new Scanner(System.in);
			// Continually accept input
			while (input.hasNext()) {	
				// Send the client input to the server
				clientOut.println(input.nextLine());
				// Accept a message from the server
				System.out.println(serverIn.readLine());
			}
		} catch (ConnectException e) {
			System.out.println("Either the server is not running or the game is already full and cannot be joined.");
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
