package clueless.gamelogic;

/**
 * A type of <code>Move</code> that involves actually moving positions on the board.
 *
 * @author matthewsobocinski
 */
public class Movement extends Move {
	private Location startLoc = null;
	private Location endLoc = null;
	private static MoveType moveType = Move.MoveType.MOVEMENT;
	
	/**
	 * Constructor.
	 * 
	 * @param startLoc location on the board to move from
	 * @param endLoc location on the board to move to
	 */
	public Movement(Location startLoc, Location endLoc) {
		super(moveType);
		this.startLoc = startLoc;
		this.endLoc = endLoc;
	}

	public Location getStartLoc() {
		return startLoc;
	}

	public Location getEndLoc() {
		return endLoc;
	}

	public void setStartLoc(Location startLoc) {
		this.startLoc = startLoc;
	}

	public void setEndLoc(Location endLoc) {
		this.endLoc = endLoc;
	}
	
	/**
	 * Create the <code>String</code> representation of a <code>Movement</code>.
	 * <p>MV_STARTLOC_ENDLOC</p>
	 */
	@Override
	public String toString() {
		return Move.MOVE_STR + Move.MOVE_SEP + startLoc + Move.MOVE_SEP + endLoc;
	}
}
