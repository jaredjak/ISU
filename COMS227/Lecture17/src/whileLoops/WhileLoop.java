package whileLoops;

import java.util.Scanner;

public class WhileLoop {

	public static void main(String[] args) {
		//while loop with counter
//		int counter = 10;
//		
//		while (counter > 0) {
//			
//			System.out.println("The current count is: " + counter);
//			
//			// update
//			counter--;
//		}
		// while loop
	
//	Scanner scnr = new Scanner(System.in);
//	String input;
//	
//	System.out.println("Continue (y/n): ");
//	input = scnr.next();
//	
//	while (input.equals("y")) {
//		System.out.println("Running...");
//		System.out.println("Continue (y/n): ");
//		input = scnr.next();
//	}
//	
//	System.out.println("Done");
	
	//do while loop
	Scanner scnr = new Scanner(System.in);
	String input;
	
//	System.out.println("Continue (y/n): ");
//	input = scnr.next();
	do {
		System.out.println("Running...");
		System.out.println("Continue (y/n): ");
		input = scnr.next(); 
	} while (input.equals("y"));
	
	System.out.println("Done");
	}

}
