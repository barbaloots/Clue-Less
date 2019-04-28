package clueless.gamelogic;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Enforce player turn order using a primitive array and a counter.
 * The integer -1, stored in ELIMINATED_FLAG, is used to mark positions
 * of players who have been eliminated from the game. This ensures that
 * their turns are skipped.
 * 
 * IMPORTANT: Player numbers START AT 1.
 * 		At index 0, store player 1. 
 * 		At index 1, store player 2, and so on. 
 * 
 * @author matthewsobocinski
 */
public class TurnEnforcement {
	private static final Logger logger = Logger.getLogger(TurnEnforcement.class);
	private static boolean gameHasStarted = false;
	private static boolean disproveSuggestionMode = false;
	private static int numberOfPlayers = 0;
	private static int currentTurn = 0;
	private static final int ELIMINATED_FLAG = -1;
	private static int[] playerArr = null;
	private static ArrayList<String> playersToDisprove = null;
	private static int indexExpectedToDisproveNext = 0;
	private static Game game = null;
	private static Player player = null;

	/**
	 * Store each player number in a primitive array.
	 * 
	 * @param numPlayers the number of players who have joined the game
	 */
	public static synchronized void initializePlayerArray(int numPlayers) {
		// Initialize logging
		DOMConfigurator.configure("log4jserver.xml");
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
		logger.info("A turn has been made.");
		currentTurn++;
		if(currentTurn == numberOfPlayers) {
			currentTurn = 0;
		}
		logger.info("It's currently Player " + playerArr[currentTurn] + "'s turn.");
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

	/**
	 * Used to enforce the logic of disproving suggestions.
	 * 
	 * @return whether the game is in "disprove suggestion" mode
	 */
	public static synchronized boolean isInDisproveSuggestionMode() {
		return disproveSuggestionMode;
	}

	/**
	 * Turn on "disprove suggestion" mode.
	 */
	public static synchronized void turnOnDisproveSuggestionMode(Game gameObj, Player playerObj) {
		game = gameObj;
		player = playerObj;
		disproveSuggestionMode = true;
	}

	/**
	 * Turn off "disprove suggestion" mode.
	 */
	public static synchronized void turnOffDisproveSuggestionMode() {
		disproveSuggestionMode = false;
	}

	/**
	 * Set the <code>ArrayList</code> of player characters who may need to disprove a suggestion that was made.
	 */
	public static synchronized void setPlayersToDisprove(ArrayList<String> players) {
		playersToDisprove = players;
		indexExpectedToDisproveNext = 0;
		// Tell the next player eligible to disprove that it's their turn to do so
		String playerExpectedToDisproveNext = playersToDisprove.get(indexExpectedToDisproveNext);
		game.tellPlayerToDisprove(playerExpectedToDisproveNext, "Please attempt to disprove the suggestion with syntax 'DS_<Card>'");
	}

	/**
	 * Tell the TurnEnforcement class that a player was unable to make a suggestion.
	 */
	public static synchronized void disproveSuggestionFailed() {
		indexExpectedToDisproveNext++;
		// If none of the players succeeded in disproving the suggestion, turn of "disprove suggestion" mode
		if(indexExpectedToDisproveNext == playersToDisprove.size()) {
			turnOffDisproveSuggestionMode();
			String msg = "No opponent was able to disprove your suggestion. Either make an accusation or end your turn.";
			game.sendSpecificPlayerMsg(player, msg);
		} else {
			// Tell the next player eligible to disprove that it's their turn to do so
			String playerExpectedToDisproveNext = playersToDisprove.get(indexExpectedToDisproveNext);
			game.tellPlayerToDisprove(playerExpectedToDisproveNext, "Please attempt to disprove the suggestion with syntax 'DS_<Card>'");
		}
	}

	/**
	 * Get the character/player who is expected to disprove a suggestion next.
	 * 
	 * @return the name of the character/player from whom a suggestion is expected next.
	 */
	public static synchronized String getPlayerFromWhomDisproveSuggestionIsExpected() {
		if(playersToDisprove != null) {
			if(indexExpectedToDisproveNext >= 0 && indexExpectedToDisproveNext < playersToDisprove.size()) {
				return playersToDisprove.get(indexExpectedToDisproveNext);
			}
		}
		return null;
	}
}
