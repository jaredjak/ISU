package edu.iastate.cs2280.hw1;


/**
 *  @author Jared Krug
 *  DONE
 */
public class Casual extends TownCell {

	public Casual(Town town, int row, int col) {
		super(town, row, col);
	}
	
	/**
	 * Gets the identity of the cell. (CASUAL)
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		// TODO Auto-generated method stub // DONE
		return State.CASUAL;
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
		
		// Rule 6a. Not Reseller or Outage and if Empty + Outage <= 1
		if (nCensus[EMPTY] + nCensus[OUTAGE] <= 1) {
			return new Reseller(tNew, row, col);
		} 
		// Rule 1a. If any Reseller, become Outage.
		else if (nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		}
		// Rule 1b. If any neighbor is Streamer, become Streamer.
		else if (nCensus[STREAMER] > 0) {
			return new Streamer(tNew, row, col);
		}
		// Rule 6b. If more than 5 Casual neighbors, become Streamer.
		else if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		}
		// Rule 7. Remain unchanged.
		else {
			return new Casual(tNew, row, col);
		}
	}
}
