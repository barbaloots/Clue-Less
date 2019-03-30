package clueless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Purely to demonstrate basic communication
 * between client(s) and the server. We'll still need a way 
 * to associate this with each client. Probably want to object-orient
 * it a little so the reader and writer are instance variables (after
 * skeletal increment).
 * 
 * TODO: Modify this class so it can be used as an instance variable of each client
 * 
 * @author matthewsobocinski
 */
public class ClientConnection {
	private static final int PORT_NUMBER = 8888;
	private static final String HOST_NAME = "127.0.0.1";
	
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

			// Send a test message to the server
			clientOut.println("Hello from the client");
			// Accept a message from the server
			System.out.println("Server says: " + serverIn.readLine());
		} catch (Exception e) {
			System.err.println("Client error: " + e.toString());
		}
	}
}
