package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmptyTest {
	
	private Town town;
	private TownCell empty;

	@BeforeEach
	void setUp() throws Exception {
		town = new Town(1,3);
		
		empty = new Empty(town, 0, 1);
		town.grid[0][1] = empty;
		
		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][2] = new Outage(town, 0, 2);
	}

	@Test
	void testWho() {
		assertEquals(State.EMPTY, empty.who());
	}

	@Test
	void testNext() {
		//Testing with the existing outage cells 
		TownCell nextState = empty.next(town);
		
		assertTrue(nextState instanceof Casual);
	}

	@Test
	void testEmpty() {
		assertNotNull(town);
	}

}
