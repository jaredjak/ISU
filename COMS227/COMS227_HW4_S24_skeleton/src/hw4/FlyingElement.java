package hw4;

/**
 * Moving element in which the vertical velocity is adjusted each frame by a
 * gravitational constant to simulate gravity. The element can be set to
 * "grounded", meaning gravity will no longer influence its velocity.
 * 
 * @author Jared Krug
 */
public class FlyingElement extends MovingElement {

	/**
	 * Tells whether the element is grounded or not
	 * True - is grounded
	 * False - is not grounded
	 */
	private boolean isGrounded;
	
	/**
	 * The gravity applied to an element
	 */
	private double gravity;
	
	/**
	 * Constructs a new FlyingElement. By default it should be grounded, meaning
	 * gravity does not influence its velocity.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public FlyingElement(double x, double y, int width, int height) {
		super(x, y, width, height);
		isGrounded = false;
		gravity = 0;
	}
	
	/**
	 * Returns whether the element is grounded or not
	 * @return whether the element is grounded or not
	 */
	public boolean isGrounded() {
		return isGrounded;
	}
	
	/**
	 * Sets the element to be grounded or not grounded based on the given boolean
	 * @param grounded - true or false
	 */
	public void setGrounded(boolean grounded) {
		if (grounded) {
			isGrounded = true;
		} else {
			isGrounded = false;
		}
	}
	
	/**
	 * Sets the gravity on the element
	 * @param gravity - gravity to be applied to an element
	 */
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}
	
	/**
	 * Updates the element by:
	 * - incrementing frameCount
	 * - Setting a new position for the element
	 * - Setting a new velocity if the element is not grounded 
	 */
	@Override
	public void update() {
		super.update();
		if (isGrounded == false) {
			setVelocity(getDeltaX(), getDeltaY() + gravity);
		}
	}
}