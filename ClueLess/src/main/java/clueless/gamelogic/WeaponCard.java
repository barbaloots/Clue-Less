package clueless.gamelogic;

/**
 * 
 * @author matthewsobocinski
 */
public class WeaponCard extends Card {
	private static final CardType cardType = CardType.WEAPON;
	
	public WeaponCard(String cardName) {
		super(cardType, cardName);
	}
}
