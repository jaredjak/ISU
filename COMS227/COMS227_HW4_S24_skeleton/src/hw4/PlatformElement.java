package hw4;

/**
 * A PlatformElement is an element with two distinctive behaviors. First, it can
 * be set up to move horizontally within a fixed set of boundaries. On reaching
 * a boundary, the x-component of its velocity is reversed. Second, it maintains
 * a list of <em>associated</em> elements whose basic motion all occurs relative
 * to the PlatformElement.
 * 
 * @author Jared Krug
 */
public class PlatformElement extends AbstractMoving {

	/**
	 * Constructs a new PlatformElement. Initially the left and right boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public PlatformElement(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	 /**
	 * Updates the element by:
	 * - incrementing frameCount
	 * - Setting a new position for the element
	 * - Ensuring that the element remains within it's bounds and updates all associated elements
	 */
	@Override
	public void update() {
		super.update();
		
		if (getXReal() + getWidth() >= getMax()) {
			setPosition(getMax() - getWidth(), getYReal());
			setVelocity(-1 * getDeltaX(), getDeltaY());
			
		} else if (getXReal() <= getMin()) {
			setPosition(getMin(), getYReal());
			setVelocity(-1 * getDeltaX(), getDeltaY());
		}

		for (int i = 0; i <= getAssociated().size() - 1; i++) {
			getAssociated().get(i).update();
		}
	}
}