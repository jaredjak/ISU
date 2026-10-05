package exam1;

import java.util.Scanner;

public class EggsSold {
	
	private double flatPrice = 6.50;
	private double dozenPrize = 3.00;
	private double hDozenPrize = 2.00;
	private double totalPrice = 0.00;
	
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		
		System.out.print("How many eggs? ");
		int eggCount = scnr.nextInt();
		
		System.out.print("Do you want brown eggs (yes/no)?");
		String brown = scnr.next();
		
		double totalPric = 5.00;
		
		System.out.println(eggCount % 30 + " flats");
		System.out.println(eggCount % 12 + " dozens");
		System.out.println(eggCount % 6 + " half dozens");
		System.out.println("Price " + totalPric);
		
	}
	
}
