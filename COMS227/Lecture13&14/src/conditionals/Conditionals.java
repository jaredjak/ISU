package conditionals;

public class Conditionals {

	public static int max(int a, int b) {
		int max;
		boolean isGreater = a > b;
		
		if (isGreater) {
			max = a;
		}
		else {
			max = b;
		}
		
		return max;
	}
	
	public static int max3(int a, int b, int c) {
		int max = a;
		
		if (b > max) {
			max = b;
		}
		if (c > max) {
			max = c;
		}
		
		return max;
	}
	public static void main(String[] args) {
		int answer = max(27, 2);
		int expected = 27;
		System.out.println("Test max - Expected: " + expected + " Actual: " + answer);
		
		answer = max3(42, 9, 22);
		expected = 42;
		System.out.println("Test max3 - Expected: " + expected + " Actual: " + answer);
		
		answer = max3(27, 27, 27);
		expected = 27;
		System.out.println("Test Max - Expected: " + expected + " Actual: " + answer);
	}

}
