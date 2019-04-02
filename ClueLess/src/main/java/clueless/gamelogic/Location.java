package clueless.gamelogic;

import java.util.ArrayList;

/**
 * Encapsulate location coordinates. This class will likely end 
 * up with many more fields.
 * 
 * @author matthewsobocinski
 */
public class Location {
	private int x;
	private int y;
	private RoomName room;

	/**
	 * Constructor
	 * 
	 * @param x
	 * 		x-coordinate on the board
	 * @param y
	 * 		y-coordinate on the board
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor
	 * 
	 * @param room
	 * 		the room to be located on the board
	 * @param x
	 * 		the x-coordinate on the board
	 * @param y
	 * 		the y-coordinate on the board
	 */
	public Location(RoomName room, int x, int y) {
		this.room = room;
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
}
