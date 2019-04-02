package clueless.gamelogic;

/**
 * Class for the character card types
 * 
 * @author matthewsobocinski
 */
public class CharacterCard extends Card {
	private static final CardType CARDTYPE = CardType.CHARACTER;
	
	/**
	 * Constructor
	 * 
	 * @param cardName
	 * 		the name of the card 
	 */
	public CharacterCard(String cardName) {
		super(CARDTYPE, cardName);
	}
}
