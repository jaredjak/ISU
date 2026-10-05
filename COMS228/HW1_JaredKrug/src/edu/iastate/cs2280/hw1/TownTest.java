package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TownTest {
	
	Town town1;
	Town town2;

	@BeforeEach
	void setUp() throws Exception {
		town1 = new Town(2, 2);
		
		town2 = new Town("./src/TownTest.txt");
	}

	@Test
	void testTown1() {
		assertEquals(2, town1.getLength());
		assertEquals(2, town1.getWidth());
		assertNotNull(town1);
	}

	@Test
	void testTown2() {
		assertEquals(2, town2.getLength());
		assertEquals(2, town2.getWidth());
		assertNotNull(town2);
	}

	@Test
	void testGetWidth() {
		assertEquals(2, town1.getWidth());
		
		assertEquals(2, town2.getWidth());
	}

	@Test
	void testGetLength() {
		assertEquals(2, town1.getLength());
		
		assertEquals(2, town2.getLength());
	}

	@Test
	void testRandomInit() {
		town1.randomInit(1020302);
		
		int count = 0;
		// Checking to make sure all cells have been populated
		for (int i = 0; i < town1.getLength(); i++) {
			for (int j = 0; j < town1.getWidth(); j++) {
				if (town1.grid[i][j] != null) {
					count++;
				}
			}
		}
		
		assertEquals(4, count);
		
	}

	@Test
	void testToString() {
		String grid = town2.toString();
		
		String[] cells = grid.split("\n");
		
//		for (int i = 0; i < cells.length; i++) {
//			System.out.println(cells[i]);
//		}
		
		assertEquals(2, cells.length);
		assertEquals(2, cells[0].split(" ").length);
	}

}
