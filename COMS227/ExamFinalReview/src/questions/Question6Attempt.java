package questions;

public class Question6Attempt {
	// new version but decreasing
	public static boolean isSortedDecreasing(String[] names, int n) {
		if (n <= 1) {
			return true;
		}
		
		if (names[n-1].compareTo(names[n-2]) > 0) {
			return false;
		}
		
		return isSortedDecreasing(names, n - 1);
	}
	
	public static boolean isSortedIncreasing(String[] names, int n) {
		if (n <= 1) {
			return true;
		}
		
		if (names[n-1].compareTo(names[n-2]) < 0) {
			return false;
		}
		
		return isSortedIncreasing(names, n-1);
	}
	
	public static void main(String[] args) {
		String[] names = {"x","y","z","a"};
		int n = 3;
		
		boolean value = isSortedIncreasing(names, n);
		System.out.println(value);
		
		
		
		String[] newNames = {"c", "b", "a", "z"};
		boolean newValue = isSortedDecreasing(newNames, n);
		System.out.println(newValue);
	}

}
