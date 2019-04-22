package main.java.clueless.gamelogic;

/**
 * Encapsulate the fields for disproving a suggestion.
 *
 * @author matthewsobocinski
 */
public class DisproveSuggestion extends Move {
	private String card;
	private static MoveType moveType = Move.MoveType.DISPROVE_SUGGESTION;
	
	/**
	 * Constructor.
	 * 
	 * @param card the card used to disprove a suggestion
	 */
	public DisproveSuggestion(String card) {
		super(moveType);
		this.card = card;
	}

	/**
	 * Create the <code>String</code> representation of a <code>DisproveSuggestion</code>.
	 * <p>DS_CARD</p>
	 */
	@Override
	public String toString() {
		return Move.DISPROVE_SUGGEST + Move.MOVE_SEP + card;
	}
	
}
