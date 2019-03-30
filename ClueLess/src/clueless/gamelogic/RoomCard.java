package clueless.gamelogic;

/**
 * 
 * @author matthewsobocinski
 */
public class RoomCard extends Card {
	private static final CardType cardType = CardType.ROOM;
	
	public RoomCard(String roomName) {
		super(cardType, roomName);
	}
}
