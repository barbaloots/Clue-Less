package main.java.clueless.gamelogic;

/**
 * Class for the room type of card
 * 
 * @author matthewsobocinski
 */
public class RoomCard extends Card {
	private static final CardType cardType = CardType.ROOM;
	
	/**
	 * Constructor 
	 * 
	 * @param roomName
	 * 		the roomName to set
	 */
	public RoomCard(String roomName) {
		super(cardType, roomName);
	}
}
