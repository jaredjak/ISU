package recursion;

public class Recursion {

	public static void countUp(int n) {
		// Base case
		if (n <= 0) {
			return;
		}
		// Recursion
		countUp(n - 1);
		System.out.println(n);
	}
	
	/**
	 * n1 = n * (n - 1)
	 * @param n
	 * @return 
	 */
	public static int fac(int n) {
		// Base case
		if (n <= 1) {
			return 1;
		}
		// Recursion
		return n * fac(n-1);
	}
	
	public static int fib(int n) {
		// Base case
		if (n == 0 || n == 1) {
			return n;
		}
		// Recursion
		return fib(n - 1) + fib(n - 2);
	}
	
	public static boolean isPalindrome(String text) {
		// Base case
		if (text.length() <= 1) {
			return true;
		}
		// Recursion
//		return isPalindrome(text.substring(1, text.length() - 1))
//				&& text.charAt(0) == text.charAt(text.length() -1);
		
		// More efficient recursion
		return text.charAt(0) == text.charAt(text.length() -1) 
				&& isPalindrome(text.substring(1, text.length() - 1));
	}
	
	public static void main(String[] args) {
//		countUp(5);
//		System.out.println(fac(4));
//		System.out.println(fib(4));
		System.out.println(isPalindrome("racecar"));
	}

}
