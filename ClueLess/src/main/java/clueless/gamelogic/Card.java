package main.java.clueless.gamelogic;

/**
 * Abstract class for the game Cards
 * 
 * @author matthewsobocinski
 */
public abstract class Card {
	private CardType cardType;
	private String cardName;

	/**
	 * Constructor
	 * 
	 * @param cardType
	 * 		type of Card
	 * @param cardName
	 * 		name of the Card
	 */
	public Card(CardType cardType, String cardName) {
		this.cardType = cardType;
		this.cardName = cardName;
	}

	/**
	 * Gets the cardType
	 * 
	 * @return the cardType
	 */
	public CardType getCardType() {
		return cardType;
	}

	/**
	 * Get the cardName
	 * 
	 * @return the cardName
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * Sets the cardType
	 * 
	 * @param cardType
	 * 		the cardType to set
	 */
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * Sets the cardName
	 * 
	 * @param cardName
	 * 		the cardName to set
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/**
	 * Converts the card information to a String
	 * 
	 * @return String representation of the Card
	 */
	@Override
	public String toString() {
		return "Card [cardType=" + cardType + ", cardName=" + cardName + "]";
	}
}
