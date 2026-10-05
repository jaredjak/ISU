package arrayList;

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListExamples {

	/**
	 * Find the median of a string
	 * E.g - 1,2,3,4,5 => 3
	 * @param data
	 * @return
	 */
	public static int findMedian(String data) {
		int median = 0;
		Scanner scnr = new Scanner(data);
		
		int count = 0;
		while (scnr.hasNextInt()) {
			scnr.nextInt();
			count++;
		}
		
		int[] arr = new int[count];
		scnr = new Scanner(data);
		for (int i = 0; i < count; i++)
		while(scnr.hasNextInt()) {
			int value = scnr.nextInt();
			arr[i] = value;
		}
		
		
		return median;
	}
	
	public static void main(String[] args) {
		
		int actual = findMedian("1 2 3 4 5");
		int expected = 3;
		System.out.println("findMedian - Expected = " + expected + " Actual = " + actual);
		
		
		ArrayList<String> names = new ArrayList<String>();
		
		names.add("Tony");
		names.add("Donale");
		names.add("Heisenberg");
		//adds an element
		names.add(3, "Jessie");
		//removes the element at the given index
		names.remove(1);
		
		System.out.println(names.size());
		
		// One way to iterate through each element in an array
		for (int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
		}
		
		System.out.println();
		
		// Another useful way to iterate if you have to go through every element
		for (String name : names) {
			System.out.println(name);
		}
		
		System.out.println();
		
		System.out.println(names.toString());
		System.out.println(names);
		
		System.out.println();
		
		// Not a primitive type. Used for ArrayList because it cannot use primitive types.
		// Always use .equals() method and not '=='
		Integer num = 24;
		Integer num2 = Integer.valueOf(24);
		if (num.equals(num2)) {
			System.out.println("TRUE");
		}
		
		//Wrong way of doing it because the scope of == is -127 to 128
		Integer num3 = 2400;
		Integer num4 = Integer.valueOf(2400);
		if (num3 == num4) {
			System.out.println("TRUE");
		} else {
			System.out.println("FALSE");
		}
		
		System.out.println();
		
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		System.out.println(ids);
		
		System.out.println();
		
		//Converts Integer to a primitive type - int
		int id = ids.get(2);
		System.out.println(id);
	}

}
