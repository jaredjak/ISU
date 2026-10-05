package test;

public class SquareTest {

	public static void main(String[] args) {
		// Area test
		int length = 10;
		Square sq = new Square(length);
		int actualVal = sq.getArea();
		int expectedVal = 100;
		System.out.println("Area test - Actual Value: " + actualVal + " Expected Value: " + expectedVal);
				
		// Perimeter test
		length = 6;
		sq = new Square(length);
		actualVal = sq.getPerimeter();
		expectedVal = 24;
		System.out.println("Perimeter test - Actual Value: " + actualVal + " Expected Value: " + expectedVal);
	}

}
