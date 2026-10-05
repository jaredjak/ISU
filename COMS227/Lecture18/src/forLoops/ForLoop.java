package forLoops;

import java.util.Scanner;

public class ForLoop {
	
	public static boolean hasDuplictes(String input) {
		for (int i = 0; i < input.length() - 1; i++) {
			if (input.charAt(i) == input.charAt(i+1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Counts the number of Ts in a string
	 * @param input input to count the number of stirngs in
	 * @return number of Ts
	 */
	public static int countTs(String input) {
		int count = 0;
		
		for(int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == 'T') {
				count++;
			}
		}
		return count;
	}
	
	public static String reverse(String input) {
		String revString = "";
		
		for (int i = input.length() - 1; i >= 0; i--) {
			revString += input.charAt(i);
		}
		
		return revString;
	}
	
	public static void main(String[] args) {
		
		String inputValue = "TITANIUM";
		String expectedVal = "MUINATIT";
		String actualVal = reverse(inputValue);
		System.out.println("Expected: " + expectedVal + " Actual: " + actualVal);
//		
//		-----------------------------------------------------
//		
//		String inputValue = "TITANIUM";
//		int expectedVal = 2;
//		int actualVal = countTs(inputValue);
//		System.out.println("Expected: " + expectedVal + " Actual: " + actualVal);
//		
//		-----------------------------------------------------
//		
//		int counter = 0;
//		while (counter < 10) {
//			System.out.println(counter);
//			counter++;
//		}
//		
//		for (int counter = 0; counter < 10; counter++) {
//			System.out.println(counter);
//		}
//		
//		---------------------------------------------------------------------
//		
//		Scanner scnr = new Scanner(System.in);
//		
//		System.out.println("Continue (y/n): ");
//		String input = scnr.next();
//		
//		// works
//		while (input.equals("y")) {
//			System.out.println("Running...");
//			System.out.println("Continue (y/n): ");
//			input = scnr.next();
//		}
//		
//		// works
//		for (String input = scnr.next(); input.equals("y"); input = scnr.next()) {
//			System.out.println("Running...");
//			System.out.println("Continue (y/n): ");
//		}

	}

}
