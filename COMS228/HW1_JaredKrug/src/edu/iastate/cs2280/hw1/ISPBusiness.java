package edu.iastate.cs2280.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Jared Krug
 * DONE
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		
		//TODO: Write your code here. // DONE
		
		// Should iterate through the cells and get the new state for the cells
		for (int i = 0; i < tOld.getLength(); i++) {
			for (int j = 0; j < tOld.getWidth(); j++) {
				tNew.grid[i][j] = tOld.grid[i][j].next(tNew);
			}
		}
		
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		
		//TODO: Write/update your code here. // DONE
		
		int profit = 0;
		
		// Should find all casual cells and add them up to get total profit
		// Total Profit = number of Casual cells ($1 per)
		for (int i = 0; i < town.getLength(); i++) {
			for (int j = 0; j < town.getWidth(); j++) {
				if (town.grid[i][j].who() == State.CASUAL) {
					profit++;
				}
			}
		}
		return profit;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		//TODO: Write your code here. // DONE
		
		Town newTown = null;
		
		Scanner scnr = new Scanner(System.in);
		
		
		System.out.println("How to populate grid (type 1 or 2): 1 from a file. 2: randomly with seed.");
		int choice = scnr.nextInt();
			
		// Needed to prevent from automatically going to else statement
		scnr.nextLine(); 
			
		// Should populate the grid from a file.
		if (choice == 1) {
			System.out.println("Please enter file path: ");
			String file = scnr.nextLine();
			try {
				newTown = new Town(file);
			} catch (FileNotFoundException e) {
				System.out.println("Not a valid file");
				scnr.close();
				return;
			}
		}
			
		// Should randomly populate the grid with the given seed.
		else if (choice == 2) {
			System.out.println("Provide rows, cols, and seed integer separated by spaces: ");
			int row = scnr.nextInt();
			int col = scnr.nextInt();
			int seed = scnr.nextInt();
				
			scnr.nextLine();
				
			newTown = new Town(row, col);
			newTown.randomInit(seed);
		}
			
		// Should do nothing if not given '1' or '2'.
		else {
			System.out.println("Not a choice");
			scnr.close();
			return;
		}

		// The rest should properly calculate the maximum profit
		int totalProfit = getProfit(newTown);
		
		for (int month = 1; month < 12; month++) {
			newTown = updatePlain(newTown);
			totalProfit += getProfit(newTown);
		}
		
		int maxProfit = newTown.getLength() * newTown.getWidth() * 12;
		double profitPercent = (100.0 * totalProfit) / maxProfit;
		
		System.out.printf("%.2f%%\n", profitPercent);
		
		scnr.close();
		
	}
}
