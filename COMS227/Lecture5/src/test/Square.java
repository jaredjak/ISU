package test;

public class Square {
	private int length;
	
	public Square(int length) {
		this.length = length;
	}
	
	int getArea() {
		return length * length;
	}
	
	int getPerimeter() {
		return length * 4;
	}
}
