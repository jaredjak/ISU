package conditionals3;

import java.util.Scanner;

public class StringCompare {

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Please enter username: ");
		String input = scnr.next();
		String username = "Bob";
		
		if (input.equals(username)) {
			System.out.println("Successful login");
		}
		else {
			System.out.println("Incorrect username");
		}
	}

}
