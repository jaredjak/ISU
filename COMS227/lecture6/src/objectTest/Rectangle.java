package objectTest;

public class Rectangle {
	// the instance variables
	double width;
	double height;
	
	// this is our constructor
	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	// this is an overloaded constructor
	public Rectangle() {
		this.width = 0;
		this.height = 0;
	}
	
	public void growRectangle(double width, double height) {
		this.width += width;
		this.height += height;
	}
	
	public double getArea() {
		return width * height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	

}
