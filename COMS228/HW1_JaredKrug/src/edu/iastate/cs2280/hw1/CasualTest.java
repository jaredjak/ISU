package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CasualTest {
	
	private Town town;
	private TownCell casual;

	@BeforeEach
	void setUp() throws Exception {
		town = new Town(1, 2);
		casual = new Casual(town, 0, 0);
		town.grid[0][0] = casual;
	}

	@Test
	void testWho() {
		assertEquals(State.CASUAL, casual.who());
	}

	@Test
	void testNext() {
		// Testing it with a Reseller cell
		town.grid[0][1] = new Reseller(town, 0, 1);
		
		TownCell nextState = casual.next(town);
		
		assertTrue(nextState instanceof Reseller);
	}

	// Was not sure how to test the constructor any other way in this situation.
	@Test
	void testCasual() {
		assertNotNull(town);
	}

}
