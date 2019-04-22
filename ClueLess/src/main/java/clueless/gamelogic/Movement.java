package main.java.clueless.gamelogic;

/**
 * A type of <code>Move</code> that involves actually moving positions on the board.
 *
 * @author matthewsobocinski
 */
public class Movement extends Move {
	private Location endLoc = null;
	private static MoveType moveType = Move.MoveType.MOVEMENT;
	
	/**
	 * Constructor.
	 * 
	 * @param endLoc location on the board to move to
	 */
	public Movement(Location endLoc) {
		super(moveType);
		this.endLoc = endLoc;
	}

	public Location getEndLoc() {
		return endLoc;
	}

	public void setEndLoc(Location endLoc) {
		this.endLoc = endLoc;
	}
	
	/**
	 * Create the <code>String</code> representation of a <code>Movement</code>.
	 * <p>MV_ENDLOC</p>
	 */
	@Override
	public String toString() {
		return Move.MOVE_STR + Move.MOVE_SEP + endLoc;
	}
}
