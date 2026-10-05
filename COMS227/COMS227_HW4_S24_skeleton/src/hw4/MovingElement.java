package hw4;

/**
 * An element in which the <code>update</code> method updates the position each
 * frame according to a <em>velocity</em> vector (deltaX, deltaY). The units are
 * assumed to be "pixels per frame".
 * 
 * @author Jared Krug
 */
public class MovingElement extends SimpleElement {
	
	/**
	 * The change in x
	 */
	private double deltaX;
	
	/**
	 * The change in Y
	 */
	private double deltaY;

	/**
	 * Constructs a MovingElement with a default velocity of zero in both
	 * directions.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public MovingElement(double x, double y, int width, int height) {
		super(x, y, width, height);
		deltaX = 0;
		deltaY = 0;
	}

	/**
	 * Gets the change in X
	 * @return the change in X
	 */
	public double getDeltaX() {
		return deltaX;
	}
	
	/**
	 * Gets the change in Y
	 * @return the change in Y
	 */
	public double getDeltaY() {
		return deltaY;
	}
	
	/**
	 * Sets the velocity of the element to move with
	 * @param deltaX - change in X
	 * @param deltaY - change in Y
	 */
	public void setVelocity(double deltaX, double deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;	
	}
	
	/**
	 * Updates the element by:
	 * - incrementing frameCount
	 * - Setting a new position for the element
	 */
	@Override
	public void update() {
		super.update();
		setPosition(getXReal() + deltaX, getYReal() + deltaY);
	}

}
