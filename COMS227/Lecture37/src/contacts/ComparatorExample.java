package contacts;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorExample {

	public static void main(String[] args) {
		
		// Basic version:
		String[] fruits = {"banana", "apple", "pear"};
		Arrays.sort(fruits);
		System.out.println("Alphabetical: " + Arrays.toString(fruits));

		
		// Creating a new class to find an order:
		Comparator<String> comp = new StrLenComparator();
		Arrays.sort(fruits, comp);
		System.out.println("By length (descending): " + Arrays.toString(fruits));
		
		
		// Creating a local interface to create a new order:
		Comparator<String> comp2 = new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.length() - b.length();
			}
		};
		Arrays.sort(fruits, comp2);
		System.out.println("By length (ascending): " + Arrays.toString(fruits));
		
		
		// Lambda Functions
		Comparator<String> comp3 = (a, b) -> b.length() - a.length();
		Arrays.sort(fruits, comp3);
		System.out.println("By length (descending): " + Arrays.toString(fruits));
	}

}

class StrLenComparator implements Comparator<String> {

	/**
	 * if a > b. return positive number
	 * if a == b. return 0
	 * if a < b. return negative number
	 */
	@Override
	public int compare(String a, String b) {
		return b.length() - a.length();
	}
	
}
