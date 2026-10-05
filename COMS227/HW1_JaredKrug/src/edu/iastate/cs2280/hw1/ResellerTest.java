package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResellerTest {
	
	private Town town;
	private TownCell reseller;

	@BeforeEach
	void setUp() throws Exception {
		town = new Town(2,3);
		
		reseller = new Reseller(town, 0, 1);
		town.grid[0][1] = reseller;
		
		town.grid[0][0] = new Casual(town, 0, 0);
		town.grid[0][2] = new Casual(town, 0, 2);
		town.grid[1][0] = new Casual(town, 1, 0);
		town.grid[1][1] = new Casual(town, 1, 1);
		town.grid[1][2] = new Empty(town, 1, 2);
	}

	@Test
	void testWho() {
		assertEquals(State.RESELLER, reseller.who());
	}

	@Test
	void testNext() {
		TownCell nextState = reseller.next(town);
		
		assertTrue(nextState instanceof Reseller);
	}

	@Test
	void testReseller() {
		assertNotNull(town);
	}

}
