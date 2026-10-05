package search;

import java.util.ArrayList;

public class Search {

	
	public static void swap(int[] arr, int from, int to) {
		int temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}
	
	public static void selectionSort2(int[] arr) {
		for (int unsortI = 0; unsortI < arr.length - 1; unsortI++) {
			int minI = indexOfMin(arr, unsortI);
			swap(arr, minI, unsortI);
		}
	}
	

	
	// Sorts the array by finding the minimum number in selectionSort
	public static int indexOfMin(int[] arr, int startI) {
		int minI = startI;
		for (; startI < arr.length; startI++) {
			if (arr[startI] < arr[minI]) {
				minI = startI;
			}
		}
		return minI;
	}
	
	public static void selectionSort(int[] arr) {
		for (int unsortI = 0; unsortI < arr.length - 1; unsortI++) {
			int minI = indexOfMin(arr, unsortI);
			int temp = arr[minI];
			arr[minI] = arr[unsortI];
			arr[unsortI] = temp;
		}
	}
	
	
	
	// Search for a given value in an array
	public static boolean search (int[] arr, int val) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == val) {
				return true;
			}
		}
		return false;
	}
	
	// Dynamic Programming
	// Something to do with memoization (not memorization)
	public static int fib(int n) {
		ArrayList<Integer> fibs = new ArrayList<Integer>();
		fibs.add(0);
		fibs.add(1);
		
		for (int i = 2; i <= n; i++) {
			int fib = fibs.get(i-2) + fibs.get(i-1);
			fibs.add(fib);
		}
		
		return fibs.get(n);
	}
	
	// Recursive
	public static int fibr(int n) {
		// Base case
		if (n == 0 || n == 1) {
			return n;
		}
		// Recursion
		return fibr(n - 1) + fibr(n - 2);
	}
	
	public static void main(String[] args) {
		
//		int testData = 500;
//		// Dynamic Programming / Memoization solution (instantaneously solved)
//		System.out.println(fib(testData));
//		// Recursive solution (incredibly slow)
//		System.out.println(fibr(testData));
		
		
//		int[] testData = {1,2,3,4,5,6,7};
//		System.out.println(search(testData, 3));
		
		
		int[] testData = {9,3,7,2,4,8,1,5,6};
		for (int num : testData) {
			System.out.print(num + " ");
		}
		selectionSort(testData);
		System.out.println();
		for (int num : testData) {
			System.out.print(num + " ");
		}

		System.out.println();
		
		int[] testData2 = {3,1,2,5,6,9,4,8,7};
		for (int num : testData2) {
			System.out.print(num + " ");
		}
		selectionSort2(testData2);
		System.out.println();
		for (int num : testData2) {
			System.out.print(num + " ");
		}
		
	}

}
