package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ISPBusinessTest {

	@Test
	void testUpdatePlain() {
		Town tOld = new Town(1,1);
		tOld.grid[0][0] = new Casual(tOld, 0, 0);
		
		Town tNew = ISPBusiness.updatePlain(tOld);
		
		assertTrue(tNew.grid[0][0] instanceof Reseller);
		assertEquals(1, tNew.getLength());
		assertEquals(1, tNew.getWidth());
	}

	@Test
	void testGetProfit() {
		Town town = new Town(1, 3);
		town.grid[0][0] = new Casual(town, 0, 0);
		town.grid[0][1] = new Empty(town, 0, 1);
		town.grid[0][2] = new Casual(town, 0, 2);
		
		int profit = ISPBusiness.getProfit(town);
		
		assertEquals(2, profit);
	}

	// Doesn't need a test I think
//	@Test
//	void testMain() {
//		fail("Not yet implemented");
//	}

}
