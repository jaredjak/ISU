package recursion;

public class HowManyWays {
	
	public static int permutation(String str) {
		// Base Case
		if (str.length() <= 1) {
			return 1;
		}
		
		// Recursion
		return str.length() * permutation(str.substring(1));
	}
	
	public static int hanoi(int n, String srcPeg, String extraPeg, String dstnPeg) {
		// Base Case
		if (n == 1) {
			System.out.println("Move: " + srcPeg + " To: " + dstnPeg);
			return 1;
		}
		
		// Recursion
		int totalMoves = 0;
		totalMoves += hanoi(n - 1, srcPeg, dstnPeg, extraPeg);
		totalMoves += hanoi(1, srcPeg, extraPeg, dstnPeg);
		totalMoves += hanoi(n - 1, extraPeg, srcPeg, dstnPeg);
		return totalMoves;
	}

	public static int howManyWays(int n) {
		// Base Case
		if (n == 1 || n <= -1) {
			return 0;
		}
		
		if (n == 0) {
			return 1;
		}
		
		// Recursion
		int howManyWays2 = howManyWays(n-2);
		int howManyWays3 = howManyWays(n-3);
		return howManyWays2 + howManyWays3;
	}
	
	public static void countUp(int n) {
		// Base Case
		if (n <= 0) {
			return;
		}
		
		// Recursion
		countUp(n - 1);
		System.out.println(n);
		
	}
	
	public static void main(String[] args) {
//		countUp(5);
//		System.out.println(howManyWays(9)); // Expected 5
//		hanoi(3, "A", "B", "C");
//		System.out.println("totalMoves: " + hanoi(3, "A", "B", "C"));
		System.out.println(permutation("ABC"));

	}

}
