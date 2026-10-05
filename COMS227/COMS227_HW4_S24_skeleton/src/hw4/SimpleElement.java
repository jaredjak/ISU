/**
 * Design choice:
 * I figured everything made the most sense setting it up the way I did because...
 * I started with Simple at the "top" of the initial hierarchy because it provides the base for the rest.
 * Next, I extended Moving and Vanishing from Simple because Moving seemed like a good base for a number of the other elements.
 * And vanishing was kind of like the opposite of Moving so I extended it from Simple because I didn't see it being useful for the other elements.
 * I extended Flying to Moving because it is going to be doing some sort of movement.
 * Platform and Lift extend a class I made called AbstractMoving so that I could remove the duplicate code found in both elements.
 * Attached also extends from Moving for the same reason but it's special because I have Follower extending it.
 * This is because Follower is also attached to an element so it makes sense to extend it from Attached.
 */



package hw4;

import api.AbstractElement;
import java.awt.Rectangle;

/**
 * Minimal concrete extension of AbstractElement. The <code>update</code> method
 * in this implementation just increments the frame count.
 * 
 * @author Jared Krug
 */
public class SimpleElement extends AbstractElement {
	
	/**
	 * The x-value of the element
	 */
	private double x;
	
	/**
	 * The x-value of the element
	 */
	private double y;
	
	/**
	 * The width of the element
	 */
	private int width;
	
	/**
	 * The width of the element
	 */
	private int height;
	
	/**
	 * The Rectangle created to represent the element
	 */
	private Rectangle boundingRect;
	
	/**
	 * The number of frames
	 */
	private int frameCount;
	
	/**
	 * Tells if an element is marked or not
	 * True - is marked
	 * False - is not marked
	 */
	private boolean mark;
	
	/**
	 * Constructs a new SimpleElement.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public SimpleElement(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		boundingRect = new Rectangle(getXInt(), getYInt(), width, height);
		frameCount = 0;
		mark = false;
	}
	
	@Override
	public int getXInt() {
		return (int) Math.round(x);
	}
	
	@Override
	public int getYInt() {
		return (int) Math.round(y);
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public java.awt.Rectangle getRect() {
		return boundingRect;
	}
	
	@Override
	public void setPosition(double newX, double newY) {
		x = newX;
		y = newY;
		boundingRect = new Rectangle(getXInt(), getYInt(), width, height);
	}
	
	@Override
	public double getXReal() {
		return x;
	}
	
	@Override
	public double getYReal() {
		return y;
	}
	
	@Override
	public void update() {
		frameCount+= 1;
	}
	
	@Override
	public int getFrameCount() {
		return frameCount;
	}
	
	@Override
	public boolean isMarked() {
		return mark;
	}
	
	@Override
	public void markForDeletion() {
		mark = true;
	}
	
	@Override
	public boolean collides(AbstractElement other) {
		Rectangle rect1 = boundingRect;
		Rectangle rect2 = new Rectangle(other.getXInt(), other.getYInt(), other.getWidth(), other.getHeight());
		
//		if (rect1.x + rect1.width > rect2.x && rect2.x + rect2.width > rect1.x) {
//			if (rect1.y + rect1.height > rect2.y && rect2.y + rect2.height > rect1.y) {
//				return true;
//			}
//		}
		
		if (rect1.intersects(rect2)) {
			return true;
		}
		return false;
	}
}