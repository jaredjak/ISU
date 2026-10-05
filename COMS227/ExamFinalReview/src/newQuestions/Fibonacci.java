package newQuestions;

public class Fibonacci {

	public static boolean FibonacciChecker(int n) {
		if (n == 0 || n==1) {
			return true;
		}
		else if (n < 0) {
			return false;
		} else {
			return fibCalc(n, 0, 1);
		}
		
		
	}
	
	private static boolean fibCalc(int target, int prev1, int prev2) {
		if (prev1 > target) {
			return false;
		} else if (prev1 == target) {
			return true;
		}
		return fibCalc(target, prev2, prev1 + prev2);
	}
}
