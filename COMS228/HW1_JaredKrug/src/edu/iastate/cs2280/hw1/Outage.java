package edu.iastate.cs2280.hw1;


/**
 *  @author Jared Krug
 *  DONE
 */
public class Outage extends TownCell{

	public Outage(Town town, int row, int col) {
		super(town, row, col);
	}
	
	/**
	 * Gets the identity of the cell. (OUTAGE)
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		// TODO Auto-generated method stub // DONE
		return State.OUTAGE;
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
		
		// Rule 4. Outage becomes Empty cell
		return new Empty(tNew, row, col);
	}

}
