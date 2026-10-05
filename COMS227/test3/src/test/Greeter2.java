package test;

import java.util.Scanner;

public class Greeter2 {
	public static void main(String[] args) {
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Hello I am a greeter robot");
		System.out.println("Please input the following things");
		// name, age, lucky number, color, animal
		
		System.out.print("Name: ");
		String name = scnr.next();
		
		System.out.print("Age: ");
		String age = scnr.next();
		
		System.out.print("Lucky number: ");
		String num = scnr.next();
		
		System.out.print("Favorite color: ");
		String color = scnr.next();
		
		System.out.print("Oh! Well " + name + " I see that you are " + age + " years old and that your lucky number is "
				+ num + " and that your favorite color is " + color);
		
		
		}

}
