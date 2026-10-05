package hw4;

import api.AbstractElement;

/**
 * An attached element is one that is associated with another "base" element
 * such as a PlatformElement or a LiftElement. Specifically, the attached
 * element's movement is determined by the movement of the base element, the
 * element always remains a fixed distance away.
 * 
 * @author Jared Krug
 */
public class AttachedElement extends MovingElement {

	/**
	 * The offset of the AttachedElement compared to the parent element
	 */
	private int offset;
	
	/**
	 * The amount that the AttachElement is hovering compared to the parent element
	 */
	private int hover;
	
	/**
	 * The AbstractElement representing this current instance that is attached to the parent element
	 */
	private AbstractElement attachedTo;
	
	/**
	 * The AbstractElement representing the parent element
	 */
	private AbstractElement parentElement;
	
	/**
	 * Constructs a new AttachedElement. Before being added to an associated "base"
	 * element such as a PlatformElement or LiftElement, the x and y coordinates are
	 * initialized to zero. When the base object is set (not in this constructor),
	 * the x-coordinate will be calculated as the base object's x-coordinate, plus
	 * the given offset, and the y-coordinate will become the base object's
	 * y-coordinate, minus this element's height, minus the hover amount.
	 * 
	 * @param width  element's width
	 * @param height element's height
	 * @param offset when added to a base object, this amount will be added to the
	 *               other object's x-coordinate to calculate this element's
	 *               x-coordinate
	 * @param hover  when added to a base object, this element's y-coordinate is the
	 *               other object's y-coordinate, minus this element's height, minus
	 *               the hover amount
	 */
	public AttachedElement(int width, int height, int offset, int hover) {
		super(0, 0, width, height);
		
		parentElement = null;
		attachedTo = this;
		
		this.offset = offset;
		this.hover = hover;
		
	}
	
	/**
	 * Sets the base of the attached element to the parent element
	 * @param b - the parent element being attached to
	 */
	public void setBase(AbstractElement b) {
		parentElement = b;
	
		attachedTo.setPosition(parentElement.getXReal() + offset, parentElement.getYReal() - getHeight() - hover);
	}
	
	/**
	 * Updates the element by:
	 * - incrementing frameCount
	 * - Setting a new position for the element
	 * - Ensures that the element moves as if it is attached to the parent element
	 */
	@Override
	public void update() {
		super.update();
		
		if (parentElement != null) {
			attachedTo.setPosition(parentElement.getXReal() + offset, parentElement.getYReal() - getHeight() - hover);
		}
	}
}