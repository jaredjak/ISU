package lab7;

import java.io.File;

public class Checkpoints {
	
	public static void main(String[] args) {
		// Checkpoint 1 : Part 1
		int[] test = {3, 4, 5, 1, 2, 3, 2}; // max should be 5
	    int result = divAndConq(test);
	    System.out.println(result);
	    
	    // Checkpoint 1 : Part 2
	    System.out.println(getPyramidCount(7));
	    
	    // Checkpoint 2 : Part 1
	    // Example 1 with just one file
//	    File ex = new File("../project6/story.txt");
//	    int count = countFiles(ex);
//	    System.out.println(count);
	    
	    // Example 2 with multiple files
	    File newEx = new File("../project6/src/lab6");
	    int newCount = countFiles(newEx);
	    System.out.println(newCount);
	    
	    // Checkpoint 2 : Part 2
	    System.out.println(countPatterns(5));
	}
	
	
	// Checkpoint 1 : Part 1
	public static int divAndConq(int[] arr) {
		return divideAndConquer(arr, 0, arr.length - 1);
	}
	public static int divideAndConquer(int[] arr, int start, int end) {
		// Base Case
		if (start == end) {
			return arr[start];
		}
		
		// Recursion
		int mid = (start + end) / 2;
		int leftSum = divideAndConquer(arr, start, mid);
		int rightSum = divideAndConquer(arr, mid + 1, end);
		
		return Math.max(leftSum, rightSum);
	}
	
	
	// Checkpoint 1 : Part 2
	public static int getPyramidCount(int levels) {		
		
		// Base Case
		if (levels == 1) {
			return 1;
		}
		
		// Recursion
		return (levels * levels) + getPyramidCount(levels - 1);
	}
	
	
	// Checkpoint 2 : Part 1
	public static int countFiles(File file) {
		// Base Case
		if (!file.isDirectory()) {
			return 1;
		}
		// Recursion
		else {
			int count = 0;
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					count += countFiles(f);
				}
			}
			
			return count;
			
		}
	}
	
	// Checkpoint 2 : Part 2
	public static int countPatterns(int n) {
		//Base Case
		if (n == 0) {
			return 1;
		}
		if (n < 0) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return 1;
		}
		
		// Recursion
		return countPatterns(n - 1) + countPatterns(n - 3);
	}

}
