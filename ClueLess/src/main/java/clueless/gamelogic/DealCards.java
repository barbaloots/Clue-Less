package clueless.gamelogic;

/**
 * Class to deal the cards out to the players
 * 
 * @author erinsmedley
 *
 */
public class DealCards {
	// There are 21 total cards, but 3 of the are part of the game solution
	private static final int CARD_NO = 18;

	/**
	 * Constructor
	 * 
	 * @param players
	 * 		the number of players in the game
	 */
	public DealCards(int players) {
		GameSolution gameSolution = new GameSolution();
		CharacterName solutionChar = gameSolution.getCharacterName();
		WeaponType solutionWeapon = gameSolution.getWeaponType();
		RoomName solutionRoom = gameSolution.getRoomName();
		
		// deal the remaining cards out to the players
	}
}
