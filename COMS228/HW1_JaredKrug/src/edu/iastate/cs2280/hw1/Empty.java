package edu.iastate.cs2280.hw1;


/**
 *  @author Jared Krug
 *  DONE
 */
public class Empty extends TownCell{

	public Empty(Town town, int row, int col) {
		super(town, row, col);
	}
	
	/**
	 * Gets the identity of the cell. (EMPTY)
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		// TODO Auto-generated method stub // DONE
		return State.EMPTY;
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
		// Rule 5. If empty, become Casual.
		else {
			return new Casual(tNew, row, col);
		}
	}
}
