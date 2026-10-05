package hw4;

/**
 * An element that does not move. Instead, it is intended to appear on the
 * screen for a fixed number of frames.
 * 
 * @author Jared Krug
 */
public class VanishingElement extends SimpleElement {
	
	/**
	 * The life-span of the element
	 */
	private int life;
	
	/**
	 * Constructs a new VanishingElement.
	 * 
	 * @param x           x-coordinate of upper left corner
	 * @param y           y-coordinate of upper left corner
	 * @param width       element's width
	 * @param height      element's height
	 * @param initialLife the number of frames until this element marks itself for
	 *                    deletion
	 */
	public VanishingElement(double x, double y, int width, int height, int initialLife) {
		super(x, y, width, height);
		this.life = initialLife;
	}

	/**
	 * Updates the element by:
	 * - incrementing frameCount
	 * - Marks the element for deletion if frameCount exceeds the elements lifespan
	 */
	@Override
	public void update() {
		super.update();
		
		if (getFrameCount() >= life) {
			markForDeletion();
		}
	}
}