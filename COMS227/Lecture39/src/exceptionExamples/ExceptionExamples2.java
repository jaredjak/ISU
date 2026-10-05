/**
 * Creating our own exceptions that aren't in the Java Library
 */

package exceptionExamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExceptionExamples2 {

	public static double getValueFromFile(File file) throws InvalidFormatException {
		Scanner scnr = null;
		double a = 0.0;
		double b = 0.0;

		try {
			scnr = new Scanner(file);
			if (scnr.hasNextDouble()) {
				a = scnr.nextDouble();
			} else {
				InvalidFormatException e = new InvalidFormatException("First word in the file is not a double...");
				throw e;
			}

			if (scnr.hasNextDouble()) {
				b = scnr.nextDouble();
			} else {
				throw new InvalidFormatException("Second word in the file is not a double...");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File " + file + " was not found...");
			
		} finally {
			if (scnr != null) {
				scnr.close();
			}
		}

		return a * b;
	}

	public static void main(String[] args) {
		File file = new File("in.txt");

		try {
			getValueFromFile(file);

		} catch (InvalidFormatException e) {
			System.out.println(e.toString());
		}

//		try {
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			System.out.println("File not found...");
//		}

	}

}
