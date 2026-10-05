package hw4;

/**
 * An element with two distinctive behaviors. First, it can be set up to move
 * vertically within a fixed set of boundaries. On reaching a boundary, the
 * y-component of its velocity is reversed. Second, it maintains a list of
 * <em>associated</em> elements whose basic motion all occurs relative to the
 * LiftElement.
 * 
 * @author Jared Krug
 */
public class LiftElement extends AbstractMoving {

	/**
	 * Constructs a new Elevator. Initially the upper and lower boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public LiftElement(double x, double y, int width, int height) {
		super(x,y,width,height);
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
		
		if (getYReal() + getHeight() >= getMax()) {
			setPosition(getXReal(), getMax() - getHeight());
			setVelocity(getDeltaX(), -1 * getDeltaY());
			
		} else if (getYReal() <= getMin()) {
			setPosition(getXReal(), getMin());
			setVelocity(getDeltaX(), -1 * getDeltaY());
		}

		for (int i = 0; i <= getAssociated().size() - 1; i++) {
			getAssociated().get(i).update();
		}
	}
}