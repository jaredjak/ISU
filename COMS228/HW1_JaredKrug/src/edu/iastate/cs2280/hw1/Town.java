package edu.iastate.cs2280.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author Jared Krug
 *  DONE
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		
		//TODO: Write your code here. // DONE
		
		this.length = length;
		this.width = width;
		this.grid = new TownCell[length][width];
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		
		//TODO: Write your code here. // DONE
		
		Scanner scnr = new Scanner(new File(inputFileName));
		
		// Should initialize the grid's dimensions assuming the first line always does so
		if (scnr.hasNextLine()) {
			String[] dim = scnr.nextLine().split(" ");
			this.length = Integer.parseInt(dim[0]);
			this.width = Integer.parseInt(dim[1]);
		}
		
		this.grid = new TownCell[length][width];
		
		for (int i = 0; i < length; i++) {
			if (scnr.hasNextLine()) {
				String currLine = scnr.nextLine();
				
				String[] cells = currLine.split(" ");
				
				for (int j = 0; j < width; j++) {
					char letter = cells[j].charAt(0);
					
					// Assigns the type of cell based on the given letter in the file
					if (letter == 'R') {
						grid[i][j] = new Reseller(this, i, j);
					} else if (letter == 'E') {
						grid[i][j] = new Empty(this, i, j);
					} else if (letter == 'C') {
						grid[i][j] = new Casual(this, i, j);
					} else if (letter == 'O') {
						grid[i][j] = new Outage(this, i, j);
					} else if (letter == 'S') {
						grid[i][j] = new Streamer(this, i, j);
					}
				}
			}
		}
		scnr.close();
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		
		//TODO: Write/update your code here. // DONE
		
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		
		//TODO: Write/update your code here. // DONE
		
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		
		Random rand = new Random(seed);
		
		//TODO: Write your code here. // DONE ??? (Implement subclasses)
		
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				int newRandomValue = rand.nextInt(5);
				
				// Made the numbers match with the values in TownCell for my own sake.
				if (newRandomValue == 0) {
					grid[i][j] = new Reseller(this, i, j);
				} else if (newRandomValue == 1) {
					grid[i][j] = new Empty(this, i, j);
				} else if (newRandomValue == 2) {
					grid[i][j] = new Casual(this, i, j);
				} else if (newRandomValue == 3) {
					grid[i][j] = new Outage(this, i, j);
				} else if (newRandomValue == 4) {
					grid[i][j] = new Streamer(this, i, j);
				}
			}
		}
		
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		
		//TODO: Write your code here. // DONE
		
		// Should give each cell type their associated letter
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] instanceof Reseller) {
					s += 'R';
				} else if (grid[i][j] instanceof Empty) {
					s += 'E';
				} else if (grid[i][j] instanceof Casual) {
					s += 'C';
				} else if (grid[i][j] instanceof Outage) {
					s += 'O';
				} else if (grid[i][j] instanceof Streamer) {
					s += 'S';
				}
				
				// Should separate each letter with a space except for the last cell in a column
				if (j < width - 1) {
					s += ' ';
				}
			}
			
			// Should create a newline after each row is iterated through
			s += '\n';
		}
		
		return s;
	}
}
