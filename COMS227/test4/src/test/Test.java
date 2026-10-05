/**
 * Lecture 3 test code. Finding 3 of the types of ints (static, instance, and local)
 * then we go through some math operators and instantiating ints as well has introducing prefix vs postfix
 * 3rd part is about a POTENTIAL TEST QUESTION
 * next part goes over casting to fix problematics
 */

package test;

public class Test {
//	public static int i1 = 12; // static
//	public int i2 = 3; // instance

	public static void main(String[] args) {
//		int i3 = 5; // local
		
//		System.out.println("Hello World!");
//		int num1 = 7;
//		System.out.println(num1);
//		
//		double trouble = 1.5;
//		System.out.println(trouble);
//		
//		int num2 = 2023 / 12;
//		System.out.println(num2);
//		
//		num2++; // look into this. lookup -> (prefix vs postfix increment java)
//		System.out.println(num2);
//		
//		++num2; // look into this. lookup -> (prefix vs postfix increment java)
//		System.out.println(num2);
		
		//POTENTIAL TEST QUESTION: 
		int numEggsInCarton = 32;
		int numEggsInDozen = 12;
		int numDozensInCarton = numEggsInCarton / numEggsInDozen;
		System.out.println("The number of dozens in a carton is: " + numDozensInCarton);
		
		int numLeftover = numEggsInCarton % numEggsInDozen;
		System.out.println("The number of eggs leftover is: " + numLeftover);
		
		
		//4th part - casting
		int value = 5;
		double number = 2.0;
		
		value = (int) (number / 2);
		System.out.println(value);
		
		number = value;
		System.out.println(number);
		
		
	}

}
