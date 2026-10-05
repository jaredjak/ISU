package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OutageTest {
	
	private Town town;
	private TownCell outage;

	@BeforeEach
	void setUp() throws Exception {
		town = new Town(1,3);
		
		outage = new Outage(town, 0, 1);
		town.grid[0][1] = outage;
		
		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][2] = new Outage(town, 0, 2);
	}

	@Test
	void testWho() {
		assertEquals(State.OUTAGE, outage.who());
	}

	@Test
	void testNext() {
		TownCell nextState = outage.next(town);
		
		assertTrue(nextState instanceof Empty);
	}

	@Test
	void testOutage() {
		assertNotNull(town);
	}

}
