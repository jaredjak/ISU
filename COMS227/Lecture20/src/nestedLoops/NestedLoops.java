package nestedLoops;

public class NestedLoops {
	
	/**
	 * Return the index of a given substring
	 * e.g: "Static", "at" -> 2
	 * @param text - the string to search
	 * @param sub - the substring to search for
	 * @return the index where the substring occurs
	 */
	public static int indexOf(String text, String sub) {
		int index = -1;
		
		// Basic way to solve this problem
//		for (int i = 0; i < text.length(); i++) {
//			if (text.substring(i, i + sub.length()).equals(sub)) {
//				index = i;
//				break;
//			}
//		}
//		return index;
		
		
		
		// A "counter" version of the solution
//		int matchSize = 0;
//		for (int start = 0; start < text.length(); start++) {
//			matchSize = 0;
//			for (int j = 0; j < sub.length(); j++) {
//				if (text.charAt(start + j) == sub.charAt(j)) {
//					matchSize++;
//				}
//			}
//			if (matchSize == sub.length()) {
//				index = start;
//				break;
//			}
//		}
//		return index;
		
		

		// One other accepted solution using boolean values;
		for (int start = 0; start < text.length(); start++) {
			boolean allMatches = true;
			for (int j = 0; j < sub.length(); j++) {
				if (text.charAt(start + j) != sub.charAt(j)) {
					allMatches = false;
					break;
				}
			}
			if (allMatches) {
				index = start;
				break;
			}
		}
		return index;
		
	}

	/**
	 * Return true if the string has any duplicated characters.
	 * "abba" = true
	 * "abc" = false
	 * @param input - The given input from main.
	 * @return the value of duplicates (true or false).
	 */
	public static boolean hasDuplicates(String input) {
		boolean duplicates = false;
		
		//Labels (e.g., outerLoop) - breaks out of the entire structure
//		outerLoop:
//		for (int i = 0; i < input.length(); i++) {
//			for (int j = i + 1; j < input.length(); j++) {
//				if (input.charAt(i) == input.charAt(j)) {
//					duplicates = true;
//					break outerLoop;
//				}
//			}
//		}
//		return duplicates;
		
		// Best version:
		for (int i = 0; i < input.length(); i++) {
			for (int j = i + 1; j < input.length(); j++) {
				if (input.charAt(i) == input.charAt(j)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		String input = "Window";
		String input2 = "nd";
		int expected = 2;
		int actual = indexOf(input, input2);
		System.out.println("indexOf - Exp: " + expected + " Act: " + actual);
		
		
//		String input = "abcdefgd";
//		boolean expected = true;
//		boolean actual = hasDuplicates(input);
//		System.out.println("hasDuplicates - Exp: " + expected + " Act: " + actual);
		
		
//		String aString = "Goodbye, World...";
//		for (int i = 0; i < aString.length(); i++) {
//			for (int j = 0; j < aString.length(); j++) {
//				System.out.println("(" + aString.charAt(i) + ", " + aString.charAt(j) + ")");
//			}
//		}
		
//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < 10; j++) {
//				System.out.println("(" + i + ", " + j + ")");
//			}
//		}
	}

}
