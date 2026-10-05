package questions;

import java.util.Arrays;

public class Question5 {
	
	/**
	 * Given merge() method - first 
	 */
	
	/**
	 * Suppose you are given an implementation of a method merge() defined as
	 * follows: Assuming that first is already sorted and second is already sorted, returns a new, sorted
	 * array containing all the elements from first and second. The two given arrays are not modified.
	 * 
	 * public static int[] merge(int[] first, int[] second)
	 * 
	 * Complete mergesort question below:
	 */
	
	public static void mergesort(int[] arr) {
		// Base Case
		if (arr.length <= 1) {
			return;
		}
		
		// Recursion
		int mid = arr.length / 2;
//		int[] first = Arrays.copyOfRange(arr, 0, mid);
		int[] first = Arrays.copyOf(arr, mid);
		int[] second = Arrays.copyOfRange(arr,  mid, arr.length);
		
		mergesort(first);
		mergesort(second);
		
		int[] newArr = merge(first, second);
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = newArr[i];
		}	
	}
	
	// Given:
	public static int[] merge(int[] first, int[] second) {
		int[] newArr = new int[first.length + second.length];
		for (int i = 0; i < first.length; i++) {
			newArr[i] = first[i];
		}
		for (int i = 0; i < second.length; i++) {
			newArr[i+first.length] = second[i];
		}
		
		return newArr;
	}
	
	public static void main(String[] args) {
		int[] testArr = {5, 8, 2, 4, 1, 3, 7, 6, 9};
		
		for (int i = 0; i < testArr.length; i++) {
			System.out.print(testArr[i] + " ");
		}
		
		System.out.println();
		mergesort(testArr);
		
		for (int i = 0; i < testArr.length; i++) {
			System.out.print(testArr[i] + " ");
		}
	}
	
	public static void mergeSort(int[] arr) {
		// base case
		if (arr.length <= 1) {
			return;
		}
		
		int mid = (arr.length - 1) / 2;
		int[] first = Arrays.copyOfRange(arr, 0, mid);
		int[] second = Arrays.copyOfRange(arr, mid, arr.length);
		
		mergeSort(first);
		mergeSort(second);
		
		int[] newArr = merge(first, second);
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = newArr[i];
		}
	}
}
