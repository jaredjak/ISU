package edu.iastate.cs2280.hw1;

/**
 * 
 * @author Jared Krug
 *	Also provide appropriate comments for this class
 *  DONE
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 

		//TODO: Write your code here. // DONE
		
		// Should hopefully only iterate around a given cell representing a neighborhood
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				
				// Should skip the given cell so it doesn't count it
				if ((i == row) && (j == col)) {
					continue;
				}
				
				// Should make sure the cell is within the bounds
				if ((i >= 0 && i < plain.getLength()) && (j >= 0 && j < plain.getWidth())) {
					// Represents neighbor cell found on the grid at [i][j]
					TownCell n = plain.grid[i][j];
					// Represents the state of the neighbor cell found with the who method
					State nState = n.who();
					
					// Should add all neighbors to the census count
					if (nState == State.RESELLER) {
						nCensus[RESELLER] += 1;
					} else if (nState == State.EMPTY) {
						nCensus[EMPTY] += 1;
					} else if (nState == State.CASUAL) {
						nCensus[CASUAL] += 1;
					} else if (nState == State.OUTAGE) {
						nCensus[OUTAGE] += 1;
					} else if (nState == State.STREAMER) {
						nCensus[STREAMER] += 1;
					}
				}
			}
		}
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
