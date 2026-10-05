package exceptions;

import java.io.File;
import java.io.FileNotFoundException;
//import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionExamples {
	
	public static double findAvg() throws FileNotFoundException {
		System.out.println("Please enter file name: ");
		Scanner scnr = new Scanner(System.in);
		String fileName = scnr.next();
		File file = new File(fileName);
		return findAverageFile(file);
	}
	
	public static double findAverageFile(File file) throws FileNotFoundException {
		double total = 0.0;
		int count = 0;
		Scanner scnr = new Scanner(file);
		
		while (scnr.hasNext()) {
			try {
				int num = scnr.nextInt();
				total += num;
				count++;
			} catch (InputMismatchException e) {
//				e.printStackTrace();
				scnr.next();
				System.out.println("File not formatted correctly. Please only use integers...");
			}
		}
		return total / count;
	}
	
	public static void main(String[] args) {
//		File file = new File("in.txt");
		double avg = 0.0;
		
		try {
			avg = findAvg();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File does not exist...");
		} catch (InputMismatchException e) {
			System.out.println("File not formatted correctly. Please only use integers...");
		} catch (Exception e) {
			System.out.println("Error");
		}
		System.out.println("The average is " + avg);
	}
}