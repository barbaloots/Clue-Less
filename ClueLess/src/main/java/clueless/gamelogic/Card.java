package clueless.gamelogic;

/**
 * 
 * @author matthewsobocinski
 */
public abstract class Card {
	private CardType cardType;
	private String cardName;

	public Card(CardType cardType, String cardName) {
		this.cardType = cardType;
		this.cardName = cardName;
	}

	public CardType getCardType() {
		return cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	@Override
	public String toString() {
		return "Card [cardType=" + cardType + ", cardName=" + cardName + "]";
	}
}
