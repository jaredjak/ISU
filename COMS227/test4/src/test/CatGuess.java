package test;

import java.util.Scanner;

public class CatGuess {
	public static final int NUMBER_OF_CATS = 2000;

	public static void main(String[] args) {
		System.out.println("How many cats do you guess?");
		Scanner scnr = new Scanner(System.in);
		int guess = scnr.nextInt();
		System.out.println("You guessed " + guess + " there are actually: " + NUMBER_OF_CATS);
	}

}
