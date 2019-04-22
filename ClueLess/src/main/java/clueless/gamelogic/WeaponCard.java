package main.java.clueless.gamelogic;

/**
 * Class for the weapon type of card
 * 
 * @author matthewsobocinski
 */
public class WeaponCard extends Card {
	private static final CardType cardType = CardType.WEAPON;
	
	/**
	 * Constructor 
	 * 
	 * @param cardName
	 * 		the type of card
	 */
	public WeaponCard(String cardName) {
		super(cardType, cardName);
	}
}
