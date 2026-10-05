package newQuestions;

public class ClimbStairs {

	public static int climbStairs(int n) {
		// base case
		if (n <= 2) {
			return n;
		}
		
		// recursion
		return climbStairs(n-1) + climbStairs(n-2);
	}
	
	public static void main(String[] args) {
		int n = 5;
		System.out.println(climbStairs(n));
	}
}
