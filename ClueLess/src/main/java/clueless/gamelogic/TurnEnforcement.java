package clueless.gamelogic;

/**
 * Enforce player turn order using a primitive array and a counter.
 * The integer -1, stored in ELIMINATED_FLAG, is used to mark positions
 * of players who have been eliminated from the game. This ensures that
 * their turns are skipped.
 * 
 * NOTE: This has not been made completely thread-safe yet.
 * 
 * IMPORTANT: Player numbers START AT 1.
 * 		At index 0, store player 1. 
 * 		At index 1, store player 2, and so on. 
 * 
 * @author matthewsobocinski
 */
public class TurnEnforcement {
	private static boolean gameHasStarted = false;
	private static int numberOfPlayers = 0;
	private static int currentTurn = 0;
	private static final int ELIMINATED_FLAG = -1;
	private static int[] playerArr = null;
	
	/**
	 * Store each player number in a primitive array.
	 * 
	 * @param numPlayers the number of players who have joined the game
	 */
	public static synchronized void initializePlayerArray(int numPlayers) {
		numberOfPlayers = numPlayers;
		playerArr = new int[numPlayers];
		for(int i = 0; i < numPlayers; i++) {
			playerArr[i] = i+1;
		}
		gameHasStarted = true;
	}
	
	/**
	 * Increment the counter to point to the next player, resetting
	 * it if necessary.
	 */
	public static synchronized void turnMade() {
		currentTurn++;
		if(currentTurn == numberOfPlayers) {
			currentTurn = 0;
		}
	}
	
	/**
	 * Get the current player from whom input should be expected by the server.
	 * 
	 * @return the player number whose turn it currently is
	 */
	public static synchronized int getCurrentPlayer() {
		int playerNum =  playerArr[currentTurn];
		while(playerNum == ELIMINATED_FLAG) {
			// Skip players who have been eliminated
			turnMade();
			// Check the next player
			playerNum = playerArr[currentTurn];
		}
		return playerNum;
	}
	
	/**
	 * Mark this particular player's entry with ELIMINATED_FLAG to ensure
	 * their turn is skipped.
	 * 
	 * @param playerNum number of the player eliminated from the game
	 */
	public static synchronized void eliminatePlayer(int playerNum) {
		System.out.println("Player " + playerNum + " has been eliminated.");
		playerArr[playerNum-1] = ELIMINATED_FLAG;
	}
	
	/**
	 * Used to ensure moves aren't attempted until the game has been initialized.
	 * 
	 * @return whether the game has been started
	 */
	public static synchronized boolean gameHasStarted() {
		return gameHasStarted;
	}
}
