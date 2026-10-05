package questions;

public class Question6 {
	
	public static void main(String[] args) {
		String[] names = {"X", "Y", "Z", "A"};
		int n = 5;
		
		boolean value = isSortedIncreasing(names, n);
		System.out.println(value);
		
	}
	
	public static boolean isSortedIncreasing(String[] names, int n) {
		// Base Case 
		if (n <= 1) {
			return true;
		}
		
		if (names[n-1].compareTo(names[n-2]) < 0) {
			return false;
		}
		
		// Recursion
		return isSortedIncreasing(names, n - 1);
	}

}

