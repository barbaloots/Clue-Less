package clueless.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Run by the client to establish a connection to the server at the specified
 * host and port number.
 * 
 * @author matthewsobocinski
 */
public class ClientConnection {
	private static final int PORT_NUMBER = 8888;
	private static final String HOST_NAME = "127.0.0.1";

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		PrintWriter clientOut = null;
		BufferedReader serverIn = null;

		try {
			// Bind to the host and port
			socket = new Socket(HOST_NAME, PORT_NUMBER);
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
			System.out.println("The game is already full and cannot be joined.");
		}
	}
}
