package edu.iastate.cs2280.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreamerTest {
	
	private Town town;
	private TownCell streamer;

	@BeforeEach
	void setUp() throws Exception {
		town = new Town(1,3);
		
		streamer = new Streamer(town, 0, 1);
		town.grid[0][1] = streamer;
		
		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][2] = new Outage(town, 0, 2);
	}

	@Test
	void testWho() {
		assertEquals(State.STREAMER, streamer.who());
	}

	@Test
	void testNext() {
		TownCell nextState = streamer.next(town);
		
		assertTrue(nextState instanceof Empty);
	}

	@Test
	void testStreamer() {
		assertNotNull(town);
	}

}
