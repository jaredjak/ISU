package conditionalsExercise;

import java.util.Random;
import java.util.Scanner;

public class GuessGame {
	private int max;
	
	public GuessGame(int max) {
		this.max = max;
	}
	
	public void play() {
		Scanner scnr = new Scanner(System.in);
		Random rand = new Random();
		int secretNumber = rand.nextInt(max);
		
		System.out.println("Enter guess: ");
		int guess = scnr.nextInt();
		
		if (guess == secretNumber) {
			System.out.println("Correct!");
		} else if (guess < secretNumber) {
			System.out.println("Too Low!");
		} else {
			System.out.println("Too High!");
		}
	}
}
