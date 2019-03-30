package clueless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Accept and handle connections from clients. This will be fleshed out
 * in later iterations, but for the skeletal increment, it's 
 * probably sufficient to just show that we can accept connections and
 * send and receive messages. This class must be running before any ClientConnections
 * can connect to it.
 * 
 * TODO: Modify so new threads are spun off to handle each individual client received.
 * TODO: Add logic to enforce 3 <= num clients <= 6.
 * 
 * @author matthewsobocinski
 */
public class ServerConnection {
	// Port number at which communication will be made
	private static final int PORT_NUMBER = 8888;

	public static void main(String[] args) throws IOException {
		String clientInput = null;
		Socket clientSocket = null;
		PrintWriter serverOut = null;
		ServerSocket serverSocket = null;
		BufferedReader clientIn = null;
		
		try {
			// Bind to the port number
			serverSocket = new ServerSocket(PORT_NUMBER);
			// Accept a client connection
			clientSocket = serverSocket.accept();     
			// Instantiate a writer for writing back to the client
			serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
			// Instantiate a reader for reading messages from the client
			clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// Parse and print input from the client
			while ((clientInput = clientIn.readLine()) != null) {
				System.out.println("Client says: " + clientInput);
				serverOut.println("Hello from the server");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			serverSocket.close();
		}
	}
}
