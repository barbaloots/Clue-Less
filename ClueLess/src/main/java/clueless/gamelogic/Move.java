package clueless.gamelogic;

import org.apache.log4j.Logger;

/**
 * Encapsulate the information necessary to represent a move. This includes logic
 * for mapping from string representations to logic in code. 
 *
 * @author matthewsobocinski
 */
public abstract class Move {
	Logger logger = Logger.getLogger(Move.class);
	
	// Instance variables
	private MoveType moveType = null;
	
	// Constants/static variables
	// Used to indicate that player wants to make an accusation
	public static final String ACCUS_STR = "AA";
	// Used to indicate that player wants to make a move
	public static final String MOVE_STR = "MV";
	// Used to indicate that player wants to make a suggestion
	public static final String SUGGEST_STR = "AS";
	// Used to disprove a suggestion
	public static final String DISPROVE_SUGGEST = "DS";
	// Used to separate the components of a move string
	public static final String MOVE_SEP = "_";
	
	// Enum for selection of move type
	public static enum MoveType { ACCUSATION, SUGGESTION, DISPROVE_SUGGESTION, MOVEMENT };
	
	/**
	 * Constructor.
	 * 
	 * @param moveType the type of move (e.g., accusation, suggestion, movement, etc.)
	 */
	public Move(MoveType moveType) {
		this.moveType = moveType;
	}
	
	public MoveType getMoveType() {
		return moveType;
	}
}
