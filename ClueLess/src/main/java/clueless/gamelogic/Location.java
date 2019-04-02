package clueless.gamelogic;

/**
 * Encapsulate location coordinates. This class will likely end 
 * up with many more fields.
 * 
 * @author matthewsobocinski
 */
public class Location {
	private int x;
	private int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
