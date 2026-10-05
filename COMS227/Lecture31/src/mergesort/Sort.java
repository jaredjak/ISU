package mergesort;

import java.util.Arrays;

public class Sort {
//	***
	// https://www.bigocheatsheet.com/
//	***
	
	// Bad version of search:
	public static int search(int[] arr, int target) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == target) {
				return i;
			}
		}
		return -1;
	}
	
	
	// Good version of search (binarySearch):
	public static int binarySearch(int[] arr, int target, int start, int end) {
		// debugging print
		System.out.println("binarySearch with range " + start + " to " + end);
		
		// base case
		if (start > end) {
			return -1;
		}
		
		// recursion
		int mid = (start + end) / 2;
		if (arr[mid] == target) {
			return mid;
		} else if (target < arr[mid]) {
			return binarySearch(arr, target, start, mid - 1);
		} else {
			return binarySearch(arr, target, mid + 1, end);
		}
	}
	
	// Way to separate a list and then merge together it's sorted parts
	public static void mergeSort(int[] arr) {
		// base case
		if (arr.length <= 1) {
			return;
		}
		
		// split into two new arrays
		int firstLength = arr.length / 2;
		int[] first = Arrays.copyOf(arr,  firstLength);
		int[] second = Arrays.copyOfRange(arr,  firstLength,  arr.length);
		
		// recursively sort each half and merge back together
		mergeSort(first);
		mergeSort(second);
		
		// merged result is put directly into 'arr'
		merge(first, second, arr);
	}
	
	// merge the parts together via a merge method
	private static void merge(int[] a, int[] b, int[] result) {
		int i = 0;
		int j = 0;
		final int iMax = a.length;
		final int jMax = b.length;
		int k = 0;
		
		while (i < iMax && j < jMax) {
			if (a[i] <= b[j]) {
				result[k] = a[i];
				i = i + 1;
				k = k + 1;
			} else {
				result[k] = b[j];
				j = j + 1;
				k = k +1;
			}
		}
		
		// pick up any 'stragglers'
		while (i < iMax) {
			result[k] = a[i];
			i = i + 1;
			k = k + 1;
		}
		while (j < jMax) {
			result[k] = b[j];
			j = j + 1;
			k = k + 1;
		}
	}
	
	
	public static void main(String[] args) {
		// Bad version of sort test:
//		int [] data = {7,4,6,2,3,5,1};
//		System.out.println(search(data, 3));
		
		
		// Good version of search test:
		// Assumes the list is sorted
//		int[] data = {1,2,3,4,5,6,7};
//		System.out.println(binarySearch(data, 3, 0, data.length - 1));
		
		//binarySearch test
		int[] data = {5,4,8,7,6,2,3,1};
		
		for (int number : data) {
			System.out.print(number + " ");
		}
		System.out.println();
		
		mergeSort(data);
		
		for (int number : data) {
			System.out.print(number + " ");
		}
		System.out.println();
	}

}
