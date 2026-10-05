package edu.iastate.cs2280.hw1;


/**
 *  @author Jared Krug
 *  DONE
 */
public class Streamer extends TownCell{

	public Streamer(Town town, int row, int col) {
		super(town, row, col);
	}
	
	/**
	 * Gets the identity of the cell. (STREAMER)
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		// TODO Auto-generated method stub // DONE
		return State.STREAMER;
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
		// Rule 2a. If Reseller anywhere, become Outage.
		else if(nCensus[RESELLER] >= 1) {
			return new Outage(tNew, row, col);
		}
		// Rule 2b. If Outage anywhere, become Empty.
		else if(nCensus[OUTAGE] >= 1) {
			return new Empty(tNew, row, col);
		}
		// Rule 7. Remain unchanged. // Could also be 6b. but they do the same thing here.
		else {
			return new Streamer(tNew, row, col);
		}
	}
}
