package arrays2d;

import java.util.Arrays;

public class ArraysExamples {

	public static int calcSum(int[][] arr) {
		int sum = 0;
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				sum += arr[i][j];
			}
		}
		
		return sum;
		
	}
	
	public static int[] calcSumOfColumns(int[][] arr) {
		int[] sums = new int [arr[0].length];
		
		for (int col = 0; col < sums.length; col++) {
			for (int row = 0; row < arr.length; row++) {
				sums[col] += arr[row][col];
			}
		}
		return sums;
	}
	
	public static void main(String[] args) {
		int[][] testData = {{1,2,3},{4,5,6}};
		int expected = 21;
		int actual = calcSum(testData);
		System.out.println("calcSum - Expected: " + expected + " Actual: " + actual);
		
//		int[][] testData2 = new int [3][3];
//		int[] actual2 = calcSumOfColumns(testData2);
//		System.out.println("calcSumOfColumns - Expected: " + actual2);
		
		
		
//		int[][] arr = new int[3][3];
//		arr[0][2] = 10;
		
		
//		// The copy changes with the original
//		int[][] copyArr = Arrays.copyOf(arr, arr.length);
//		copyArr[0][2] = 7;
		
		
//		// The copy now has it's own references in memory so it does not change with the original
//		int[][] copyArr = new int[arr.length][arr[0].length];
//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr[i].length; j++) {
//				copyArr[i][j] = arr[i][j];
//			}
//		}
//		copyArr[0][2] = 7;
		
		
		// This single for loop does the same as the above nested for loop. Creates it's own references
		// These ideas only work for primitive types. Not object references (i.e. Strings)
//		int[][] copyArr = new int[arr.length][arr[0].length];
//		for (int row = 0; row < arr.length; row++) {
//			copyArr[row] = Arrays.copyOf(arr[row], arr[row].length);
//		}
//		copyArr[0][2] = 7;
//		
//		System.out.println(arr[0][2]);
//		System.out.println(copyArr[0][2]);
		
		
//		int [][] arr = {{1,2,3}, {4,5,6}};
//		
//		int[] row0 = {9,9,9,9,9};
//		int[] row1 = {3,3,3,3,3,3,3};
//		
//		arr[0] = row0;
//		arr[1] = row1;
//		
//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr[i].length; j++) {
//				System.out.print(arr[i][j] + " ");
//			}
//			System.out.println();
//		}
//		
//		// Prints the amount of rows in the array
//		System.out.println(arr.length);
//		// Prints the amount of columns at the given row
//		System.out.println(arr[0].length);

	}

}
