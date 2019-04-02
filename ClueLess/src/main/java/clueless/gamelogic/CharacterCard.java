package clueless.gamelogic;

/**
 * 
 * @author matthewsobocinski
 */
public class CharacterCard extends Card {
	private static final CardType cardType = CardType.CHARACTER;
	
	public CharacterCard(String cardName) {
		super(cardType, cardName);
	}
}
