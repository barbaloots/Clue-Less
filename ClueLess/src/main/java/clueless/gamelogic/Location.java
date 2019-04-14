package clueless.gamelogic;

/**
 * Encapsulate location coordinates.
 * 
 * @author matthewsobocinski
 */
public class Location {
	private int x;
	private int y;

	/**
	 * @param x
	 * 		the x-coordinate on the board
	 * @param y
	 * 		the y-coordinate on the board
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the x-coordinate
	 * 
	 * @return the x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y-coordinate
	 * 
	 * @return the y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the x-coordinate
	 * 
	 * @param x
	 * 		the x-coordinate to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the y-coordinate
	 * 
	 * @param y
	 * 		the y-coordinate to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
}