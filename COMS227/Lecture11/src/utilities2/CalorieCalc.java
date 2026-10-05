// This code was meant to teach us about the separation of interests in code. Throw everything into different methods

package utilities2;

import java.util.Scanner;

public class CalorieCalc {
	
	private final static double FEET_PER_STEP = 2.5; // Typical adult
	private final static int FEET_PER_MILE = 5280;
	private final static double STEPS_PER_MINUTE = 70.0; // Typical adult
	private final static double CALORIES_PER_MINUTE_WALKING = 3.5; // Typical adult
	
	public static void main(String[] args) {
		int stepsWalked = inputNumSteps();
		
		double milesWalked = calcMilesWalked(stepsWalked);
		double calories = calcCalories(stepsWalked);
		displayMilesWalked(milesWalked);
		displayCalories(calories);
	}
	
	public static int inputNumSteps() {
		Scanner scnr = new Scanner(System.in);	
		System.out.print("Enter number of steps walked: ");
		int stepsWalked = scnr.nextInt();
		return stepsWalked;
	}
	
	public static double calcMilesWalked(int stepsWalked) {
		double milesWalked;
		milesWalked = stepsWalked * FEET_PER_STEP * (1.0 / FEET_PER_MILE);
		return milesWalked;
	}
	
	public static void displayMilesWalked(double milesWalked) {
		System.out.println("Miles walked: " + milesWalked);
	}
	
	public static double calcApproxMin(int stepsWalked) {
		double minutesTotal = stepsWalked / STEPS_PER_MINUTE;
		return minutesTotal;
	}
	
	public static double calcCalories(int stepsWalked) {
		double minutesTotal = calcApproxMin(stepsWalked);
		double caloriesTotal = minutesTotal * CALORIES_PER_MINUTE_WALKING;
		return caloriesTotal;
	}
	
	public static void displayCalories(double caloriesTotal) {
		System.out.println("Calories: " + caloriesTotal);
	}
}