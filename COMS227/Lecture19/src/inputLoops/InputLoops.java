package inputLoops;

import java.util.Scanner;

// Cardinality - translates the number of elements in a set

public class InputLoops {

	public static boolean containsCharacter (String text, char c) {
		
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == c) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Take input number from the user until user enters 'q'.
	 * @return The average of the numbers the user entered.
	 */
	public static double averageFromConsole() {
		double total = 0;
		int count = 0;
		Scanner scnr = new Scanner(System.in);
		
		String input = "";
		
		while(!input.equals("q")) {
			System.out.print("Please enter a double (q to quit): ");
			input = scnr.next();
			
			if (input.equals("q")) {
				break;
			}
			double number = Double.parseDouble(input);
			total += number;
			count++;
		}
		
		return total / count;
	}
	
	/**
	 * Return the average of values found in a string.
	 * E.g., 1 2 3 4 5 => 3.0
	 * @param input the string with double inside of it.
	 * @return the average of values inside the string.
	 */
	public static double averageFromString(String text) {
		double total = 0;
		int count = 0;
		Scanner scnr = new Scanner(text);
		
		while (scnr.hasNextDouble()) {
			total += scnr.nextDouble();
			count ++;
		}
		
		return total / count;
	}
	
	public static void main(String[] args) {
		String input = "Hello World!";
		char input2 = 'x';
		boolean expected = false;
		boolean actual = containsCharacter(input, input2);
		System.out.println("containsCharacter - Expected: " + expected + " Actual: " + actual);
		
//		String input = "-1 0 1";
//		double expected = 0.0;
//		double actual = averageFromString(input);
//		System.out.println("averageFromString | Expected: " + expected + " Actual: " + actual);
//		
//		expected = 0.0;
//		actual = averageFromConsole();
//		System.out.println("averageFromConsole | Expected: " + expected + " Actual: " + actual);
	}

}
