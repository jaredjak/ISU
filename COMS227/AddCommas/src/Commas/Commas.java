package Commas;

import java.util.Scanner;

public class Commas {
	public static void printArrayInBatches(String[] arr, int batchSize) {
//		int count = 0;
		for (int i = 0; i < arr.length; i += batchSize) {
			int end = Math.min(i + batchSize, arr.length);
			for (int j = i; j < end; j++) {
				if (j == end - 1) {
					System.out.print(arr[j]);
				} else {
					System.out.print(arr[j] + ", ");
//					count++;
				}
			}
			System.out.println();
//			System.out.println(count);
		}
	}

	public static void main(String[] args) {
		// String to delete for every go at it...
		System.out.println("Enter codes: ");
		Scanner scnr = new Scanner(System.in);
		String temp = scnr.nextLine();
				
		String[] myArray = temp.split(" ");

		int batchSize = 50;

		printArrayInBatches(myArray, batchSize);

	}

}
