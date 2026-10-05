package objectTest;

/**
 * This class represents a rectangle. It has a width and height and can change it's size
 * @author Jared Krug
 */
public class Rectangle {
	/**
	 * this is the width of the rectangle.
	 */
	double width;
	/**
	 * this is the height of the rectangle.
	 */
	double height;
	
	/**
	 * this constructor creates a new rectangle with a specific width and height.
	 * @param width. The width of the rectangle.
	 * @param height. The height of the rectangle.
	 */
	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * This constructor creates a new rectangle with default values of 0/0 for width and height.
	 */
	public Rectangle() {
		this.width = 0;
		this.height = 0;
	}
	
	/**
	 * This method will grow the rectangle by a given width and height.
	 * @param width. The amount to grow the width.
	 * @param height. The amount to grow the height.
	 */
	public void growRectangle(double width, double height) {
		this.width += width;
		this.height += height;
	}
	
	/**
	 * This method multiplies the width and height to return the rectangle's area.
	 * @return width * height = the area of the rectangle.
	 */
	public double getArea() {
		return width * height;
	}

	/**
	 * GetWidth returns the current width of the rectangle.
	 * @return the width of the rectangle.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Sets the width of a rectangle to a new value.
	 * @param width. The new rectangle width.
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * GetHeight returns the current height of the rectangle.
	 * @return height of the current rectangle.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Set the height of he rectangle to a new value.
	 * @param height. The new rectangle height.
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	

}
