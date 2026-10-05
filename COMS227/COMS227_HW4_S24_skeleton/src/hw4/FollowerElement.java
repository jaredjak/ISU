package hw4;

import api.AbstractElement;

/**
 * A follower element is one that is associated with another "base" element such
 * as a PlatformElement or LiftElement. Specifically, the follower element's
 * movement is determined by the movement of the base element, when the base
 * move up 10 pixels, the follower moves up 10 pixels. However, the follower may
 * not always be at a fixed location relative to the base. When the horizontal
 * velocity of the follower is set to a non-zero value, the follower will
 * oscillate between the left and right edges of the PlatformElement or
 * LiftElement it is associated with.
 * 
 * @author Jared Krug
 */
public class FollowerElement extends AttachedElement {

	/**
	 * The max bound on the x-axis
	 */
	private double max;
	
	/**
	 * The min bound on the x-axis
	 */
	private double min;
	
	/**
	 * Represents the max value before the current max
	 */
	private double prevMax;
	
	/**
	 * Represents the min value before the current min
	 */
	private double prevMin;
	
	/**
	 * Represents the new Y (used for parent element)
	 */
	private double newY;
	
	/**
	 * Represents the previous Y (used for prent element)
	 */
	private double prevY;
	
	/**
	 * The offset of the FollowerElement compared to the parent element
	 */
	private int offset;
	
	/**
	 * The AbstractElement representing this current instance that is "following" the parent element
	 */
	private AbstractElement follower;
	
	/**
	 * The AbstractElement representing the parent element
	 */
	private AbstractElement parentElement;
	
	/**
	 * Constructs a new FollowerElement. Before being added to a "base" element such
	 * as a PlatformElement or LiftElement, the x and y coordinates are zero. When a
	 * base element is set, the initial x-coordinate becomes the base's
	 * x-coordinate, plus the given offset, and the y-coordinate becomes the base's
	 * y-coordinate, minus this element's height.
	 * 
	 * @param width         element's width
	 * @param height        element's height
	 * @param initialOffset when added to a base, this amount will be added to the
	 *                      bases's x-coordinate to calculate this element's initial
	 *                      x-coordinate
	 */
	public FollowerElement(int width, int height, int initialOffset) {
		super(width, height, initialOffset, 0);
		
		this.offset = initialOffset;
		
		follower = this;
		parentElement = null;
		
		max = 0;
		min = 0;
		
		prevMax = 0;
		prevMin = 0;
		
		newY = 0;
		prevY = 0;
		
	}

	/**
	 * Gets the max bound of the element
	 * @return the max bound
	 */
	public double getMax() {
		return max;
	}
	
	/**
	 * Gets the min bound of the element
	 * @return the min bound
	 */
	public double getMin() {
		return min;
	}
	
	/**
	 * Sets the bounds of the FollowerElement based on how the parentElement moves
	 * @param min - the new min bound
	 * @param max - the new max bound
	 */
	public void setBounds(double min, double max) {
		prevMax = this.max;
		prevMin = this.min;
		
		
		this.max = max;
		this.min = min;
	}
	
	/**
	 * Sets the base of the follower element relative to the parent element
	 * Also sets the values for two variables: prevY and newY
	 * @param b - the parent element being followed
	 */
	@Override
	public void setBase(AbstractElement b) {
		parentElement = b;

		newY = parentElement.getYReal();
		prevY = parentElement.getYReal();
		
		follower.setPosition(parentElement.getXReal() + offset, parentElement.getYReal() - getHeight());
		
		setBounds(parentElement.getXReal(), parentElement.getXReal() + parentElement.getWidth());
	}
	
	/**
	 * Updates the element by:
	 * - incrementing frameCount
	 * - Setting a new position for the element
	 * - Ensures that the element moves as if it is attached to the parent element
	 * - Resets the bounds to be updated based on the parent elements movement and updates position
	 */
	@Override
	public void update() {
		super.update();
		
		setBounds(parentElement.getXReal(), parentElement.getXReal() + parentElement.getWidth());
		
		if (prevMax != max && prevMin != min) {
			follower.setPosition(follower.getXReal() + (min - prevMin), follower.getYReal());
		}
		
		prevY = newY;
		newY = parentElement.getYReal();
		
		if (parentElement != null) {
			follower.setPosition(follower.getXReal(), follower.getYReal() + (newY - prevY));
		}
		
		if (getXReal() + getWidth() >= max) {
			setPosition(max - getWidth(), getYReal());
			setVelocity(-1 * getDeltaX(), getDeltaY());
		} else if (getXReal() <= min) {
			setPosition(min, getYReal());
			setVelocity(-1 * getDeltaX(), getDeltaY());
		}
	}
}