package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TownCellTest {
	
	private Town town;
	private TownCell empty;
	private TownCell casual;
	private TownCell reseller;

	@BeforeEach
	void setUp() throws Exception {
		town = new Town(1,3);
		
		empty = new Empty(town, 0, 0);
		casual = new Casual(town, 0, 1);
		reseller = new Reseller(town, 0, 2);
		
		town.grid[0][0] = empty;
		town.grid[0][1] = casual;
		town.grid[0][2] = reseller;
	}

	@Test
	void testTownCell() {
		assertNotNull(town);
	}

	@Test
	void testCensus() {
		int[] censusCount = new int[5];
		
		// this will check the neighborhood around the cell at (0,1) aka the casual cell
		town.grid[0][1].census(censusCount);
		
		assertEquals(1, censusCount[TownCell.EMPTY]);
		assertEquals(1, censusCount[TownCell.RESELLER]);
		assertEquals(0, censusCount[TownCell.CASUAL]);
		assertEquals(0, censusCount[TownCell.OUTAGE]);
		assertEquals(0, censusCount[TownCell.STREAMER]);
		
		
	}

	@Test
	void testWho() {
		assertEquals(State.EMPTY, empty.who());
		assertEquals(State.CASUAL, casual.who());
		assertEquals(State.RESELLER, reseller.who());
	}

	@Test
	void testNext() {
		TownCell nextState = empty.next(town);
		assertTrue(nextState instanceof Reseller);
		
		TownCell nextState2 = casual.next(town);
		assertTrue(nextState2 instanceof Reseller);
		
		TownCell nextState3 = reseller.next(town);
		assertTrue(nextState3 instanceof Empty);
	}

}
