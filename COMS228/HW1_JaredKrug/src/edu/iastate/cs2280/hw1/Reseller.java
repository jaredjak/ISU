package edu.iastate.cs2280.hw1;


/**
 *  @author Jared Krug
 *  DONE
 */
public class Reseller extends TownCell {

	public Reseller(Town town, int row, int col) {
		super(town, row, col);
	}
	
	/**
	 * Gets the identity of the cell. (RESELLER)
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		// TODO Auto-generated method stub // DONE
		return State.RESELLER;
	}

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return new cell type
	 */
	@Override
	public TownCell next(Town tNew) {
		// TODO Auto-generated method stub // DONE
		
		// Should create a call to TownCell's census method
		int[] nCensus = new int[NUM_CELL_TYPE];
		census(nCensus);
		
		// Rule 3a. If 3 or less Casual, become Empty
		if (nCensus[CASUAL] <= 3) {
			return new Empty(tNew, row, col);
		}
		// Rule 3b. If 3 or more Empty, become Empty
		else if (nCensus[EMPTY] >= 3) {
			return new Empty(tNew, row, col);
		}
		// Rule 6b. If more than 5 Casual neighbors, become Streamer.
		else if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		}
		// Rule 7. Remain unchanged.
		else {
			return new Reseller(tNew, row, col);
		}
	}
}
